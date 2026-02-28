package com.teamportfolio.service;

import com.teamportfolio.model.ContactMessage;
import com.teamportfolio.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ContactService — Business logic layer for contact form submissions.
 * Rewritten without Lombok for Java 25 compatibility.
 */
@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    // Constructor injection (replaces @RequiredArgsConstructor)
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Save a new contact message to the database.
     */
    @Transactional
    public ContactMessage saveMessage(ContactMessage message) {
        log.info("Saving new contact message from: {} <{}>", message.getName(), message.getEmail());
        ContactMessage saved = contactRepository.save(message);
        log.info("Contact message saved successfully. ID: {}", saved.getId());
        return saved;
    }

    /**
     * Retrieve all messages.
     */
    @Transactional(readOnly = true)
    public List<ContactMessage> getAllMessages() {
        log.debug("Fetching all contact messages");
        return contactRepository.findAll();
    }

    /**
     * Find a contact message by its primary key.
     */
    @Transactional(readOnly = true)
    public Optional<ContactMessage> getMessageById(Long id) {
        log.debug("Fetching contact message with id: {}", id);
        return contactRepository.findById(id);
    }

    /**
     * Retrieve all unread messages.
     */
    @Transactional(readOnly = true)
    public List<ContactMessage> getUnreadMessages() {
        return contactRepository.findByIsReadFalseOrderBySubmittedAtDesc();
    }

    /**
     * Mark a specific message as read.
     */
    @Transactional
    public Optional<ContactMessage> markAsRead(Long id) {
        return contactRepository.findById(id).map(msg -> {
            msg.setIsRead(true);
            log.info("Marked message {} as read", id);
            return contactRepository.save(msg);
        });
    }

    /**
     * Count total number of unread messages.
     */
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return contactRepository.countByIsReadFalse();
    }

    /**
     * Delete a contact message by id.
     */
    @Transactional
    public void deleteMessage(Long id) {
        log.info("Deleting contact message with id: {}", id);
        contactRepository.deleteById(id);
    }
}
