'use strict';

angular.module('userApp').controller('UserController', [
    'UserService', '$scope', '$uibModal', function (UserService, $scope) {
        var self = this;
        self.user = {};

        self.submitUser = submitUser;
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;

        $scope.editUser = editUser;
        $scope.reset = reset;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitUser() {
            if (self.user.id === undefined || self.user.id === null) {
                console.log('Criando novo usuário');
                createUser(self.user);
            } else {
                updateUser(self.user, self.user.id);
                console.log('Atualizado usuário com id ', self.user.id);
            }
        }

        function createUser(user) {
            UserService.createUser(user).then(
                function (response) {
                    console.log('Usuário criado com sucesso!');
                    self.errorMessage = '';
                    self.successMessage = 'Usuário criado com sucesso!';
                    self.done = true;
                    self.user = {};
                },
                function (errResponse) {
                    console.error('Erro ao criar usuário');
                    self.successMessage = '';
                    self.errorMessage = 'Erro ao criar usuário: ' + errResponse.data.errorMessage;
                }
            );
        }

        function updateUser(user, id) {
            UserService.updateUser(user, id).then(
                function (response) {
                    console.log('Usuário atualizado com sucesso!', response);
                    self.errorMessage = '';
                    self.successMessage = 'Usuário atualizado com sucesso!';
                    self.done = true;
                },
                function (errResponse) {
                    console.error('Erro ao atualizar usuário');
                    self.successMessage = '';
                    self.errorMessage = 'Erro ao atualizar usuário ' + errResponse.data;
                }
            );
        }

        function removeUser(id) {
            UserService.removeUser(id).then(
                function () {
                    console.log('Usuário ' + id + ' removido com sucesso');
                    self.errorMessage = '';
                    self.successMessage = 'Usuário removido com sucesso!';
                },
                function (errResponse) {
                    console.error('Erro ao remover usuário ' + id + ', Erro :' + errResponse.data);
                }
            );
        }

        function getAllUsers() {
            return UserService.getAllUsers();
        }

        function editUser(id) {
            self.successMessage = '';
            self.errorMessage = '';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                },
                function (errResponse) {
                    console.error('Erro enviar o usuário ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.user = {};
        }
		
    }]);