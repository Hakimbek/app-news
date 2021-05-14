package uz.pdp.appnews.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.appnews.entity.enums.Permission;
import uz.pdp.appnews.entity.enums.RoleType;
import uz.pdp.appnews.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    private String description;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Permission> permissions;
}
