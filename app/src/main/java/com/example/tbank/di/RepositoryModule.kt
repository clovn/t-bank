package com.example.tbank.di

import com.example.tbank.data.repository.AuthRepositoryImpl
import com.example.tbank.data.repository.ExpensesRepositoryImpl
import com.example.tbank.domain.repository.ExpensesRepository
import com.example.tbank.domain.repository.TripRepository
import com.example.tbank.domain.repository.UserRepository
import com.example.tbank.data.repository.TokensRepositoryImpl
import com.example.tbank.data.repository.TripRepositoryImpl
import com.example.tbank.data.repository.UserRepositoryImpl
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
    abstract fun bindsTripRepository(tripRepositoryImpl: TripRepositoryImpl): TripRepository

    @Binds
    abstract fun bindsExpensesRepository(expensesRepositoryImpl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsTokenRepository(tokensRepositoryImpl: TokensRepositoryImpl): TokensRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}
