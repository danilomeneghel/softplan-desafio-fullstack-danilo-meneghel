'use strict';

angular.module('parecerApp').factory('ParecerService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllPareceres: loadAllPareceres,
            getAllPareceres: getAllPareceres,
            getParecer: getParecer,
            processoParecer: processoParecer,
            resultadoProcesso: resultadoProcesso,
            createParecer: createParecer,
            updateParecer: updateParecer,
            removeParecer: removeParecer
        };

        return factory;

        function loadAllPareceres() {
            var deferred = $q.defer();
            $http.get(urls.PARECER_SERVICE_API).then(
                function (response) {
                    console.log('Pareceres carregados com sucesso', response.data);
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
        
        function resultadoProcesso() {
            return $localStorage.processo;
        }
        
        function getAllPareceres() {
            return $localStorage.pareceres;
        }

        function getParecer(id) {
            $localStorage.parecer = "";
            $localStorage.processo = "";
            var deferred = $q.defer();
            $http.get(urls.PARECER_SERVICE_API + "/" + id).then(
                function (responseParecer) {
                	$http.get(urls.USER_SERVICE_API).then(
            			function (responseUser) {
                			console.log('Parecer carregado com id :' + id);
                			deferred.resolve(responseParecer.data);
            			}
                    );
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
            $http.post(urls.PARECER_SERVICE_API, parecer).then(
                function (response) {
                    loadAllPareceres();
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
            $http.put(urls.PARECER_SERVICE_API + "/" + id, parecer).then(
                function (response) {
                    loadAllPareceres();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao atualizar parecer: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function removeParecer(id) {
            if(confirm('Tem certeza que deseja excluir esse item?')) {
                var deferred = $q.defer();
                $http.delete(urls.PARECER_SERVICE_API + "/" + id).then(
                    function (response) {
                        loadAllPareceres();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Erro ao remover a parecer com id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            }
            return deferred.promise;
        }
    }]);