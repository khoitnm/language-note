/*
 http://jasonwatmore.com/post/2016/01/31/angularjs-pagination-example-with-logic-like-google
 */
function DataTable(data, pageSize) {
    this.data = undefined;
    this.dataInPage = undefined;
    this.pager = undefined;

    this.construct(data, pageSize);
}
DataTable.prototype.construct = function (data, pageSize) {
    if (!hasValue(data)) {
        data = [];
    }
    this.data = data;
    this.pager = new Pager(data.length, pageSize);
    this.pager.calculatePages(0);
    this.dataInPage = this.calculateDataInPage(this.data, this.pager);
};
DataTable.prototype.calculateDataInPage = function (data, pager) {
    var dataInPage = [];
    var startIndex = pager.startIndex;
    var endIndex = pager.endIndex;
    if (startIndex >= 0 && endIndex >= 0) {
        for (i = startIndex; i <= endIndex; i++) {
            var row = data[i];
            row.$$index = i;
            dataInPage.push(row);
        }
    }
    return dataInPage;
};
DataTable.prototype.setPage = function (currentPageIndex) {
    this.pager.calculatePages(currentPageIndex);
    this.dataInPage = this.calculateDataInPage(this.data, this.pager);
};
function Pager(totalItems, pageSize) {
    this.MAX_PAGES_TO_SHOW = 10;
    this.MAX_PAGES_BEFORE_CURRENT = 4;//Before current page will have maximum 4 pages.
    this.MAX_PAGES_AFTER_CURRENT = 4;//After current page will have maximum 4 pages.

    this.totalItems = totalItems;
    this.pageSize = pageSize;

    this.currentPage = undefined;
    this.totalPages = undefined;
    this.startPageIndex = undefined;
    this.endPageIndex = undefined;
    this.startIndex = undefined;
    this.endIndex = undefined;
    this.pages = undefined;

    this.calculatePages(0);
}

// service implementation
Pager.prototype.calculatePages = function (currentPage) {
    var maxPagesToShow = this.MAX_PAGES_TO_SHOW;
    var maxPagesFromBeginning = this.MAX_PAGES_BEFORE_CURRENT;//Before current page will have maximum 4 pages.
    var maxPagesToEnding = this.MAX_PAGES_AFTER_CURRENT;//After current page will have maximum 4 pages.

    var totalItems = this.totalItems;
    var pageSize = this.pageSize;

    // default to first page
    currentPage = currentPage || 0;

    // calculate total pages
    var totalPages = Math.ceil(totalItems / pageSize);

    var startPageIndex = undefined;
    var endPageIndex = undefined;

    if (totalPages <= maxPagesToShow) {
        // less than 10 total pages so show all
        startPageIndex = 0;
        endPageIndex = totalPages - 1;
    } else {
        // more than 10 total pages so calculate start and end pages
        if (currentPage <= (maxPagesToShow - maxPagesFromBeginning)) {
            startPageIndex = 0;
            endPageIndex = maxPagesToShow - 1;
        } else if (currentPage + maxPagesToEnding >= totalPages) {
            startPageIndex = totalPages - (maxPagesToShow - 1) - 1;
            endPageIndex = totalPages - 1;
        } else {
            startPageIndex = currentPage - (maxPagesFromBeginning + 1);
            endPageIndex = currentPage + maxPagesToEnding;
        }
    }

    // calculate start and end item indexes
    var startIndex = (currentPage) * pageSize;
    var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

    // create an array of pages to ng-repeat in the pager control
    //var pages = _.range(startPage, endPage + 1);
    var pages = Page.newArray(startPageIndex, endPageIndex);//Array.prototype.newArray(startPage, endPage + 1);
    if (endPageIndex < totalPages - 2) {
        pages.push(new Page(totalPages - 2, '...'));
        pages.push(new Page(totalPages - 1, totalPages));
    } else if (endPageIndex < totalPages - 1) {
        pages.push(new Page(totalPages - 1, totalPages));
    }
    if (startPageIndex > 1) {
        pages.unshift(new Page(1, '...'));
        pages.unshift(new Page(0, 1));
    } else if (startPageIndex > 0) {
        pages.unshift(new Page(0, 1));
    }

    // return object with all pager properties required by the view
    this.currentPage = currentPage;
    this.startPage = startPageIndex;
    this.endPage = endPageIndex;
    this.totalPages = totalPages;
    this.startPage = startPageIndex;
    this.endPage = endPageIndex;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.pages = pages;
};
function Page(index, label) {
    this.index = index;
    this.label = label;
};
Page.newArray = function (startIndex, endIndex) {
    var pages = [];
    for (i = startIndex; i <= endIndex; i++) {
        pages.push(new Page(i, i + 1));
    }
    return pages;
};