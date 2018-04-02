(function() {
  'use strict';

  angular
    .module('app')
    .factory('AmbasadorService', AmbasadorService);
  
  AmbasadorService.$inject = ['$http', '$location'];
  
  function AmbasadorService($http, $location) {
    
    var service = {};

    service.getAllAmbasadors = function(success, error) {
      $http({
        url: '/ambasadors/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.getAmbasadorById = function(id, success, error) {
      $http({
        url: '/ambasadors/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getAmbasadorsByScreenId = function(id, success, error) {
      $http({
        url: '/ambasadors/screen/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveAmbasador = function(ambasador, success, error) {
      $http({
        url: '/ambasadors/create',
        method: 'POST',
        data: ambasador,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editAmbasador = function(ambasador, success, error) {
      $http({
        url: '/ambasadors/update',
        method: 'PUT',
        data: ambasador,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteAmbasadorById = function(id, success, error) {
      $http({
        url: '/ambasadors/delete/'+id,
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