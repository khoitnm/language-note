var CompositeEditor = function (skeleton, root, functionsMap) {
    this.skeleton = skeleton;
    this.root = root;
    this.functionsMap = functionsMap || {};
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
CompositeEditor.prototype.removeItemInList = function (list, index) {
    var item = list[index];
    this.removeItem(item);
};
CompositeEditor.prototype.removeItem = function (item) {
    var self = this;
    var analyzeChildAndParentResult = self.analyzeChildAndParentPaths(item);
    var childPaths = analyzeChildAndParentResult.childPaths;
    var parentProperty = analyzeChildAndParentResult.parentProperty;
    self.removeItemByParentProperty(childPaths, parentProperty, item);
};
CompositeEditor.prototype.cleanRecursiveItem = function (item) {
    var self = this;
    var childPaths = $r.findChildPaths(self.root, item);
    var parentProperty = $r.findParentPropertyFromChildPaths(self.root, childPaths);
    if (self.isItemEmpty(parentProperty, item)) {
        self.removeItemByParentProperty(childPaths, parentProperty, item)
    } else {
        for (iprop in item) {
            if ($r.isObject(iprop)) {
                self.cleanRecursiveItem(iprop);
            }
        }
    }
};
//INTERNAL METHOS /////////////////////////////////////////
CompositeEditor.prototype.removeItemByParentProperty = function (childPaths, parentProperty, item) {
    var self = this;
    var parent = parentProperty.propertyValue;
    parent.remove(item);
    var injectedFunction = self.getInjectFnRemoveItem(parentProperty.propertyName);
    injectedFunction.call(self, item);
    self.addEmptySiblingItemToDirectParentIfNecessary(parentProperty, childPaths);
};
CompositeEditor.prototype.addEmptySiblingItemIfNecessary = function (item) {
    var self = this;
    var analyzeChildAndParentResult = self.analyzeChildAndParentPaths(item);
    var childPaths = analyzeChildAndParentResult.childPaths;
    var parentProperty = analyzeChildAndParentResult.parentProperty;
    //var parent = analyzeChildAndParentResult.parentProperty.propertyValue;//parent is an array containing children items.
    var itemSkeleton = self.addEmptySiblingItemToDirectParentIfNecessary(parentProperty, childPaths);
    return {childPaths: childPaths, parentProperty: parentProperty, itemSkeleton: itemSkeleton};
};
CompositeEditor.prototype.analyzeChildAndParentPaths = function (item) {
    var self = this;
    var childPaths = $r.findChildPaths(self.root, item);
    var parentProperty = $r.findParentPropertyFromChildPaths(self.root, childPaths);
    if (parentProperty == null || childPaths.length == 0) {
        throw "Not found item from root. Root: " + self.root + ", Item: " + item;
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