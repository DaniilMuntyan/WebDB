package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(RoleService roleService, UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                /*roleService.save(new Role("ADMIN"));
                roleService.save(new Role("COLLECTOR"));
                roleService.save(new Role("USER"));*/
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
            }
        };
    }

}
