(function () {
  'use strict';

  angular
    .module('app')
    .controller('AnnouncementController', AnnouncementController);

  AnnouncementController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'AnnouncementService', 'ErrorController'];
  function AnnouncementController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, AnnouncementService, ErrorController) {

    var ctrl = this;

    $scope.announcements = [];
    
    $rootScope.$on('AnnouncementReload', function (event, message){
      ctrl.getScreenAnnouncements();
    });
    
    ctrl.$onInit = function() {
      //ctrl.getAllAnnouncements();
      ctrl.getScreenAnnouncements();
    };

    ctrl.getAllAnnouncements = function() {
      AnnouncementService.getAllAnnouncements(getAnnouncementsSuccessCb, ErrorController.httpGetErroCb);
    }
    
    ctrl.getScreenAnnouncements = function() {
      AnnouncementService.getAnnouncementByScreenId($rootScope.chosenScreenId, getAnnouncementsSuccessCb, ErrorController.httpGetErroCb);
    }

    var getAnnouncementsSuccessCb = function(data, status, headers) {
      angular.forEach(data, function(announcement, index) {
        announcement.viewType = announcement.type.title;
        announcement.viewStatus = announcement.status == "1" ? "Active" : "Inactive";
      });
      $scope.announcements = data;
    }
    
    var getAnnouncementsErrorCb = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.addAnnouncement = function() {
      var modal = $uibModal.open({
        resolve: {
          currentAnnouncement: {}
        },
        size: 'md',
        templateUrl: 'app/views/announcement-details.html',
        controller: AnnouncementModalController
      });
      modal.result.then(function(){
        ctrl.getAllAnnouncements();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.editAnnouncement = function(announcement) {
      var announcementToEdit = announcement;
      var modal = $uibModal.open({
        resolve: {
          currentAnnouncement: angular.copy(announcementToEdit)
        },
        size: 'md',
        templateUrl: 'app/views/announcement-details.html',
        controller: AnnouncementModalController
      });
      modal.result.then(function(){
        ctrl.getAllAnnouncements();
      }, ErrorController.httpGetErroCb);
    }
    
    $scope.deleteAnnouncement = function(announcement) {
      $scope.currentAnnouncement = announcement;
      var modal = $uibModal.open({
        scope: $scope,
        template: '<div class="modal-header"><h4 class="modal-title">Confirmation</h4></div>'+
                  '<div class="modal-body">Do you want to delete announcement "{{ currentAnnouncement.text }}" ?</div>'+
                  '<div class="modal-footer">'+
                    '<button class="btn btn-small" ng-click="confirm(currentAnnouncement.id)">Yes</button>'+
                    '<button class="btn btn-small" ng-click="cancel()">No</button>'+
                  '</div>',
        controller: function($uibModalInstance, AnnouncementService, ErrorController) {

          $scope.confirm = function(id) {
            AnnouncementService.deleteAnnouncementById(id, function(){
              //console.log("Successfully deleted announcement");
              $uibModalInstance.close();
            }, function(){
              console.log("Error while deleting announcement");
            });
          }
          
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          }
        }
      });

      modal.result.then(function(){
        ctrl.getAllAnnouncements();
      }, ErrorController.httpGetErroCb);
    }
  }
  

  angular.module('app').controller('AnnouncementModalController', AnnouncementModalController);

  AnnouncementModalController.$inject = ['$scope', '$uibModalInstance', '$cookies', '$routeParams', '$location', 'currentAnnouncement', 'AnnouncementService', 'ScreenService'];
  function AnnouncementModalController($scope, $uibModalInstance, $cookies, $routeParams, $location, currentAnnouncement, AnnouncementService, ScreenService) {
    var ctrl = this;

    $scope.currentAnnouncement = currentAnnouncement;
    if (undefined == $scope.currentAnnouncement.screen) {
      $scope.currentAnnouncement.screen = {id: "0", title: "None"};
      $scope.currentAnnouncement.status = "0";
    }

    $scope.modalTitle = "";
    if (currentAnnouncement.id) {
      $scope.modalTitle = "Edit announcement";
    } else {
      $scope.modalTitle = "Add new announcement";
    }
    
    $scope.screens = [];
    $scope.types = [];
    $scope.viewTimeInput = false;

    ctrl.$onInit = function() {
      ctrl.getAllScreens();
      ctrl.getTypes();
    };
    
    ctrl.getTypes = function() {
      AnnouncementService.getAnnouncementTypes(function(data, status, headers){
        $scope.types = data;
        if (undefined == $scope.currentAnnouncement.id) { // for new announcement - default planned change
          var planned = data.filter(function(item) { return item.code === "PLANNED_CHANGE"; });
          if (undefined != planned.length && 1 == planned.length) {
            $scope.currentAnnouncement.type = planned[0];
            $scope.viewTimeInput = true;
          } 
        } else if ($scope.currentAnnouncement.type.code === "PLANNED_CHANGE" ) {
          $scope.viewTimeInput = true;
        }
      }, function(){});
    };

    ctrl.getAllScreens = function() {
      ScreenService.getAllScreens(getScreensSuccessCallback, getScreensErrorCallback);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      $scope.screens = data;
      if (undefined == $scope.currentAnnouncement.screen.id) {
        $scope.currentAnnouncement.screen.id = 0;
      }
    }
    
    var getScreensErrorCallback = function(data, status, headers) {
      //console.log(status);
    }
    
    $scope.checkSelectedType = function() {
      if ('PLANNED_CHANGE' == $scope.currentAnnouncement.type.code) {
        $scope.viewTimeInput = true;
      } else {
        $scope.viewTimeInput = false;
      }
    }

    var saveAnnouncementErrorCb = function(data, status, headers) {
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
      $scope.errorMessage = data.errorMessage;
      if (500 == status) {
        $scope.errorMessage = 'Internal server error while processing the request';
      }
    }

    $scope.save = function(currentAnnouncement) {
      currentAnnouncement.modifiedBy = $cookies.getObject('user');
      if (currentAnnouncement.id) {
        AnnouncementService.editAnnouncement(currentAnnouncement, function() {
          //console.log("Successfully edited announcement");
          $uibModalInstance.close();
        }, saveAnnouncementErrorCb);
      } else {
        currentAnnouncement.owner = $cookies.getObject('user');
        AnnouncementService.saveAnnouncement(currentAnnouncement, function() {
          //console.log("Successfully saved announcement");
          $uibModalInstance.close();
        }, saveAnnouncementErrorCb);
      }
    }

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    }

  }
})();