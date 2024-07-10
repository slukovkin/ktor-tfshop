package com.all4drive.utils

fun validateEmail(email: String): Boolean {
    return email.trim().isNotEmpty() && email.contains('@') && email.contains('.')
}