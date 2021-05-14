package uz.pdp.appnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnews.aop.CheckPermission;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.RoleDto;
import uz.pdp.appnews.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;


    // add
    @CheckPermission(permission = "ADD_ROLE")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.add(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // edit
    @CheckPermission(permission = "EDIT_ROLE")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.edit(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // delete
    @CheckPermission(permission = "DELETE_ROLE")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // get all
    @CheckPermission(permission = "VIEW_ROLE")
    @GetMapping
    public ResponseEntity<?> get() {
        List<Role> roles = roleService.get();
        return ResponseEntity.status(roles.size() != 0 ? 200 : 409).body(roles);
    }


    // get by id
    @CheckPermission(permission = "VIEW_ROLE")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return ResponseEntity.status(role != null ? 200 : 409).body(role);
    }
}
