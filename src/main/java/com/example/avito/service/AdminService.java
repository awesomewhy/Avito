package com.example.avito.service;

import com.example.avito.entity.User;

import java.util.List;

public interface AdminService {
    String convertUsersToJson(List<User> users);
}
