package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.EditUserDto;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean userExists(String username) {
        return findByUsername(username).isPresent();
    }

    public String register(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (this.userExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username",
                    "Username already in use"));
        }

        if (user.getPassword() != null && user.getRpassword() != null) {
            if (!user.getPassword().equals(user.getRpassword())) {
                bindingResult.addError(new FieldError("user", "rpassword", "" +
                        "Passwords must match"));
            }
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        redirectAttributes.addFlashAttribute("message", "Success! Your registration is now complete");
        user.setPassword(encodePassword(user.getPassword()));
        Optional<Role> userRole = roleRepository.findByName("USER");
        userRole.ifPresent(role -> user.setRoles(Collections.singleton(role)));
        this.save(user);
        return "redirect:/login";
    }

    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }

    public EditUserDto userToDto(User user) {
        EditUserDto editUserDto = new EditUserDto();
        editUserDto.setMyUser(user);
        for(Role role: user.getRoles()) {
            switch (role.getName()) {
                case "USER":
                    editUserDto.setRoleUser(true);
                    break;
                case "COLLECTOR":
                    editUserDto.setRoleCollector(true);
                    break;
                case "ADMIN":
                    editUserDto.setRoleAdmin(true);
                    break;
                default:
                    break;
            }
        }
        return editUserDto;
    }

    public void updateUser(EditUserDto editUserDto) {
        User user = editUserDto.getMyUser();
        Set<Role> roles = new HashSet<>();
        if (editUserDto.isRoleUser()) {
            Role role = roleRepository.findByName("USER").get();
            roles.add(role);
        }
        if (editUserDto.isRoleCollector()) {
            Role role = roleRepository.findByName("COLLECTOR").get();
            roles.add(role);
        }
        if (editUserDto.isRoleAdmin()) {
            Role role = roleRepository.findByName("ADMIN").get();
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
