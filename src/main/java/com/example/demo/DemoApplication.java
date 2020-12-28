package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;

@SpringBootApplication
@EntityScan("com.example.demo.domain")
@EnableJpaRepositories("com.example.demo.repositories")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(RoleService roleService, UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role roleAdmin;
                Role roleCollector;
                Role roleUser;
                if (!roleService.roleExists("ADMIN")) {
                    roleAdmin = new Role("ADMIN");
                    roleService.save(roleAdmin);
                } else {
                    roleAdmin = roleService.findByName("ADMIN").get();
                }

                if (!roleService.roleExists("COLLECTOR")) {
                    roleCollector = new Role("COLLECTOR");
                    roleService.save(roleCollector);
                } else {
                    roleCollector = roleService.findByName("COLLECTOR").get();
                }

                if (!roleService.roleExists("USER")) {
                    roleUser = new Role("USER");
                    roleService.save(roleUser);
                } else {
                    roleUser = roleService.findByName("USER").get();
                }

                if (userService.findByUsername("admin").isEmpty()) {
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(userService.encodePassword("admin"));
                    admin.setRoles(Collections.singleton(roleAdmin));
                    admin.setFirstName("Danyil");
                    admin.setLastName("Muntian");
                    admin.setPhone("+380671364725");
                    admin.setEmail("dfjkl@fjdkl.djfkl");
                    System.out.println(userService.save(admin));
                }

                if(userService.findByUsername("qqqq").isEmpty()) {
                    User client = new User();
                    client.setUsername("qqqq");
                    client.setPassword(userService.encodePassword("qqqq"));
                    client.setRoles(Collections.singleton(roleUser));
                    client.setFirstName("Danyil");
                    client.setLastName("Muntian");
                    client.setPhone("0508974518");
                    client.setEmail("qwerty@mail.com");
                    System.out.println(userService.save(client));
                }

                if(userService.findByUsername("wwww").isEmpty()) {
                    User collector = new User();
                    collector.setUsername("wwww");
                    collector.setPassword(userService.encodePassword("wwww"));
                    collector.setRoles(Collections.singleton(roleCollector));
                    collector.setFirstName("Ivan");
                    collector.setLastName("Ivaniv");
                    collector.setPhone("+380973695824");
                    collector.setEmail("ivanov@gmail.com");
                    System.out.println(userService.save(collector));
                }
            }
        };
    }

}
