package com.example.tbank.di

import com.example.tbank.data.remote.AuthApiService
import com.example.tbank.data.remote.UserApiService
import com.example.tbank.data.remote.authenticator.TokenAuthenticator
import com.example.tbank.data.remote.interceptor.AuthInterceptor
import com.example.tbank.data.remote.interceptor.UnlockingInterceptor
import com.example.tbank.data.remote.interceptor.UuidInterceptor
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.AuthRepository
import com.example.tbank.domain.repository.TokensRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.sync.Mutex
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideOkHttpClient(authRepository: AuthRepository, tokensRepository: TokensRepository): OkHttpClient {
        val mutex = Mutex()
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokensRepository))
            .addInterceptor(UuidInterceptor())
            .authenticator(
                TokenAuthenticator(
                    authRepository,
                    mutex,
                )
            )
            .addInterceptor(UnlockingInterceptor(mutex))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

}
