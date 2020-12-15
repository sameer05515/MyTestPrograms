angular.module('scopeExample', []).controller('MyController',
		[ '$scope', function($scope) {
			$scope.username = 'World';

			$scope.sayHello = function() {
				$scope.greeting = 'Hello ' + $scope.username + '!';
			};
		} ]);
		
//////////////////////
/* angular.module('scopeExample', []).filter("as", function($parse) {
  return function(value, context, path) {
    return $parse(path).assign(context, value);
  };
}); */
//////////////////////

angular
		.module('scopeExample', [])
		.controller(
				'fileSearchController',
				[
						'$scope',
						'$http',
						function($scope, $http) {
							// $scope.fileNames = [{name:'Agile Metrics in
							// Action.pdf'}];
							// $scope.fileNames = [{name:'Agile Metrics in
							// Action.pdf' ,
							// filePath:'C:/Users/796412/Desktop/readings/agile/Agile
							// Metrics in Action.pdf'} ,
							// {name:'AgileMethodologies.pdf' ,
							// filePath:'C:/Users/796412/Desktop/readings/agile/AgileMethodologies.pdf'}
							// , {name:'AgileMethods.pdf' ,
							// filePath:'C:/Users/796412/Desktop/readings/agile/AgileMethods.pdf'}
							// , {name:'Intro_to_Agile.pdf' ,
							// filePath:'C:/Users/796412/Desktop/readings/agile/Intro_to_Agile.pdf'}
							// , {name:'scrum_tutorial.pdf' ,
							// filePath:'C:/Users/796412/Desktop/readings/agile/scrum_tutorial.pdf'}];
							$scope.errorMessage = "";
							$scope.filteredItems=[];
							$scope.fileNames = [];
							
							
							// /////////////////
							
							$scope.loadResult =function() {
								
								$scope.errorMessage = "Loading data...";
								var urrrlll="http://127.0.0.1:9999/FileService/fileService.jsp?fileName="+$scope.foldername+"";
								$http(
										{
											method : 'POST',
											/* url : 'http://127.0.0.1:8888/FileService/fileService.jsp?fileName=C:/Users/796412/Desktop/21-dec-2015/07-june-2016/practical-probabilistic-programming/' */
											url :urrrlll
										})
										.success(function(data) {
											// set the list of roles with
											// $scope.roleList = data or
											// data.<SOMETHING>
											$scope.fileNames = data;
											$scope.errorMessage = "";
										})
										.error(
												function(data) {
													$scope.errorMessage += "There was an error fetching the list of available roles. Please reload the page and try again.";
												});
								
							};
							
							//$scope.loadResult();

							

							// //////////////////

							// $scope.sayHello = function() {
							// $scope.greeting = 'Hello ' + $scope.username +
							// '!';
							// };
						} ]);