package com.example.core

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
}