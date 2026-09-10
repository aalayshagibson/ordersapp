package com.shadsluiter.ordersapp.service;

import com.shadsluiter.ordersapp.data.UserRepository;
import com.shadsluiter.ordersapp.models.UserEntity;
import com.shadsluiter.ordersapp.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel save(UserModel userModel) {

        UserEntity userEntity = convertToEntity(userModel);

        // Encrypt the password before saving it
        userEntity.setPassword(
                passwordEncoder.encode(userModel.getPassword())
        );

        UserEntity result = userRepository.save(userEntity);

        return convertToModel(result);
    }

    public UserModel findByLoginName(String loginName) {

        UserEntity userEntity =
                userRepository.findByLoginName(loginName);

        return convertToModel(userEntity);
    }

    // Convert Entity to Model
    public UserModel convertToModel(UserEntity userEntity) {

        if (userEntity == null) {
            return null;
        }

        UserModel userModel = new UserModel();

        String id = Long.toString(userEntity.getId());

        userModel.setId(id);
        userModel.setLoginName(userEntity.getLoginName());
        userModel.setPassword(userEntity.getPassword());

        return userModel;
    }

    // Convert Model to Entity
    private UserEntity convertToEntity(UserModel userModel) {

        UserEntity userEntity = new UserEntity();

        if (userModel.getId() != null) {
            userEntity.setId(
                    Long.parseLong(userModel.getId())
            );
        }

        userEntity.setLoginName(userModel.getLoginName());
        userEntity.setPassword(userModel.getPassword());

        return userEntity;
    }
}