angular.module('app', ['ui.bootstrap']);
function CarouselDemoCtrl($scope){
  $scope.myInterval = 3000;
  /* $scope.slides = [
    {
      image: 'http://lorempixel.com/400/200/'
    },
    {
      image: 'http://lorempixel.com/400/200/food'
    },
    {
      image: 'http://lorempixel.com/400/200/sports'
    },
    {
      image: 'http://lorempixel.com/400/200/people'
    }
  ]; */
  
  $scope.slides=[{"name":"1.jpg" , "image":"http://127.0.0.1:8888/carousel-with-angularjs/my.jsp?documentId=C:/Users/796412/Desktop/wallpaper/1.jpg"} , {"name":"2.jpg" , "image":"http://127.0.0.1:8888/carousel-with-angularjs/my.jsp?documentId=C:/Users/796412/Desktop/wallpaper/2.jpg"} , {"name":"3.jpg" , "image":"http://127.0.0.1:8888/carousel-with-angularjs/my.jsp?documentId=C:/Users/796412/Desktop/wallpaper/3.jpg"},{"name":"3.jpg" , "image":"http://127.0.0.1:8888/carousel-with-angularjs/my.jsp?documentId=C:/Users/796412/Desktop/wallpaper/umaid-bhawan-hall.jpg"}];
}