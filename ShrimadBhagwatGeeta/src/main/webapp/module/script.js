// Code goes here
var appRoot = angular.module('smapp', ['ngRoute', 'ui.bootstrap']);

appRoot.config([
    '$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'partials/home.html', controller: 'homeCtrl' })
            .when('/chapter/:chapterNo', { templateUrl: 'partials/chapter.html', controller: 'chapterCtrl' })
            .when('/chapter/:chapterNo/verse/:verseNo', { templateUrl: 'partials/verse.html', controller: 'verseCtrl' })
            .when('/word-meaning/:wordId', { templateUrl: 'partials/wordMeaning.html', controller: 'wordMeaningCtrl' })
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

appRoot.controller('chapterCtrl', function ($scope, $http, $routeParams, $log) {
    $scope.chapterSummaryList = [];
    $scope.chapterObj = {};
    $log.log("" + $routeParams.chapterNo + "");
    $http({ method: 'GET', url: 'data/json/chapter-verse-detail-temp.json' }).
        then(function (response) {
            $scope.chapterSummaryList = response.data;
            $scope.chapterObj = $scope.chapterSummaryList.filter(function (chain) {
                return chain.id === $routeParams.chapterNo;
            })[0];
            $log.log("" + angular.toJson($scope.chapterObj) + "");
        }, function (response) {
            $scope.status = response.status;
        });
});

appRoot.controller('verseCtrl', function ($scope, $http, $routeParams, $log) {
    $scope.chapterSummaryList = [];
    $scope.chapterObj = {};
    $scope.verseObj = {};
    $log.log("" + $routeParams.chapterNo + "");
    $http({ method: 'GET', url: 'data/json/chapter-verse-detail-temp.json' }).
        then(function (response) {
            $scope.chapterSummaryList = response.data;
            $scope.chapterObj = $scope.chapterSummaryList.filter(function (chain) {
                return chain.id === $routeParams.chapterNo;
            })[0];

            $scope.verseObj = $scope.chapterObj.verses.filter(function (chain) {
                return chain.id === $routeParams.verseNo;
            })[0];

            $log.log("" + angular.toJson($scope.verseObj) + "");
        }, function (response) {
            $scope.status = response.status;
        });
});

appRoot.controller('wordMeaningCtrl', function ($scope, $http, $routeParams, $log) {
    $scope.wordMeaningList = [];
    $scope.wordMeaningObj = {};
    //$scope.verseObj = {};
    $log.log("" + $routeParams.wordId + "");
    $http({ method: 'GET', url: 'data/json/chapter-verse-word-meaning-temp.json' }).
        then(function (response) {
            $scope.wordMeaningList = response.data;
            $scope.wordMeaningObj = $scope.wordMeaningList.filter(function (chain) {
                return chain.id === $routeParams.wordId;
            })[0];

            $log.log("" + angular.toJson($scope.wordMeaningObj) + "");

            // $scope.verseObj = $scope.chapterObj.verses.filter(function (chain) {
            //     return chain.id === $routeParams.verseNo;
            // })[0];

            //$log.log("" + angular.toJson($scope.verseObj) + "");
        }, function (response) {
            $scope.status = response.status;
        });
});

appRoot.controller('IndexCtrl', function ($scope) {
    $scope.Students = [{ "Id": "stud1", "FirstName": "FirstName1", "LastName": "LastName1" }, { "Id": "stud2", "FirstName": "FirstName2", "LastName": "LastName2" }];

});

// appRoot.directive('myStudentDirective', function () {
//     return {
//         scope: {},
//         //     template: '<div>\
//         //     <span><b>Student Id</b> : {{myStudentId}}</span><br>\
//         //     <span><b>Student First Name</b> : {{myStudentFName}}</span><br>\
//         //     <span><b>Student Last Name</b> : {{myStudentLName}}</span><br>\
//         // </div>',
//         templateUrl: "studentDirective.template.html",
//         link: function (scope, element, attrs) {
//             console.log('==>> myStudentDirective ' + JSON.stringify(attrs));
//             var idd = attrs.id;
//             var firstNamee = attrs.firstName;
//             var lastNamee = attrs.lastName;
//             scope.myStudentId = idd;
//             scope.myStudentFName = firstNamee;
//             scope.myStudentLName = lastNamee;
//         }
//     }
// });

// appRoot.directive('myStudentListDirective', function () {
//     return {

//         scope: {},
//         templateUrl: 'studentListDirective.template.html',
//         link: function (scope, element, attrs) {

//             console.log('==>> myStudentListDirective ' + JSON.stringify(attrs));
//             var idd = scope.$eval(attrs.myStudentsList);
//             console.log('---------->>>>>>>>  scope.myStudentsList ' + idd);
//             // console.log('---------->>>>>>>>  attrs.StudentsList '+attrs.StudentsList);
//             console.log('---------->>>>>>>>  attrs.myyTitle ' + attrs.myyTitle);
//             console.log('---------->>>>>>>>  attrs.myyAge ' + attrs.myyAge);
//             //scope.StudentsList=idd;
//             scope.myTitle = attrs.myyTitle;
//             //scope.StudentsList=attrs.StudentsList;
//             scope.myyTitle = attrs.myyAge;
//         }
//     }
// });

// appRoot.directive('rowDirctive', function ($compile) {
//     return {
//         restrict: 'C',
//         replace: false,
//         link: function (scope, element) {
//             element.bind("click", function () {
//                 var rowId = "#" + scope.student.Id;
//                 var innerhtml = angular.element($compile('<span class="" ng-include="\'_StudentDetailsTabs.html\'"></span>')(scope));

//                 if ($(rowId + " td span").length === 0) {
//                     $(rowId + " td").html(innerhtml);
//                 }

//                 if ($(rowId).is(':hidden')) {
//                     $(rowId).slideDown("slow");
//                 } else {
//                     $(rowId).slideUp("slow");

//                 }
//             });
//         }
//     };
// });


// appRoot.controller('StudentDetailsCtrl', function ($scope) {
//     $scope.StudentPD = "student's personal details";
//     $scope.StudentED = "student's educational details";
//     $scope.StudentOD = "student's other details";

//     $scope.PersonalDetails = function () {
//         $scope.Details = "Personal Details"
//     };

//     $scope.EducationalDetails = function () {
//         $scope.Details = "Educational Details"
//     };
// });
