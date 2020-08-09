var app = angular.module('parecerApp', ['ui.router', 'ngStorage', 'ui.bootstrap']);
var url_base = window.location.origin;

app.constant('urls', {
    BASE: url_base,
    PROCESSO_SERVICE_API: url_base + '/api/processo',
    PARECER_SERVICE_API: url_base + '/api/parecer',
    PROCESSOPARECER_SERVICE_API: url_base + '/api/processo-parecer'
});

app.config([
    '$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state('pareceres', {
            url: '/pareceres',
            templateUrl: 'partials/parecerLista',
            controller: 'ParecerController',
            controllerAs: 'ctrl',
            resolve: {
                pareceres: function ($q, ParecerService) {
                    var deferred = $q.defer();
                    ParecerService.loadAllPareceres().then(deferred.resolve, deferred.resolve);
                    return deferred.promise;
                }
            }
        });
        $urlRouterProvider.otherwise('/pareceres');
    }
]);
