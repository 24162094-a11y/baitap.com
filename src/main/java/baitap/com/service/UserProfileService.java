package baitap.com.service;

import baitap.com.models.UserProfile;

public interface UserProfileService {
    UserProfile findByUsername(String username);
    void update(UserProfile profile);
}