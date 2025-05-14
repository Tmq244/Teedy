package com.sismics.docs.core.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * Registration request entity.
 */
@Entity
@Table(name = "T_REGISTRATION_REQUEST")
public class RegistrationRequest implements Loggable {
    /**
     * Request ID.
     */
    @Id
    @Column(name = "REQ_ID_C", length = 36)
    private String id;
    
    /**
     * Username.
     */
    @Column(name = "REQ_USERNAME_C", nullable = false, length = 50)
    private String username;
    
    /**
     * Email address.
     */
    @Column(name = "REQ_EMAIL_C", nullable = false, length = 100)
    private String email;
    
    /**
     * Password hash.
     */
    @Column(name = "REQ_PASSWORD_C", nullable = false, length = 100)
    private String password;
    
    /**
     * Status (PENDING, APPROVED, REJECTED).
     */
    @Column(name = "REQ_STATUS_C", nullable = false, length = 20)
    private String status;
    
    /**
     * Creation date.
     */
    @Column(name = "REQ_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Response date.
     */
    @Column(name = "REQ_RESPONSEDATE_D")
    private Date responseDate;
    
    /**
     * Response message.
     */
    @Column(name = "REQ_RESPONSEMSG_C", length = 500)
    private String responseMessage;

    public String getId() {
        return id;
    }

    public RegistrationRequest setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RegistrationRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegistrationRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RegistrationRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public RegistrationRequest setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public RegistrationRequest setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public RegistrationRequest setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    @Override
    public String toMessage() {
        return username;
    }

    @Override
    public Date getDeleteDate() {
        return null; // Registration requests are not deleted
    }
} 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 