var app = angular.module('app', ['chart.js', 'ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '../index.html',
            controller: 'indexController'
        })
        .when('/home', {
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
        .when('/analysis', {
            templateUrl: '../views/analysis.html',
            controller: 'analysisController'
        })
});

app.controller('homeController', function($rootScope, $scope, $http, $window) {
    $rootScope.articleTitle = undefined;

    $scope.getArticleAnalysis = function() {
        console.log("Input title is: " + $rootScope.articleTitle);

        $http.get("http://localhost:8080/article?titles=" + $rootScope.articleTitle)
            .then(function(response) {
                console.log("Analysis done!");
                $rootScope.data = [];
                $rootScope.labels = [];
                window.scrollBy(0, 1000);
                console.log("cancer!");
                var objDiv = document.getElementById("final");
                objDiv.scrollTop = objDiv.scrollHeight;
                angular.forEach(response.data.topOccurrences, function(entry) {
                    $rootScope.data.push(entry.frequency);
                    $rootScope.labels.push(entry.word);
                });
                // $window.location.href = "#analysis";
            });
    }

    $scope.getRandomArticleAnalysis = function() {
        console.log("Getting Random Article");

        $http.get("http://localhost:8080/article/random")
            .then(function(response) {
                console.log("Analysis done!");
                $rootScope.data = [];
                $rootScope.labels = [];

                console.log("cancer!");
                $rootScope.articleTitle=response.data.articleTitle;
                angular.forEach(response.data.topOccurrences, function(entry) {
                    $rootScope.data.push(entry.frequency);
                    $rootScope.labels.push(entry.word);
                }
                );
                window.scrollBy(0, 1000);
                // $window.location.href = "#analysis";
            });
    }
});

app.controller('analysisController', function($rootScope) {
    console.log($rootScope.labels);
    console.log($rootScope.data);
});

app.controller('formController', function($scope) {

    // we will store our form data in this object
    $scope.formData = {};
    WikiContentAnalyzer.setIgnoreCommon($scope.formData.ignoreCommon.valueOf());
    console.log($scope.formData.ignoreCommon.valueOf());

});


app.controller('MainCtrl', function ($scope) {
    $scope.showContent = function($fileContent){
        $scope.content = $fileContent;
    };
});

app.directive('onReadFile', function ($parse) {
    return {
        restrict: 'A',
        scope: false,
        link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);

            element.on('change', function(onChangeEvent) {
                var reader = new FileReader();

                reader.onload = function(onLoadEvent) {
                    scope.$apply(function() {
                        fn(scope, {$fileContent:onLoadEvent.target.result});
                    });
                };

                reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
            });
        }
    };
});