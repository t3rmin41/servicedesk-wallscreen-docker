(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$scope', '$cookies', '$location', 'LoginService'];

    function LoginController($rootScope, $scope, $cookies, $location, LoginService) {
      
      $scope.login = function() {
        LoginService.login($scope.credentials, loginSuccessCallback, loginErrorCallback);
      };
      
      var loginSuccessCallback = function(data, status, headers) {
        $cookies.put('token', headers('Authorization'));
        $cookies.put('authenticated', true);
        delete $scope.userLoggedOut;
        delete $scope.$root.httpErrorMessage;
        LoginService.loginSuccessful(function(data, status, headers){
          //console.log(data);
          delete $scope.$root.httpErrorMessage;
          $cookies.putObject('user', data);
          $location.path("/");
        }, function(){});
      };
      var loginErrorCallback = function(data, status, headers) {
        var error = {};
        error.message = data.message;
        $scope.error = error;
      };
    }
})();