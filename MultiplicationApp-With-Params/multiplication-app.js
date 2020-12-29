var ngModule = angular.module('MultiplicationApp', [])

ngModule.directive('multiplicationTable', [function() {
  return {
    templateUrl : 'multiplication-table-tpl.html',
    controllerAs : 'ctrl',
    transclude: true,
    bindToController: true,
    scope: {
      x : '=',
      y : '='
    },
    controller : function() {
      var x = this.x || 0;
      var y = this.y || 0;

      var table = this.rows = [];
      for(var i=0;i<y;i++) {
        var array = table[i] = [];
        for(var j=0;j<x;j++) {
          array.push(1); 
        }
      }
    }
  }
}]);

ngModule.directive('multiplicationCell', [function() {
  return {
    controllerAs : 'multiplication',
    controller : ['$attrs', '$scope', function($attrs, $scope) {
      this.x = $scope.$eval($attrs.x);
      this.y = $scope.$eval($attrs.y);
      this.value = this.x * this.y;
    }]
  };
}])