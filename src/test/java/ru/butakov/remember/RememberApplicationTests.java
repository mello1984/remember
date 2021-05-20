package ru.butakov.remember;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.butakov.remember.controllers.MainController;
import ru.butakov.remember.controllers.RecordsController;
import ru.butakov.remember.controllers.RegistrationController;
import ru.butakov.remember.controllers.UserController;
import ru.butakov.remember.dao.RecordsRepository;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.service.RecordsService;
import ru.butakov.remember.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RememberApplicationTests {
    @Autowired
    MainController mainController;
    @Autowired
    RecordsController recordsController;
    @Autowired
    RegistrationController registrationController;
    @Autowired
    UserController userController;
    @Autowired
	RecordsRepository recordsRepository;
    @Autowired
	UserRepository userRepository;
    @Autowired
    RecordsService recordsService;
    @Autowired
    UserService userService;


    @Test
    void contextLoads() {
        assertThat(mainController).isNotNull();
        assertThat(recordsController).isNotNull();
        assertThat(registrationController).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(recordsRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(recordsService).isNotNull();
        assertThat(userService).isNotNull();
    }

}
