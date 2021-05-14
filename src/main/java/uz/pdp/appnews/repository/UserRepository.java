package uz.pdp.appnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnews.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndEmailCode(String username, String emailCode);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
