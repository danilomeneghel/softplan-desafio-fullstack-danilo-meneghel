var app = angular.module('processoApp', ['ui.router', 'ngStorage', 'ui.bootstrap', 'ngSanitize', 'ui.select']);
var url_base = window.location.origin;

app.constant('urls', {
    BASE: url_base,
    PROCESSO_SERVICE_API: url_base + '/api/processo',
    PARECER_SERVICE_API: url_base + '/api/parecer',
    FINALIZADOR_SERVICE_API: url_base + '/api/finalizador'
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
                    ProcessoService.loadAllFinalizadores().then(deferred.resolve, deferred.resolve);
                    return deferred.promise;
                }
            }
        });
        $urlRouterProvider.otherwise('/processos');
    }
]);

app.controller("ModalController", function($scope, $uibModal) {
    var modalInstance;
    $scope.openModal = function(id) {
        var data;
        if(id != undefined && id != null)
            data = $scope.editProcesso(id);
        else
            data = $scope.reset();
        
        modalInstance = $uibModal.open({
            scope: $scope,
            templateUrl: 'partials/processoForm',
            controller: 'ProcessoController',
            size: 'md',
            resolve: {
                items: function() {
                    return data;
                }
            }
        });
    };
    $scope.cancel = function () {
        modalInstance.close("closed result");
    };
});

app.filter('propsFilter', function() {
    return function(items, props) {
      var out = [];
  
        if (angular.isArray(items)) {
            var keys = Object.keys(props);

            items.forEach(function(item) {
                var itemMatches = false;

                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            out = items;
        }

        return out;
    };
});
