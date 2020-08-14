'use strict';

angular.module('processoApp').factory('ProcessoService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllProcessos: loadAllProcessos,
            loadAllFinalizadores: loadAllFinalizadores,
            getAllProcessos: getAllProcessos,
            getAllFinalizadores: getAllFinalizadores,
            getProcesso: getProcesso,
            createProcesso: createProcesso,
            updateProcesso: updateProcesso,
            removeProcesso: removeProcesso
        };

        return factory;

        function loadAllProcessos() {
            $localStorage.processos = "";
            var deferred = $q.defer();
            $http.get(urls.PROCESSO_SERVICE_API).then(
                function (response) {
                    console.log('Processos carregados com sucesso', response.data);
                    $localStorage.processos = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Erro ao carregar processos');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function loadAllFinalizadores() {
            $localStorage.finalizadores = "";
            var deferred = $q.defer();
            $http.get(urls.FINALIZADOR_SERVICE_API).then(
                function (response) {
                    console.log('Finalizadores carregados com sucesso', response.data);
                    $localStorage.finalizadores = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Erro ao carregar finalizadores');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
        function getAllProcessos() {
            return $localStorage.processos;
        }
        
        function getAllFinalizadores() {
            return $localStorage.finalizadores;
        }

        function getProcesso(id) {
            $localStorage.processo = "";
            var deferred = $q.defer();
            $http.get(urls.PROCESSO_SERVICE_API + "/" + id).then(
                function (response) {
                    delete response.data.users;
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

        function createProcesso(processo) {
            var deferred = $q.defer();
            $http.post(urls.PROCESSO_SERVICE_API, processo).then(
                function (response) {
                    loadAllProcessos();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao criar processo: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function updateProcesso(processo, id) {
            var deferred = $q.defer();
            $http.put(urls.PROCESSO_SERVICE_API + "/" + id, processo).then(
                function (response) {
                    loadAllProcessos();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao atualizar processo: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function removeProcesso(id) {
            if(confirm('Tem certeza que deseja excluir esse item?')) {
                var deferred = $q.defer();
                $http.delete(urls.PROCESSO_SERVICE_API + "/" + id).then(
                    function (response) {
                        loadAllProcessos();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Erro ao remover a processo com id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            }
            return deferred.promise;
        }
    }]);