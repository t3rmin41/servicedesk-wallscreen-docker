(function () {
  'use strict';

  angular
    .module('app')
    .controller('ClientController', ClientController);

  ClientController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ClientService', 'ErrorController'];
  function ClientController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, ClientService, ErrorController) {

    var ctrl = this;

    $scope.clients = [];
    
    $rootScope.$on('ClientReload', function (event, message){
      ctrl.getScreenClients();
    });
    
    ctrl.$onInit = function() {
      //ctrl.getEditableClients();
      ctrl.getScreenClients();
    };

    ctrl.getAllClients = function() {
      ClientService.getAllClients(getClientsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getScreenClients = function() {
      ClientService.getEditableClientsByScreenId($rootScope.chosenScreenId, getClientsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getEditableClients = function() {
      ClientService.getEditableClients(getClientsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    var getClientsSuccessCb = function(data, status, headers) {
      $scope.clients = data;
    }
    
    var getClientsErrorCb = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.addClient = function() {
      var modal = $uibModal.open({
        resolve: {
          currentClient: { skills : [] }
        },
        templateUrl: 'app/views/client-details.html',
        controller: ClientModalController,
        size: 'md'
      });
      modal.result.then(function(){
        ctrl.getEditableClients();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editClient = function(client) {
      var clientToEdit = client;
      var modalSize = client.skills.length > 0 ? 'lg' : 'md';
      var modal = $uibModal.open({
        resolve: {
          currentClient: angular.copy(clientToEdit)
        },
        templateUrl: 'app/views/client-details.html',
        controller: ClientModalController,
        size : modalSize
      });
      modal.result.then(function(){
        ctrl.getEditableClients();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteClient = function(client) {
      $scope.currentClient = client;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete client "{{ currentClient.clientName }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentClient.ID)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, ClientService, ErrorController) {

          $scope.confirm = function(id) {
            ClientService.deleteClientById(id, function(){
              //console.log("Successfully deleted client");
              $uibModalInstance.close();
            }, ErrorController.httpGetErroCb);
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      modal.result.then(function(){
        ctrl.getAllClients();
      }, function() {});
    }
  }
  

  angular.module('app').controller('ClientModalController', ClientModalController);

  ClientModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentClient', 'ClientService', 'ScreenService', 'SkillService'];
  function ClientModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentClient, ClientService, ScreenService, SkillService) {
    var ctrl = this;

    $scope.modalTitle = "";
    if (currentClient.ID) {
      $scope.modalTitle = "Edit client";
    } else {
      $scope.modalTitle = "Add new client";
    }
    
    $scope.convertIntegerFieldsToBoolean = function(client) {
      client.noPanel = (client.noPanel === "1" ? true : false);
      client.noSLA = (client.noSLA === "1" ? true : false);
      return client;
    }
    
    $scope.currentClient = $scope.convertIntegerFieldsToBoolean(currentClient);
    if (undefined == $scope.currentClient.screen) {
      $scope.currentClient.screen = {id: "0", title: "None"};
    }

    $scope.screens = [];
    $scope.showSkillTable = $scope.currentClient.skills.length > 0 ? true : false;

    ctrl.$onInit = function() {
      ctrl.getAllScreens();
    };
    
    ctrl.getAllScreens = function() {
      ScreenService.getAllScreens(getScreensSuccessCallback, getScreensErrorCallback);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      $scope.screens = data;
      if (undefined == $scope.currentClient.screen.id) {
        $scope.currentClient.screen.id = 0;
      }
    }
    
    var getScreensErrorCallback = function(data, status, headers) {
      //console.log(status);
    }

    var saveClientErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }

    $scope.save = function(currentClient) {
      currentClient = $scope.convertBooleanFieldsToInt(currentClient);
      if (currentClient.ID) {
        ClientService.editClient(currentClient, function() {
          //console.log("Successfully edited client");
          $uibModalInstance.close();
        }, saveClientErrorCb);
      } else {
        ClientService.saveClient(currentClient, function() {
          //console.log("Successfully saved client");
          $uibModalInstance.close();
        }, saveClientErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }
    
    $scope.convertBooleanFieldsToInt = function(client) {
      client.noPanel = client.noPanel == true ?  "1" : "0";
      client.noSLA = client.noSLA == true ?  "1" : "0";
      return client;
    }

  }
})();