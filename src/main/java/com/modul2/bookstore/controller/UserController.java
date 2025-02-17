package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.UserDTO;
import com.modul2.bookstore.entities.User;
import com.modul2.bookstore.mapper.UserMapper;
import com.modul2.bookstore.repository.UserRepository;
import com.modul2.bookstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appUsers")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        User userToCreate = UserMapper.userDTO2User(userDTO);
        User createdUser = userService.create(userToCreate);
        return ResponseEntity.ok(UserMapper.user2UserDTO(createdUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable(name = "userId") Long userIdToSearchFor) {
        User foundUser = userService.getById(userIdToSearchFor);
        return ResponseEntity.ok(UserMapper.user2UserDTO(foundUser));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(UserMapper::user2UserDTO).toList());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "userId") Long userIdToDelete) {
        userService.deleteById(userIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "userId") Long userIdToUpdate, @RequestBody UserDTO userBody) {
        User userEntity = UserMapper.userDTO2User(userBody);
        User updatedUser = userService.updateById(userIdToUpdate, userEntity);
        return ResponseEntity.ok(UserMapper.user2UserDTO(updatedUser));
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam("userId") Long userId, @RequestParam("code") String code) {
        User verifiedUser = userService.verifyCode(userId, code);
        return ResponseEntity.ok(UserMapper.user2UserDTO(verifiedUser));
    }
}
