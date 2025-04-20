package com.dk.Launcher;

import com.dk.Dtos.LoginRequest;
import com.dk.Model.User;
import com.dk.Repository.UserRepository;
import com.dk.Security.JwtUtils;
import com.dk.Service.JwtAuthenticationReponse;
import com.dk.Service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationReponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()) );

        SecurityContextHolder.getContext().setAuthentication((authentication));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        return new JwtAuthenticationReponse(jwt);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
