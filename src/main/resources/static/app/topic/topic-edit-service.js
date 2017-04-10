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
    CommonService.call(this);
};
inherit(CommonService, TopicEditService);
TopicEditService.prototype.init = function () {
    var self = this;
    var topicId = self.$routeParams.topicId;
    self.initData(topicId);
};
TopicEditService.prototype.initData = function (topicId) {
    var self = this;
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
        if (isBlank(topicId)) {
            self.topic = angular.copy(self.topic);//Otherwise, the topic and topicSkeleton will reference to the same memory (because of AngularJS cache?)
        }
        self.topicSkeleton = arrayOfResults[1].data;
        self.topicCompositionEditor = new CompositeEditor(self.topicSkeleton, self.topic, {
            'expressions': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
                , function (item, childPaths) {
                    if (!hasValue(item.id)) return;
                    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/topics/" + superId + "/expressions/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
            , 'lexicalEntries': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
            )
            , 'family': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
            )
            , 'synonyms': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
            )
            , 'antonyms': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
            )
            , 'senseGroups': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.lexicalType);
                }
                , function (item, childPaths) {
                    if (!hasValue(item.id)) return;
                    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
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
                , function (item, childPaths) {
                    if (!hasValue(item.id)) return;
                    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/senseGroups/" + superId + "/senses/" + item.id,
                        method: 'DELETE',
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }).then(function () {
                    })
                }
            )
            , 'photos': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.fileItemId);
                }
                //Remove Item
                , function (item, childPaths) {
                    if (!hasValue(item.id)) return;
                    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                    var superId = superProperty.propertyValue.id;
                    self.$http({
                        url: contextPath + "/api/senses/" + superId + "/photos/" + item.id + "/detach",
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
                //Remove Item
                , function (item, childPaths) {
                    if (!hasValue(item.id)) return;
                    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
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
        if (isEmpty(self.topic.id)) {
            self.saveTopic();
        } else {
            self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
        }

    });
};
TopicEditService.prototype.constructNewTopic = function () {
    this.initData();
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
        var savedFileItem = response;
        var digitalAssetSkeleton = self.topicSkeleton.expressions[0].senseGroups[0].senses[0].photos[0];
        var digitalAsset = angular.copy(digitalAssetSkeleton);
        digitalAsset.fileItemId = savedFileItem.id;
        if (sense.photos.length > 0) {
            var lastPhoto = sense.photos[sense.photos.length - 1];
            if (!hasValue(lastPhoto.fileItemId)) {
                sense.photos.remove(lastPhoto);
            }
        }
        sense.photos.push(digitalAsset);
        if (!hasValue(sense.mainPhoto) || isEmpty(sense.mainPhoto.fileItemId)) {
            sense.mainPhoto = digitalAsset;
        }
    };
    self.uploader.onCompleteAll = function () {
        var queue = this.queue;
        for (var i = 0; i < queue.length; i++) {
            var item = queue[i];
            item.sense = undefined;
        }
        this.clearQueue();
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
TopicEditService.prototype.selectMainPhoto = function (sense, photo) {
    sense.mainPhoto = photo;
    //photos.remove(photo);
    //photos.unshift(photo);
};
TopicEditService.prototype.cleanTopic = function () {
    this.topicCompositionEditor.cleanRecursiveRoot();
};
TopicEditService.prototype.saveTopic = function (callback) {
    var self = this;
    self.topic.isSaving = true;
    self.topicCompositionEditor.cleanRecursiveRoot();
    self.$http.post(contextPath + '/api/topic-composites', self.topic).then(
        function (successResponse) {
            //Update id of topic and composites fields.
            self.topic = successResponse.data;
            self.editingExpression = undefined;
            self.topicCompositionEditor.root = self.topic;
            self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
            if (callback) callback.call(self, self.topic, self.topicCompositionEditor);
        }
    );
};
TopicEditService.prototype.lookUpExpression = function (expression) {
    var self = this;
    var expressionText = expression.text;
    if (isBlank(expressionText)) {
        var skeletonExpressions = self.topicCompositionEditor.getSkeletonByPropertyName('expressions');
        var skeletonExpression = skeletonExpressions[0];
        $r.copyProperties(angular.copy(skeletonExpression), expression);
    } else {
        self.$http.get(contextPath + '/api/expressions/detail/lookup?text=' + expressionText).then(function (successRespond) {
            var lookupExpression = successRespond.data;
            if (hasValue(lookupExpression)) {
                $r.copyProperties(lookupExpression, expression);
            }
        });
    }
};
TopicEditService.prototype.validateNotExistExpressionText = function (expression) {
    var self = this;
    var expressionText = expression.text;
    if (isBlank(expressionText)) {
        return;
    } else {
        self.$http.get(contextPath + '/api/expressions/brief/lookup?text=' + expressionText).then(function (successRespond) {
            var lookupExpression = successRespond.data;
            if (hasValue(lookupExpression)) {
                self.showErrorMessage("The text '" + expressionText + "' was used by another expression.")
            } else {
                self.showSuccessMessage(undefined);
            }
        });
    }
};
//Use AngularFileUpload
angularApp.service('topicEditService', ['$rootScope', '$http', '$q', '$routeParams', 'hotkeys', 'FileUploader', TopicEditService]);
angularApp.controller('topicEditController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'topicEditService', 'hotkeys', 'FileUploader', function ($rootScope, $scope, $http, $q, $location, $routeParams, topicEditService, hotkeys, FileUploader) {
    $scope.topicEditService = topicEditService;
}]);

