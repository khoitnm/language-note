var CommonService = function () {
    this.userErrorMessage = undefined;
};
CommonService.prototype.showErrorMessage = function (msg) {
    this.userErrorMessage = msg;
    this.userSuccessMessage = undefined;
};
CommonService.prototype.showSuccessMessage = function (msg) {
    this.userErrorMessage = undefined;
    this.userSuccessMessage = msg;
};

CommonService.prototype.playSound = function (expression) {
    if (expression.isPlayingSound || isBlank(expression.text)) return;
    expression.isPlayingSound = true;
    var url = contextPath + '/api/tts?text=' + expression.text;
    var localeString = this.getExpressionLocaleString(expression);
    if (isNotBlank(localeString)) {
        //TODO should add locale
        //url += '&locale=' + localeString;
    }
    var audio = new Audio(url);
    audio.play();
    audio.onended = function () {
        expression.isPlayingSound = undefined;
    }
};
CommonService.prototype.getExpressionLocaleString = function (expression) {
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