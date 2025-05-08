package com.example.tbank.di

import com.example.tbank.data.repository.mock.ExpensesRepositoryMockImpl
import com.example.tbank.data.repository.mock.TripRepositoryMockImpl
import com.example.tbank.data.repository.mock.UserRepositoryMockImpl
import com.example.tbank.domain.repository.ExpensesRepository
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
}
