var TopicsService = function ($http, $q) {
    this.$http = $http;
    this.$q = $q;

    this.topics = [];
    this.keyword = undefined;
    this.topicsPageSize = 10;
    this.topicsDataTable = new DataTable(this.topics, this.topicsPageSize);
    this.init();
};

TopicsService.prototype.init = function () {
    var self = this;
    var url = contextPath + '/api/topic-briefs/mine';
    if (self.keyword) url += '?where=3&keyword=' + self.keyword;
    var topicsGet = self.$http.get(url);
    self.$q.all([topicsGet]).then(function (arrayOfResults) {
        self.topics = arrayOfResults[0].data;
        self.topicsDataTable.setData(self.topics);
    });
};
TopicsService.prototype.removeTopic = function (item) {
    var self = this;
    self.topics.remove(item);
    var removeRequest = {
        id: item.id
        , removeCompositions: false
    };
    self.$http({
        url: contextPath + "/api/topics",
        method: 'DELETE',
        data: removeRequest,
        headers: {"Content-Type": "application/json;charset=utf-8"}
    });
};
TopicsService.prototype.rename = function (item) {
    var self = this;
    if (!hasValue(item.id)) return;
    self.$http.put(contextPath + '/api/topics/name', item).then(
        function (successResponse) {
            //self.setLesson(successResponse.data);
        }
    );
};
angularApp.service('topicsService', ['$http', '$q', '$routeParams', TopicsService]);
angularApp.controller('topicsController', ['$scope', '$http', '$q', '$routeParams', 'topicsService', function ($scope, $http, $q, $routeParams, topicsService) {
    $scope.service = topicsService;
}]);

