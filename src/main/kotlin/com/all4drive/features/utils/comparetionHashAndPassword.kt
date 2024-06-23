package com.all4drive.features.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun comparePassword(passwordHash: String, password: String): Boolean {
    val result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash)
    return result.verified
}