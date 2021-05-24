package ru.butakov.remember.service;

public interface MailSender {
    void send(String mailTo, String subject, String text);
}
