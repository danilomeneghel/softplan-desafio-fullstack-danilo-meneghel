'use strict';

angular.module('userApp').controller('UserController', [
    'UserService', '$scope', function (UserService, $scope) {
        var self = this;
        self.user = {};
        self.users = [];

        self.submitUser = submitUser;
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.editUser = editUser;
        self.reset = reset;
		self.divCollapse = divCollapse;
		self.class = 'fa fa-plus';
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

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
                    self.successMessage = 'Usuário criado com sucesso!';
                    self.errorMessage = '';
                    self.done = true;
                    self.user = {};
                    $scope.userForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao criar usuário');
                    self.errorMessage = 'Erro ao criar usuário: ' + errResponse.data.errorMessage;
                    self.successMessage = '';
                }
            );
        }

        function updateUser(user, id) {
            UserService.updateUser(user, id).then(
                function (response) {
                    console.log('Usuário atualizado com sucesso!', response);
                    self.successMessage = 'Usuário atualizado com sucesso!';
                    self.errorMessage = '';
                    self.done = true;
                    $scope.userForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao atualizar usuário');
                    self.errorMessage = 'Erro ao atualizar usuário ' + errResponse.data;
                    self.successMessage = '';
                }
            );
        }

        function removeUser(id) {
            UserService.removeUser(id).then(
                function () {
                    console.log('Usuário ' + id + ' removido com sucesso');
                    self.successMessage = 'Usuário removido com sucesso!';
                    self.errorMessage = '';
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
			//$(".collapse").collapse('show');
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                },
                function (errResponse) {
                    console.error('Erro ao editar usuário ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.user = {};
            $scope.userForm.$setPristine();
        }
		
		function divCollapse() {
			$(".collapse").collapse('toggle');
		}
		
		$(".collapse").on('show.bs.collapse', function () {
			self.class = 'fa fa-minus';
		}).on('hide.bs.collapse', function () {
			self.class = 'fa fa-plus';
		});
    }]);