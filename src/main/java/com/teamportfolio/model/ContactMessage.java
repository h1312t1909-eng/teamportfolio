package com.teamportfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * ContactMessage — JPA Entity mapped to the contact_messages table.
 * Stores messages submitted through the portfolio website contact form.
 * Written without Lombok for compatibility with Java 25.
 */
@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Sender's full name */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Sender's email address */
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    /** Message subject */
    @NotBlank(message = "Subject is required")
    @Size(min = 3, max = 200, message = "Subject must be between 3 and 200 characters")
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    /** Message body */
    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    /** Auto-populated timestamp when the record is created */
    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    /** Optional: IP address of the request sender */
    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    /** Whether the message has been read by the admin */
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    // ==================== Constructors ====================

    public ContactMessage() {
    }

    public ContactMessage(Long id, String name, String email, String subject,
            String message, LocalDateTime submittedAt,
            String ipAddress, Boolean isRead) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.submittedAt = submittedAt;
        this.ipAddress = ipAddress;
        this.isRead = isRead;
    }

    // ==================== Getters ====================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    // ==================== Setters ====================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubmittedAt(LocalDateTime t) {
        this.submittedAt = t;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    // ==================== toString ====================

    @Override
    public String toString() {
        return "ContactMessage{id=" + id + ", name=" + name +
                ", email=" + email + ", subject=" + subject + "}";
    }
}
