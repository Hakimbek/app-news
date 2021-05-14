package uz.pdp.appnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.entity.enums.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
