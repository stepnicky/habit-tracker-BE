package com.habittracker.backend.feature.user.model;

public record RequestChangePassword(String oldPassword, String newPassword) {

}
