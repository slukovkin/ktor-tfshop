package com.all4drive.utils

sealed class ErrorException(val message: String) {
    class SmallLengthPassword : ErrorException("Required password length is more than 6 characters")
    class EmptyPassword : ErrorException("The password must not be empty")
    class SuccessPassword : ErrorException("Valid password")
}