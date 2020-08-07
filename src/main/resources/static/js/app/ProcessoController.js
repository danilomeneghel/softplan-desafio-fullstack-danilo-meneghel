'use strict';

angular.module('processoApp').controller('ProcessoController', [
    'ProcessoService', '$scope', function (ProcessoService, $scope) {
        var self = this;
        self.processo = {};
        self.processos = [];
        self.parecer = {};

        self.submitProcesso = submitProcesso;
        self.getAllProcessos = getAllProcessos;
        self.getAllUsers = getAllUsers;
        self.createProcesso = createProcesso;
        self.updateProcesso = updateProcesso;
        self.removeProcesso = removeProcesso;
        self.editProcesso = editProcesso;
        self.processoParecer = processoParecer;
        self.resultadoParecer = resultadoParecer;
        self.reset = reset;
        self.resetParecer = resetParecer;
		self.divCollapse = divCollapse;
		self.class = 'fa fa-plus';
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitProcesso() {
            if (self.processo.id === undefined || self.processo.id === null) {
                console.log('Criando novo processo');
                createProcesso(self.processo);
            } else {
                updateProcesso(self.processo, self.processo.id);
                console.log('Atualizando processo com id ', self.processo.id);
            }
        }
        
        function createProcesso(processo) {
            processo.pareceres = [];
            Object.values(processo.finalizador).forEach(id => {
                var user = {id:null};
                var parecer = {user:null};
                user.id = id;
                parecer.user = user;
                processo.pareceres.push(parecer);
            });

            ProcessoService.createProcesso(processo).then(
                function (response) {
                    console.log('Processo criado com sucesso!');
                    console.log(processo);

                    self.successMessage = 'Processo criado com sucesso!';
                    self.errorMessage = '';
                    self.done = true;
                    self.processo = {};
                    $scope.processoForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao criar processo');
                    self.errorMessage = 'Erro ao criar processo: ' + errResponse.data.errorMessage;
                    self.successMessage = '';
                }
            );
        }

        function updateProcesso(processo, id) {
            ProcessoService.updateProcesso(processo, id).then(
                function (response) {
                    console.log('Processo atualizado com sucesso');
                    self.successMessage = 'Processo atualizado com sucesso';
                    self.errorMessage = '';
                    self.done = true;
                    $scope.processoForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao atualizar processo');
                    self.errorMessage = 'Erro ao atualizar processo ' + errResponse.data;
                    self.successMessage = '';
                }
            );
        }

        function removeProcesso(id) {
            ProcessoService.removeProcesso(id).then(
                function () {
                    console.log('Processo ' + id + ' removida com sucesso');
                    self.successMessage = 'Processo removida com sucesso!';
                    self.errorMessage = '';
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
			//$(".collapse").collapse('show');
            ProcessoService.getProcesso(id).then(
                function (processo) {
                    self.processo = processo;
                },
                function (errResponse) {
                    console.error('Erro ao editar processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function processoParecer(id) {
        	self.successMessage = '';
            self.errorMessage = '';
            ProcessoService.getProcesso(id).then(
                function (response) {
                    self.processo = response;
                },
                function (errResponse) {
                    console.error('Erro ao localizar processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function resultadoParecer() {
            return ProcessoService.resultadoParecer();
        }

        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.processo = {};
            $scope.processoForm.$setPristine();
        }
        
        function resetParecer() {
            self.errorMessage = '';
            self.parecer = {};
            $scope.parecerForm.$setPristine();
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