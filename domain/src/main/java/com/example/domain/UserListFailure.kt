package com.example.domain

/* 
    Created by Carlos Piña on 16/11/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

sealed interface UserListFailure {
    object NetworkConnection : UserListFailure
    object ServerError : UserListFailure
}