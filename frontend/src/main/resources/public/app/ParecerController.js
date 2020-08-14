'use strict';

angular.module('parecerApp').controller('ParecerController', [
    'ParecerService', '$scope', function (ParecerService, $scope) {
        var self = this;
        self.processo = {};

        self.submitParecer = submitParecer;
        self.getAllProcessoParecer = getAllProcessoParecer;
        self.createParecer = createParecer;
        self.updateParecer = updateParecer;

        $scope.enviarParecer = enviarParecer;
        $scope.reset = reset;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitParecer() {
            if (self.processo.pareceres[0].id === undefined || self.processo.pareceres[0].id === null) {
                console.log('Criando novo parecer', self.processo);
                createParecer(self.processo);
            } else {
                console.log('Atualizando parecer com id ', self.processo.pareceres[0].id);
                updateParecer(self.processo, self.processo.pareceres[0].id);
            }
        }
        
        function createParecer(processo) {
            ParecerService.createParecer(processo).then(
                function (response) {
                    console.log('Parecer criado com sucesso!', response);
                    self.errorMessage = '';
                    self.successMessage = 'Parecer criado com sucesso!';
                    self.done = true;
                    self.processo = {};
                },
                function (errResponse) {
                    console.error('Erro ao criar parecer');
                    self.successMessage = '';
                    self.errorMessage = 'Erro ao criar parecer: ' + errResponse.data.errorMessage;
                }
            );
        }

        function updateParecer(processo, id) {
            ParecerService.updateParecer(processo, id).then(
                function (response) {
                    console.log('Parecer atualizado com sucesso', response);
                    self.errorMessage = '';
                    self.successMessage = 'Parecer atualizado com sucesso';
                    self.done = true;
                },
                function (errResponse) {
                    console.error('Erro ao atualizar parecer');
                    self.successMessage = '';
                    self.errorMessage = 'Erro ao atualizar parecer ' + errResponse.data;
                }
            );
        }

        function getAllProcessoParecer() {
            return ParecerService.getAllProcessoParecer();
        }
        
        function enviarParecer(id) {
            self.successMessage = '';
            self.errorMessage = '';
            ParecerService.getProcesso(id).then(
                function (processo) {
                    self.processo = processo;
                },
                function (errResponse) {
                    console.error('Erro ao enviar o parecer ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.processo = {};
        }
        
    }]);