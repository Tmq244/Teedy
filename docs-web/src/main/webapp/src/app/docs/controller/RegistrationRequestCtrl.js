angular.module('docs').controller('RegistrationRequestCtrl', function($scope, $http, $window) {
    $scope.form = {};
    $scope.submitting = false;

    $scope.submitRegistration = function() {
        if (!$scope.form.username || !$scope.form.password || !$scope.form.email) {
            alert('Please fill in all fields');
            return;
        }
        $scope.submitting = true;
        $http.post('../api/registration', $scope.form)
            .then(function() {
                alert('Registration request submitted successfully. Please wait for admin approval.');
                $window.location.href = '#/login';
            }, function(response) {
                alert((response.data && response.data.message) || 'Error submitting registration request');
            })
            .finally(function() {
                $scope.submitting = false;
            });
    };
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 