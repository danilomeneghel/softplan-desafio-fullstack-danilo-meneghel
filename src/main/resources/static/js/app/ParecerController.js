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
        self.editParecer = editParecer;
        self.processoParecer = processoParecer;
        self.reset = reset;
		
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        function submitParecer() {
            if (self.parecer.id === undefined || self.parecer.id === null) {
                console.log('Criando novo parecer', self.parecer);
                createParecer(self.parecer);
            } else {
                updateParecer(self.parecer, self.parecer.id);
                console.log('Atualizando parecer com id ', self.parecer.id);
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
        
        function reset(id) {
            processoParecer(id);
            self.successMessage = '';
            self.errorMessage = '';
            self.pareceres = {comentario:null};
            $scope.parecerForm.$setPristine();
        }
        
    }]);