package uz.pdp.appnews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.entity.enums.RoleType;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.RoleDto;
import uz.pdp.appnews.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;


    // add
    public ApiResponse add(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName())) {
            return new ApiResponse("Role already exist", false);
        }

        Role role = new Role(
                roleDto.getName(),
                RoleType.CUSTOM,
                roleDto.getDescription(),
                roleDto.getPermissions()
        );
        roleRepository.save(role);
        return new ApiResponse("Role successfully saved", true);
    }


    // edit
    public ApiResponse edit(Long id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }

        if (roleRepository.existsByNameAndIdNot(roleDto.getName(), id)) {
            return new ApiResponse("Role name already exist", false);
        }

        Role role = optionalRole.get();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setPermissions(roleDto.getPermissions());
        roleRepository.save(role);
        return new ApiResponse("Role edited", true);
    }


    // delete
    public ApiResponse delete(Long id) {
        try {
            roleRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    // get all
    public List<Role> get() {
        return roleRepository.findAll();
    }


    // get by id
    public Role getById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }
}
