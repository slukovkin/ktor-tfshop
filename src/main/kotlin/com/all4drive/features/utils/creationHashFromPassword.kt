package com.all4drive.features.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun generateHashFromPassword(password: String): String {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray())
}


