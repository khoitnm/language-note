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
                })
            , 'senseGroups': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.lexicalType);
                }
            )
            , 'senses': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || (isBlank(item.explanation) && isBlank(item.shortExplanation));
                }
            )
            , 'examples': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
            )
        });
    });
};
TopicEditService.prototype.initUploader = function () {
    var self = this;
    self.uploader = new self.FileUploader({
        url: self.$rootScope.contextPath + '/api/files'
    });
    self.uploader.onBeforeUploadItem = function (item) {
        item.sense = this.sense;
        console.info('onBeforeUploadItem');
    };
    self.uploader.onSuccessItem = function (fileUploadItem, response, status, headers) {
        var sense = fileUploadItem.sense;
        fileUploadItem.sense = undefined;
        sense.photos = sense.photos || [];
        //TODO should map fileItem to digitalAsset item
        var savedFileItem = response;
        var digitalAssetSkeleton = self.topicSkeleton.expressions[0].senseGroups[0].senses[0].photos[0];
        var digitalAsset = angular.copy(digitalAssetSkeleton);
        digitalAsset.fileItemId = savedFileItem.id;
        sense.photos.push(digitalAsset);
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
    expression.isEditing = !expression.isEditing || true;
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

//Use AngularFileUpload
angularApp.service('topicEditService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', 'FileUploader', TopicEditService]);
angularApp.controller('topicEditController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'topicEditService', 'hotkeys', 'FileUploader', function ($rootScope, $scope, $http, $q, $location, $routeParams, topicEditService, hotkeys, FileUploader) {
    $scope.topicEditService = topicEditService;
}]);


////angularApp.service('topicEditService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', TopicEditService]);
////angularApp.controller('topicEditController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'topicEditService', 'hotkeys', function ($rootScope, $scope, $http, $q, $location, $routeParams, topicEditService, hotkeys) {
////    $scope.topicEditService = topicEditService;
////}]);
//
//var CompositeManagement = function (skeletonRoot, root) {
//    this.skeletonRoot = skeletonRoot;
//    this.root = root;
//};
//CompositeManagement.prototype.removeItem = function (type, list, item) {
//    list.remove(item);
//    if (!this.hasEmptyItem(type, list)) {
//        var skeleton = this.getSkeleton(type);
//        list.add(skeleton);
//    }
//};
//CompositeManagement.prototype.hasEmptyItem = function (type, list) {
//    for (var i = 0; i < list.length; i++) {
//        var item = list[i];
//        if (this.isEmptyItem(type, item)) {
//            return true;
//        }
//    }
//    return false;
//};
//CompositeManagement.prototype.isEmptyItem = function (type, item) {
//    if (type == 'examples') {
//        return (!hasValue(item) || !isBlank(item.text));
//    }
//};
//CompositeManagement.prototype.getSkeleton = function (type) {
//    if (type == 'examples') {
//        return this.skeletonRoot.senseGroups[0].sense[0].examples[0];
//    }
//};