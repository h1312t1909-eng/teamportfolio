-- =====================================================
-- Team Portfolio — MySQL Database Schema
-- Database: team_portfolio_db
-- Run this script in MySQL Workbench or CLI:
--   mysql -u root -p < database/schema.sql
-- =====================================================

-- Create the database (if it doesn't exist)
CREATE DATABASE IF NOT EXISTS team_portfolio_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE team_portfolio_db;

-- =====================================================
-- Table: contact_messages
-- Stores all messages submitted via the contact form.
-- Spring Boot's ddl-auto=update will also create this
-- automatically, but this script gives you full control.
-- =====================================================
CREATE TABLE IF NOT EXISTS contact_messages (
    id            BIGINT          NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100)    NOT NULL            COMMENT 'Full name of the sender',
    email         VARCHAR(150)    NOT NULL            COMMENT 'Email address of the sender',
    subject       VARCHAR(200)    NOT NULL            COMMENT 'Message subject line',
    message       TEXT            NOT NULL            COMMENT 'Full message body',
    submitted_at  DATETIME        DEFAULT NOW()       COMMENT 'Timestamp when message was submitted',
    ip_address    VARCHAR(50)     DEFAULT NULL        COMMENT 'IP address of the request (spam tracking)',
    is_read       TINYINT(1)      NOT NULL DEFAULT 0  COMMENT '0 = unread, 1 = read',

    PRIMARY KEY (id),
    INDEX idx_email        (email),
    INDEX idx_submitted_at (submitted_at DESC),
    INDEX idx_is_read      (is_read)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_unicode_ci
COMMENT = 'Contact form submissions from the portfolio website';

-- =====================================================
-- Sample / Seed Data (optional — remove in production)
-- =====================================================
INSERT INTO contact_messages (name, email, subject, message, is_read)
VALUES
    ('Alice Johnson',  'alice@example.com', 'Project Inquiry',       'Hi, I am interested in hiring your team for a new web application project. Can we schedule a call?', 0),
    ('Bob Kumar',      'bob@example.com',   'Collaboration Request',  'Hello! I represent a startup and we are looking for a development partner. Would love to discuss.', 0),
    ('Carol Williams', 'carol@example.com', 'Quote Request',          'Can you provide a quote for building an e-commerce site with Java backend and React frontend?', 1);
