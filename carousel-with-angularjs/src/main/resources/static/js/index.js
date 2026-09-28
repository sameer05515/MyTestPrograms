angular.module('app', ['ui.bootstrap']).controller('CarouselDemoCtrl', function ($scope) {
    $scope.myInterval = 3000;

    /**
     * Demo slides (remote images). To use local files via /my.jsp, set image to e.g.
     * '/my.jsp?documentId=' + encodeURIComponent('C:/path/to/image.jpg')
     * and configure carousel.filesystem.root if you enabled path restriction.
     */
    $scope.slides = [
        {name: 'demo-1', image: 'https://picsum.photos/800/400?random=1'},
        {name: 'demo-2', image: 'https://picsum.photos/800/400?random=2'},
        {name: 'demo-3', image: 'https://picsum.photos/800/400?random=3'},
        {name: 'demo-4', image: 'https://picsum.photos/800/400?random=4'}
    ];
});
