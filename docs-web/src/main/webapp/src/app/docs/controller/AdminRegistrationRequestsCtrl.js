angular.module('docs').controller('AdminRegistrationRequestsCtrl', function($scope, $http) {
    $scope.requests = [];
    $scope.loading = true;

    // Fetch registration requests
    $http.get('../api/registration').then(function(response) {
        $scope.requests = response.data.requests;
        $scope.loading = false;
    }, function() {
        $scope.loading = false;
        alert('Error loading registration requests');
    });

    $scope.approve = function(request) {
        var message = prompt('Enter approval message (optional):');
        $http.post('../api/registration/' + request.id, {
            action: 'approve',
            message: message
        }).then(function() {
            alert('Request approved');
            // Remove from list
            $scope.requests = $scope.requests.filter(r => r.id !== request.id);
        }, function(response) {
            alert(response.data.message || 'Error processing request');
        });
    };

    $scope.reject = function(request) {
        var message = prompt('Enter rejection reason (required):');
        if (!message) {
            alert('Please provide a reason for rejection');
            return;
        }
        $http.post('../api/registration/' + request.id, {
            action: 'reject',
            message: message
        }).then(function() {
            alert('Request rejected');
            // Remove from list
            $scope.requests = $scope.requests.filter(r => r.id !== request.id);
        }, function(response) {
            alert(response.data.message || 'Error processing request');
        });
    };
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 