(function() {
  'use strict';

  angular
    .module('app')
    .factory('ClockService', ClockService);
  
  ClockService.$inject = ['$http', '$location'];
  
  function ClockService($http, $location) {
    
    var service = {};

    service.getAllClocks = function(success, error) {
      $http({
        url: '/clocks/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.getClockById = function(id, success, error) {
      $http({
        url: '/clocks/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getClocksByScreenId = function(id, success, error) {
      $http({
        url: '/clocks/screen/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveClock = function(clock, success, error) {
      $http({
        url: '/clocks/create',
        method: 'POST',
        data: clock,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editClock = function(clock, success, error) {
      $http({
        url: '/clocks/update',
        method: 'PUT',
        data: clock,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteClockById = function(id, success, error) {
      $http({
        url: '/clocks/delete/'+id,
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