var app = angular.module('app', ['chart.js', 'ngRoute']);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            // .when('/', {
            //     templateUrl: '../index.html'
            // })
        .when('/', {
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
        // .when('/analysis', {
        //     templateUrl: '../views/analysis.html',
        //     controller: 'analysisController'
        // })
}]);

app.controller('homeController', function($rootScope, $scope, $http) {
    $scope.getArticleAnalysis = function () {
        if ($scope.inputTitle) {
            $scope.articleTitle = $scope.inputTitle;
        }

        console.log("Input title is: " + $scope.articleTitle);

        $http.get("http://localhost:8080/article?titles=" + $scope.articleTitle)
            .then(function (response) {
                console.log("Analysis done!");
                $scope.data = [];
                $scope.labels = [];
                angular.forEach(response.data.topOccurrences, function (entry) {
                    $scope.data.push(entry.frequency);
                    $scope.labels.push(entry.word);
                });
                $scope.articleTitle = response.data.articleTitle;
            });
    };

    $scope.getRandomArticleAnalysis = function () {
        console.log("Getting Random Article");

        $http.get("http://localhost:8080/article/random")
            .then(function (response) {
                console.log("Analysis done!");
                $scope.data = [];
                $scope.labels = [];
                angular.forEach(response.data.topOccurrences, function (entry) {
                        $scope.data.push(entry.frequency);
                        $scope.labels.push(entry.word);
                    }
                );
                $scope.articleTitle = response.data.articleTitle;
            });
    };
    $scope.showContent = function ($fileContent) {
        $scope.content = $fileContent;
        var strings = $scope.content.split("\n");
        $rootScope.articleTitles = [];
        $.each(strings, function () {
            $rootScope.articleTitles.push(this);
        });
        console.log($rootScope.articleTitles);
    };
});

app.controller('checkBoxController', function($scope) {

    // we will store our form data in this object
    $scope.formData = {};
    WikiContentAnalyzer.setIgnoreCommon($scope.formData.ignoreCommon.valueOf());
    console.log($scope.formData.ignoreCommon.valueOf());

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
