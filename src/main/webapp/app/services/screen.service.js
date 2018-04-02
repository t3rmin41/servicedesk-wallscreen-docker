(function() {
  'use strict';

  angular
    .module('app')
    .factory('ScreenService', ScreenService);
  
  ScreenService.$inject = ['$http', '$location'];
  
  function ScreenService($http, $location) {
    
    var service = {};
    
    service.getAllScreens = function(success, error) {
      $http({
        url: '/screens/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getEditableScreens = function(success, error) {
      $http({
        url: '/screens/editable',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getScreenById = function(id, success, error) {
      $http({
        url: '/screens/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveScreen = function(screen, success, error) {
      $http({
        url: '/screens/create',
        method: 'POST',
        data: screen,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editScreen = function(screen, success, error) {
      $http({
        url: '/screens/update',
        method: 'PUT',
        data: screen,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteScreenById = function(id, success, error) {
      $http({
        url: '/screens/delete/'+id,
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