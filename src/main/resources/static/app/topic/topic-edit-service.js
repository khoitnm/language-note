var TopicEditService = function ($http, $q) {
    this.$http = $http;
    this.$q = $q;

    this.topicEdit = [];
    this.init();
};

TopicEditService.prototype.init = function () {
    var self = this;
    var topicEditGet = self.$http.get(contextPath + '/api/topics/mine');
    self.$q.all([topicEditGet]).then(function (arrayOfResults) {
        self.topicEdit = arrayOfResults[0].data;
    });
};
TopicEditService.prototype.removeTopic = function (item) {
    var self = this;
    self.topicEdit.remove(item);
    var removeRequest = {
        id: item.id
        , removeCompositions: false
    };
    self.$http({
        url: contextPath + "/api/topicEdit",
        method: 'DELETE',
        data: removeRequest,
        headers: {"Content-Type": "application/json;charset=utf-8"}
    });
};
TopicEditService.prototype.rename = function (item) {
    var self = this;
    self.$http.put(contextPath + '/api/topicEdit/name', item).then(
        function (successResponse) {
            //self.setLesson(successResponse.data);
        }
    );
};
angularApp.service('topicEditService', ['$http', '$q', '$routeParams', TopicEditService]);
angularApp.controller('topicEditController', ['$scope', '$http', '$q', '$routeParams', 'topicEditService', function ($scope, $http, $q, $routeParams, topicEditService) {
    $scope.topicEditService = topicEditService;
}]);

