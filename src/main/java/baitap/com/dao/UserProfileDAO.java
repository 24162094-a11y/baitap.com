package baitap.com.dao;

import baitap.com.models.UserProfile;

public interface UserProfileDAO {
    UserProfile findByUsername(String username);
    void update(UserProfile profile);
}