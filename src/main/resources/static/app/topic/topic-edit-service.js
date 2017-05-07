var TopicEditService = function ($rootScope, $http, $q, $routeParams, hotkeys, FileUploader) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;
    this.FileUploader = FileUploader;

    this.topic = undefined;
    this.topicSkeleton = undefined;
    this.topicCompositionEditor;

    //this.expressionsPageContainer = new PageContainer(30);
    this.expressionsPageSize = 20;
    this.expressionsDataTable = new DataTable([], this.expressionsPageSize);

    this.init();
    ExpressionService.call(this);
};
inherit(ExpressionService, TopicEditService);
TopicEditService.prototype.init = function () {
    var self = this;
    var topicId = self.$routeParams.topicId;
    self.initData(topicId);
};
//Load more: http://www.developphp.com/video/JavaScript/Scroll-Load-Dynamic-Content-When-User-Reach-Bottom-Ajax

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
        self.expressionsDataTable.setData(self.topic.expressions);
        self.topicSkeleton = arrayOfResults[1].data;
        self.topicCompositionEditor = new CompositeEditor(self.topicSkeleton, self.topic, {
            'expressions': {
                fnIsItemEmpty: function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
                , fnChangeItemCallback: function (item) {
                    self.lookUpExpression(item);
                }
                //, function (item, childPaths) {
                //    if (!hasValue(item.id)) return;
                //    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                //    var superId = superProperty.propertyValue.id;
                //    self.$http({
                //        url: contextPath + "/api/topics/" + superId + "/expressions/" + item.id,
                //        method: 'DELETE',
                //        headers: {"Content-Type": "application/json;charset=utf-8"}
                //    }).then(function () {
                //    })
                //}
            }
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
                //, function (item, childPaths) {
                //    if (!hasValue(item.id)) return;
                //    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                //    var superId = superProperty.propertyValue.id;
                //    self.$http({
                //        url: contextPath + "/api/expressions/" + superId + "/senseGroups/" + item.id,
                //        method: 'DELETE',
                //        headers: {"Content-Type": "application/json;charset=utf-8"}
                //    }).then(function () {
                //    })
                //}
            )
            , 'senses': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || (isBlank(item.explanation) && isBlank(item.shortExplanation));
                }
                //, function (item, childPaths) {
                //    if (!hasValue(item.id)) return;
                //    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                //    var superId = superProperty.propertyValue.id;
                //    self.$http({
                //        url: contextPath + "/api/senseGroups/" + superId + "/senses/" + item.id,
                //        method: 'DELETE',
                //        headers: {"Content-Type": "application/json;charset=utf-8"}
                //    }).then(function () {
                //    })
                //}
            )
            , 'photos': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.fileItemId);
                }
                //Remove Item
                //, function (item, childPaths) {
                //    if (!hasValue(item.id)) return;
                //    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                //    var superId = superProperty.propertyValue.id;
                //    self.$http({
                //        url: contextPath + "/api/senses/" + superId + "/photos/" + item.id + "/detach",
                //        method: 'DELETE',
                //        headers: {"Content-Type": "application/json;charset=utf-8"}
                //    }).then(function () {
                //    })
                //}
            )
            , 'videos': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || (isBlank(item.fileItemId) && isBlank(item.externalUrl));
                }
            )
            , 'examples': new InjectedFunction(
                function (item) {
                    return !hasValue(item) || isBlank(item.text);
                }
                //Remove Item
                //, function (item, childPaths) {
                //    if (!hasValue(item.id)) return;
                //    var superProperty = $r.findSuperPropertyFromChildPaths(self.topic, childPaths, 2);
                //    var superId = superProperty.propertyValue.id;
                //    self.$http({
                //        url: contextPath + "/api/senses/" + superId + "/examples/" + item.id,
                //        method: 'DELETE',
                //        headers: {"Content-Type": "application/json;charset=utf-8"}
                //    }).then(function () {
                //    })
                //}
            )
        });
        self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
        //if (isEmpty(self.topic.id)) {
        //    self.saveTopic();
        //} else {
        //    self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
        //}

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
/**
 * @deprecated
 * @param callback
 */
TopicEditService.prototype.saveTopic = function (callback) {
    var self = this;
    self.topic.isSaving = true;
    self.topicCompositionEditor.cleanRecursiveRoot();
    self.topicCompositionEditor.root.expressions.sortByField('text', 1);
    self.$http.post(contextPath + '/api/topic-composites', self.topic).then(
        function (successResponse) {
            //Update id of topic and composites fields.
            self.topic = successResponse.data;
            self.expressionsDataTable.setData(self.topic.expressions);
            self.topic.isSaving = undefined;
            self.editingExpression = undefined;
            self.topicCompositionEditor.root = self.topic;
            self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
            if (callback) callback.call(self, self.topic, self.topicCompositionEditor);
        }
    );
};
//TODO need to handle the unsaved expression -> need to handle original data and updated data
TopicEditService.prototype.saveTopicOnly = function (callback) {
    var self = this;
    self.topic.isSaving = true;
    var expressions = self.topicCompositionEditor.root.expressions;
    self.recalculateExpressionIdsForTopic(self.topic);
    self.$http.post(contextPath + '/api/topics', self.topic).then(
        function (successResponse) {
            //Update id of topic and composites fields.
            var savedTopic = successResponse.data;
            //copy properties only, don't replace the whole object because the result is the topic only, not topic-composite.
            $r.copyMissingProperties(savedTopic, self.topic);
            self.expressionsDataTable.setData(self.topic.expressions);
            self.topic.isSaving = undefined;
            self.editingExpression = undefined;
            self.topicCompositionEditor.root = self.topic;
            self.topicCompositionEditor.addEmptyChildIfNecessary('expressions');
            if (callback) callback.call(self, self.topic, self.topicCompositionEditor);
        }
    );
};
TopicEditService.prototype.recalculateExpressionIdsForTopic = function (topic) {
    var expressionIds = getArrayByFields(topic.expressions, "id");
    var notBlankExpressionIds = expressionIds.toArrayNotBlank();
    topic.expressionIds = notBlankExpressionIds;
}
TopicEditService.prototype.saveExpressionInTopic = function (expression, callback) {
    var self = this;
    var isNewExpression = isBlank(expression.id);
    self.topic.isSaving = true;
    self.topicCompositionEditor.cleanRecursiveItem(expression);
    self.saveExpression(expression, function () {
        if (isNewExpression) {
            //Need to recalculate ExpressionIds because the id of saved expression has just generated.
            self.recalculateExpressionIdsForTopic(self.topic);
        }
        self.topicCompositionEditor.copyMissingSkeleton(expression);
        self.saveTopicOnly(callback);
    });
};
TopicEditService.prototype.saveOriginalExpressionText = function (expression) {
    expression.originalText = expression.text;
};
TopicEditService.prototype.lookUpExpression = function (expression, callback) {
    var self = this;
    var expressionText = expression.text;
    if (expression.text == expression.originalText) {
        console.log("Same as original text, nothing change");
        return;
    } else {
        console.log("Different from the original text, will update it: '" + expression.text + "' vs. '" + expression.originalText + "'");
        self.saveOriginalExpressionText(expression);
    }

    var comparator = new ComparatorByFields([new FieldSort('text', 1)]);
    if ($list.hasDuplication(self.topic.expressions, expression, comparator)) {
        expression.errorMessage = "The expression '" + expressionText + "' was already used in this topic.";
        //setTimeout(function () {
        //    expression.errorMessage = undefined;
        //}, 5000);
        return;
    }
    expression.errorMessage = undefined;
    if (isBlank(expressionText)) {
        var skeletonExpressions = self.topicCompositionEditor.getSkeletonByPropertyName('expressions');
        var skeletonExpression = skeletonExpressions[0];
        $r.copyProperties(angular.copy(skeletonExpression), expression);
    } else {
        self.$http.get(contextPath + '/api/expressions/detail/lookup?text=' + expressionText).then(function (successRespond) {
            var lookupExpression = successRespond.data;
            if (hasValue(lookupExpression)) {
                $r.copyProperties(lookupExpression, expression);
                self.topicCompositionEditor.copyMissingSkeleton(expression);
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
    $scope.service = topicEditService;
    topicEditService.init();
}]);

