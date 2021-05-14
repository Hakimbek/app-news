package uz.pdp.appnews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.entity.enums.RoleType;
import uz.pdp.appnews.exception.ResourceNotFoundException;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.LoginDto;
import uz.pdp.appnews.payload.RegisterDto;
import uz.pdp.appnews.repository.RoleRepository;
import uz.pdp.appnews.repository.UserRepository;
import uz.pdp.appnews.security.JwtProvider;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    // register
    public ApiResponse register(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPrePassword())) {
            return new ApiResponse("Passwords not equal", false);
        }

        if (userRepository.existsByUsername(registerDto.getEmail())) {
            return new ApiResponse("User already exist", false);
        }

        User user = new User(
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                UUID.randomUUID().toString(),
                roleRepository.findByRoleType(RoleType.USER).orElseThrow(() -> new ResourceNotFoundException("Role", "RoleType", RoleType.USER))
        );
        User savedUser = userRepository.save(user);

        sendEmail(savedUser.getUsername(), savedUser.getEmailCode());

        return new ApiResponse("User successfully registered", true);
    }


    // login
    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Password or login is incorrect", false);
        }
    }


    // verify account
    public ApiResponse verifyAccount(String username, String emailCode, LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByUsernameAndEmailCode(username, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
            userRepository.save(user);

            return new ApiResponse("Account is verified", true);
        }
        return new ApiResponse("Account already verified", false);
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
