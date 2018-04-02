(function() {
  'use strict';

  angular
    .module('app')
    .factory('ClientService', ClientService);
  
  ClientService.$inject = ['$http', '$location'];
  
  function ClientService($http, $location) {
    
    var service = {};

    service.getAllClients = function(success, error) {
      $http({
        url: '/clients/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getEditableClients = function(success, error) {
      $http({
        url: '/clients/editable',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.getClientById = function(id, success, error) {
      $http({
        url: '/clients/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getEditableClientsByScreenId = function(id, success, error) {
      $http({
        url: '/clients/editable/screen/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveClient = function(client, success, error) {
      $http({
        url: '/clients/create',
        method: 'POST',
        data: client,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editClient = function(client, success, error) {
      $http({
        url: '/clients/update',
        method: 'PUT',
        data: client,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteClientById = function(id, success, error) {
      $http({
        url: '/clients/delete/'+id,
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