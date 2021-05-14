package uz.pdp.appnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnews.payload.ApiResponse;
import uz.pdp.appnews.payload.LoginDto;
import uz.pdp.appnews.payload.RegisterDto;
import uz.pdp.appnews.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    // register
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = authService.register(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // verify account
    @PostMapping("/verifyAccount")
    public ResponseEntity<?> verify(@RequestParam String emailCode, @RequestParam String email, @RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.verifyAccount(email, emailCode, loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
