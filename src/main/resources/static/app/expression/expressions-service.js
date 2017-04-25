var ExpressionService = function () {
    CommonService.call(this);
};
inherit(CommonService, ExpressionService);
ExpressionService.prototype.playSound = function (expression) {
    var self = this;
    if (isBlank(expression.text) || self.isPlayingSound == expression.text) return;
    self.isPlayingSound = expression.text;
    if (self.audio) self.audio.stop();

    var localeString = this.getExpressionLocaleString(expression);

    var texts = [];
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
                texts.push(example.text);
            }
        }
    }
    self.playSoundOfTexts(localeString, texts, function () {
        self.isPlayingSound = undefined;
    });
};
ExpressionService.prototype.playSoundOfTexts = function (localeString, texts, callback) {
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
ExpressionService.prototype.getSoundUrlFromText = function (localeString, text) {
    var url = contextPath + '/api/tts?text=' + text;
    if (isNotBlank(localeString)) {
        //TODO should add locale
        //url += '&locale=' + localeString;
    }
    return url;
};
ExpressionService.prototype.playSoundOfText = function (localeString, text, callback) {
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
ExpressionService.prototype.getExpressionLocaleString = function (expression) {
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