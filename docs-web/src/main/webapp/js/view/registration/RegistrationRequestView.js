/**
 * Registration request view.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/registration/registration_request.html'
], function($, _, Backbone, registrationRequestTemplate) {
    return Backbone.View.extend({
        template: _.template(registrationRequestTemplate),
        
        events: {
            'submit #registration-form': 'submitRegistration'
        },
        
        initialize: function() {
            this.render();
        },
        
        render: function() {
            this.$el.html(this.template());
            return this;
        },
        
        submitRegistration: function(e) {
            e.preventDefault();
            
            var username = $('#username').val();
            var password = $('#password').val();
            var email = $('#email').val();
            
            // Validate form
            if (!username || !password || !email) {
                alert('Please fill in all fields');
                return;
            }
            
            // Submit registration request
            $.ajax({
                url: 'api/registration',
                type: 'POST',
                data: {
                    username: username,
                    password: password,
                    email: email
                },
                success: function() {
                    alert('Registration request submitted successfully. Please wait for admin approval.');
                    window.location.href = '#login';
                },
                error: function(xhr) {
                    var error = xhr.responseJSON;
                    alert(error.message || 'Error submitting registration request');
                }
            });
        }
    });
}); 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 