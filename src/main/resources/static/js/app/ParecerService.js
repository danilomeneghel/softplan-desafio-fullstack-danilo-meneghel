'use strict';

angular.module('parecerApp').factory('ParecerService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllProcessoParecer: loadAllProcessoParecer,
            getAllProcessoParecer: getAllProcessoParecer,
            getParecer: getParecer,
            processoParecer: processoParecer,
            createParecer: createParecer,
            updateParecer: updateParecer
        };

        return factory;

        function loadAllProcessoParecer() {
            var deferred = $q.defer();
            $http.get(urls.PROCESSOPARECER_SERVICE_API).then(
                function (response) {
                    console.log('Processo e Parecer carregados com sucesso', response.data);
                    $localStorage.pareceres = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Erro ao carregar pareceres');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function processoParecer() {
            return $localStorage.parecer;
        }
        
        function getAllProcessoParecer() {
            return $localStorage.pareceres;
        }

        function getParecer(id) {
            $localStorage.parecer = "";
            $localStorage.processo = "";
            var deferred = $q.defer();
            $http.get(urls.PARECER_SERVICE_API + "/" + id).then(
                function (responseParecer) {
                    console.log('Parecer carregado com id :' + id);
                    deferred.resolve(responseParecer.data);
                },
                function (errResponse) {
                    console.error('Erro ao carregar a parecer com o id :' + id);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function createParecer(parecer) {
            var deferred = $q.defer();
            console.log(parecer);
            $http.post(urls.PROCESSOPARECER_SERVICE_API, parecer).then(
                function (response) {
                    loadAllProcessoParecer();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao criar parecer: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function updateParecer(parecer, id) {
            var deferred = $q.defer();
            console.log(parecer);
            $http.put(urls.PROCESSOPARECER_SERVICE_API + "/" + id, parecer).then(
                function (response) {
                    loadAllProcessoParecer();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao atualizar parecer: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

    }]);