var app = angular.module('app', ['nvd3', 'ngRoute']);

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
                $rootScope.result = response.data;
            });
    }
});

app.controller('analysisController', function($rootScope, $scope) {
    console.log($rootScope.result);

    $scope.options = {
        chart: {
            type: 'discreteBarChart',
            height: 450,
            margin : {
                top: 20,
                right: 20,
                bottom: 50,
                left: 55
            },
            x: function(d){return d.word;},
            y: function(d){return d.frequency;},
            showValues: true,
            valueFormat: function(d){
                return d;
            },
            duration: 500,
            xAxis: {
                axisLabel: 'Top Words'
            },
            yAxis: {
                axisLabel: 'Frequency',
                axisLabelDistance: -10
            }
        }
    };
    
    $scope.data = JSON.stringify($scope.data);
    console.log($scope.data);
});