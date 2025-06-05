package com.example.tbank.di

import com.example.tbank.data.repository.mock.ExpensesRepositoryMockImpl
import com.example.tbank.data.repository.mock.TripRepositoryMockImpl
import com.example.tbank.data.repository.mock.UserRepositoryMockImpl
import com.example.tbank.domain.repository.ExpensesRepository
import com.example.tbank.domain.repository.TripRepository
import com.example.tbank.domain.repository.UserRepository
import com.example.tbank.data.repository.TokensRepositoryImpl
import com.example.tbank.data.repository.TripRepositoryImpl
import com.example.tbank.data.repository.UserRepositoryImpl
import com.example.tbank.data.repository.mock.AuthRepositoryMockImpl
import com.example.tbank.data.repository.mock.UserRepositoryMockImpl
import com.example.tbank.domain.repository.AuthRepository
import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.TripRepository
import com.example.tbank.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsUserRepository(userRepositoryMockImpl: UserRepositoryMockImpl): UserRepository

    @Binds
    abstract fun bindsTripRepository(tripRepositoryMockImpl: TripRepositoryMockImpl): TripRepository

    @Binds
    abstract fun bindsExpensesRepository(expensesRepositoryMockImpl: ExpensesRepositoryMockImpl): ExpensesRepository

    @Binds
    abstract fun bindsAuthRepository(userRepositoryMockImpl: AuthRepositoryMockImpl): AuthRepository

    @Binds
    abstract fun bindsTokenRepository(tokensRepositoryImpl: TokensRepositoryImpl): TokensRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryMockImpl): UserRepository

    @Binds
    abstract fun bindsTripRepository(tripRepositoryImpl: TripRepositoryImpl): TripRepository
}
