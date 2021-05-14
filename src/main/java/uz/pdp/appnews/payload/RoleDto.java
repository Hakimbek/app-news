package uz.pdp.appnews.payload;

import lombok.Data;
import uz.pdp.appnews.entity.enums.Permission;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleDto {
    @NotBlank
    private String name;

    private String description;

    private List<Permission> permissions;
}
