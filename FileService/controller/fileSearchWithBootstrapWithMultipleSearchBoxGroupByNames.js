/* angular.module('scopeExample', []).controller('MyController',
		[ '$scope', function($scope) {
			$scope.username = 'World';

			$scope.sayHello = function() {
				$scope.greeting = 'Hello ' + $scope.username + '!';
			};
		} ]); */

//////////////////////
/* angular.module('scopeExample', []).filter("as", function($parse) {
  return function(value, context, path) {
    return $parse(path).assign(context, value);
  };
}); */
//////////////////////

/* angular
		.module('scopeExample', [])
		.controller(
				'fileSearchmultiController',
				[
						'$scope','$log',
						'$http',
						function($scope, $http, $log) {
							
							$scope.errorMessage = "";
							$scope.filteredItems=[];
							$scope.fileNames = [];
							
							
							// /////////////////
							
							$scope.loadResult =function() {
								
								$scope.errorMessage = "Loading data...";
								var urrrlll="http://127.0.0.1:8888/FileService/fileService.jsp?fileName="+$scope.foldername+"";
								$http(
										{
											method : 'POST',
											url :urrrlll
										})
										.success(function(data) {
											$scope.fileNames = data;
											$scope.errorMessage = "";
										})
										.error(
												function(data) {
													$scope.errorMessage += "There was an error fetching the list of available roles. Please reload the page and try again.";
												});
								
							};
						} ]); */

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

angular
    .module("scopeExample", ["angular.filter"])
    .controller("fileSearchController", [
        "$scope",
        "$log",
        "$http",
        function ($scope, $log, $http) {
            //$scope.choices = [{id: 'choice1'}, {id: 'choice2'}];
            //$scope.choices = [{"id":"choice1","name":"C:/Users/premendra.kumar1/Desktop/readings"},{"id":"choice2","name":"C:/Users/premendra.kumar1/Desktop/Text files"},{"id":"choice3","name":"C:/Users/premendra.kumar1/Desktop/Zetta Support"},{"id":"choice4","name":"C:/apache-tomcat-8.0.28"},{"id":"choice5","name":"C:/backup"},{"id":"choice6","name":"C:/CUSTOM INSTALLATIONS"}];
            //$scope.choices = [{"id":"choice1","name":"G:/05-july-2016"},{"id":"choice2","name":"G:/21-dec-2015-home-pc"},{"id":"choice3","name":"G:/21-dec-2015-office"},{"id":"choice4","name":"G:/APPLN_SERVERS-office"},{"id":"choice5","name":"G:/chef-repo1-master-11-july-2016"},{"id":"choice6","name":"G:/CUSTOM INSTALLATIONS-office"},{"id":"choice7","name":"G:/ebooks-home-pc"},{"id":"choice8","name":"G:/home-pc"},{"id":"choice9","name":"G:/KT-Documents"},{"id":"choice10","name":"G:/movies"},{"id":"choice11","name":"G:/mum 507"},{"id":"choice12","name":"G:/oppo-06-aug-17"},{"id":"choice13","name":"G:/oppo-home-pc"},{"id":"choice14","name":"G:/readings"},{"id":"choice15","name":"G:/readings-office"},{"id":"choice16","name":"G:/SELF"},{"id":"choice17","name":"G:/Sup"},{"id":"choice18","name":"G:/TUTORIAL"},{"id":"choice19","name":"H:/21-dec-2015"},{"id":"choice20","name":"H:/backup"},{"id":"choice21","name":"H:/ebooks"},{"id":"choice22","name":"H:/Executables"},{"id":"choice23","name":"H:/lenovoe420"},{"id":"choice24","name":"H:/PREM-BACKUP"},{"id":"choice25","name":"H:/rimjhim"},{"id":"choice26","name":"H:/softwares"}];
            //$scope.choices = [{"id":"choice1","name":"C:/Users/premendra.kumar1/Desktop/readings"},{"id":"choice2","name":"C:/Users/premendra.kumar1/Desktop/Text files"},{"id":"choice3","name":"C:/Users/premendra.kumar1/Desktop/Zetta Support"},{"id":"choice4","name":"C:/apache-tomcat-8.0.28"},{"id":"choice5","name":"C:/backup"},{"id":"choice6","name":"C:/CUSTOM INSTALLATIONS"}];

            $scope.choices = [{
                id: "choice1",
                name: "C:/tutorials/interview-questions"
            }];

            $scope.addNewChoice = function () {
                var newItemNo = $scope.choices.length + 1;
                $scope.choices.push({
                    id: "choice" + newItemNo
                });
            };

            $scope.removeChoice = function () {
                var lastItem = $scope.choices.length - 1;
                $scope.choices.splice(lastItem);
            };

            $scope.errorMessage = [];
            $scope.loadingMessage = [];

            $scope.FileToBeRenamedPath = "";
            $scope.FileToBeMovedPath = "";
            $scope.nayyaFileName = "";
            $scope.targetFolderPath = "";
            $scope.newmovingFileName="";


            $scope.openMovePopup = function (fileKaPath, targetFolderPathh,movingFileNametoRenamed) {
                //var sourceUrl=whichgame.getAttribute("href");
                //alert("sourceUrl : "+sourceUrl);
                $scope.FileToBeMovedPath = fileKaPath;
                $scope.targetFolderPath = targetFolderPathh;
                $scope.newmovingFileName=movingFileNametoRenamed;
                // var game = document.getElementById("objectToEmbed");
                //var clone = game.cloneNode(true);
                //clone.setAttribute("src", sourceUrl);
                // game.parentNode.replaceChild(clone, game);

                $("#moveFile").dialog({
                    width: 500,
                    height: 200,
                    autoOpen: true
                });
            };

            $scope.moveFile = function (fileKaPath, nayaFilePath,nayaMovingFileName) {

                if (confirm("Are you sure you want to move File?" + fileKaPath + " \n to new path ? " + nayaFilePath)) {
                    var urrrlll = "http://127.0.0.1:8888/FileService/fileMove.jsp";
                    $http({
                            method: "POST",
                            url: urrrlll,
                            params: {
                                fileName: fileKaPath,
                                targetPath: nayaFilePath,
                                newName : nayaMovingFileName
                            }
                        })
                        .success(function (data) {
                            /* $scope.fileNames = data;
												$scope.errorMessage = ""; */
                            if (data.status == "success") {
                                alert(
                                    "sucessfully moved file" +
                                    fileKaPath +
                                    " with name "+nayaMovingFileName+
                                    " data.status : " +
                                    data.status
                                );
                            } else if (data.status == "fail") {
                                alert(
                                    "error in renaming file" +
                                    fileKaPath +
                                    " data.status : " +
                                    data.status
                                );
                            }
                        })
                        .error(function (data) {
                            alert("error in renaming file" + fileKaPath);
                        });
                }

            };

            $scope.openRenamePopup = function (fileKaPath, fileNametoRenamed) {
                //var sourceUrl=whichgame.getAttribute("href");
                //alert("sourceUrl : "+sourceUrl);
                $scope.FileToBeRenamedPath = fileKaPath;
                $scope.newFileName = fileNametoRenamed;
                // var game = document.getElementById("objectToEmbed");
                //var clone = game.cloneNode(true);
                //clone.setAttribute("src", sourceUrl);
                // game.parentNode.replaceChild(clone, game);

                $("#renameFile").dialog({
                    width: 500,
                    height: 200,
                    autoOpen: true
                });
            };

            $scope.renameFile = function (fileKaPath, nayaFileName) {

                if (confirm("Are you sure you want to Rename Files?" + fileKaPath)) {
                    var urrrlll = "http://127.0.0.1:8888/FileService/fileRename.jsp";
                    $http({
                            method: "POST",
                            url: urrrlll,
                            params: {
                                fileName: fileKaPath,
                                newName: nayaFileName
                            }
                        })
                        .success(function (data) {
                            /* $scope.fileNames = data;
												$scope.errorMessage = ""; */
                            if (data.status == "success") {
                                alert(
                                    "sucessfully Renamed file" +
                                    fileKaPath +
                                    " data.status : " +
                                    data.status
                                );
                            } else if (data.status == "fail") {
                                alert(
                                    "error in renaming file" +
                                    fileKaPath +
                                    " data.status : " +
                                    data.status
                                );
                            }
                        })
                        .error(function (data) {
                            alert("error in renaming file" + fileKaPath);
                        });
                }

            };

            $scope.deleteFile = function (fileKaPath) {
                //alert(fileKaPath+"");
                if (confirm("Are you sure you want to Delete Files?" + fileKaPath)) {
                    var urrrlll = "http://127.0.0.1:8888/FileService/fileDelete.jsp";
                    $http({
                            method: "POST",
                            url: urrrlll,
                            params: {
                                fileName: fileKaPath
                            }
                        })
                        .success(function (data) {
                            /* $scope.fileNames = data;
												$scope.errorMessage = ""; */
                            if (data.status == "success") {
                                alert(
                                    "sucessfully deleted data" +
                                    fileKaPath +
                                    " data.status : " +
                                    data.status
                                );
                            } else if (data.status == "fail") {
                                alert(
                                    "error in deleting data" +
                                    fileKaPath +
                                    " data.status : " +
                                    data.status
                                );
                            }
                        })
                        .error(function (data) {
                            alert("error in deleting data" + fileKaPath);
                        });
                }
            };

            $scope.loadMultiResult = function () {
                $scope.loadingMessage = [];
                $scope.errorMessage = [];
                $scope.fileNames = [];
                angular.forEach($scope.choices, function (value, key) {
                    //console.log("name : "+value.name );
                    $scope.loadResult(value.name);
                });
                //$scope.loadingMessage = "Loading complete";
            };

            /////////////////
            //$scope.errorMessage = "";
            $scope.filteredItems = [];
            $scope.fileNames = [];

            // /////////////////

            $scope.loadResult = function (myfolder) {
                $scope.loadingMessage.push("Loading data....");

                var obj = {};
                obj.message = "Starting search for : " + myfolder;
                obj.type = "load";

                //$scope.errorMessage.push("Starting search for : "+myfolder);
                $scope.errorMessage.push(obj);
                console.log("Starting search for : " + myfolder);
                //var urrrlll="http://127.0.0.1:8888/FileService/fileService.jsp?fileName="+myfolder+"";
                var urrrlll = "http://127.0.0.1:8888/FileService/fileService.jsp";
                $http({
                        method: "POST",
                        /* url : 'http://127.0.0.1:8888/FileService/fileService.jsp?fileName=C:/Users/796412/Desktop/21-dec-2015/07-june-2016/practical-probabilistic-programming/' */
                        url: urrrlll,

                        params: {
                            fileName: myfolder,
                            extensions: $scope.checked_fruits
                        }
                    })
                    .success(function (data) {
                        //$scope.fileNames = data;
                        //alert(myfolder+"  "+data.length+"  "+data);
                        for (var i = 0; i < data.length; i++) {
                            if (axis.isObject(data[i])) {
                                data[i].showValue = true;
                                $scope.fileNames.push(data[i]);
                            }
                        }

                        //$scope.errorMessage.push("<span class=\"successMessage\">"+"Successfully got "+data.length+" result for "+myfolder+"</span>");
                        var obj = {};
                        obj.message =
                            "Successfully got " + data.length + " result for " + myfolder;
                        obj.type = "success";
                        //$scope.errorMessage.push("Successfully got "+data.length+" result for "+myfolder);
                        $scope.errorMessage.push(obj);

                        $scope.removeLoadingData();
                    })
                    .error(function (data) {
                        var obj = {};
                        obj.message = "Error while getting result for " + myfolder;
                        obj.type = "fail";
                        //$scope.errorMessage.push("Error while getting result for "+myfolder) ;
                        $scope.errorMessage.push(obj);

                        $scope.removeLoadingData();
                    });
            };

            $scope.removeLoadingData = function () {
                var lastItem = $scope.loadingMessage.length - 1;
                $scope.loadingMessage.splice(lastItem);
            };

            /**
             * Code to open popup basis of clicked item in front end
             **/

            $scope.testAnchor = [];
            $scope.titleOfPdf = "";

            $scope.openPopup = function (sourceUrl) {
                //var sourceUrl=whichgame.getAttribute("href");
                //alert("sourceUrl : "+sourceUrl);
                $scope.titleOfPdf = sourceUrl;
                var game = document.getElementById("objectToEmbed");
                var clone = game.cloneNode(true);
                clone.setAttribute("src", sourceUrl);
                game.parentNode.replaceChild(clone, game);

                $("#hello").dialog({
                    width: 1100,
                    height: 700,
                    autoOpen: true
                });
            };

            $scope.ppp = function (sourceUrl, givenFilePath, indexVal) {
                $scope.counterrr = indexVal;

                console.log("IndexVal : " + indexVal);
                console.log("$scope.counterrr" + $scope.counterrr);
                $scope.topic = $scope.filteredItems[$scope.counterrr];
                $scope.openPopup(sourceUrl);
                //$scope.idSelectedVote(givenFilePath);
                return false;
            };

            $scope.idCounter = 0;
            $scope.optionTexts = [];
            $scope.loadInitialSettings = function () {
                $("ul li").each(function () {
                    $(this)
                        .children("a")
                        .each(function () {
                            $(this).attr("id", "myanch" + idCounter);
                            $scope.optionTexts.push(this);
                            $("#" + "myanch" + idCounter).live("click", function () {
                                $scope.openPopup(this);
                                return false;
                            });
                            $scope.idCounter = $scope.idCounter + 1;
                        });
                });
            };

            //////////////////
            $scope.fruits = [
                ".pdf",
                ".mp3",
                ".mp4",
                ".exe",
                ".zip",
                ".png",
                ".jpeg",
                ".jpg",
                ".jar",
                ".rar",
                ".epub"
            ];
            //$scope.checked_fruits = [".pdf", ".mp3"];
            $scope.checked_fruits = [".pdf"];
            $scope.addFruit = function (fruit) {
                if ($scope.checked_fruits.indexOf(fruit) != -1) return;
                $scope.checked_fruits.push(fruit);
            };
            /////////////////

            ////////////////////////
            $scope.propertyName = "name";
            $scope.reverse = true;
            //$scope.friends = friends;

            $scope.sortBy = function (propertyName) {
                $log.log(
                    "$scope.propertyName : " +
                    $scope.propertyName +
                    "$scope.reverse : " +
                    $scope.reverse
                );
                $scope.reverse =
                    $scope.propertyName === propertyName ? !$scope.reverse : false;
                $scope.propertyName = propertyName;
                $log.log(
                    "propertyName : " +
                    propertyName +
                    "$scope.reverse : " +
                    $scope.reverse
                );
            };

            //Hghlight selected row
            $scope.filePathSelectedVote = null;
            $scope.setSelected = function (filePathSelectedVote) {
                $scope.filePathSelectedVote = filePathSelectedVote;
            };

            $scope.topic = {};
            //var $scope.counterrr = 1;
            $scope.counterrr = 0;
            $scope.filteredItems = [];
            $scope.next = function () {
                $scope.counterrr =
                    $scope.counterrr >= $scope.filteredItems.length - 1 ?
                    0 :
                    $scope.counterrr + 1;
                $scope.topic = $scope.filteredItems[$scope.counterrr];
                $scope.ppp(
                    "http://127.0.0.1:8888/FileService/my.jsp?documentId=" +
                    $scope.topic.filePath,
                    $scope.topic.filePath,
                    $scope.counterrr
                );
            };

            $scope.previous = function () {
                $scope.counterrr =
                    $scope.counterrr == 0 ?
                    $scope.filteredItems.length - 1 :
                    $scope.counterrr - 1;
                $scope.topic = $scope.filteredItems[$scope.counterrr];
                $scope.ppp(
                    "http://127.0.0.1:8888/FileService/my.jsp?documentId=" +
                    $scope.topic.filePath,
                    $scope.topic.filePath,
                    $scope.counterrr
                );
            };

            $scope.timerStarted = false;
            $scope.timerID = null;

            /* var nextAltFn= function(){
						//$scope.next();
						console.log("aaaaaaa=>>>>");
					}; */

            $scope.timerInterval = 5000;
            $scope.slideShowStart = function () {
                $scope.timerStarted = true;
                $scope.timerID = setTimeout(function nextAltFn() {
                    $scope.next();
                    console.log("aaaaaaa=>>>>");
                    $scope.timerID = setTimeout(nextAltFn, $scope.timerInterval);
                }, $scope.timerInterval);
                console.log("Timer started : timerID " + $scope.timerID);
                console.log("timerStarted " + $scope.timerStarted);
            };

            $scope.slideShowCancel = function () {
                $scope.timerStarted = false;
                clearTimeout($scope.timerID);
                $scope.timerID = null;

                console.log("Timer stopped : timerID " + $scope.timerID);
                console.log("timerStarted " + $scope.timerStarted);
            };
        }
    ])
    .directive("checkList", function () {
        return {
            scope: {
                list: "=checkList",
                value: "@"
            },
            link: function (scope, elem, attrs) {
                var handler = function (setup) {
                    var checked = elem.prop("checked");
                    var index = scope.list.indexOf(scope.value);

                    if (checked && index == -1) {
                        if (setup) elem.prop("checked", false);
                        else scope.list.push(scope.value);
                    } else if (!checked && index != -1) {
                        if (setup) elem.prop("checked", true);
                        else scope.list.splice(index, 1);
                    }
                };

                var setupHandler = handler.bind(null, true);
                var changeHandler = handler.bind(null, false);

                elem.bind("change", function () {
                    scope.$apply(changeHandler);
                });
                scope.$watch("list", setupHandler, true);
            }
        };
    });