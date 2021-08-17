package com.itlize.korera.service;

import com.itlize.korera.model.Role;
import com.itlize.korera.model.User;
import com.itlize.korera.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j // logs
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    //Check if username exists
    @Override
    public boolean usernameExists(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    //Checking if user exists by Id
    @Override
    public boolean userIdExists(Integer userId){
        return userRepository.existsById(userId);
    }

    //Saving user information
    @Override
    public User saveUser(User user) {
        log.info("Encrypting password.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTimeCreated(LocalDateTime.now());
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new user to the database.");
        return userRepository.save(user);
    }

    //Find user by username
    @Override
    public User findByUsername(String username) {
        log.info("Fetching user with username: " + username);
        User user =  userRepository.findByUsername(username).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("Username: " + username + " was not found in the database.");
        }

        return user;
    }

    //Find user by user id
    @Override
    public User findByUserId(Integer userId) {
        log.info("Fetching user with id: " + userId);
        User user =  userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("User id: " + userId + " was not found in the database.");
        }

        return user;
    }

    //Find all users
    @Override
    public List<User> getUsers() {
        log.info("Fetching all users.");
        return userRepository.findAll();
    }

    //Update username
    @Override
    public User updateUsername(User user, String username) {
        log.info("Updating username.");
        user.setUsername(username);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated username information to the database.");
        return userRepository.save(user);
    }

    //Update password
    @Override
    public User updatePassword(User user, String password) {
        log.info("Updating password.");
        user.setPassword(password);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated password information to the database.");
        return userRepository.save(user);
    }

    //Update email
    @Override
    public User updateEmail(User user, String email) {
        log.info("Updating email.");
        user.setEmail(email);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated email information to the database.");
        return userRepository.save(user);
    }

    //Update first name
    @Override
    public User updateFirstName(User user, String firstName) {
        log.info("Updating first name.");
        user.setFirstName(firstName);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated first name information to the database.");
        return userRepository.save(user);
    }

    //Update last name
    @Override
    public User updateLastName(User user, String lastName) {
        log.info("Updating first name.");
        user.setLastName(lastName);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated last name information to the database.");
        return userRepository.save(user);
    }

    //Update phone
    @Override
    public User updatePhone(User user, Integer phone) {
        log.info("Updating first name.");
        user.setPhone(phone);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated last name information to the database.");
        return userRepository.save(user);
    }

    //Update phone
    @Override
    public User updateRole(User user, Role role) {
        log.info("Updating first name.");
        user.setRole(role);
        user.setTimeUpdated(LocalDateTime.now());
        log.info("Saving new updated role information to the database.");
        return userRepository.save(user);
    }

    //Delete user by username
    @Override
    public void deleteByUsername(String username) {
        log.info("Deleting user with username: " + username);
        userRepository.deleteByUsername(username);
    }

    //Delete user by user id
    @Override
    public void deleteByUserId(Integer userId) {
        log.info("Deleting user with username: " + userId);
        userRepository.deleteById(userId);
    }

    //Delete all users
    @Override
    public void deleteUsers() {
        log.info("Deleting all users.");
        userRepository.deleteAll();
    }
}
