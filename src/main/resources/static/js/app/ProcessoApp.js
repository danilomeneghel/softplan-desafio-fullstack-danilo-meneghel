var app = angular.module('processoApp', ['ui.router', 'ngStorage', 'ui.bootstrap']);
var url_base = window.location.origin;

app.constant('urls', {
    BASE: url_base,
    PROCESSO_SERVICE_API: url_base + '/api/processo',
    PARECER_SERVICE_API: url_base + '/api/parecer',
    USER_SERVICE_API: url_base + '/api/user-logged'
});

app.config([
    '$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider.state('processos', {
            url: '/processos',
            templateUrl: 'partials/processoLista',
            controller: 'ProcessoController',
            controllerAs: 'ctrl',
            resolve: {
                processos: function ($q, ProcessoService) {
                    var deferred = $q.defer();
                    ProcessoService.loadAllProcessos().then(deferred.resolve, deferred.resolve);
                    return deferred.promise;
                }
            }
        });
        $urlRouterProvider.otherwise('/processos');
    }
]);
