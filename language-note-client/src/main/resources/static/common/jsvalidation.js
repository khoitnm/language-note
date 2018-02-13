function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function validateNumber(numString, digit) {
    var re;
    if (hasValue(digit)) {
        re = new RegExp("^[0-9]{" + digit + "}$");
    } else {
        re = new RegExp("^[0-9]+$");
    }
    return re.test(numString);
}
/**
 * return invalid result.
 * @param array
 * @return {@link ValidationListResult}
 */
function validateEmails(array) {
    var result = new ValidationListResult();
    for (i = 0; i < array.length; i++) {
        var item = array[i];
        if (isNotBlank(item)) {
            if (!validateEmail(item)) {
                result.push(new InvalidItem(i, item));
            }
        }
    }
    return result;
}

var InvalidItem = function (index, value) {
    this.index = index;
    this.value = value;
};
var ValidationListResult = function () {
    this.invalidItems = [];
};
ValidationListResult.prototype.push = function (invalidItem) {
    this.invalidItems.push(invalidItem);
};
ValidationListResult.prototype.isValid = function () {
    return this.invalidItems.length == 0;
};
ValidationListResult.prototype.toStringInvalidItems = function () {
    var toStringItems = [];
    for (i = 0; i < this.invalidItems.length; i++) {
        var invalidItem = this.invalidItems[i];
        var invalidItemToString = "[" + invalidItem.index + "] " + invalidItem.value;
        toStringItems.push(invalidItemToString);
    }
    return toStringItems.join(", ");
};