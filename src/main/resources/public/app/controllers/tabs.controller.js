(function () {
    'use strict';

    angular
        .module('app')
        .controller('TabsController', TabsController);

    TabsController.$inject = ['$rootScope', '$scope',  '$cookies', '$filter', '$location', '$sce', 'ScreenService'];

    function TabsController($rootScope, $scope, $cookies, $filter, $location, $sce, ScreenService) {

      var ctrl = this;

      var hasAdmin = $cookies.getObject('user').roles.filter(function(role){ return role.code == "ADMIN"}).length > 0;
      var hasManager = $cookies.getObject('user').roles.filter(function(role){ return role.code == "MANAGER"}).length > 0;

      $scope.tabs = [
                      { id: '0', title: 'Announcements', path: 'app/views/announcement.html', ctrl : 'AnnouncementController', reloadEvent: 'AnnouncementReload' }
                    ];
      
      var managerTabs = [ { id: '1', title: 'Screens', path: 'app/views/screen.html', ctrl : 'ScreenController', reloadEvent: 'ScreenReload' },
                          { id: '2', title: 'Ambasadors', path:  'app/views/ambasador.html', ctrl : 'AmbasadorController', reloadEvent: 'AmbasadorReload' },
                          { id: '3', title: 'Clocks', path: 'app/views/clock.html', ctrl : 'ClockController', reloadEvent: 'ClockReload' },
                          { id: '4', title: 'Clients',  path: 'app/views/client.html', ctrl : 'ClientController', reloadEvent: 'ClientReload' },
                          { id: '5', title: 'Skills', path: 'app/views/skill.html', ctrl : 'SkillController', reloadEvent: 'SkillReload' }
                        ];
      
      var adminTabs = [
                        { id: '6', title: 'Users', path: 'app/views/user.html', ctrl : 'UserController', reloadEvent: 'UserReload' }
                      ];
      
      if (hasManager || hasAdmin) {
        $scope.tabs = $scope.tabs.concat(managerTabs);
      }
      if (hasAdmin) {
        $scope.tabs = $scope.tabs.concat(adminTabs);
      }
      
      $scope.currentTab = $scope.tabs[0];
      $rootScope.currentTab = $scope.currentTab;
      
      $scope.setCurrentTab = function(i) {
        $scope.currentTab = $scope.tabs.filter(function(tab) { return tab.id == i})[0];
        $rootScope.currentTab = $scope.currentTab;
        $rootScope.httpErrorMessage = null;
      }

      $scope.templateUrl = function(index) {
        return $scope.tabs.filter(function(tab) { return tab.id == index})[0].path;
      }
      
    }
})();