/**
 * $r: Reflection
 */
var $r = (function (module) {
        var PropertyNameAndValue = function (propertyName, propertyValue) {
            this.propertyName = propertyName;
            this.propertyValue = propertyValue;
        };
        PropertyNameAndValue.prototype.isRoot = function () {
            return isEmpty(this.propertyName);
        };
        PropertyNameAndValue.newRoot = function (root) {
            return new PropertyNameAndValue('', root);
        };
        module.PropertyNameAndValue = PropertyNameAndValue;

        /**
         * @param root
         * @param childValue
         * @returns {Array}, each element is {propertyName, propertyValue}
         */
        module.findChildPaths = function (root, childValue) {
            var result = [];
            for (var propertyName in root) {
                var propertyValue = root[propertyName];
                var resultPart = new PropertyNameAndValue(propertyName, propertyValue);
                if (propertyValue == childValue) {
                    result.push(resultPart);
                    break;
                } else {
                    if (module.isObject(propertyValue)) {
                        var findResultInChildren = module.findChildPaths(propertyValue, childValue);
                        if (findResultInChildren.length > 0) {
                            result.push(resultPart);
                            result = result.concat(findResultInChildren);
                        }
                    }
                }

            }
            return result;
        };
        /**
         * This method is similar to findChildPaths, only difference is the comparator
         * @param root
         * @param propertyName
         * @returns {Array}
         */
        module.findChildPathsByPropertyName = function (root, propertyName) {
            var result = [];
            for (var ipropertyName in root) {
                var ipropertyValue = root[ipropertyName];
                var resultPart = new PropertyNameAndValue(ipropertyName, ipropertyValue);
                if (ipropertyName == propertyName) {
                    result.push(resultPart);
                    break;
                } else {
                    if (hasValue(ipropertyValue) && module.isObject(ipropertyValue)) {
                        var findResultInChildren = module.findChildPathsByPropertyName(ipropertyValue, propertyName);
                        if (findResultInChildren.length > 0) {
                            result.push(resultPart);
                            result = result.concat(findResultInChildren);
                        }
                    }
                }

            }
            return result;
        };
        module.removeChild = function (root, childValue) {
            var childPaths = module.findChildPaths(root, childValue);
            var parentProperty = module.findParentPropertyFromChildPaths(root, childPaths);
            var parent = parentProperty.propertyValue;
            parent.remove(childValue);

        };
        module.findParentPathsFromChildPaths = function (root, childPaths) {
            var parentPaths = [];
            var parentProperty = module.findParentPropertyFromChildPaths(root, childPaths);
            if (parentProperty != null) {
                if (parentProperty.isRoot()) {
                    parentPaths.push(parentProperty);
                } else {
                    parentPaths.push(PropertyNameAndValue.newRoot(root));
                    parentPaths = parentPaths.concat(childPaths.slice(0, childPaths.length - 2));
                }
            }
        };
        module.findParentPropertyFromChildPaths = function (root, childPaths) {
            return module.findSuperPropertyFromChildPaths(root, childPaths, 1);
        };
        module.findParentPropertyFromRoot = function (root, childValue) {
            var childPaths = module.findChildPaths(root, childValue);
            return module.findParentPropertyFromChildPaths(childPaths);
        };
        module.findSuperPropertyFromRoot = function (root, childValue, parentRelativeLevels) {
            var childPaths = module.findChildPaths(root, childValue);
            return module.findSuperPropertyFromChildPaths(root, childPaths, parentRelativeLevels);
        };
        module.findSuperPropertyFromChildPaths = function (root, childPaths, parentRelativeLevels) {
            var indexParent = childPaths.length - parentRelativeLevels - 1;
            if (indexParent < -1) {
                return null;
            } else if (indexParent == -1) {
                return PropertyNameAndValue.newRoot(root);
            } else {
                return childPaths[indexParent];
            }
        };

        module.getPropertyValueFromChildPaths = function (root, childPaths) {
            var propertyValue = root;
            for (var i = 0; i < childPaths.length; i++) {
                var childPath = childPaths[i];
                propertyValue = propertyValue[childPath.propertyName];
            }
            return propertyValue;
        };
        module.copyProperties = function (source, dest) {
            for (var field in source) {//includes inherited fields
                dest[field] = source[field];
            }
            return dest;
        };
        module.copyArray = function (array) {
            return array.slice();
        };
        /**
         * Copy the properties which are missing from destination.
         * @param source
         * @param dest
         * @returns {*}
         */
        module.copyMissingProperties = function (source, dest) {
            for (var field in source) {//includes inherited fields
                var destFieldValue = dest[field];
                if (isEmpty(destFieldValue) || isBlank(destFieldValue) || destFieldValue === 0) {
                    dest[field] = source[field];
                }
            }
            return dest;
        };
        module.isNumber = function (value) {
            return $.isNumeric(value);
        };
        module.isObject = function (value) {
            if (!hasValue(value)) return false;
            return (typeof(value) === 'object');
        };
        module.isArray = Array.isArray;
        return module;
    }($r || {})
);