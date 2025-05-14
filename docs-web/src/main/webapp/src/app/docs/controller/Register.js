'use strict';

/**
 * Registration controller.
 */
angular.module('docs').controller('Register', function($scope, $rootScope, $state, $location, Restangular) {
  $scope.user = {};
  $scope.registrationSuccess = false;
  
  // Register
  $scope.register = function() {
    Restangular.one('registration').save({
      username: $scope.user.username,
      password: $scope.user.password,
      email: $scope.user.email
    }).then(function() {
      $scope.registrationSuccess = true;
      $rootScope.appSuccess({ key: 'registration.request.success' });
    }, function(e) {
      if (e.data.type === 'UsernameAlreadyExists') {
        $rootScope.appAlert({
          key: 'registration.request.error.alreadyexists'
        });
      } else {
        $rootScope.appAlert({
          key: 'registration.request.error'
        });
      }
    });
  };
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 