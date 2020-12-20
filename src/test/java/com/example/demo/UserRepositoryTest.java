package com.example.demo;

import com.example.demo.config.RedisConfig;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {RedisConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPostgres() {
        User user = new User();
        user.setUsername("nick");
        user.setFirstName("Danyil");
        user.setLastName("Muntian");
        user.setPhone("+380123456789");
        user.setEmail("daniilmuntjan@gmail.com");

        User savedUser = userRepository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getUserId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testCreateUser() {
        Role role = new Role("USER");
        roleRepository.save(role);

        User user = new User();
        user.setUsername("winner");
        user.setPassword("12345678");
        user.setFirstName("Danyil");
        user.setLastName("Muntian");
        user.setPhone("+380123456789");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setEmail("qwerty@gmail.com");

        User savedUser = userRepository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getUserId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByUsername() {
        String username = "winner";

        User user = userRepository.findByUsername(username).get();

        assertThat(user).isNotNull();
    }

}
