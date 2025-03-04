package org.example.miniprojet.services;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.LogManager;
import org.example.miniprojet.DTO.NewPasswordDTO;
import org.example.miniprojet.entities.User;
import org.example.miniprojet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createCode(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user == null)
            return null;

        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(99999999);
        String body = "Your reset password code is : "+code;

        emailService.sendMail(email, "Reset Password", body);
        user.setCode(code);
        return userRepository.save(user);
    }

    @Override
    public User newPassword(NewPasswordDTO newPasswordDTO) {
        User updatedUser = userRepository.findById(newPasswordDTO.idUser()).orElse(null);
        if(updatedUser == null)
            return null;

        updatedUser.setPassword(bCryptPasswordEncoder.encode(newPasswordDTO.newPassword()));
        return userRepository.save(updatedUser);
    }
}
