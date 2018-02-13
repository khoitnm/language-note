findMatchString = function (target, findingString) {
    var resultWithHighlightText, matches, re;
    // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
    // Escape user input to be treated as a literal string within a regular expression
    re = new RegExp(findingString.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), 'i');
    if (!target) {
        return;
    }
    if (!target.match || !target.replace) {
        target = target.toString();
    }
    matches = target.match(re);
    if (matches) {
        resultWithHighlightText = target.replace(re,
            '<span class="search-highlight">' + matches[0] + '</span>');
    } else {
        resultWithHighlightText = target;
    }
    return {
        matches: matches,
        resultWithHighlightText: resultWithHighlightText,
        target: target
    }
};
var Filter = (function (module) {

    /**
     * This method check whether the target matches the findingString.
     * @param target
     * @param findingString this is usually the input of user.
     */
    module.findMatchString = function (target, findingString) {
        var resultWithHighlightText, matches, re;
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
        // Escape user input to be treated as a literal string within a regular expression
        re = new RegExp(findingString.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), 'i');
        if (!target) {
            return;
        }
        if (!target.match || !target.replace) {
            target = target.toString();
        }
        matches = target.match(re);
        if (matches) {
            resultWithHighlightText = target.replace(re,
                '<span class="search-highlight">' + matches[0] + '</span>');
        } else {
            resultWithHighlightText = target;
        }
        return {
            matches: matches,
            resultWithHighlightText: resultWithHighlightText,
            target: target
        }
    }
}(window.Filter || {}));