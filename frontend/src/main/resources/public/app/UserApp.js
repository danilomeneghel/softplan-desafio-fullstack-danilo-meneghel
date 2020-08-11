var app = angular.module('userApp', ['ui.router', 'ngStorage', 'ui.bootstrap']);
var url_base = window.location.origin;

app.constant('urls', {
    BASE: url_base,
    USER_SERVICE_API: url_base + '/api/user'
});

app.config([
    '$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state('users', {
            url: '/users',
            templateUrl: 'partials/userLista',
            controller: 'UserController',
            controllerAs: 'ctrl',
            resolve: {
                users: function ($q, UserService) {
                    var deferred = $q.defer();
                    UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                    return deferred.promise;
                }
            }
        });
        $urlRouterProvider.otherwise('/users');
    }
]);
