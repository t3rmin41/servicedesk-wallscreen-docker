(function () {
  'use strict';

  angular
    .module('app')
    .controller('AmbasadorController', AmbasadorController);

  AmbasadorController.$inject = ['$rootScope', '$scope', '$cookies', '$route', '$routeParams', '$uibModal', '$location', 'AmbasadorService', 'ScreenService', 'ErrorController'];

  function AmbasadorController($rootScope, $scope, $cookies, $route, $routeParams, $uibModal, $location, AmbasadorService, ScreenService, ErrorController) {

    var ctrl = this;
    
    $scope.ambasadors = [];
    
    $scope.tableHeader = {
            "name" : "Name",
            "avayaID" : "Avaya ID",
            "phoneID" : "Phone ID",
            "title" : "Associated screen",
            "ENmember" : "ENmember",
            "ENSME" : "ENSME",
            "Lowerskill" : "Lowerskill",
            "hideAccount" : "hideAccount"
    };

    $scope.sort = {
            sortingOrder : 'name',
            reverse : false
    };

    $scope.selectedColumn = function (column) {
      if (column == $scope.sort.sortingOrder) {
          return ('fa fa-chevron-' + (($scope.sort.reverse) ? 'down' : 'up'));
      }
      else {
          return 'fa fa-sort'
      }
   };

    $scope.changeSorting = function(column) {
      var sort = $scope.sort;
      if (sort.sortingOrder == column) {
        sort.reverse = !$scope.sort.reverse;
      } else {
        sort.sortingOrder = column;
        sort.reverse = false;
      }
    };
    
    $rootScope.$on('AmbasadorReload', function (event, message){
      ctrl.getScreenAmbasadors();
    });
    
    ctrl.$onInit = function() {
      //ctrl.getAllAmbasadors();
      ctrl.getScreenAmbasadors();
    };

    ctrl.getAllAmbasadors = function() {
      AmbasadorService.getAllAmbasadors(getAmbasadorsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getScreenAmbasadors = function() {
      AmbasadorService.getAmbasadorsByScreenId($rootScope.chosenScreenId, getAmbasadorsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    var getAmbasadorsSuccessCb = function(data, status, headers) {
      $scope.ambasadors = data;
    }
    
    var getAmbasadorsErrorCb = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.addAmbasador = function() {
      var modal = $uibModal.open({
        resolve: {
          currentAmbasador: {}
        },
        size: 'md',
        templateUrl: 'app/views/ambasador-details.html',
        controller: AmbasadorModalController
      });
      modal.result.then(function(){
        ctrl.getAllAmbasadors();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editAmbasador = function(ambasador) {
      var ambasadorToEdit = ambasador;
      var modal = $uibModal.open({
        resolve: {
          currentAmbasador: angular.copy(ambasadorToEdit)
        },
        size: 'md',
        templateUrl: 'app/views/ambasador-details.html',
        controller: AmbasadorModalController
      });
      modal.result.then(function(){
        ctrl.getAllAmbasadors();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteAmbasador = function(ambasador) {
      $scope.currentAmbasador = ambasador;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete "{{ currentAmbasador.name }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentAmbasador.id)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, AmbasadorService, ErrorController) {

          $scope.confirm = function(id) {
            AmbasadorService.deleteAmbasadorById(id, function(){
              //console.log("Successfully deleted ambasador");
              $uibModalInstance.close();
            }, ErrorController.httpGetErroCb);
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      
      modal.result.then(function(){
        ctrl.getAllAmbasadors();
      }, ErrorController.httpGetErroCb);
    }

  }
  
  angular.module('app').controller('AmbasadorModalController', AmbasadorModalController);

  AmbasadorModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentAmbasador', 'AmbasadorService', 'ScreenService'];
  function AmbasadorModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentAmbasador, AmbasadorService, ScreenService) {
    var ctrl = this;

    $scope.convertIntegerFieldsToBoolean = function(ambasador) {
      ambasador.ENmember = (ambasador.ENmember === "1" ?  true : false);
      ambasador.ENSME = (ambasador.ENSME === "1" ? true : false);
      ambasador.hideAccount = (ambasador.hideAccount === "1" ? true : false);
      ambasador.Lowerskill = (ambasador.Lowerskill === "1" ? true : false);
      return ambasador;
    }

    $scope.currentAmbasador = $scope.convertIntegerFieldsToBoolean(currentAmbasador);
    
    $scope.modalTitle = "";
    if (currentAmbasador.id) {
      $scope.modalTitle = "Edit ambasador";
    } else {
      $scope.modalTitle = "Add new ambasador";
    }
    
    if (undefined == $scope.currentAmbasador.screen) {
      $scope.currentAmbasador.screen = { id: "0", title: "None" };
    }

    $scope.screens = [];
    
    ctrl.$onInit = function() {
      ctrl.getAllScreens();
    };
    
    ctrl.getAllScreens = function() {
      ScreenService.getAllScreens(getScreensSuccessCallback, getScreensErrorCallback);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      $scope.screens = data;
      if (undefined == $scope.currentAmbasador.screen.id) {
        $scope.currentAmbasador.screen.id = 0;
      }
    }
    
    var getScreensErrorCallback = function(data, status, headers) {
      //console.log(status);
    }
    
    var saveAmbasadorErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }

    $scope.save = function(currentAmbasador) {
      currentAmbasador = $scope.convertBooleanFieldsToInt(currentAmbasador);
      if (currentAmbasador.id) {
        AmbasadorService.editAmbasador(currentAmbasador, function() {
          //console.log("Successfully edited ambasador");
          $uibModalInstance.close();
        }, saveAmbasadorErrorCb);
      } else {
        AmbasadorService.saveAmbasador(currentAmbasador, function() {
          //console.log("Successfully saved ambasador");
          $uibModalInstance.close();
        }, saveAmbasadorErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }
    
    $scope.convertBooleanFieldsToInt = function(ambasador) {
      ambasador.ENmember = (ambasador.ENmember == true ?  "1" : "0");
      ambasador.ENSME = (ambasador.ENSME == true ?  "1" : "0");
      ambasador.hideAccount = (ambasador.hideAccount == true ?  "1" : "0");
      ambasador.Lowerskill = (ambasador.Lowerskill == true ?  "1" : "0");
      return ambasador;
    }
    

  }

})();