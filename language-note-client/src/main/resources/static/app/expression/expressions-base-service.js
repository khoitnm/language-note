var ExpressionBaseService = function () {
    CommonService.call(this);
    this.isPlayingSound = undefined;
    this.isForceStopSound = undefined;
    this.audio = undefined;
};
inherit(CommonService, ExpressionBaseService);
ExpressionBaseService.prototype.stopSound = function (expression) {
    this.isPlayingSound = undefined;
    this.isForceStopSound = true;
    this.audio.stop();
};
ExpressionBaseService.prototype.initUploader = function (digitalAssetSkeleton) {
    var self = this;
    self.uploader = new self.FileUploader({
        url: self.$rootScope.contextPathResourceServer + '/api/files'
        , autoUpload: true
    });
    self.uploader.onSuccessItem = function (fileUploadItem, response, status, headers) {
        var sense = fileUploadItem.sense;
        fileUploadItem.sense = undefined;
        sense.photos = sense.photos || [];
        var savedFileItem = response;
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
ExpressionBaseService.prototype.switchExpressionMode = function (expression) {
    var self = this;
    if (self.editingExpression == expression) {
        self.resetEditingExpression(expression);
        self.editingExpression = undefined;
    } else {
        self.editingExpression = expression;
        self.originalExpression = angular.copy(expression);
    }
};
ExpressionBaseService.prototype.resetEditingExpression = function (expression) {
    var self = this;
    var resetExpression = angular.copy(self.originalExpression);
    $r.replaceProperties(resetExpression, expression);
};
ExpressionBaseService.prototype.saveExpressionOnly = function (expression, callback) {
    var self = this;
    self.$http.post(contextPathResourceServer + '/api/expression-composites', expression).then(
        function (successResponse) {
            expression = $r.copyProperties(successResponse.data, expression);
            if (callback) callback.call(self, expression);
        }
    );
};
ExpressionBaseService.prototype.selectMainPhoto = function (sense, photo) {
    sense.mainPhoto = photo;
};
ExpressionBaseService.prototype.playSoundAutomatically = function (expression) {
    if (this.isForceStopSound) return;
    this.playSound(expression);
};
ExpressionBaseService.prototype.playSound = function (expression) {
    var self = this;
    if (isBlank(expression.text) || self.isPlayingSound == expression.text) return;
    self.isPlayingSound = expression.text;
    self.isForceStopSound = undefined;

    if (self.audio) self.audio.stop();

    var localeString = this.getExpressionLocaleString(expression);

    var texts = [];
    texts.push(expression.text);
    texts.push(expression.text);
    texts.push(expression.text);
    for (var i = 0; i < expression.senseGroups.length; i++) {
        var senseGroup = expression.senseGroups[i];
        var senses = senseGroup.senses;
        for (var j = 0; j < senses.length; j++) {
            var sense = senses[j];
            //texts.push(sense.explanation);
            var examples = sense.examples;
            for (var iexample = 0; iexample < examples.length && iexample < 2; iexample++) {
                var example = examples[iexample];
                if (isNotBlank(example.text)) {
                    texts.push(example.text);
                }
            }
        }
    }
    self.playSoundOfTexts(localeString, texts, function () {
        self.isPlayingSound = undefined;
    });
};
ExpressionBaseService.prototype.playSoundOfTexts = function (localeString, texts, callback) {
    var self = this;
    if (!hasValue(texts) || index >= texts.length) {
        if (callback) callback.call(self);
        return;
    }
    var index = 0;
    var text = texts[index];
    self.audio = new Audio(self.getSoundUrlFromText(localeString, text));
    var audio = self.audio;
    audio.addEventListener("ended", function () {
        index++;
        if (!hasValue(texts) || index >= texts.length) {
            if (callback) callback.call(self);
            return;
        }
        text = texts[index];
        audio.src = self.getSoundUrlFromText(localeString, text);
        audio.play();
    });
    audio.play();
};
ExpressionBaseService.prototype.getSoundUrlFromText = function (localeString, text) {
	var self = this;
	//FIXME, there's no AccessToken header if we call url directly like this.
    var url = contextPathResourceServer + '/api/tts?text=' + text;
    if (isNotBlank(localeString)) {
        //TODO should add locale
        //url += '&locale=' + localeString;
    }
//    return self.$http.get(url).then(
//            function (successResponse) {
//            	return successResponse;
//            }
//    );
    return url;
};
/**
 * @deprecated Not use because cannot play many texts
 * @param localeString
 * @param text
 * @param callback
 */
ExpressionBaseService.prototype.playSoundOfText = function (localeString, text, callback) {
    var self = this;
    if (isBlank(text)) {
        if (callback) callback.call(self);
    }
    var url = self.getSoundUrlFromText(localeString, text);
    if (hasValue(self.audio)) {
        self.audio.stop();
    }
    self.audio = new Audio(url);
    self.audio.play();
    self.audio.onended = function () {
        if (callback) callback.call(self);
    }
};
ExpressionBaseService.prototype.getExpressionLocaleString = function (expression) {
    if (!hasValue(expression) || !hasValue(expression.locale)) return null;
    var language = expression.locale.language;
    if (isBlank(language)) {
        return null;
    }
    var result = language;
    var country = expression.locale.country;
    if (isNotBlank(country)) {
        result += "-" + country;
    }
    return result;
};
ExpressionBaseService.prototype.favour = function (expression, favourite) {
    var self = this;
    expression.favourite = favourite;
    if (isBlank(expression.id)) return;
    var favouriteData = {
        expressionId: expression.id
        , favourite: expression.favourite
    };
    self.$http.post(contextPathResourceServer + '/api/expression/favourite', favouriteData).then(
        function (successResponse) {
            //self.setLesson(successResponse.data);
        }
    );
};