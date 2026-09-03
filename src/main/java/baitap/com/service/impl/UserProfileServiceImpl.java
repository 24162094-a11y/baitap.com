package baitap.com.service.impl;

import baitap.com.dao.UserProfileDAO;
import baitap.com.dao.impl.UserProfileDAOImpl;
import baitap.com.models.UserProfile;
import baitap.com.service.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileDAO dao = new UserProfileDAOImpl();
    @Override public UserProfile findByUsername(String username) { return dao.findByUsername(username); }
    @Override public void update(UserProfile profile) { dao.update(profile); }
}