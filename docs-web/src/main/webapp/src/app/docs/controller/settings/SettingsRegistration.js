'use strict';

/**
 * Settings registration requests controller.
 */
angular.module('docs').controller('SettingsRegistration', function($scope, $rootScope, $http, Restangular, $dialog) {
  // Load registration requests
  $scope.loadRegistrationRequests = function() {
    Restangular.one('registration').get().then(function(data) {
      $scope.requests = data.requests;
    });
  };
  
  $scope.loadRegistrationRequests();

  // Process a request (approve or reject)
  $scope.processRequest = function(request, action) {
    Restangular.one('registration', request.id).post('', {
      action: action
    }).then(function() {
      $scope.loadRegistrationRequests();
      if (action === 'approve') {
        $rootScope.appSuccess({ key: 'admin.registration.requests.approve.success' });
      } else {
        $rootScope.appSuccess({ key: 'admin.registration.requests.reject.success' });
      }
    }, function() {
      $rootScope.appError({ key: 'admin.registration.requests.error' });
    });
  };
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 