angular
		.module('scopeExample', [])
		.controller(
				'fileSearchWithPopupController',
				[
						'$scope',
						'$http',
						function($scope, $http) {
							
							$scope.testAnchor=[];
							$scope.titleOfPdf="";
							$scope.errorMessage = "";
							$scope.searchFolder="";
							
							$scope.mytestuurl="http://127.0.0.1:8888/sharedServicesProvider/pdfContentViewer.jsp?fileName=D:/ebooks/GAUNTLT/curl-7.43.0/curl-7.43.0/docs/libcurl/opts/CURLOPT_DNS_USE_GLOBAL_CACHE.pdf";
							
							var searchFolderStr=$scope.searchFolder;
							$scope.filteredItems=[];
							$scope.fileNames = [];
							
							
							
							$scope.loadResult =function() {
								
								if(!$scope.searchFolder){
									$scope.errorMessage = "Enter folder";
									return;
								}
								$scope.filteredItems=[];
								$scope.fileNames = [];
								var urllll='http://127.0.0.1:9999/FileService/fileService.jsp?fileName='+$scope.searchFolder;
								//alert(urllll+"");
								$scope.errorMessage = "Loading data...";
								$http(
										{
											method : 'POST',
											url : urllll
										})
										.success(function(data) {
											// set the list of roles with
											// $scope.roleList = data or
											// data.<SOMETHING>
											$scope.fileNames = data;
											$scope.errorMessage = "";
											///////////////
											//$scope.loadInitialSettings();
											////////////////
										})
										.error(
												function(data) {
													$scope.errorMessage += "There was an error fetching the list of available roles. Please reload the page and try again.";
												});
								
							};
							
							//$scope.loadResult();
							
							////////////////////
//							$("#hello").dialog({
//								autoOpen : false
//							});
							
							$scope.openPopup=function (sourceUrl) {								
								//var sourceUrl=whichgame.getAttribute("href");
								//alert("sourceUrl : "+sourceUrl);
								$scope.titleOfPdf=sourceUrl;
								var game = document.getElementById("objectToEmbed");
								var clone = game.cloneNode(true);
								clone.setAttribute('src', sourceUrl);
								game.parentNode.replaceChild(clone, game);

								$("#hello").dialog({
									width : 1000,
									height : 600,
									autoOpen : true
								});								
							};
							
							$scope.ppp=function (sourceUrl){
								$scope.openPopup(sourceUrl);
								return false;
							};
							
							$scope.idCounter=0;
							$scope.optionTexts = [];
							$scope.loadInitialSettings=function(){
								
								$("ul li").each(function() {  
									$(this).children('a').each(function () {
										$(this).attr("id","myanch"+idCounter);
										$scope.optionTexts.push(this);
										$('#'+'myanch'+idCounter).live('click',function () {		
											$scope.openPopup(this);
											return false;
											});
										$scope.idCounter=$scope.idCounter+1;
									});
								});
								
							};
							
							/////////////////////////
						} ]);