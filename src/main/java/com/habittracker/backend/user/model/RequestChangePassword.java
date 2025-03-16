package com.habittracker.backend.user.model;

public record RequestChangePassword(String oldPassword, String newPassword) {

}
