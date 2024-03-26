package com.example.web_project.model.DAO;

import java.util.List;

import com.example.web_project.model.Entity.UserEntity;

public interface UserDao {
    // select
    public UserEntity getUserByName(String userId);

    public List<UserEntity> getAllUser();

    // insert
    public void insertUser(UserEntity entity) throws Exception;

    // update
    public void updateUser(UserEntity entity);

    // delete
    public void deleteUser(String userId);


    
}
