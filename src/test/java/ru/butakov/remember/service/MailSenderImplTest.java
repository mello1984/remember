package ru.butakov.remember.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"/application-test.properties", "/private-test.properties"})
class MailSenderImplTest {
    @Autowired
    MailSender mailSender;
    @MockBean
    JavaMailSender javaMailSender;

    @Test
    void send() {
        Mockito.doNothing().when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));

        mailSender.send("to", "sub", "text");

        Mockito.verify(javaMailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }
}