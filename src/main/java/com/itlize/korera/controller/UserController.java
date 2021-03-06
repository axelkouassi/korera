package com.itlize.korera.controller;

import com.itlize.korera.model.User;
import com.itlize.korera.service.UserService;
import com.itlize.korera.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    //Controller/api to register/create an account
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/korera/user/register")
                .toUriString());
        if (!service.usernameExists(user.getUsername())){
            service.saveUser(user);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
            //return ResponseEntity.status(409).body("Username already exists!");
            //return new ResponseEntity<>("Username already exists!", HttpStatus.CONFLICT);
        }
        return ResponseEntity.created(uri).body(user);
    }

    //Controller/api to log in/generate authentication token to already registered account
    @PostMapping( "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user )  throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        User response = service.findByUsername(user.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        //jwt gives token in postman switch with user to get user details
        return  new ResponseEntity<>(jwt, HttpStatus.OK);
    }


    //Controller/api to get user information based on username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        return ResponseEntity.ok().body(service.findByUsername(username));
    }

    //Controller/api to get user information based on user id
    @GetMapping("/id/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userid){
        if (!service.userIdExists(userid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id \"" + userid + "\" does not exists!");
        }
        return ResponseEntity.ok().body(service.findByUserId(userid));
    }

    //Controller/api to get all the users' information
    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(service.getUsers());
    }

    //Controller/api to update username of user
    @PostMapping("/update/username/{username}")
    public ResponseEntity<?> updateUsername(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updateUsername(verifiedUser,user.getUsername());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update password of user
    @PostMapping("/update/password/{username}")
    public ResponseEntity<?> updatePassword(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updatePassword(verifiedUser,user.getPassword());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update email of user
    @PostMapping("/update/email/{username}")
    public ResponseEntity<?> updateEmail(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updateEmail(verifiedUser,user.getEmail());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update first name of user
    @PostMapping("/update/firstname/{username}")
    public ResponseEntity<?> updateFirstName(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updateFirstName(verifiedUser,user.getFirstName());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update last name of user
    @PostMapping("/update/lastname/{username}")
    public ResponseEntity<?> updateLastName(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updateLastName(verifiedUser,user.getLastName());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update phone of user
    @PostMapping("/update/phone/{username}")
    public ResponseEntity<?> updatePhone(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updatePhone(verifiedUser,user.getPhone());
        return ResponseEntity.ok().body(verifiedUser);
    }

    //Controller/api to update role of user
    @PostMapping("/update/role/{username}")
    public ResponseEntity<?> updateRole(@RequestBody User user, @PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        User verifiedUser = service.findByUsername(username);
        verifiedUser = service.updateRole(verifiedUser,user.getRole());
        return ResponseEntity.ok().body(verifiedUser);
    }



    //Controller/api to delete user information based on username
    @GetMapping("/delete/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username){
        if (!service.usernameExists(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }
        service.deleteByUsername(username);
        return ResponseEntity.ok().body("User with username \"" + username + "\" was successfully deleted.");
    }

    //Controller/api to delete user information based on user id
    @GetMapping("/delete/id/{userid}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer userid){
        if (!service.userIdExists(userid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id \"" + userid + "\" does not exists!");
        }
        service.deleteByUserId(userid);
        return ResponseEntity.ok().body("User with user id \"" + userid + "\" was successfully deleted.");
    }

    //Controller/api to delete all users
    @GetMapping("/delete/users")
    public ResponseEntity<?> deleteUsers(){
        service.deleteUsers();
        return ResponseEntity.ok().body("All users have been successfully deleted.");
    }




}
