package com.all4drive.features.auth.service

import com.all4drive.features.models.User
import com.all4drive.features.utils.checkPassword
import com.all4drive.features.utils.comparePassword
import com.all4drive.features.utils.generateHashFromPassword
import com.all4drive.features.utils.validateEmail
import java.util.*

class AuthService {
    private val users = com.all4drive.mock.users

    fun registration(candidate: User): Boolean {
        if (candidate.email.trim().isNotEmpty() && candidate.password.trim().isNotEmpty()) {
            val uniqueUser = users.find { user -> user.email == candidate.email.trim() }
            if (uniqueUser != null) return false
            val user = User(
                id = UUID.randomUUID().toString(),
                email = candidate.email,
                password = generateHashFromPassword(candidate.password)
            )
            users.add(user)
            return true
        }
        return false
    }

    fun login(email: String, password: String): Boolean {
        return if (validateEmail(email) && checkPassword(password)) {
            val user = users.find { user ->
                user.email == email && comparePassword(user.password, password.trim())
            }
            return user?.email?.isNotEmpty() ?: false
        } else {
            false
        }
    }
}