package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.User;
import com.modul2.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        String md5Hex = DigestUtils
                .md5Hex(user.getPassword()).toUpperCase();

        user.setPassword(md5Hex);
        return userRepository.save(user);
    }

    public User getById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateById(Long userId, User userBody) {
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        updatedUser.setFirstName(userBody.getFirstName());
        updatedUser.setLastName(userBody.getLastName());
        updatedUser.setYearOfBirth(userBody.getYearOfBirth());
        updatedUser.setGender(userBody.getGender());
        updatedUser.setEmail(userBody.getEmail());
        updatedUser.setPhoneNumber(userBody.getPhoneNumber());
        updatedUser.setPassword(DigestUtils.md5Hex(userBody.getPassword()).toUpperCase());
        updatedUser.setCountry(userBody.getCountry());

        return userRepository.save(updatedUser);
    }
}
