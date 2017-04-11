/**
 * $list
 */
var $list = (function (module) {

        module.hasDuplication = function (list, checkDuplicateItem, comparator) {
            for (var i = 0; i < list.length; i++) {
                var item = list[i];
                if (item != checkDuplicateItem) {
                    if (comparator.compare(item, checkDuplicateItem) == 0) {
                        return true;
                    }
                }
            }
            return false;
        };

        return module;
    }($list || {})
);