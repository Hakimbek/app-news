package uz.pdp.appnews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.UserDto;
import uz.pdp.appnews.repository.RoleRepository;
import uz.pdp.appnews.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;


    // add
    public ApiResponse add(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getEmail())) {
            return new ApiResponse("User already exist", false);
        }

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        Role role = optionalRole.get();

        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                UUID.randomUUID().toString(),
                role
        );
        User savedUser = userRepository.save(user);

        sendEmail(savedUser.getUsername(), savedUser.getEmailCode());

        return new ApiResponse("Successfully added",true);
    }


    // delete
    public ApiResponse delete(Long id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    // get
    public List<User> get() {
        return userRepository.findAll();
    }


    // get by id
    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


    // edit
    public ApiResponse edit(Long id, UserDto userDto) {
        if (userRepository.existsByUsernameAndIdNot(userDto.getEmail(), id)) {
            return new ApiResponse("User already exist", false);
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }

        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        Role role = optionalRole.get();

        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setRole(role);
        userRepository.save(user);
        return new ApiResponse("Successfully edited", true);
    }


    // send message to user email
    public void sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("email.sender.hr@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("Verify account");
            simpleMailMessage.setText("http://localhost:8080/api/auth/verifyAccount?emailCode=" + emailCode + "&email=" + sendingEmail);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
