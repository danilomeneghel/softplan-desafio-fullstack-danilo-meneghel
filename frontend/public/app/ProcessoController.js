'use strict';

angular.module('processoApp').controller('ProcessoController', [
    'ProcessoService', '$scope', function (ProcessoService, $scope) {
        var self = this;
        self.processo = {};

        self.submitProcesso = submitProcesso;
        self.getAllProcessos = getAllProcessos;
        self.getAllFinalizadores = getAllFinalizadores;
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
                    console.log('Processo ' + id + ' removido com sucesso');
                    self.errorMessage = '';
                    self.successMessage = 'Processo removido com sucesso!';
                },
                function (errResponse) {
                    console.error('Erro ao remover processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }

        function getAllProcessos() {
            return ProcessoService.getAllProcessos();
        }
        
        function getAllFinalizadores() {
            return ProcessoService.getAllFinalizadores();
        }

        function editProcesso(id) {
            self.successMessage = '';
            self.errorMessage = '';
            ProcessoService.getProcesso(id).then(
                function (processo) {
                    self.processo = processo;
                },
                function (errResponse) {
                    console.error('Erro ao carregar processo com ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.processo = {};
        }
        
        function finalizadores(processo) {
            if(processo.users == null) {
                self.successMessage = '';
                self.errorMessage = 'É necessário selecionar ao menos um finalizador';
                return false;
            } 
            return processo.users;
        }
        
    }]);