package uz.pdp.appnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnews.aop.CheckPermission;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.UserDto;
import uz.pdp.appnews.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    // add
    @CheckPermission(permission = "ADD_USER")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // delete
    @CheckPermission(permission = "DELETE_USER")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // get
    @CheckPermission(permission = "VIEW_USERS")
    @GetMapping
    public ResponseEntity<?> get() {
        List<User> users = userService.get();
        return ResponseEntity.status(users.size() != 0 ? 200 : 409).body(users);
    }


    // get by id
    @CheckPermission(permission = "VIEW_USERS")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.status(user != null ? 200 : 409).body(user);
    }


    // edit
    @CheckPermission(permission = "EDIT_USER")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.edit(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
