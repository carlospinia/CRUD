package com.example.domain

/*
    Created by Carlos Piña on 16/11/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

sealed interface UserFailure {
    object NetworkConnection : UserFailure
    object ServerError : UserFailure
    object InvalidDateFormat : UserFailure
}