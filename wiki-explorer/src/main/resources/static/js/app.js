var app = angular.module('app', ['nvd3', 'ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            template: '../home.html',
            controller: 'homeController'
        })
        .when('analysis', {
            template: '../analysis.html',
            controller: 'analysisController'
        })
});

app.controller('homeController', function($scope, $http) {
    $scope.articleTitle = undefined;

    $scope.getArticleAnalysis = function() {
        console.log("Input title is: " + $scope.articleTitle);

        $http.get("http://localhost:8080/article?titles=" + $scope.articleTitle)
            .then(function(response) {
                $scope.result = response.data;
                console.log($scope.result);
            });
    }
});

app.controller('analysisController', function($scope) {
    $scope.message = "some information";
});