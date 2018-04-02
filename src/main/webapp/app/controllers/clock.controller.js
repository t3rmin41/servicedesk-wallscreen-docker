(function () {
  'use strict';

  angular
    .module('app')
    .controller('ClockController', ClockController);

  ClockController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ClockService', 'ErrorController'];
  function ClockController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, ClockService, ErrorController) {

    var ctrl = this;

    $scope.clocks = [];
    
    $rootScope.$on('ClockReload', function (event, message){
      ctrl.getScreenClocks();
    });
    
    ctrl.$onInit = function() {
      //ctrl.getAllClocks();
      ctrl.getScreenClocks();
    };

    ctrl.getAllClocks = function() {
      ClockService.getAllClocks(getClocksSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getScreenClocks = function() {
      ClockService.getClocksByScreenId($rootScope.chosenScreenId, getClocksSuccessCb, ErrorController.httpGetErroCb);
    }

    var getClocksSuccessCb = function(data, status, headers) {
      $scope.clocks = data;
    }
    
    var getClocksErrorCb = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.addClock = function() {
      var modal = $uibModal.open({
        resolve: {
          currentClock: {}
        },
        size: 'md',
        templateUrl: 'app/views/clock-details.html',
        controller: ClockModalController
      });
      modal.result.then(function(){
        ctrl.getAllClocks();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editClock = function(clock) {
      var clockToEdit = clock;
      var modal = $uibModal.open({
        resolve: {
          currentClock: angular.copy(clockToEdit)
        },
        size: 'md',
        templateUrl: 'app/views/clock-details.html',
        controller: ClockModalController
      });
      modal.result.then(function(){
        ctrl.getAllClocks();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteClock = function(clock) {
      $scope.currentClock = clock;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete clock "{{ currentClock.clockName }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentClock.id)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, ClockService, ErrorController) {

          $scope.confirm = function(id) {
            ClockService.deleteClockById(id, function(){
              //console.log("Successfully deleted clock");
              $uibModalInstance.close();
            }, ErrorController.httpGetErroCb);
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      modal.result.then(function(){
        ctrl.getAllClocks();
      }, function() {});
    }
  }
  

  angular.module('app').controller('ClockModalController', ClockModalController);

  ClockModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentClock', 'ClockService', 'ScreenService'];
  function ClockModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentClock, ClockService, ScreenService) {
    var ctrl = this;

    $scope.currentClock = currentClock;
    if (undefined == $scope.currentClock.screen) {
      $scope.currentClock.screen = {id: "0", title: "None"};
    }
    if (undefined != $scope.currentClock.id && null != $scope.currentClock.id) {
      $scope.currentClock.clockGMT = parseFloat($scope.currentClock.clockGMT);
    }

    $scope.screens = [];
    
    $scope.modalTitle = "";
    if (currentClock.id) {
      $scope.modalTitle = "Edit clock";
    } else {
      $scope.modalTitle = "Add new clock";
    }
    
    ctrl.$onInit = function() {
      ctrl.getAllScreens();
    };
    
    ctrl.getAllScreens = function() {
      ScreenService.getAllScreens(getScreensSuccessCallback, getScreensErrorCallback);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      $scope.screens = data;
      if (undefined == $scope.currentClock.screen.id) {
        $scope.currentClock.screen.id = 0;
      }
    }
    
    var getScreensErrorCallback = function(data, status, headers) {
      //console.log(status);
    }

    var saveClockErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }
    
    $scope.save = function(currentClock) {
      if (currentClock.id) {
        ClockService.editClock(currentClock, function() {
          //console.log("Successfully edited clock");
          $uibModalInstance.close();
        }, saveClockErrorCb);
      } else {
        ClockService.saveClock(currentClock, function() {
          //console.log("Successfully saved clock");
          $uibModalInstance.close();
        }, saveClockErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }
  }

})();