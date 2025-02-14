package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.User;
import com.modul2.bookstore.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
