'use strict';

angular.module('parecerApp').factory('ParecerService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllProcessoParecer: loadAllProcessoParecer,
            getAllProcessoParecer: getAllProcessoParecer,
            getProcesso: getProcesso,
            createParecer: createParecer,
            updateParecer: updateParecer
        };

        return factory;

        function loadAllProcessoParecer() {
            $localStorage.pareceres = "";
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
                
        function getAllProcessoParecer() {
            return $localStorage.pareceres;
        }

        function getProcesso(id) {
            $localStorage.processo = "";
            var deferred = $q.defer();
            $http.get(urls.PROCESSO_SERVICE_API + "/" + id).then(
                function (response) {
                    console.log('Processo carregado com id :' + id);
                    console.log(response);
                    $localStorage.processo = response;
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao carregar processo com o id :' + id);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function createParecer(processo) {
            var deferred = $q.defer();
            $http.post(urls.PROCESSOPARECER_SERVICE_API, processo).then(
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

        function updateParecer(processo, id) {
            var deferred = $q.defer();
            $http.put(urls.PROCESSOPARECER_SERVICE_API + "/" + id, processo.pareceres[0]).then(
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