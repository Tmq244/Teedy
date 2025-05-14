.state('login', {
  url: '/login?redirectState&redirectParams',
  views: {
    'page': {
      templateUrl: 'partial/docs/login.html',
      controller: 'Login'
    }
  }
})
.state('register', {
  url: '/register',
  views: {
    'page': {
      templateUrl: 'partial/docs/register.html',
      controller: 'Register'
    }
  }
})
.state('settings.registration', {
  url: '/registration',
  views: {
    'settings': {
      templateUrl: 'partial/docs/settings.registration.html',
      controller: 'SettingsRegistration'
    }
  }
}) 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 