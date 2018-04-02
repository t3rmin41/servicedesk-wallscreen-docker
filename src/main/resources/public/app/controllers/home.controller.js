(function () {
  'use strict';

  angular
    .module('app')
    .controller('HomeController', HomeController);

  HomeController.$inject = ['$rootScope', '$scope', '$cookies', '$route', '$routeParams', '$location', 'LoginService', 'ScreenService', 'ErrorController'];

  function HomeController($rootScope, $scope, $cookies, $route, $routeParams, $location, LoginService, ScreenService, ErrorController) {

    var ctrl = this;
    
    $scope.screens = [];
    $rootScope.chosenScreenId = "0";
    $scope.chosenScreen = "0";
    $scope.route = $route;

    $scope.authenticated = ('true' == $cookies.get('authenticated'));
    
    //$rootScope.httpErrorMessage = "Some error";
    
    $scope.updateChosenScreen = function() {
      $rootScope.chosenScreenId = $scope.chosenScreen;
      $scope.$emit($rootScope.currentTab.reloadEvent, $rootScope.currentTab.ctrl);
      //$scope.route.reload($rootScope.currentTab.ctrl);
      //$location.path($rootScope.currentTab.path);
      //$route.reload($rootScope.currentTab.path);
    }
    
    ctrl.$onInit = function() {
      if (undefined != $cookies.get('token') && null != $cookies.get('token')) {
        ctrl.getAllScreens();
      }
    };
    
    ctrl.getAllScreens = function() {
      ScreenService.getAllScreens(getScreensSuccessCallback, getScreensErrorCallback);
    }

    var getScreensSuccessCallback = function(data, status, headers) {
      //console.log(data);
      $scope.screens = data;
    }

    var getScreensErrorCallback = function(data, status, headers) {
      console.log(status);
    }
    
    if (!$scope.authenticated) {
      $location.path("/login");
    }
    
    $scope.user = $cookies.getObject('user');
    
    $scope.logout = function() {
      LoginService.logout(logoutCallback);
    };

    var logoutCallback = function(data, status, headers) {
      $cookies.put('authenticated', false);
      $scope.authenticated = false;
      $scope.userLoggedOut = true;
      $location.path("/login");
    };

  }
})();