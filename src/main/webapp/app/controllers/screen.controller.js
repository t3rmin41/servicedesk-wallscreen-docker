(function () {
  'use strict';

  angular
    .module('app')
    .controller('ScreenController', ScreenController);

  ScreenController.$inject = ['$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ScreenService', 'ErrorController'];
  function ScreenController($scope, $cookies, $routeParams, $uibModal, $location, ScreenService, ErrorController) {

    var ctrl = this;
    
    $scope.screens = [];

    ctrl.$onInit = function() {
      if (undefined != $cookies.get('token') && null != $cookies.get('token')) {
        ctrl.getEditableScreens();
      }
    };
    
    ctrl.getEditableScreens = function() {
      ScreenService.getEditableScreens(getScreensSuccessCallback, ErrorController.httpGetErroCb);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      //console.log(data);
      $scope.screens = data;
    }
    
    var getScreensErrorCallback = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.addScreen = function() {
      var modal = $uibModal.open({
        resolve: {
          currentScreen: {}
        },
        size: 'md',
        templateUrl: 'app/views/screen-details.html',
        controller: ScreenModalController
      });
      modal.result.then(function(){
        ctrl.getEditableScreens();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editScreen = function(screen) {
      var screenToEdit = screen;
      var modal = $uibModal.open({
        resolve: {
          currentScreen: angular.copy(screenToEdit)
        },
        size: 'md',
        templateUrl: 'app/views/screen-details.html',
        controller: ScreenModalController
      });

      modal.result.then(function(){
        ctrl.getEditableScreens();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteScreen = function(screen) {
      $scope.currentScreen = screen;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete screen "{{ currentScreen.title }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentScreen.id)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, ScreenService, ErrorController) {

          $scope.confirm = function(id) {
            ScreenService.deleteScreenById(id, function(){
              //console.log("Successfully deleted screen");
              $uibModalInstance.close();
            }, ErrorController.httpGetErroCb);
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      modal.result.then(function(){
        ctrl.getEditableScreens();
      }, ErrorController.httpGetErroCb);
    }
  }
  
  angular
    .module('app')
    .controller('ScreenModalController', ScreenModalController);

  ScreenModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentScreen', 'ScreenService'];
  function ScreenModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentScreen, ScreenService) {
    var ctrl = this;
    
    $scope.currentScreen = currentScreen;
    
    $scope.modalTitle = "";
    if (currentScreen.id) {
      $scope.modalTitle = "Edit screen";
    } else {
      $scope.modalTitle = "Add new screen";
    }
    
    var saveScreenErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }

    $scope.save = function(currentScreen) {
      if (currentScreen.id) {
        ScreenService.editScreen(currentScreen, function(){
          //console.log("Successfully edited screen");
          $uibModalInstance.close();
        }, saveScreenErrorCb);
      } else {
        ScreenService.saveScreen(currentScreen, function(){
          //console.log("Successfully saved screen");
          $uibModalInstance.close();
        }, saveScreenErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }
  }
  
})();