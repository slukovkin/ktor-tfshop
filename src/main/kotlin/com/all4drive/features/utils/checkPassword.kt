package com.all4drive.features.utils

fun checkPassword(password: String): Boolean {
    return password.trim().isNotEmpty()
}