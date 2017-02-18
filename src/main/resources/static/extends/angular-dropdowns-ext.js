var AngularDropDowns = function (items) {
    this.selectedItem = {};
    this.items = items;
};
var AngularDropDownsItem = function (text, href, value, divider, someprop) {
    this.text = text;
    this.href = href;
    this.value = value;
    this.divider = divider;
    this.someprop = someprop;
};
//
//$scope.ddSelectOptions = [
//    {
//        text: 'Option1',
//        value: 'a value'
//    },
//    {
//        text: 'Option2',
//        value: 'another value',
//        someprop: 'somevalue'
//    },
//    {
//        // Any option with divider set to true will be a divider
//        // in the menu and cannot be selected.
//        divider: true
//    },
//    {
//        // Any divider option with a 'text' property will
//        // behave similarly to a divider and cannot be selected.
//        divider: true,
//        text: 'divider label'
//    },
//    {
//        // Example of an option with the 'href' property
//        text: 'Option4',
//        href: '#option4'
//    }
//];
//
//$scope.ddSelectSelected = {}; // Must be an object