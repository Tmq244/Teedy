/**
 * Registration requests admin view.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/admin/registration_requests.html'
], function($, _, Backbone, registrationRequestsTemplate) {
    return Backbone.View.extend({
        template: _.template(registrationRequestsTemplate),
        
        events: {
            'click .approve-request': 'approveRequest',
            'click .reject-request': 'rejectRequest'
        },
        
        initialize: function() {
            this.loadRequests();
        },
        
        render: function() {
            this.$el.html(this.template());
            return this;
        },
        
        loadRequests: function() {
            var self = this;
            $.ajax({
                url: 'api/registration',
                type: 'GET',
                success: function(response) {
                    var requests = response.requests;
                    var $tbody = self.$('#requests-table tbody');
                    $tbody.empty();
                    
                    requests.forEach(function(request) {
                        $tbody.append(
                            '<tr>' +
                            '<td>' + request.username + '</td>' +
                            '<td>' + request.email + '</td>' +
                            '<td>' + new Date(request.create_date).toLocaleString() + '</td>' +
                            '<td>' +
                            '<button class="btn btn-success btn-sm approve-request" data-id="' + request.id + '">Approve</button> ' +
                            '<button class="btn btn-danger btn-sm reject-request" data-id="' + request.id + '">Reject</button>' +
                            '</td>' +
                            '</tr>'
                        );
                    });
                },
                error: function() {
                    alert('Error loading registration requests');
                }
            });
        },
        
        approveRequest: function(e) {
            var requestId = $(e.target).data('id');
            var message = prompt('Enter approval message (optional):');
            
            this.processRequest(requestId, 'approve', message);
        },
        
        rejectRequest: function(e) {
            var requestId = $(e.target).data('id');
            var message = prompt('Enter rejection reason (required):');
            
            if (!message) {
                alert('Please provide a reason for rejection');
                return;
            }
            
            this.processRequest(requestId, 'reject', message);
        },
        
        processRequest: function(requestId, action, message) {
            var self = this;
            $.ajax({
                url: 'api/registration/' + requestId,
                type: 'POST',
                data: {
                    action: action,
                    message: message
                },
                success: function() {
                    alert('Request ' + action + 'd successfully');
                    self.loadRequests();
                },
                error: function(xhr) {
                    var error = xhr.responseJSON;
                    alert(error.message || 'Error processing request');
                }
            });
        }
    });
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 