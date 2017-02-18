angularApp.directive("limitTo", [function () {
    return {
        restrict: "A",
        link: function (scope, elem, attrs) {
            var limit = parseInt(attrs.limitTo);
            angular.element(elem).on("keypress", function (e) {
                if (this.value.length == limit) e.preventDefault();
            });
        }
    }
}]);
angularApp.directive('capitalize', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, modelCtrl) {
            var capitalize = function (inputValue) {
                if (inputValue == undefined) inputValue = '';
                var capitalized = inputValue.toUpperCase();
                if (capitalized !== inputValue) {
                    modelCtrl.$setViewValue(capitalized);
                    modelCtrl.$render();
                }
                return capitalized;
            };
            modelCtrl.$parsers.push(capitalize);
            capitalize(scope[attrs.ngModel]); // capitalize initial value
        }
    };
});

//angularApp.directive('ngSimpleAutoComplete', function ($timeout) {
//    return {
//        require: 'ngModel',
//        link: function (scope, iElement, iAttrs, modelCtrl) {
//            iElement.autocomplete({
//                source: scope[iAttrs.uiItems],
//                select: function () {
//                    $timeout(function () {
//                        iElement.trigger('input');
//                    }, 0);
//                }
//            });
//        }
//    };
//});

/**
 * Not tested yet
 * https://codepen.io/TheLarkInn/post/angularjs-directive-labs-ngenterkey
 */
angularApp.directive('ngKeyCodePress', ngKeyCodePress);
function ngKeyCodePress() {
    return {
        restrict: 'A',
        link: function ($scope, $element, $attrs) {
            $element.bind("keypress", function (event) {
                var inputKeyCode = event.which || event.keyCode;
                var definedKeyCode = $attrs["key-code"];
                if (inputKeyCode == definedKeyCode) {
                    $scope.$apply(function () {
                        $scope.$eval(definedKeyCode, {$event: event});
                    });

                }
            });
        }
    };
}

angularApp.directive('ckEditor', function () {
    return {
        require: '?ngModel',
        link: function (scope, elm, attr, ngModel) {
            var ck = addCkeditor(elm[0]);
            //addCkeditorButton(ck);
            if (!ngModel) return;
            ck.on('instanceReady', function () {
                ck.setData(ngModel.$viewValue);
            });
            function updateModel() {
                scope.$apply(function () {
                    ngModel.$setViewValue(ck.getData());
                });
            }

            ck.on('change', updateModel);
            ck.on('key', updateModel);
            ck.on('dataReady', updateModel);

            ngModel.$render = function (value) {
                ck.setData(ngModel.$viewValue);
            };
        }
    };
});
angularApp.directive('ck-editor-inline', function () {
    return {
        require: '?ngModel',
        link: function (scope, elm, attr, ngModel) {
            var ck = addCkeditor(elm[0], {toolbarStartupExpanded: false});
            if (!ngModel) return;
            ck.on('instanceReady', function () {
                ck.setData(ngModel.$viewValue);
            });
            function updateModel() {
                scope.$apply(function () {
                    ngModel.$setViewValue(ck.getData());
                });
            }

            ck.on('change', updateModel);
            ck.on('key', updateModel);
            ck.on('dataReady', updateModel);

            ngModel.$render = function (value) {
                ck.setData(ngModel.$viewValue);
            };
        }
    };
});
var addCkeditor = function (element, options) {
    return CKEDITOR.replace(element, options);
};
var addCkeditorButton = function (ck) {
    CKEDITOR.plugins.add('timestamp', {
        icons: 'timestamp',
        init: function (editor) {
            editor.addCommand('insertTimestamp', {
                exec: function (editor) {
                    var now = new Date();
                    editor.insertHtml('The current date and time is: <em>' + now.toString() + '</em>');
                }
            });
            editor.ui.addButton('Timestamp', {
                label: 'Insert Timestamp',
                command: 'insertTimestamp',
                toolbar: 'insert'
            });
        }
    });
};
