//////////////////////////////////////////////////////////////////////////////////////////////////
//
// This common file has nothing to do with other JavaScript framework, just pure JavaScript function.
//
///////////////////////////////////////////////////////////////////////////////////////////////////

Array.prototype.remove = function (item) {
    var j = 0;
    while (j < this.length) {
        // alert(originalArray[j]);
        if (this[j] == item) {
            this.splice(j, 1);
        } else {
            j++;
        }
    }
};
Array.prototype.findItemByField = function (itemFieldExpression, itemValue) {
    var j = 0;
    while (j < this.length) {
        var element = this[j];
        if (hasValue(element)) {
            var fieldValue = getField(element, itemFieldExpression);
            if (fieldValue == itemValue) {
                return element;
            }
        }
        j++;
    }
};
Array.prototype.newArray = function (fromValue, toValue) {
    var result = [];
    var length = toValue - fromValue;
    for (i = 0; i < length; i++) {
        result.push(fromValue + i);
    }
    return result;
};
Array.prototype.copyTop = function (size) {
    var end = this.length;
    if (size <= this.length) {
        end = size;
    }
    return this.slice(0, end);
};
Array.prototype.mergeNotBlankValuesToString = function (delimiterString, fieldName) {
    var result = null;
    var delimiter = ", ";
    if (isNotBlank(delimiterString)) {
        delimiter = delimiterString;
    }
    for (i = 0; i < this.length; i++) {
        var item = this[i];
        var itemValue;
        if (hasValue(fieldName) && hasValue(item)) {
            itemValue = getField(item, fieldName);
        } else {
            itemValue = item;
        }
        if (isNotBlank(itemValue)) {
            if (hasValue(result)) {
                result += delimiter + itemValue;
            } else {
                result = itemValue;
            }
        }
    }
    return result;
};
var FieldSort = function (fieldName, asc) {
    this.fieldName = fieldName;
    this.asc = asc;
};
var ComparatorByFields = function (fieldSorts) {
    this.fieldSorts = fieldSorts;
}
''
ComparatorByFields.prototype.compareByField = function (fieldName, asc, a, b) {
    var valA = getField(a, fieldName);
    var valB = getField(b, fieldName);
    var result = 0;
    if (hasValue(valA)) {
        if (hasValue(valB)) {
            if (valA < valB) {
                result = -1;
            } else if (valA > valB) {
                result = 1;
            }
        } else {
            result = 1;
        }
    } else {
        if (hasValue(valB)) {
            result = -1;
        } else {
            result = 0;
        }
    }

    if (asc == -1) {
        result = result * asc;
    }
    return result;
};
ComparatorByFields.prototype.compareByFields = function (a, b) {
    var result = 0;
    for (i = 0; i < this.fieldSorts.length; i++) {
        var fieldSort = this.fieldSorts[i];
        result = this.compareByField(fieldSort.fieldName, fieldSort.asc, a, b);
        if (result != 0) {
            break;
        }
    }
    return result;

};
/**
 * @param fieldName
 * @param asc 1 or -1
 */
Array.prototype.sortByField = function (fieldName, asc) {
    var comparator = new ComparatorByFields([new FieldSort(fieldName, asc)]);
    var comparatorByField = function (a, b) {
        return comparator.compareByFields(a, b);
    };
    this.sort(comparatorByField);
};
Array.prototype.sortByFields = function (fieldSorts) {
    var comparator = new ComparatorByFields(fieldSorts);
    var comparatorByField = function (a, b) {
        return comparator.compareByFields(a, b);
    };
    this.sort(comparatorByField);
};
/**
 * @param fieldExpressions array of field to check duplicated.
 */
Array.prototype.getDuplicatesByFields = function (fieldExpressions) {
    var duplicates = [];
    var length = this.length;
    for (var i = 0; i < length - 1; i++) {
        var itemi = this[i];
        for (var j = i + 1; j < length; j++) {
            var itemj = this[j];
            if (isEqualsByFields(itemi, itemj, fieldExpressions)) {
                duplicates.push(
                    {
                        indexA: i
                        , indexB: j
                        , itemA: itemi
                        , itemB: itemj
                    }
                );
            }
        }
    }
    return duplicates;
};
function isEqualsByFields(objA, objB, fieldExpressions) {
    var fieldValuesA = getFields(objA, fieldExpressions);
    var fieldValuesB = getFields(objB, fieldExpressions);
    for (var i = 0; i < fieldExpressions.length; i++) {
        var ivalA = fieldValuesA[i];
        var ivalB = fieldValuesB[i];
        if (hasValue(ivalA)) {
            if (hasValue(ivalB)) {
                if (isString(ivalA)) {
                    ivalA = ivalA.toUpperCase();
                    ivalB = ivalB.toUpperCase();
                }
                if (ivalA != ivalB) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if (hasValue(ivalB)) {
                return false;
            }
        }
    }
    return true;
};
function getFields(obj, fieldExpressions) {
    var values = [];
    for (var i = 0; i < fieldExpressions.length; i++) {
        values.push(getField(obj, fieldExpressions[i]));
    }
    return values;
};
function getArrayByFields(arrayObjects, fieldExpression) {
    var arrayFields = [];
    for (var i = 0; i < arrayObjects.length; i++) {
        var ielement = arrayObjects[i];
        var ielementField;
        if (hasValue(ielement)) {
            ielementField = getField(ielement, fieldExpression);
        } else {
            ielementField = null;
        }
        arrayFields.push(ielementField);
    }
    return arrayFields;
}
function isString(obj) {
    return (typeof obj === 'string' || obj instanceof String);
}
/**
 * http://stackoverflow.com/questions/6491463/accessing-nested-javascript-objects-with-string-key
 * @param object
 * @param fieldExpression
 * Example:
 * You have an object like this.
 * <pre>
 * var someObject = {
 *   'part3' : [
 *       {
 *           'name': 'Part 3A',
 *           'size': '10',
 *           'qty' : '20'
 *       }, {
 *           'name': 'Part 3B',
 *           'size': '5',
 *           'qty' : '20'
 *       }, {
 *           'name': 'Part 3C',
 *           'size': '7.5',
 *           'qty' : '20'
 *       }
 *   ]
 * };
 * </pre>
 * So getField(someObject, "part3[0].name") will return "Part 3A".
 * @returns
 */
function getField(object, fieldExpression) {
    fieldExpression = fieldExpression.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
    fieldExpression = fieldExpression.replace(/^\./, '');           // strip a leading dot
    var a = fieldExpression.split('.');
    for (var i = 0, n = a.length; i < n; ++i) {
        var fieldName = a[i];
        if (hasValue(object) && fieldName in object) {
            object = object[fieldName];
        } else {
            return;
        }
    }
    return object;
}
function shuffleArray(a) {
    var j, x, i;
    for (i = a.length; i; i--) {
        j = Math.floor(Math.random() * i);
        x = a[i - 1];
        a[i - 1] = a[j];
        a[j] = x;
    }
}
String.prototype.beginWithRegExp = function (regExp) {
    var rs = false;
    if (pathname.match(regExp)) {
        rs = true;
    }
    return rs;
    //return(this.indexOf(needle) == 0);
};
/**
 * @param s This method check whether the string start with @param s or not.
 * @returns {Boolean}
 */
String.prototype.beginWith = function (s) {
    var rs = (this.substr(0, s.length) == s);
    return rs;
};
String.prototype.splitToNotBlankValues = function () {
    var result = [];
    var array = this.split(',');
    for (var i = 0; i < array.length; i++) {
        var itemValue = array[i];
        if (isNotBlank(itemValue)) {
            var trimItemValue = itemValue.trim();
            result.push(trimItemValue);
        }
    }
    return result;
}
function inheritPrototype(prototype) {
    function F() {
    }; // Dummy constructor
    F.prototype = prototype;
    return new F();
}
function inherit(parentClazz, childClazz) {
    childClazz.prototype = inheritPrototype(parentClazz.prototype);
    childClazz.prototype.constructor = childClazz;
}
function isArray(obj) {
    return ( Object.prototype.toString.call(obj) === '[object Array]' );
}
/**
 * @param variable
 * @returns {Boolean} Topic: if variable is an empty string (""), it still return true;
 */
function hasValue(variable) {
    return (typeof variable !== 'undefined') && (variable !== null);
}
function isNotEmpty(variable) {
    return (typeof variable !== 'undefined') && (variable !== null) && (variable.length !== 0);
}
function isNotBlank(variable) {
    return isNotEmpty(variable) || (isString(variable) && isNotEmpty(variable.trim()));
}
function isBlank(variable) {
    return !isNotBlank(variable);
}
function isUndefined(value) {
    return typeof value == 'undefined';
}
/**
 * Just for testing
 * @param msecs
 */
function wait(msecs) {
    var start = new Date().getTime();
    var cur = start;
    while (cur - start < msecs) {
        cur = new Date().getTime();
    }
}

function numericOnly(field) {
    var num = field.value;
    var len = num.length;
    var string = num.substring(len - 1, len);

    if (string == " ")
        field.value = num.replace(string, "");

    if (isNaN(num))
        field.value = num.replace(string, "");
}
//REFLECTION ////////////////////////
function copyProperties(source, dest) {
    for (var key in source) {
        //copy all the fields
        dest[key] = source[key];
    }

    return dest;
}