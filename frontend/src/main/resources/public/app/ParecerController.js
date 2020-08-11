'use strict';

angular.module('parecerApp').controller('ParecerController', [
    'ParecerService', '$scope', function (ParecerService, $scope) {
        var self = this;
        self.parecer = {};
        self.pareceres = [];
        self.parecer = {};

        self.submitParecer = submitParecer;
        self.getAllProcessoParecer = getAllProcessoParecer;
        self.createParecer = createParecer;
        self.updateParecer = updateParecer;
        self.enviarParecer = enviarParecer;
        self.processoParecer = processoParecer;
        self.reset = reset;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitParecer() {
            if (self.processo.pareceres[0].id === undefined || self.processo.pareceres[0].id === null) {
                console.log('Criando novo parecer', self.processo);
                createParecer(self.processo);
            } else {
                console.log('Atualizando parecer com id ', self.processo.id);
                updateParecer(self.processo, self.processo.pareceres[0].id);
            }
        }
        
        function createParecer(processo) {
            ParecerService.createParecer(processo).then(
                function (response) {
                    console.log('Parecer criado com sucesso!');
                    self.successMessage = 'Parecer criado com sucesso!';
                    self.errorMessage = '';
                    self.done = true;
                    self.parecer = {};
                    $scope.parecerForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao criar parecer');
                    self.errorMessage = 'Erro ao criar parecer: ' + errResponse.data.errorMessage;
                    self.successMessage = '';
                }
            );
        }

        function updateParecer(processo, id) {
            ParecerService.updateParecer(processo, id).then(
                function (response) {
                    console.log('Parecer atualizado com sucesso');
                    self.successMessage = 'Parecer atualizado com sucesso';
                    self.errorMessage = '';
                    self.done = true;
                    $scope.parecerForm.$setPristine();
                },
                function (errResponse) {
                    console.error('Erro ao atualizar parecer');
                    self.errorMessage = 'Erro ao atualizar parecer ' + errResponse.data;
                    self.successMessage = '';
                }
            );
        }

        function getAllProcessoParecer() {
            return ParecerService.getAllProcessoParecer();
        }
        
        function processoParecer(id) {
            self.successMessage = '';
            self.errorMessage = '';
            ParecerService.getProcesso(id).then(
                function (processo) {
                    self.processo = processo;
                },
                function (errResponse) {
                    console.error('Erro ao editar processo ' + id + ', Erro :' + errResponse.data);
                }
            );
        }

        function enviarParecer(id) {
            processoParecer(id);
        }
        
        function reset(id) {
            self.successMessage = '';
            self.errorMessage = '';
            $scope.parecerForm.$setPristine();
        }
        
    }]);