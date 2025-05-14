package com.sismics.docs.rest.resource;

import com.sismics.docs.core.constant.Constants;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.exception.ServerException;
import com.sismics.rest.util.ValidationUtil;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

/**
 * Registration request REST resources.
 */
@Path("/registration")
public class RegistrationRequestResource extends BaseResource {
    private final UserDao userDao = new UserDao();
    
    /**
     * Submits a new registration request.
     *
     * @param username Username
     * @param password Password
     * @param email Email
     * @return Response
     */
    @POST
    public Response submit(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email) {
        
        // Validate input
        ValidationUtil.validateRequired(username, "username");
        ValidationUtil.validateRequired(password, "password");
        ValidationUtil.validateRequired(email, "email");
        ValidationUtil.validateLength(username, "username", 3, 50);
        ValidationUtil.validateLength(password, "password", 8, 50);
        ValidationUtil.validateEmail(email, "email");
        
        // Check if username already exists
        if (userDao.getActiveByUsername(username) != null) {
            throw new ServerException("UsernameAlreadyExists", "Username already exists");
        }
        
        // Create a user with the default role (inactive until approved)
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoleId(Constants.DEFAULT_USER_ROLE); // Use default role
        user.setStorageQuota(1000000L); // 1MB default quota for new users
        user.setDisableDate(new Date()); // Disable the user initially
        
        try {
            userDao.create(user, "guest");
        } catch (Exception e) {
            throw new ServerException("UserCreationError", "Error creating user: " + e.getMessage());
        }
        
        // Return the response
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("status", "ok")
                .add("message", "Registration request submitted successfully");
        return Response.ok().entity(response.build()).build();
    }
    
    /**
     * Lists all pending registration requests.
     *
     * @return Response
     */
    @GET
    public Response list() {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Check if user is admin
        if (!principal.getId().equals("admin")) {
            throw new ForbiddenClientException();
        }
        
        // Get all disabled users (users with disableDate not null)
        List<User> disabledUsers = userDao.findByDisableDate();
        
        // Build the response
        JsonArrayBuilder requestsArray = Json.createArrayBuilder();
        for (User user : disabledUsers) {
            requestsArray.add(Json.createObjectBuilder()
                    .add("id", user.getId())
                    .add("username", user.getUsername())
                    .add("email", user.getEmail())
                    .add("create_date", user.getCreateDate().getTime()));
        }
        
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("status", "ok")
                .add("requests", requestsArray);
        return Response.ok().entity(response.build()).build();
    }
    
    /**
     * Processes a registration request.
     *
     * @param id Request ID
     * @param action Action (approve/reject)
     * @return Response
     */
    @POST
    @Path("/{id: [a-z0-9\\-]+}")
    public Response process(
            @PathParam("id") String id,
            @FormParam("action") String action) {
        
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Check if user is admin
        if (!principal.getId().equals("admin")) {
            throw new ForbiddenClientException();
        }
        
        // Validate input
        ValidationUtil.validateRequired(action, "action");
        if (!action.equals("approve") && !action.equals("reject")) {
            throw new ServerException("InvalidAction", "Action must be either 'approve' or 'reject'");
        }
        
        // Get the user
        User user = userDao.getById(id);
        if (user == null || user.getDisableDate() == null) {
            throw new ServerException("RequestNotFound", "Registration request not found");
        }
        
        if (action.equals("approve")) {
            // Enable the user (remove disable date)
            user.setDisableDate(null);
            userDao.update(user, principal.getId());
        } else {
            // Delete the user if rejected
            userDao.delete(user.getUsername(), principal.getId());
        }
        
        // Return the response
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("status", "ok");
        return Response.ok().entity(response.build()).build();
    }
} 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 