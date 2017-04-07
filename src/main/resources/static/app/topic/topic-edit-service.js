var TopicEditService = function ($rootScope, $http, $q, $routeParams, hotkeys, FileUploader) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.FileUploader = FileUploader;

    this.topic = undefined;
    this.topicSkeleton = undefined;
    this.topicCompositionEditor;
    this.topicComposite;
    this.init();
};

TopicEditService.prototype.init = function () {
    var self = this;
    var topicId = self.$routeParams.topicId;
    self.initUploader();
    var topicSkeletonGet = self.$http.get(contextPath + '/api/topics/construct');
    var topicDetailGet;
    if (hasValue(topicId)) {
        topicDetailGet = self.$http.get(contextPath + '/api/topics/' + topicId + '/detail');
    } else {
        topicDetailGet = topicSkeletonGet;
    }
    self.$q.all([topicDetailGet, topicSkeletonGet]).then(function (arrayOfResults) {
        self.topic = arrayOfResults[0].data;
        self.topicSkeleton = arrayOfResults[1].data;
        self.topicCompositionEditor = new CompositeEditor(self.topicSkeleton, self.topic, {
            'expressions': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
                , function (item) {
                    self.$http({
                        url: contextPath + "/api/topics/" + topic.id + "/expressions/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
            , 'senseGroups': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.lexicalType);
                }
                , function (item) {
                    var superProperty = $r.findSuperPropertyFromRoot(self.topic, item, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/expressions/" + superId + "/senseGroups/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
            , 'senses': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || (isBlank(item.explanation) && isBlank(item.shortExplanation));
                }
                , function (item) {
                    var superProperty = $r.findSuperPropertyFromRoot(self.topic, item, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/senseGroups/" + superId + "/senses/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
            , 'examples': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
                , function (item) {
                    var superProperty = $r.findSuperPropertyFromRoot(self.topic, item, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/senses/" + superId + "/examples/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
        });
    });
};
TopicEditService.prototype.initUploader = function () {
    var self = this;
    self.uploader = new self.FileUploader({
        url: self.$rootScope.contextPath + '/api/files'
        , autoUpload: true
    });
    self.uploader.onSuccessItem = function (fileUploadItem, response, status, headers) {
        var sense = fileUploadItem.sense;
        fileUploadItem.sense = undefined;
        sense.photos = sense.photos || [];
        //TODO should map fileItem to digitalAsset item
        var savedFileItem = response;
        var digitalAssetSkeleton = self.topicSkeleton.expressions[0].senseGroups[0].senses[0].photos[0];
        var digitalAsset = angular.copy(digitalAssetSkeleton);
        digitalAsset.fileItemId = savedFileItem.id;
        sense.photos.unshift(digitalAsset);
        console.info('onSuccessItem', fileUploadItem, response, status, headers);
    };
    self.uploader.onCompleteAll = function () {
        var queue = this.queue;
        for (var i = 0; i < queue.length; i++) {
            var item = queue[i];
            item.sense = undefined;
        }
        this.clearQueue();
        console.info('onCompleteAll');
    };
};
TopicEditService.prototype.modeEdit = function (expression) {
    if (this.editingExpression == expression) {
        this.editingExpression = undefined;
    } else {
        this.editingExpression = expression;
    }
    //
    //if (hasValue(expression.isEditing)) {
    //    expression.isEditing = !expression.isEditing;
    //} else {
    //    expression.isEditing = true;
    //}
    //
};
TopicEditService.prototype.removeTopic = function (item) {
    var self = this;
    self.topicEdit.remove(item);
    var removeRequest = {
        id: item.id
        , removeCompositions: false
    };
    self.$http({
        url: contextPath + "/api/topicEdit",
        method: 'DELETE',
        data: removeRequest,
        headers: {"Content-Type": "application/json;charset=utf-8"}
    });
};
TopicEditService.prototype.rename = function (item) {
    var self = this;
    self.$http.put(contextPath + '/api/topicEdit/name', item).then(
        function (successResponse) {
            //self.setLesson(successResponse.data);
        }
    );
};
TopicEditService.prototype.startUploadImages = function (sense) {
    var self = this;
    if (sense == self.uploader.sense) {
        self.uploader.sense = undefined;
    } else {
        self.uploader.sense = sense;
        self.uploader.clearQueue();
    }
};
TopicEditService.prototype.selectMainPhoto = function (photos, photo) {
    photos.remove(photo);
    photos.unshift(photo);
};
TopicEditService.prototype.saveTopic = function () {
    var self = this;
    self.$http.post(contextPath + '/api/topics', self.topic).then(
        function (successResponse) {
            self.topic = successResponse.data;
        }
    );
};
//Use AngularFileUpload
angularApp.service('topicEditService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', 'FileUploader', TopicEditService]);
angularApp.controller('topicEditController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'topicEditService', 'hotkeys', 'FileUploader', function ($rootScope, $scope, $http, $q, $location, $routeParams, topicEditService, hotkeys, FileUploader) {
    $scope.topicEditService = topicEditService;
}]);

