'use strict';

angular.module('parecerApp').controller('ParecerController', [
    'ParecerService', '$scope', function (ParecerService, $scope) {
        var self = this;
        self.parecer = {};
        self.pareceres = [];
        self.parecer = {};

        self.submitParecer = submitParecer;
        self.getAllProcessoPareceres = getAllProcessoPareceres;
        self.createParecer = createParecer;
        self.updateParecer = updateParecer;
        self.removeParecer = removeParecer;
        self.editParecer = editParecer;
        self.processoParecer = processoParecer;
        self.resultadoParecer = resultadoParecer;
        self.reset = reset;
        self.resetParecer = resetParecer;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitParecer() {
            if (self.parecer.id === undefined || self.parecer.id === null) {
                console.log('Criando novo parecer');
                createParecer(self.parecer);
            } else {
                updateParecer(self.parecer, self.parecer.id);
                console.log('Atualizando parecer com id ', self.parecer.id);
            }
        }
        
        function createParecer(parecer) {
            ParecerService.createParecer(parecer).then(
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

        function updateParecer(parecer, id) {
            var update = {id:null, comentario:null, status:null};
            update.id = parecer.id;
            update.comentario = parecer.comentario;
            update.status = parecer.status;
            ParecerService.updateParecer(update, id).then(
                function (response) {
                    console.log('Parecer atualizado com sucesso', response);
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

        function removeParecer(id) {
            ParecerService.removeParecer(id).then(
                function () {
                    console.log('Parecer ' + id + ' removido com sucesso');
                    self.successMessage = 'Parecer removido com sucesso!';
                    self.errorMessage = '';
                },
                function (errResponse) {
                    console.error('Erro ao remover parecer ' + id + ', Erro :' + errResponse.data);
                }
            );
        }

        function getAllProcessoPareceres() {
            return ParecerService.getAllProcessoPareceres();
        }

        function editParecer(id) {
            self.successMessage = '';
            self.errorMessage = '';
            ParecerService.getParecer(id).then(
                function (parecer) {
                    self.parecer = parecer;
                },
                function (errResponse) {
                    console.error('Erro ao editar parecer ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function processoParecer(id) {
        	self.successMessage = '';
            self.errorMessage = '';
            ParecerService.getParecer(id).then(
                function (response) {
                    self.parecer = response;
                },
                function (errResponse) {
                    console.error('Erro ao localizar parecer ' + id + ', Erro :' + errResponse.data);
                }
            );
        }
        
        function resultadoParecer() {
            return ParecerService.resultadoParecer();
        }

        function reset() {
            self.successMessage = '';
            self.errorMessage = '';
            self.parecer = {};
            $scope.parecerForm.$setPristine();
        }
        
        function resetParecer() {
            self.errorMessage = '';
            self.parecer = {};
            $scope.parecerForm.$setPristine();
        }
		
    }]);