package com.example.tbank.di

import com.example.tbank.data.repository.TokensRepositoryImpl
import com.example.tbank.data.repository.mock.AuthRepositoryMockImpl
import com.example.tbank.domain.repository.AuthRepository
import com.example.tbank.domain.repository.TokensRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsUserRepository(userRepositoryMockImpl: AuthRepositoryMockImpl): AuthRepository

    @Binds
    abstract fun bindsTokenRepository(tokensRepositoryImpl: TokensRepositoryImpl): TokensRepository
}
