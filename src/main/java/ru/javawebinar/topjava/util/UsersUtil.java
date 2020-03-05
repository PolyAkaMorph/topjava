package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "John", "john@gmail.com", "abcde", 2400, true, EnumSet.of(Role.ROLE_USER)),
            new User(null, "Kate", "kate@gmail.com", "bcde ", 1800, true, EnumSet.of(Role.ROLE_USER)),
            new User(null, "Igor", "igor@gmail.com", "123", 2500, true, EnumSet.of(Role.ROLE_USER)),
            new User(null, "root", "root@gmail.com", "root", 100, true, EnumSet.of(Role.ROLE_USER,Role.ROLE_ADMIN))
    );
}
