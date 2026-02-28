package com.teamportfolio.controller;

import com.teamportfolio.model.ContactMessage;
import com.teamportfolio.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ContactController — REST API controller for the contact form.
 * Rewritten without Lombok for Java 25 compatibility.
 *
 * Endpoints:
 * POST /api/contact — Submit a new contact message (public)
 * GET /api/contact — Retrieve all messages (admin)
 * GET /api/contact/{id} — Retrieve a single message (admin)
 * PATCH /api/contact/{id}/read — Mark a message as read (admin)
 * DELETE /api/contact/{id} — Delete a message (admin)
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    // Constructor injection
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // ==========================================================
    // POST /api/contact — Submit new contact message from form
    // ==========================================================
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitContactForm(
            @Valid @RequestBody ContactMessage message,
            HttpServletRequest request) {
        log.info("Received contact form submission from {} <{}>", message.getName(), message.getEmail());

        // Capture sender IP for spam tracking
        String ipAddress = extractClientIp(request);
        message.setIpAddress(ipAddress);

        // Persist via service layer
        ContactMessage saved = contactService.saveMessage(message);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Thank you! Your message has been received. We'll get back to you within 24 hours.");
        response.put("id", saved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==========================================================
    // GET /api/contact — Retrieve all contact messages
    // ==========================================================
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMessages() {
        List<ContactMessage> messages = contactService.getAllMessages();
        long unreadCount = contactService.getUnreadCount();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", messages.size());
        response.put("unreadCount", unreadCount);
        response.put("messages", messages);

        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // GET /api/contact/{id} — Retrieve a single message
    // ==========================================================
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMessageById(@PathVariable Long id) {
        return contactService.getMessageById(id)
                .map(msg -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", msg);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Message not found with id: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    // ==========================================================
    // PATCH /api/contact/{id}/read — Mark a message as read
    // ==========================================================
    @PatchMapping("/{id}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(@PathVariable Long id) {
        return contactService.markAsRead(id)
                .map(msg -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Message marked as read");
                    response.put("updatedMessage", msg);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Message not found with id: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    // ==========================================================
    // DELETE /api/contact/{id} — Delete a contact message
    // ==========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMessage(@PathVariable Long id) {
        if (contactService.getMessageById(id).isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Message not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        contactService.deleteMessage(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Message deleted successfully");
        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // Validation Error Handler — returns field-level errors
    // ==========================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Validation failed. Please check the submitted data.");
        response.put("errors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ==========================================================
    // Helper — Extract client IP address from request headers
    // ==========================================================
    private String extractClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
