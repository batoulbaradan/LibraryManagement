package com.java.springBoot.app.RestController;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.java.springBoot.app.Service.UserService;
import com.java.springBoot.app.Class.ResponseStatus;
import com.java.springBoot.app.Class.Request.AuthRequest;
import com.java.springBoot.app.Class.Response.APIResponse;
import com.java.springBoot.app.Class.Response.DTO.UserDTO;
import com.java.springBoot.app.Class.Response.DTO.Mapper.UserMapper;
import com.java.springBoot.app.Component.JWTUtil;
import com.java.springBoot.app.DTO.UserLoginDTO;
import com.java.springBoot.app.DTO.UserRegisterDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public APIResponse<UserDTO> register(@Valid @RequestBody AuthRequest request) {
    	UserMapper userMapper = null;
    	APIResponse apiResponse= new APIResponse<UserDTO>();
        if (userService.isUsernameTaken(request.getUsername())) {
            return APIResponse.error(ResponseStatus.USERNAME_TAKEN);
        }

    	UserDTO userDTO =userMapper.parse(userService.saveUser(request)); 
    	apiResponse = APIResponse.success(userDTO);
        return apiResponse;
    }

    @PostMapping("/login")
    public APIResponse<String> login(@RequestBody AuthRequest request) {
    	APIResponse apiResponse= new APIResponse<String>();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(request.getUsername());
    	apiResponse = APIResponse.success(token);
        return apiResponse;
    }

    @PostMapping("/logout")
    public APIResponse<String> logout() {
    	APIResponse apiResponse= new APIResponse<String>();

        SecurityContextHolder.clearContext();
        apiResponse.setResultCode(0);
    	apiResponse.setMessage("Logged out successfully!");
    	apiResponse.setData(null);
    	
        return apiResponse;
    }
}
