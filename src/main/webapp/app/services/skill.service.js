(function() {
  'use strict';

  angular
    .module('app')
    .factory('SkillService', SkillService);
  
  SkillService.$inject = ['$http', '$location'];
  
  function SkillService($http, $location) {
    
    var service = {};

    service.getAllSkills = function(success, error) {
      $http({
        url: '/skills/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.getSkillById = function(id, success, error) {
      $http({
        url: '/skills/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getSkillsByClientId = function(id, success, error) {
      $http({
        url: '/skills/client/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.getSkillsByScreenId = function(id, success, error) {
      $http({
        url: '/skills/screen/'+id,
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    service.saveSkill = function(skill, success, error) {
      $http({
        url: '/skills/create',
        method: 'POST',
        data: skill,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.editSkill = function(skill, success, error) {
      $http({
        url: '/skills/update',
        method: 'PUT',
        data: skill,
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };
    
    service.deleteSkillById = function(id, success, error) {
      $http({
        url: '/skills/delete/'+id,
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