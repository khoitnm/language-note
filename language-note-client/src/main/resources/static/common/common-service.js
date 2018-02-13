var CommonService = function () {
    this.$y = $y;
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
