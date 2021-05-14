package uz.pdp.appnews.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.entity.enums.Permission;
import uz.pdp.appnews.entity.enums.RoleType;
import uz.pdp.appnews.repository.RoleRepository;
import uz.pdp.appnews.repository.UserRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            Permission[] permissions = Permission.values();

            // admin role qo'shdim
            Role adminRole = roleRepository.save(new Role(
                    "Admin",
                    RoleType.ADMIN,
                    "Dastur egasi",
                    Arrays.asList(permissions))
            );

            // user role qo'shdim
            roleRepository.save(new Role(
                    "User",
                    RoleType.USER,
                    "Oddiy foydalanuvchi",
                    Arrays.asList(
                            Permission.ADD_COMMENT,
                            Permission.EDIT_COMMENT,
                            Permission.DELETE_MY_COMMENT
                    )
            ));

            // admin qo'shdim
            userRepository.save(new User(
                    "AdminFirstName",
                    "AdminLastName",
                    "Admin@mail.com",
                    passwordEncoder.encode("123"),
                    adminRole,
                    true
            ));
        }
    }
}
