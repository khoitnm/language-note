var LessonEditService = function ($rootScope, $http, $q, $routeParams, hotkeys) {
    this.$rootScope = $rootScope;
    this.$http = $http;
    this.$q = $q;
    this.$routeParams = $routeParams;

    this.lessonInit = undefined;
    this.topicInit = undefined;
    this.expressionItemInit = undefined;
    this.meaningInit = undefined;
    this.wordInit = undefined;

    this.isShowExpression = true;
    this.isShowMeaning = true;

    this.lesson = {};
    this.lesson.defaultExpressionType = "word";


    this.wordTypes = [
        new WordType("n", "NOUN"),
        new WordType("v", "VERB"),
        new WordType("adj", "ADJ"),
        new WordType("adv", "ADV"),
        new WordType("prep", "PREPOSITION")
    ];
    this.lessons = [];
    this.topics = [];
    this.topicNames = [];
    this.menu = new AngularDropDowns(this.lessons);

    this.sourceLanguage = "en";
    this.destLanguage = "vi";

    // Editor options.
    this.editingNote = false;
    var self = this;
    this.ckeditor = {
        options: {
            language: 'en',
            allowedContent: true,
            entities: false
        },
        // Called when the editor is completely ready.
        onReady: function () {
            self.editingNote = false;
        }
    };

};
LessonEditService.prototype.init = function () {
    var self = this;
    var lessonInitGet = self.$http.get(contextPath + '/api/lessons/initiation');
    var topicInitGet = self.$http.get(contextPath + '/api/topics/initiation');
    var expressionItemInitGet = self.$http.get(contextPath + '/api/expression-items/initiation?type=' + self.lesson.defaultExpressionType);
    var meaningInitGet = self.$http.get(contextPath + '/api/expression-items/meanings/initiation');
    var lessonsGet = self.$http.get(contextPath + '/api/lessons/introductions');
    var topicsGet = self.$http.get(contextPath + '/api/topics');

    self.$q.all([lessonInitGet, topicInitGet, expressionItemInitGet, meaningInitGet, lessonsGet, topicsGet]).then(function (arrayOfResults) {
        self.lessonInit = arrayOfResults[0].data;
        self.topicInit = arrayOfResults[1].data;
        self.setExpressionItemInit(arrayOfResults[2].data);
        self.meaningInit = arrayOfResults[3].data;
        self.lessons = arrayOfResults[4].data;
        self.topics = arrayOfResults[5].data;
        self.topicNames = getArrayByFields(self.topics, "name");

        self.constructLessonsMenu(self.lessons);

        var lessonId = self.$routeParams.lessonId;
        if (!hasValue(lessonId)) {
            self.constructNewLesson();
        } else {
            self.$http.get(contextPath + "/api/lessons/" + lessonId).then(function (successResponse) {
                self.setLesson(successResponse.data);
            });
        }
    });
};
LessonEditService.prototype.constructNewLesson = function () {
    var self = this;
    self.setLesson(angular.copy(self.lessonInit));
    self.lesson.expressionItems = [self.constructNewExpressionItem()];
};
LessonEditService.prototype.setLesson = function (lesson) {
    var self = this;
    self.lesson = lesson;
    if (!hasValue(lesson.expressionItems) || lesson.expressionItems.length == 0) {
        lesson.expressionItems = [self.constructNewExpressionItem()];
    }
};
LessonEditService.prototype.constructNewExpressionItem = function () {
    return angular.copy(this.expressionItemInit);
};
LessonEditService.prototype.constructLessonsMenu = function (lessons) {
    var self = this;
    var items = [];
    for (var i = 0; i < lessons.length; i++) {
        var lesson = lessons[i];
        var item = new AngularDropDownsItem(lesson.name, "#!/expression-item-edit?lessonId=" + lesson.id);
        items.push(item);
    }
    self.menu = new AngularDropDowns(items);
};
LessonEditService.prototype.onOffExpression = function () {
    this.isShowExpression = !this.isShowExpression;
};
LessonEditService.prototype.onOffMeaning = function () {
    this.isShowMeaning = !this.isShowMeaning;
};
LessonEditService.prototype.addTopic = function () {
    var self = this;
    self.lesson.topics.push(angular.copy(self.topicInit));
};
LessonEditService.prototype.addExpressionItem = function () {
    var self = this;
    self.lesson.expressionItems.push(self.constructNewExpressionItem());
};
LessonEditService.prototype.addExpressionItemIfNecessary = function (expressionItem) {
    var self = this;
    var expression = expressionItem.expression;
    if (hasValue(expression) || self.hasPhrasalVerbWords(expressionItem)) {
        //If changing data of the last item, then add new blank item.
        var index = self.lesson.expressionItems.indexOf(expressionItem);
        if (index == self.lesson.expressionItems.length - 1) {
            self.addExpressionItem();
        }
    }
};
LessonEditService.prototype.addExpressionItemIfNecessaryForLesson = function (lesson) {
    var self = this;
    var expression = null;
    var expressions = lesson.expressionItems;
    if (expressions.length > 0) {
        expression = expressions[expressions.length - 1];
    }
    if (!hasValue(expression)) {
        self.addExpressionItem();
    } else {
        self.addExpressionItemIfNecessary(expression);
    }
};
LessonEditService.prototype.selectExpressionItemHeadword = function (expressionItem) {
    var self = this;
    self.focusExpressionItem(expressionItem, function () {
        if (isNotBlank(expressionItem.expression)) {
            self.playSound(expressionItem);
        }
    });
};
LessonEditService.prototype.selectExpressionItemMeaning = function ($event, expressionItem, meaning) {
    var self = this;
    self.changeExpressionMeaning($event, expressionItem, meaning, function () {
        self.playSound(expressionItem);
    });
};
LessonEditService.prototype.translateExpressionItem = function (expressionItem, callback) {
    var self = this;
    var meaning = expressionItem.meanings[0];
    if (isNotBlank(expressionItem.expression) && isBlank(meaning.explanation)) {
        if (!hasValue(expressionItem.id)) {
            self.$http.get(contextPath + "/api/expression-items/search?expression=" + expressionItem.expression).then(function (successResponse) {
                var existingExpressionItem = successResponse.data;
                if (hasValue(existingExpressionItem) && isNotBlank(existingExpressionItem)) {
                    copyProperties(existingExpressionItem, expressionItem);
                } else {
                    self.lookUpInDictionary(expressionItem, callback);
                }
            });
        } else {
            self.lookUpInDictionary(expressionItem, callback);
        }
    } else {
        if (callback) callback.call(self);
    }
};
LessonEditService.prototype.lookUpInDictionary = function (expressionItem, callback) {
    var self = this;
    var translation = new Translation(self.sourceLanguage, self.destLanguage, expressionItem.expression);
    self.$http.post(contextPath + "/api/dictionary/words/meanings", translation).then(function (successResponse) {
        //have to recheck empty again to avoid that the user input something when ajax request was called.
        var dictionaryMeanings = successResponse.data;
        if (hasValue(dictionaryMeanings) && dictionaryMeanings.length > 0) {
            self.cleanMeanings(expressionItem.meanings);
            for (var i = 0; i < dictionaryMeanings.length; i++) {
                var dictionaryMeaning = dictionaryMeanings[i];
                expressionItem.meanings.push(dictionaryMeaning);
            }
        }
        if (callback) callback.call(self);
    });
};
LessonEditService.prototype.changePhrasalVerb = function (expressionItem) {
    var self = this;
    self.mergePhrasalVerbToExpression(expressionItem);
    self.addExpressionItemIfNecessary(expressionItem);
};
LessonEditService.prototype.mergePhrasalVerbToExpression = function (expressionItem) {
    var words = expressionItem.phrasalVerbDetail.words;
    var expression = words.mergeNotBlankValuesToString(" ", "value");
    expressionItem.expression = expression;
};
LessonEditService.prototype.startEditingNote = function () {
    this.editingNote = true;
};
LessonEditService.prototype.stopEditingNote = function () {
    this.editingNote = false;
};

LessonEditService.prototype.favourite = function (expressionItem) {
    var self = this;
    var userPoint = expressionItem.userPoints[USER_ID];
    if (!hasValue(userPoint)) {
        userPoint = {};
        expressionItem.userPoints[USER_ID] = userPoint;
    }
    userPoint.favourite = (userPoint.favourite == -1) ? 0 : -1;
    var favouriteUpdateRequest = {
        expressionId: expressionItem.id
        , favourite: userPoint.favourite
    };
    self.$http.post(contextPath + "/api/expression-items/favourite", favouriteUpdateRequest).then(function (successResponse) {
        var updatedRowsCount = successResponse.data;
        if (updatedRowsCount <= 0) {
            console.log("Something wrong, there's no expression favourite is updated: " + updatedRowsCount);
        }
    });
};
LessonEditService.prototype.addExpressionMeaning = function (expressionItem) {
    var self = this;
    var meaning = angular.copy(self.meaningInit);
    expressionItem.meanings.push(meaning);
};
LessonEditService.prototype.focusExpressionItem = function (expressionItem, callback) {
    var self = this;
    if (expressionItem.type == "phrasal verb") {
        var phrasalVerbDetail = expressionItem.phrasalVerbDetail;
        if (!hasValue(phrasalVerbDetail.words)) {
            phrasalVerbDetail.words = [];
        }
        while (phrasalVerbDetail.words.length < 3) {
            phrasalVerbDetail.words.push(self.wordInit);
        }
    }
    if (expressionItem.meanings.length == 0) {
        self.addExpressionMeaning(expressionItem);
    }
    self.addExpressionItemIfNecessary(expressionItem);
    callback.call(self);
};
LessonEditService.prototype.addExpressionMeaningIfNecessary = function (expressionItem, meaning) {
    var self = this;
    if (hasValue(meaning.explanation)) {
        //If changing data of the last item, then add new blank item.
        var index = expressionItem.meanings.indexOf(meaning);
        if (index == expressionItem.meanings.length - 1) {
            self.addExpressionMeaning(expressionItem);
        }

        var examples = meaning.examples;
        if (examples.length == 0) {
            examples.push("");
        }
    }
};
LessonEditService.prototype.changeExpressionMeaning = function ($event, expressionItem, meaning, callback) {
    var self = this;
    var explanation = meaning.explanation;
    if (hasValue(explanation)) {
        meaning.explanationLinesLength = (explanation.match(/\r\n|\r|\n/g) || []).length + 1;
    }
    self.addExpressionMeaningQuestionIfNecessary(meaning);
    self.addExpressionMeaningIfNecessary(expressionItem, meaning);
    if (callback) callback.call(self);
};
LessonEditService.prototype.addExpressionMeaningQuestionIfNecessary = function (meaning) {
    var self = this;
    meaning.fillingQuestions = meaning.fillingQuestions || self.meaningInit.fillingQuestions;
    var lastQuestion = self.getLastExpressionMeaningQuestion(meaning);
    if (!hasValue(lastQuestion) || !self.isWordsEmpty(lastQuestion.words)) {
        meaning.fillingQuestions.push(self.meaningInit.fillingQuestions[0]);
    }
};
LessonEditService.prototype.isWordsEmpty = function (words) {
    if (!hasValue(words)) return true;
    for (var i = 0; i < words.length; i++) {
        var word = words[i];
        if (isBlank(word.value)) {
            return true;
        }
    }
    return false;
};
LessonEditService.prototype.getLastExpressionMeaningQuestion = function (meaning) {
    if (meaning.fillingQuestions.length == 0) return null;
    return meaning.fillingQuestions[meaning.fillingQuestions.length - 1];
};
LessonEditService.prototype.addMeaningExampleIfNecessary = function (meaning, example, $index) {
    var self = this;
    if (isNotBlank(example)) {
        //If changing data of the last item, then add new blank item.
        //var index = meaning.examples.indexOf(example);
        if ($index == meaning.examples.length - 1) {
            meaning.examples.push("");
        }
    }
};
LessonEditService.prototype.saveLesson = function () {
    var self = this;
    self.cleanLesson(self.lesson);
    self.$http.post(contextPath + '/api/lessons', self.lesson).then(
        function (successResponse) {
            self.setLesson(successResponse.data);
        }
    );
};
LessonEditService.prototype.selectWordType = function ($item) {
    var self = this;
    var meaning = this.$parent.meaning;
    var selectedWordType = meaning.wordTypeObject;
    if (hasValue(selectedWordType)) {
        meaning.wordType = selectedWordType.originalObject.value;
    } else {
        meaning.wordType = $item;
    }
    console.log(meaning.wordType);
};
LessonEditService.prototype.finishInputTopic = function (topic) {
    var self = this;
    topic.id = null;//this will become the new topic entity.
    if (!isBlank(topic.name)) {
        var existingTopic = self.topics.findItemByField("name", topic.name.trim().toLowerCase());
        if (hasValue(existingTopic)) {
            copyProperties(existingTopic, topic);//include copying id.
        }
    }
};
LessonEditService.prototype.removeLesson = function () {
    var self = this;
    if (isBlank(self.lesson.id)) {
        return;
    }
    var removeLessonRequest = {
        lessonId: self.lesson.id
        , includeExpressions: true
    };
    self.$http({
        url: contextPath + "/api/lesson",
        method: 'DELETE',
        data: removeLessonRequest,
        headers: {"Content-Type": "application/json;charset=utf-8"}
    }).then(function () {
        self.constructNewLesson();
    })
    ;
};

LessonEditService.prototype.cleanLesson = function (lesson) {
    var self = this;
    self.cleanTopics(lesson.topics);
    self.cleanExpressionItems(lesson.expressionItems);
};
LessonEditService.prototype.cleanTopics = function (topics) {
    if (!hasValue(topics)) {
        return;
    }
    for (var i = topics.length - 1; i >= 0; i--) {
        var topic = topics[i];
        if (isBlank(topic.name)) {
            topics.splice(i, 1);
        }
    }
};
LessonEditService.prototype.cleanExpressionItems = function (expressionItems) {
    var self = this;
    if (!hasValue(expressionItems)) {
        return;
    }
    expressionItems.sortByField("expression", 1);
    for (var i = expressionItems.length - 1; i >= 0; i--) {
        var expressionItem = expressionItems[i];
        self.cleanMeanings(expressionItem.meanings);
        if (self.isEmptyExpression(expressionItem)) {
            expressionItems.splice(i, 1);
        }
    }
};
LessonEditService.prototype.isEmptyExpression = function (expressionItem) {
    var isEmptyPhrasalVerb = !this.hasPhrasalVerbWords(expressionItem) && expressionItem.meanings.length == 0;
    var isEmptyExpression = isBlank(expressionItem.expression) && expressionItem.meanings.length == 0;
    return isEmptyPhrasalVerb && isEmptyExpression;
};
LessonEditService.prototype.hasPhrasalVerbWords = function (expressionItem) {
    if (expressionItem.type == "phrasal verb") {
        var isEmptyWords = true;
        if (!hasValue(expressionItem.phrasalVerbDetail)) return false;
        var words = expressionItem.phrasalVerbDetail.words;
        for (var i = 0; i < words.length; i++) {
            var word = words[i];
            if (!isBlank(word.value)) {
                isEmptyWords = false;
                break;
            }
        }
        return !isEmptyWords;
    } else {
        return false;
    }
};
LessonEditService.prototype.cleanMeanings = function (meanings) {
    var self = this;

    for (var i = meanings.length - 1; i >= 0; i--) {
        var meaning = meanings[i];
        self.cleanMeaningExamples(meaning.examples);
        if (isBlank(meaning.explanation) && meaning.examples.length == 0) {
            meanings.splice(i, 1);
        }
    }
};
LessonEditService.prototype.cleanMeaningExamples = function (examples) {
    var self = this;

    for (var i = examples.length - 1; i >= 0; i--) {
        var example = examples[i];
        if (isBlank(example)) {
            examples.splice(i, 1);
        }
    }
};
LessonEditService.prototype.playSound = function (expressionItem) {
    if (isNotBlank(expressionItem.expression)) {
        var audio = new Audio(contextPath + '/api/tts?text=' + expressionItem.expression);
        audio.play();
    }
};
LessonEditService.prototype.selectExpressionType = function () {
    var self = this;
    self.$http.get(contextPath + '/api/expression-items/initiation?type=' + self.lesson.defaultExpressionType).then(
        function (successResponse) {
            self.setExpressionItemInit(successResponse.data);
            self.cleanLesson(self.lesson);
            self.addExpressionItemIfNecessaryForLesson(self.lesson);
            var expressionItems = self.lesson.expressionItems;
            //TODO add expression if possible
            if (isBlank(self.lesson.id) && (!hasValue(expressionItems) || expressionItems.length == 0)) {
                self.lesson.expressionItems = [self.constructNewExpressionItem()];
            }
        }
    );
};
LessonEditService.prototype.setExpressionItemInit = function (expressionItem) {
    var self = this;
    self.expressionItemInit = expressionItem;
    if ("phrasal verb" == expressionItem.type) {
        var phrasalVerbDetail = expressionItem.phrasalVerbDetail;
        var words = phrasalVerbDetail.words;
        self.wordInit = words[0];
    }
};
var Translation = function (sourceLanguage, destLanguage, sourceText) {
    this.sourceLanguage = sourceLanguage;
    this.destLanguage = destLanguage;
    this.sourceText = sourceText;
};
var WordType = function (label, value) {
    this.label = label;
    this.value = value;
};

var lessonEditService = angularApp.service('lessonEditService', ['$rootScope', '$http', '$q', '$routeParams', LessonEditService]);
angularApp.controller('lessonEditController', ['$rootScope', '$scope', '$http', '$q', '$location', '$routeParams', 'lessonEditService', 'hotkeys', function ($rootScope, $scope, $http, $q, $location, $routeParams, lessonEditService, hotkeys) {
    $rootScope.isRunning = true;
    $scope.lessonEditService = lessonEditService;
    //lessonEditService.$rootScope = $rootScope;
    lessonEditService.init();

    $scope.USER_ID = USER_ID;
    //$locationProvider.html5Mode({rewriteLinks: false});

    hotkeys.bindTo($scope)
        .add({
            combo: ['ctrl+s', 'command+s'],
            description: 'Save lesson',
            callback: function (event) {
                //prevent default short-cut behaviours of Browser.
                event.preventDefault();
                lessonEditService.saveLesson();
            }
        })
        .add({
            combo: ['ctrl+t', 'command+t'],
            description: 'Test Lesson',
            callback: function (event) {
                event.preventDefault();
                $location.url("/expression-item-test?lessonId=" + lessonEditService.lesson.id);
            }
        })
        .add({
            combo: ['ctrl+n', 'command+n'],
            description: 'New Lesson',
            callback: function (event) {
                event.preventDefault();
                lessonEditService.constructNewLesson();
            }
        })
    ;


}]);

