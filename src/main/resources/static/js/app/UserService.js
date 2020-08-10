'use strict';

angular.module('userApp').factory('UserService', [
    '$localStorage', '$http', '$q', 'urls',
    function ($localStorage, $http, $q, urls) {
        var factory = {
            loadAllUsers: loadAllUsers,
            getAllUsers: getAllUsers,
            getUser: getUser,
            createUser: createUser,
            updateUser: updateUser,
            removeUser: removeUser
        };

        return factory;

        function loadAllUsers() {
            var deferred = $q.defer();
            $http.get(urls.USER_SERVICE_API).then(
                function (response) {
                    console.log('Usuários carregado com sucesso');
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

        function getUser(id) {
            var deferred = $q.defer();
            $http.get(urls.USER_SERVICE_API + "/" + id).then(
                function (response) {
                    console.log('Usuário carregado com id :' + id);
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao carregar usuário com id :' + id);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function createUser(user) {
            var deferred = $q.defer();
            $http.post(urls.USER_SERVICE_API, user).then(
                function (response) {
                    loadAllUsers();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao criar usuário: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function updateUser(user, id) {
            var deferred = $q.defer();
            $http.put(urls.USER_SERVICE_API + "/" + id, user).then(
                function (response) {
                    loadAllUsers();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Erro ao atualizar usuário: ' + errResponse.data);
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function removeUser(id) {
            if(confirm('Tem certeza que deseja excluir esse item?')) {
                var deferred = $q.defer();
                $http.delete(urls.USER_SERVICE_API + "/" + id).then(
                    function (response) {
                        loadAllUsers();
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Erro ao remover o usuário com id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            }
            return deferred.promise;
        }
    }]);