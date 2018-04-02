(function() {
  'use strict';

  angular
    .module('app')
    .factory('AnnouncementService', AnnouncementService);
  
  AnnouncementService.$inject = ['$http', '$location'];
  
  function AnnouncementService($http, $location) {
    
    var service = {};

    service.getAllAnnouncements = function(success, error) {
      $http({
        url: '/announcements/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.getAnnouncementTypes = function(success, error) {
      $http({
        url: '/announcements/types',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getAnnouncementById = function(id, success, error) {
      $http({
        url: '/announcements/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getAnnouncementByScreenId = function(id, success, error) {
      $http({
        url: '/announcements/screen/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveAnnouncement = function(announcement, success, error) {
      $http({
        url: '/announcements/create',
        method: 'POST',
        data: announcement,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editAnnouncement = function(announcement, success, error) {
      $http({
        url: '/announcements/update',
        method: 'PUT',
        data: announcement,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteAnnouncementById = function(id, success, error) {
      $http({
        url: '/announcements/delete/'+id,
        method: 'DELETE',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    return service;
  }
  
})();