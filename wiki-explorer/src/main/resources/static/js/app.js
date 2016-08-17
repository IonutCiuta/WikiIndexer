var app = angular.module('app', ['chart.js', 'ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
        .when('/analysis', {
            templateUrl: '../views/analysis.html',
            controller: 'analysisController'
        })
});

app.controller('homeController', function($rootScope, $scope, $http) {
    $scope.articleTitle = undefined;

    $scope.getArticleAnalysis = function() {
        console.log("Input title is: " + $scope.articleTitle);

        $http.get("http://localhost:8080/article?titles=" + $scope.articleTitle)
            .then(function(response) {
                console.log("Analysis done!");
                $rootScope.data = [];
                $rootScope.label = [];

                angular.forEach(response.data.topOccurrences, function(entry) {
                    $rootScope.data.push(entry.frequency);
                    $rootScope.label.push(entry.word);
                });
            });
    }
});

app.controller('analysisController', function($rootScope) {
    console.log($rootScope.label);
    console.log($rootScope.data);
});