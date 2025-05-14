package com.sismics.docs.core.dao;

import com.sismics.docs.core.constant.AuditLogType;
import com.sismics.docs.core.model.jpa.RegistrationRequest;
import com.sismics.docs.core.util.AuditLogUtil;
import com.sismics.util.context.ThreadLocalContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Registration request DAO.
 */
public class RegistrationRequestDao {
    /**
     * Creates a new registration request.
     * 
     * @param request Registration request
     * @return New ID
     */
    public String create(RegistrationRequest request) {
        // Create the UUID
        request.setId(UUID.randomUUID().toString());
        
        // Set initial status
        request.setStatus("PENDING");
        request.setCreateDate(new Date());
        
        // Create the request
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(request);
        
        return request.getId();
    }
    
    /**
     * Gets a registration request by ID.
     * 
     * @param id Request ID
     * @return Registration request
     */
    public RegistrationRequest getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        return em.find(RegistrationRequest.class, id);
    }
    
    /**
     * Gets a pending registration request by username.
     * 
     * @param username Username
     * @return Registration request
     */
    public RegistrationRequest getPendingByUsername(String username) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select r from RegistrationRequest r where r.username = :username and r.status = 'PENDING'");
        q.setParameter("username", username);
        try {
            return (RegistrationRequest) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Gets all pending registration requests.
     * 
     * @return List of registration requests
     */
    public List<RegistrationRequest> getPendingRequests() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select r from RegistrationRequest r where r.status = 'PENDING' order by r.createDate");
        return q.getResultList();
    }
    
    /**
     * Updates a registration request status.
     * 
     * @param request Registration request
     * @param status New status
     * @param message Response message
     * @param userId User ID who processed the request
     */
    public void updateStatus(RegistrationRequest request, String status, String message, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        
        request.setStatus(status);
        request.setResponseDate(new Date());
        request.setResponseMessage(message);
        
        // Create audit log
        AuditLogUtil.create(request, AuditLogType.UPDATE, userId);
    }
} 

// AI-Generation: by Cursor
// prompt：New user request: a guest could send a request to admin to register as a new user. The
// admin could accept or reject the request. Once accepted, the guest could use the new account to login 