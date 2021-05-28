package ru.butakov.remember;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.butakov.remember.config.MailConfig;
import ru.butakov.remember.controllers.MainController;
import ru.butakov.remember.controllers.PostController;
import ru.butakov.remember.controllers.RegistrationController;
import ru.butakov.remember.controllers.UserController;
import ru.butakov.remember.dao.PostRepository;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.service.MailSender;
import ru.butakov.remember.service.PostService;
import ru.butakov.remember.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource({"/application-test.properties", "/private-test.properties"})
class RememberApplicationTests {
    @Autowired
    MainController mainController;
    @Autowired
    PostController postController;
    @Autowired
    RegistrationController registrationController;
    @Autowired
    UserController userController;
    @Autowired
    PostRepository postRepository;
    @Autowired
	UserRepository userRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @MockBean
    MailConfig mailConfig;
    @MockBean
    MailSender mailSender;


    @Test
    void contextLoads() {
        assertThat(mainController).isNotNull();
        assertThat(postController).isNotNull();
        assertThat(registrationController).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(postRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(postService).isNotNull();
        assertThat(userService).isNotNull();
    }

}
