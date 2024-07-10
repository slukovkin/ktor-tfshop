package com.all4drive.utils

fun checkPassword(password: String): Boolean {
    return password.trim().isNotEmpty() && password.trim().length >= 6
}
