'use strict';

angular.module('processoApp').controller('ProcessoController', [
    'ProcessoService', '$scope', function (ProcessoService, $scope) {
        var self = this;
        self.processo = {};

        self.submitProcesso = submitProcesso;
        self.getAllProcessos = getAllProcessos;
        self.getAllUsers = getAllUsers;
        self.createProcesso = createProcesso;
        self.updateProcesso = updateProcesso;
        self.removeProcesso = removeProcesso;

        $scope.editProcesso = editProcesso;
        $scope.reset = reset;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        
        function submitProcesso() {
            if (self.processo.id === undefined || self.processo.id === null) {
                console.log('Criando novo processo', self.processo);
                createProcesso(self.processo);
            } else {
                console.log('Atualizando processo com id ', self.processo.id);
                updateProcesso(self.processo, self.processo.id);
            }
        }
        
        function createProcesso(processo) {
            if(finalizadores(processo)) {
                ProcessoService.createProcesso(processo).then(
                    function (response) {
                        console.log('Processo criado com sucesso!', response);
                        self.errorMessage = '';
                        self.successMessage = 'Processo criado com sucesso!';
                        self.done = true;
                        self.processo = {};
                    },
                    function (errResponse) {
                        console.error('Erro ao criar processo');
                        self.successMessage = '';
                        self.errorMessage = 'Erro ao criar processo: ' + errResponse.data.errorMessage;
                    }
                );
            }
        }

        function updateProcesso(processo, id) {
            if(finalizadores(processo)) {
                ProcessoService.updateProcesso(processo, id).then(
                    function (response) {
                        console.log('Processo atualizado com sucesso', response);
                        self.errorMessage = '';
                        self.successMessage = 'Processo atualizado com sucesso';
                        self.done = true;
                    },
                    function (errResponse) {
                        console.error('Erro ao atualizar processo');
                        self.successMessage = '';
                        self.errorMessage = 'Erro ao atualizar processo ' + errResponse.data;
                    }
                );
            }
        }

        function removeProcesso(id) {
            ProcessoService.removeProcesso(id).then(
                function () {
                    console.log('Processo ' + id + ' removida com sucesso');
                    self.errorMessage = '';
                    self.successMessage = 'Processo removida com sucesso!';
                },
                function (errResponse) {
                    console.error('Erro ao remover processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }

        function getAllProcessos() {
            return ProcessoService.getAllProcessos();
        }
        
        function getAllUsers() {
            return ProcessoService.getAllUsers();
        }

        function editProcesso(id) {
            self.successMessage = '';
            self.errorMessage = '';
            ProcessoService.getProcesso(id).then(
                function (processo) {
                    self.processo = processo;
                },
                function (errResponse) {
                    console.error('Erro ao enviar o processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.processo = {};
        }
        
        function finalizadores(processo) {
            processo.users = [];
            if(processo.finalizadores != null) {
                Object.values(processo.finalizadores).forEach(id => {
                    var user = {id:null};
                    user.id = id;
                    processo.users.push(user);
                });
                return processo.users;
            } else {
                self.successMessage = '';
                self.errorMessage = 'É necessário selecionar ao menos um finalizador';
                return false;
            }
        }

    }]);