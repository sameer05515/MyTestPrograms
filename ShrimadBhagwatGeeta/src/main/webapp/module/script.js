// Code goes here
var appRoot = angular.module('smapp', ['ngRoute', 'ui.bootstrap']);

appRoot.config([
    '$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'partials/home.html', controller: 'homeCtrl' })
            .when('/chapter/:chapterNo', { templateUrl: 'partials/chapter.html' })
            .when('/chapter/:chapterNo/verse/:verseNo', { templateUrl: 'partials/verse.html' })
//            .when('/ngIndex', { templateUrl: 'ngIndex.html', controller: 'IndexCtrl' })
//            .when('/drag', { template: '<span my-draggable>Drag Me</span>' })
        //     .when('/docsTransclusionDirective', {
        //         template: '<div>\
        //     <my-dialog>Check out the contents, {{name}}! <br> <span my-draggable>Drag My panty!!</span></my-dialog>\
        //   </div>', controller: 'docsTransclusionDirective.Controller'
        //     })
        //     .when('/docsIsoFnBindExample', {
        //         template: '<div>\
        //   {{message}}\
        //   <my-dialog-in-docs-iso-fn-bind-example ng-hide="dialogIsHidden" on-close="hideDialog(message)">\
        //     Check out the contents, {{name}}!\
        //   </my-dialog-in-docs-iso-fn-bind-example>\
        // </div>', controller: 'docsIsoFnBindExample.Controller'
        //     })
        //     .when('/docsTabsExample',{template:'<my-tabs-docs-tabs-example>\
        //     <my-pane-docs-tabs-example title="Hello">\
        //       <p>Lorem ipsum dolor sit amet</p>\
        //     </my-pane-docs-tabs-example>\
        //     <my-pane-docs-tabs-example title="World">\
        //       <em>Mauris elementum elementum enim at suscipit.</em>\
        //       <p><a href ng-click="i = i + 1">counter: {{i || 0}}</a></p>\
        //     </my-pane-docs-tabs-example>\
        //     <my-pane-docs-tabs-example title="Premendra">\
        //       <p>Premendra is learning angularjs directives</p>\
        //     </my-pane-docs-tabs-example>\
        //   </my-tabs-docs-tabs-example>'})
            .otherwise({ redirectTo: '/' });
    }
]);

appRoot.controller('homeCtrl',function ($scope,$http) {

    $scope.chapterSummaryList=[];
    $http({method: 'GET', url: 'data/json/chapter-summary.json'}).
        then(function(response) {
            $scope.chapterSummaryList = response.data;
        }, function(response) {          
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
