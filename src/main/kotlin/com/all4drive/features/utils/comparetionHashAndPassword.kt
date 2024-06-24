package com.all4drive.features.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun comparePassword(passwordHash: String, password: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified
}