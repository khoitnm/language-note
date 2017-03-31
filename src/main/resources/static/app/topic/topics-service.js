var TopicsService = function ($http, $q) {
    this.$http = $http;
    this.$q = $q;

    this.topics = [];
    this.init();
};

TopicsService.prototype.init = function () {
    var self = this;
    var lessonsGet = self.$http.get(contextPath + '/api/lessons/introductions');
    self.$q.all([lessonsGet]).then(function (arrayOfResults) {
        self.lessons = arrayOfResults[0].data;
    });
};
TopicsService.prototype.removeTopic = function (item) {
    var self = this;
    self.lessons.remove(item);
    var removeLessonRequest = {
        lessonId: item.id
        , includeExpressions: true
    };
    self.$http({
        url: contextPath + "/api/lesson",
        method: 'DELETE',
        data: removeLessonRequest,
        headers: {"Content-Type": "application/json;charset=utf-8"}
    });
};
angularApp.service('lessonsService', ['$http', '$q', '$routeParams', LessonsService]);
angularApp.controller('lessonsController', ['$scope', '$http', '$q', '$routeParams', 'lessonsService', function ($scope, $http, $q, $routeParams, lessonsService) {
    $scope.lessonsService = lessonsService;
}]);
