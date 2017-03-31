var LessonsService = function ($http, $q) {
    this.$http = $http;
    this.$q = $q;

    this.lessons = [];
    this.init();
};

LessonsService.prototype.init = function () {
    var self = this;
    var lessonsGet = self.$http.get(contextPath + '/api/lessons/introductions');
    self.$q.all([lessonsGet]).then(function (arrayOfResults) {
        self.lessons = arrayOfResults[0].data;
    });
};
LessonsService.prototype.removeLesson = function (lesson) {
    var self = this;
    self.lessons.remove(lesson);
    var removeLessonRequest = {
        lessonId: lesson.id
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
