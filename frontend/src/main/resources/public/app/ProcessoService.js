'use strict';

angular.module('processoApp').factory('ProcessoService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllProcessos: loadAllProcessos,
            loadAllUsers: loadAllUsers,
            getAllProcessos: getAllProcessos,
            getAllUsers: getAllUsers,
            getProcesso: getProcesso,
            createProcesso: createProcesso,
            updateProcesso: updateProcesso,
            removeProcesso: removeProcesso
        };

        return factory;

        function loadAllProcessos() {
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
        
        function loadAllUsers() {
            var deferred = $q.defer();
            $http.get(urls.FINALIZADOR_SERVICE_API).then(
                function (response) {
                    console.log('Usuários carregado com sucesso', response.data);
                    $localStorage.users = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Erro ao carregar users');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function getAllUsers() {
            return $localStorage.users;
        }
                
        function getAllProcessos() {
            return $localStorage.processos;
        }
        
        function getProcesso(id) {
            $localStorage.processo = "";
            $localStorage.parecer = "";
            var deferred = $q.defer();
            $http.get(urls.PROCESSO_SERVICE_API + "/" + id).then(
                function (response) {
                    console.log('Processo carregado com id :' + id);
                    console.log(response);
                    $localStorage.processo = response;
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao carregar a processo com o id :' + id);
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