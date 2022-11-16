package com.example.data

import com.example.core.Network
import com.example.domain.UserRepository
import com.example.domain.createUser.CreateUser
import com.example.domain.deleteUser.DeleteUser
import com.example.domain.editUser.EditUser
import com.example.domain.getUser.GetUser
import com.example.domain.loadUserList.LoadUserList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/* 
    Created by Carlos Piña on 16/11/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesCreateUser(userRepository: UserRepository): CreateUser = CreateUser(userRepository)

    @Provides
    fun providesEditUser(userRepository: UserRepository): EditUser = EditUser(userRepository)

    @Provides
    fun providesGetUser(userRepository: UserRepository): GetUser = GetUser(userRepository)

    @Provides
    fun providesDeleteUser(userRepository: UserRepository): DeleteUser = DeleteUser(userRepository)

    @Provides
    fun providesLoadUserList(userRepository: UserRepository): LoadUserList = LoadUserList(userRepository)

    @Provides
    fun providesRetrofit(): UserRetrofit = Network.initRetrofit().create(UserRetrofit::class.java)

    @Provides
    fun providesRemoteDataSource(retrofit: UserRetrofit): UserRepository = UserRepositoryImpl(retrofit)
}