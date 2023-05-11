package com.crevan.votesystem.util;

import com.crevan.votesystem.config.SecurityConfig;
import com.crevan.votesystem.model.User;
import com.crevan.votesystem.model.Role;
import com.crevan.votesystem.to.UserTo;

public class UserUtil {
    public static User createNewFromTo(final UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getPassword(), userTo.getEmail().toLowerCase(), Role.USER);
    }

    public static User updateFromTo(final User user, final UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(final User user) {
        user.setPassword(SecurityConfig.PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
