package com.org.kodvix.redbooks.service;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
