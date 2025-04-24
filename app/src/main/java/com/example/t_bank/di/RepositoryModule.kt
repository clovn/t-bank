package com.example.t_bank.di

import com.example.t_bank.data.repository.mock.UserRepositoryMockImpl
import com.example.t_bank.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsUserRepository(userRepositoryMockImpl: UserRepositoryMockImpl): UserRepository
}