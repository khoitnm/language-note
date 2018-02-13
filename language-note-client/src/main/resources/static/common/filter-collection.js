var FilterCollection = function ($sce, filterField) {
    this.$sce = $sce;
    this.filterValue = undefined;
    this.filterField = filterField;
    this.originalItems = [];
    this.filteredItems = [];
    this.selectAll = false;
    this.selectedItems = [];
};
FilterCollection.prototype.initByOriginalItems = function (originalItems, selectedItemId) {
    this.originalItems = originalItems;
    this.remainItems = $r.copyArray(originalItems);
    this.filterItems();
    this.selectedItems = [];
    if (isNotBlank(selectedItemId)) {
        this.selectItemByField("id", selectedItemId);
    }
};
FilterCollection.prototype.filterItems = function () {
    var self = this;
    self.filteredItems = [];
    if (isNotBlank(self.filterValue)) {
        for (var i = 0; i < self.remainItems.length; i++) {
            var item = self.remainItems[i];
            if (hasValue(item)) {
                var itemFieldValue = getField(item, self.filterField);
                var findResult = findMatchString(itemFieldValue, self.filterValue);
                if (findResult.matches) {
                    item.resultWithHighlightText = self.$sce.trustAsHtml(findResult.resultWithHighlightText);
                    self.filteredItems.push(item);
                } else {
                    item.resultWithHighlightText = null;
                }
            }
        }
    } else {
        this.filteredItems = self.remainItems.slice();
    }
};
FilterCollection.prototype.selectItemByField = function (itemField, itemValue) {
    var self = this;
    var item = self.remainItems.findItemByField(itemField, itemValue);
    self.selectItem(item);
};
FilterCollection.prototype.clickItem = function (item) {
    var checked = item['$selected'];
    if (checked) {
        this.unselectItem(item);
    } else {
        this.selectItem(item);
    }
};
FilterCollection.prototype.selectItem = function (item) {
    var self = this;
    item['$selected'] = true;
    self.remainItems.remove(item);
    self.filteredItems.remove(item);
    self.selectedItems.push(item);
};
FilterCollection.prototype.unselectItem = function (item) {
    var self = this;
    item['$selected'] = false;
    self.selectedItems.remove(item);
    self.remainItems.push(item);
    self.filterItems();
};