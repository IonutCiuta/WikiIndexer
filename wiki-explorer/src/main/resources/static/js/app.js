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

app.controller('homeController', function($rootScope, $scope, $http, $window) {
    $scope.articleTitle = undefined;

    $scope.getArticleAnalysis = function() {
        console.log("Input title is: " + $scope.articleTitle);

        $http.get("http://localhost:8080/article?titles=" + $scope.articleTitle)
            .then(function(response) {
                console.log("Analysis done!");
                $rootScope.data = [];
                $rootScope.labels = [];

                angular.forEach(response.data.topOccurrences, function(entry) {
                    $rootScope.data.push(entry.frequency);
                    $rootScope.labels.push(entry.word);
                });
                $window.location.href = "#analysis";
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
    console.log($scope.formData);

});



app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })

            .success(function(){
            })

            .error(function(){
            });
    }
}]);

app.controller('myCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
    $scope.uploadFile = function(){
        var file = $scope.myFile;

        console.log('file is ' );
        console.dir(file);

        var uploadUrl = "/fileUpload";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
}]);