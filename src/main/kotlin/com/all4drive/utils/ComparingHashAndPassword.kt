package com.all4drive.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun comparingHashAndPassword(passwordHash: String, password: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified
}