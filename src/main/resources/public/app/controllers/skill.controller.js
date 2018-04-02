(function () {
  'use strict';

  angular
    .module('app')
    .controller('SkillController', SkillController);

  SkillController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'SkillService', 'ErrorController'];
  function SkillController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, SkillService, ErrorController) {

    var ctrl = this;

    $scope.skills = [];

    $rootScope.$on('SkillReload', function (event, message){
      ctrl.getScreenSkills();
    });
    
    ctrl.$onInit = function() {
      //ctrl.getAllSkills();
      ctrl.getScreenSkills();
    };

    ctrl.getAllSkills = function() {
      SkillService.getAllSkills(getSkillsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getScreenSkills = function() {
      SkillService.getSkillsByScreenId($rootScope.chosenScreenId, getSkillsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    var getSkillsSuccessCb = function(data, status, headers) {
      $scope.skills = data;
    }
    
    var getSkillsErrorCb = function(data, status, headers) {
      console.log(status);
    }
    
    $scope.addSkill = function() {
      var modal = $uibModal.open({
        resolve: {
          currentSkill: {}
        },
        size: 'md',
        templateUrl: 'app/views/skill-details.html',
        controller: SkillModalController
      });
      modal.result.then(function(){
        ctrl.getAllSkills();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editSkill = function(skill) {
      var skillToEdit = skill;
      var modal = $uibModal.open({
        resolve: {
          currentSkill: angular.copy(skillToEdit)
        },
        size: 'md',
        templateUrl: 'app/views/skill-details.html',
        controller: SkillModalController
      });
      modal.result.then(function(){
        ctrl.getAllSkills();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteSkill = function(skill) {
      $scope.currentSkill = skill;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete skill "{{ currentSkill.skillName }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentSkill.id)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, SkillService, ErrorController) {

          $scope.confirm = function(id) {
            SkillService.deleteSkillById(id, function(){
              //console.log("Successfully deleted skill");
              $uibModalInstance.close();
            }, ErrorController.httpGetErroCb);
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      modal.result.then(function(){
        ctrl.getAllSkills();
      }, ErrorController.httpGetErroCb);
    }
  }
  

  angular.module('app').controller('SkillModalController', SkillModalController);

  SkillModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentSkill', 'SkillService', 'ClientService'];
  function SkillModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentSkill, SkillService, ClientService) {
    var ctrl = this;

    $scope.convertIntegerFieldsToBoolean = function(skill) {
      skill.ignoreStaffing = (skill.ignoreStaffing === "1" ? true : false);
      return skill;
    }
    
    $scope.currentSkill = $scope.convertIntegerFieldsToBoolean(currentSkill);
    if (undefined == $scope.currentSkill.clientID) {
      $scope.currentSkill.clientID = {};
    }

    $scope.modalTitle = "";
    if (currentSkill.id) {
      $scope.modalTitle = "Edit skill";
    } else {
      $scope.modalTitle = "Add new skill";
    }
    
    $scope.clients = [];
    
    $scope.selectedClient = {};
    
    $scope.getClientById = function(id) {
      var client;
      angular.forEach($scope.clients, function(c) {
        if(c.ID === id) {
          client = c;
        }
      });
      return client;
    };
    
    ctrl.$onInit = function() {
      ctrl.getAllClients();
    };
    
    ctrl.getAllClients = function() {
      ClientService.getAllClients(getClientsSuccessCallback, getClientsErrorCallback);
    }

    var getClientsSuccessCallback = function(data, status, headers) {
      $scope.clients = data;
      $scope.selectedClient = $scope.getClientById(currentSkill.clientID);
      if (undefined == $scope.currentSkill.id || null == $scope.currentSkill.id) {
        $scope.selectedClient = $scope.getClientById("0");
      }
    }
    
    var getClientsErrorCallback = function(data, status, headers) {
      //console.log(status);
    }

    var saveSkillErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }
    
    $scope.save = function(currentSkill) {
      this.currentSkill.clientID = this.selectedClient.ID;
      currentSkill = $scope.convertBooleanFieldsToInt(currentSkill);
      if (currentSkill.id) {
        SkillService.editSkill(currentSkill, function() {
          //console.log("Successfully edited skill");
          $uibModalInstance.close();
        }, saveSkillErrorCb);
      } else {
        SkillService.saveSkill(currentSkill, function() {
          //console.log("Successfully saved skill");
          $uibModalInstance.close();
        }, saveSkillErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }
    
    $scope.convertBooleanFieldsToInt = function(skill) {
      skill.ignoreStaffing = (skill.ignoreStaffing == true ?  "1" : "0");
      return skill;
    }

  }
})();