// Code goes here
var appRoot = angular.module('smapp', ['ngRoute', 'ui.bootstrap']);

appRoot.config([
    '$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'partials/home.html', controller: 'homeCtrl' })
            .when('/vivran', { templateUrl: 'partials/vivran.html' })
            .when('/goswami-tulsidas', { templateUrl: 'partials/goswami-tulsidas.html' })
            //.when('/chapter/:chapterNo', { templateUrl: 'partials/chapter.html', controller: 'chapterCtrl' })
            //.when('/chapter/:chapterNo/verse/:verseNo', { templateUrl: 'partials/verse.html', controller: 'verseCtrl' })
            //.when('/word-meaning/:wordId', { templateUrl: 'partials/wordMeaning.html', controller: 'wordMeaningCtrl' })
            .otherwise({ redirectTo: '/' });
    }
]);

appRoot.controller('homeCtrl', function ($scope, $http) {

    $scope.chapterSummaryList = [];
    $http({ method: 'GET', url: 'data/json/chapter-summary.json' }).
        then(function (response) {
            $scope.chapterSummaryList = response.data;
        }, function (response) {
            $scope.status = response.status;
        });

});

// appRoot.controller('chapterCtrl', function ($scope, $http, $routeParams, $log) {
//     $scope.chapterSummaryList = [];
//     $scope.chapterObj = {};
//     $log.log("" + $routeParams.chapterNo + "");
//     $http({ method: 'GET', url: 'data/json/chapter-verse-detail-temp.json' }).
//         then(function (response) {
//             $scope.chapterSummaryList = response.data;
//             $scope.chapterObj = $scope.chapterSummaryList.filter(function (chain) {
//                 return chain.id === $routeParams.chapterNo;
//             })[0];
//             $log.log("" + angular.toJson($scope.chapterObj) + "");
//         }, function (response) {
//             $scope.status = response.status;
//         });
// });

// appRoot.controller('verseCtrl', function ($scope, $http, $routeParams, $log) {
//     $scope.chapterSummaryList = [];
//     $scope.chapterObj = {};
//     $scope.verseObj = {};
//     $log.log("" + $routeParams.chapterNo + "");
//     $http({ method: 'GET', url: 'data/json/chapter-verse-detail-temp.json' }).
//         then(function (response) {
//             $scope.chapterSummaryList = response.data;
//             $scope.chapterObj = $scope.chapterSummaryList.filter(function (chain) {
//                 return chain.id === $routeParams.chapterNo;
//             })[0];

//             $scope.verseObj = $scope.chapterObj.verses.filter(function (chain) {
//                 return chain.id === $routeParams.verseNo;
//             })[0];

//             $log.log("" + angular.toJson($scope.verseObj) + "");
//         }, function (response) {
//             $scope.status = response.status;
//         });
// });

// appRoot.controller('wordMeaningCtrl', function ($scope, $http, $routeParams, $log) {
//     $scope.wordMeaningList = [];
//     $scope.wordMeaningObj = {};
//     //$scope.verseObj = {};
//     $log.log("" + $routeParams.wordId + "");
//     $http({ method: 'GET', url: 'data/json/chapter-verse-word-meaning-temp.json' }).
//         then(function (response) {
//             $scope.wordMeaningList = response.data;
//             $scope.wordMeaningObj = $scope.wordMeaningList.filter(function (chain) {
//                 return chain.id === $routeParams.wordId;
//             })[0];

//             $log.log("" + angular.toJson($scope.wordMeaningObj) + "");
//         }, function (response) {
//             $scope.status = response.status;
//         });
// });

// appRoot.controller('IndexCtrl', function ($scope) {
//     $scope.Students = [{ "Id": "stud1", "FirstName": "FirstName1", "LastName": "LastName1" }, { "Id": "stud2", "FirstName": "FirstName2", "LastName": "LastName2" }];

// });
