package com.example.tbank.di

import com.example.tbank.data.remote.ExpensesApiService
import com.example.tbank.data.remote.TripApiService
import com.example.tbank.data.remote.UserApiService
import com.example.tbank.data.remote.AuthApiService
import com.example.tbank.data.remote.authenticator.TokenAuthenticator
import com.example.tbank.data.remote.interceptor.AuthInterceptor
import com.example.tbank.data.remote.interceptor.UnlockingInterceptor
import com.example.tbank.data.remote.interceptor.UuidInterceptor
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

    companion object{
        const val BASE_URL = "http://192.168.0.104:8081/api/v1/"
    }

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
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    fun providesTripApiService(retrofit: Retrofit): TripApiService {
        return retrofit.create(TripApiService::class.java)
    }

    @Provides
    fun providesExpensesApiService(retrofit: Retrofit): ExpensesApiService {
        return retrofit.create(ExpensesApiService::class.java)
    }
}
