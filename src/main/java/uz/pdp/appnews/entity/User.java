package uz.pdp.appnews.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appnews.entity.enums.Permission;
import uz.pdp.appnews.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbsEntity implements UserDetails {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Role role;

    private String emailCode;

    private boolean enabled;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    public User(String firstName, String lastName, String username, String password, String emailCode, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.emailCode = emailCode;
        this.role = role;
    }

    public User(String firstName, String lastName, String username, String password, Role role, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

//============= USER DETAILS METHOD =============//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> permissions = role.getPermissions();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (Permission permission : permissions) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(permission.name()));
        }

        return grantedAuthorityList;
    }
}
