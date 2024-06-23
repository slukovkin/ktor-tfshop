package com.all4drive.features.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun generateHashFromPassword(password: String): String {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray())
}

fun comparePassword(passwordHash: String, password: String): Boolean {
    val result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash)
    return result.verified
}
