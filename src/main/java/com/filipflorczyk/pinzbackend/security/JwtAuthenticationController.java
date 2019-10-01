package com.filipflorczyk.pinzbackend.security;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/auth/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest registerRequest) throws Exception {

        userService.registerUser(registerRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/validate-token", method = RequestMethod.POST)
    public ResponseEntity<?> validateToken(@RequestBody JwtValidationRequest validationRequest) throws Exception {

        if(jwtTokenUtil.validateToken(validationRequest.getToken(),
                userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(validationRequest.getToken())))) {
            throw new JwtException("Jwt token is not valid");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
