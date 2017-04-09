var CompositeEditor = function (skeleton, root, functionsMap) {
    this.skeleton = skeleton;
    this.root = root;
    this.functionsMap = functionsMap || {};
};
CompositeEditor.prototype.addEmptyChildIfNecessary = function (containerPropertyName) {
    var self = this;
    var containerPaths = $r.findChildPathsByPropertyName(self.root, containerPropertyName);
    if (containerPaths.length == 0) {
        throw new Error('Not found property in root. propertyName: ' + containerPropertyName + ", root:" + self.root);
    }
    var containerProperty = containerPaths[containerPaths.length - 1];
    var containerValue = containerProperty.propertyValue;
    if (!hasValue(containerValue)) {
        var containerParent = containerPaths.length >= 2 ? containerPaths[containerPaths.length - 2].propertyValue : self.root;
        containerParent[containerPropertyName] = [];
        containerPaths = $r.findChildPathsByPropertyName(self.root, containerPropertyName);
        containerProperty = containerPaths[containerPaths.length - 1]
    }
    var childPaths = containerPaths.slice();
    childPaths.push(new $r.PropertyNameAndValue('0', null));
    self.addEmptySiblingItemToDirectParentIfNecessary(containerProperty, childPaths);
};
CompositeEditor.prototype.getSkeletonByPropertyName = function (propertyName) {
    var self = this;
    var skeletonPaths = $r.findChildPathsByPropertyName(self.skeleton, propertyName);
    if (skeletonPaths.length == 0) {
        throw new Error('Not found property in skeleton. propertyName: ' + propertyName + ", skeleton:" + self.skeleton);
    }
    var skeletonProperty = skeletonPaths[skeletonPaths.length - 1];
    return skeletonProperty.propertyValue;
};
CompositeEditor.prototype.changeItem = function (item) {
    var self = this;
    var addingSiblingResult = self.addEmptySiblingItemIfNecessary(item);
    var itemSkeleton = addingSiblingResult.itemSkeleton;

    //Add an init children object to ichild
    $r.copyMissingProperties(angular.copy(itemSkeleton), item);
};
CompositeEditor.prototype.changeItemInList = function (list, index) {
    var item = list[index];
    this.changeItem(item);
};
CompositeEditor.prototype.removeItem = function (item) {
    var self = this;
    var analyzeChildAndParentResult = self.analyzeChildAndParentPaths(item);
    var childPaths = analyzeChildAndParentResult.childPaths;
    var parentProperty = analyzeChildAndParentResult.parentProperty;
    self.removeItemByParentProperty(childPaths, parentProperty, item);
    return childPaths;
};
CompositeEditor.prototype.removeItemAndAddSkeleton = function (item) {
    var self = this;
    var analyzeChildAndParentResult = self.analyzeChildAndParentPaths(item);
    var childPaths = analyzeChildAndParentResult.childPaths;
    var parentProperty = analyzeChildAndParentResult.parentProperty;
    self.removeItemAndAddSkeletonByParentProperty(childPaths, parentProperty, item);
    return childPaths;
};
CompositeEditor.prototype.cleanRecursiveRoot = function () {
    var root = this.root;
    for (propertyName in root) {
        var propertyValue = root[propertyName];
        if ($r.isObject(propertyValue) || $r.isArray(propertyValue)) {
            this.cleanRecursiveItem(propertyValue);
        }
    }
};
CompositeEditor.prototype.cleanRecursiveItem = function (item) {
    var self = this;
    if ($r.isArray(item)) {
        for (var i = 0; i < item.length; i++) {
            self.cleanRecursiveItem(item[i]);
        }
    } else {
        var childPaths = $r.findChildPaths(self.root, item);
        var parentProperty = $r.findParentPropertyFromChildPaths(self.root, childPaths);
        if (self.isItemEmpty(parentProperty.propertyName, item)) {
            self.removeItemByParentProperty(childPaths, parentProperty, item)
        } else {
            for (ipropName in item) {
                var ipropValue = item[ipropName];
                if ($r.isObject(ipropValue)) {
                    self.cleanRecursiveItem(ipropValue);
                }
            }
        }
    }
};
//INTERNAL METHOS /////////////////////////////////////////
CompositeEditor.prototype.removeItemAndAddSkeletonByParentProperty = function (childPaths, parentProperty, item) {
    var self = this;
    self.removeItemByParentProperty(childPaths, parentProperty, item);
    self.addEmptySiblingItemToDirectParentIfNecessary(parentProperty, childPaths);
};
CompositeEditor.prototype.removeItemByParentProperty = function (childPaths, parentProperty, item) {
    var self = this;
    var parent = parentProperty.propertyValue;
    parent.remove(item);
    var injectedFunction = self.getInjectFnRemoveItem(parentProperty.propertyName);
    injectedFunction.call(self, item, childPaths);
};
CompositeEditor.prototype.addEmptySiblingItemIfNecessary = function (item) {
    var self = this;
    var analyzeChildAndParentResult = self.analyzeChildAndParentPaths(item);
    var childPaths = analyzeChildAndParentResult.childPaths;
    var parentProperty = analyzeChildAndParentResult.parentProperty;
    var itemSkeleton = self.addEmptySiblingItemToDirectParentIfNecessary(parentProperty, childPaths);
    return {childPaths: childPaths, parentProperty: parentProperty, itemSkeleton: itemSkeleton};
};
CompositeEditor.prototype.analyzeChildAndParentPaths = function (item) {
    var self = this;
    var childPaths = $r.findChildPaths(self.root, item);
    var parentProperty = $r.findParentPropertyFromChildPaths(self.root, childPaths);
    if (parentProperty == null || childPaths.length == 0) {
        throw new Error("Not found item from root. Root: " + self.root + ", Item: " + item);
    }
    return {childPaths: childPaths, parentProperty: parentProperty};
};
CompositeEditor.prototype.addEmptySiblingItemToDirectParentIfNecessary = function (parentProperty, childPaths) {
    var self = this;
    var childSkeletonPaths = self.toSkeletonPaths(childPaths);
    var itemSkeleton = $r.getPropertyValueFromChildPaths(self.skeleton, childSkeletonPaths);
    if (!self.hasEmptyItem(parentProperty)) {
        var parent = parentProperty.propertyValue;
        parent.push(angular.copy(itemSkeleton));
    }
    return itemSkeleton;
};
CompositeEditor.prototype.toSkeletonPaths = function (paths) {
    var skeletonPaths = [];
    for (var i = 0; i < paths.length; i++) {
        var ipath = paths[i];
        var ipathName = ipath.propertyName;
        if ($r.isNumber(ipathName)) {
            ipathName = 0;
        }
        var iskeletonPath = new $r.PropertyNameAndValue(ipathName, ipath.propertyValue);
        skeletonPaths.push(iskeletonPath);
    }
    return skeletonPaths;
};
CompositeEditor.prototype.hasEmptyItem = function (parentProperty) {
    return this.findEmptyItem(parentProperty) != null;
};
CompositeEditor.prototype.findEmptyItem = function (parentProperty) {
    var items = parentProperty.propertyValue;
    if (!hasValue(items)) return null;
    var self = this;
    for (var i = 0; i < items.length; i++) {
        var iItem = items[i];
        if (self.isItemEmpty(parentProperty.propertyName, iItem)) {
            return iItem;
        }
    }
    return null;
};
CompositeEditor.prototype.isItemEmpty = function (containerPropertyName, item) {
    var injectedFunction = this.getInjectFnIsItemEmpty(containerPropertyName);
    return injectedFunction.call(this, item);
};
CompositeEditor.prototype.getInjectFnIsItemEmpty = function (containerPropertyName) {
    var injectedFunction = this.functionsMap[containerPropertyName];
    if (!hasValue(injectedFunction) || !hasValue(injectedFunction.fnIsItemEmpty)) {
        //throw 'Not found fnIsItemEmpty of ' + containerPropertyName;
        return function (item) {
            return !hasValue(item)
        };
    }
    return injectedFunction.fnIsItemEmpty;
};
CompositeEditor.prototype.getInjectFnRemoveItem = function (containerPropertyName) {
    var self = this;
    var injectedFunction = self.functionsMap[containerPropertyName];
    if (!hasValue(injectedFunction) || !hasValue(injectedFunction.fnRemoveItem)) {
        return function (item) {
        };
    }
    return injectedFunction.fnRemoveItem;
};
var InjectedFunction = function (fnIsItemEmpty, fnRemoveItem) {
    this.fnIsItemEmpty = fnIsItemEmpty;
    this.fnRemoveItem = fnRemoveItem;
};