package com.teamportfolio.repository;

import com.teamportfolio.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ContactRepository — Spring Data JPA repository for ContactMessage.
 * Extends JpaRepository, providing standard CRUD operations automatically.
 *
 * Custom query methods follow Spring Data naming conventions.
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactMessage, Long> {

    /**
     * Find all unread messages, ordered by submission time (newest first).
     * 
     * @return list of unread ContactMessage entities
     */
    List<ContactMessage> findByIsReadFalseOrderBySubmittedAtDesc();

    /**
     * Find all messages from a specific email address.
     * 
     * @param email the sender email to search for
     * @return list of matching messages
     */
    List<ContactMessage> findByEmailOrderBySubmittedAtDesc(String email);

    /**
     * Count the total number of unread messages.
     * 
     * @return count of unread messages
     */
    long countByIsReadFalse();

    /**
     * Custom JPQL query: search messages by keyword in subject or message body.
     * 
     * @param keyword the search term
     * @return matching messages
     */
    @Query("SELECT m FROM ContactMessage m WHERE " +
            "LOWER(m.subject) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.message) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY m.submittedAt DESC")
    List<ContactMessage> searchByKeyword(String keyword);
}
