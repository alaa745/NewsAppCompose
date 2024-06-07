package com.example.newsappcompose.di

import com.example.newsappcompose.data.remote.api.LoggingInterceptor
import com.example.newsappcompose.data.remote.api.NewsApiManager
import com.example.newsappcompose.data.remote.datasource.NewsApiDataSourceImpl
import com.example.newsappcompose.data.remote.repository.NewsApiRepositoryImpl
import com.example.newsappcompose.domain.Constants.BASE_URL
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.repository.NewsApiRepository
import com.example.newsappcompose.domain.usecase.GetNewsUseCase
import com.example.newsappcompose.domain.usecase.GetTopNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(100 , TimeUnit.SECONDS)
            .readTimeout(100 , TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsApi(retrofit: Retrofit): NewsApiManager{
        return retrofit.create(NewsApiManager::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsApiDataSource(apiManager: NewsApiManager): NewsApiDataSource = NewsApiDataSourceImpl(newsApiManager = apiManager)


    @Provides
    @Singleton
    fun providesNewsApiRepository(newsApiDataSource: NewsApiDataSource): NewsApiRepository = NewsApiRepositoryImpl(newsApiDataSource)

    @Provides
    @Singleton
    fun providesGetNewsUseCase(newsApiRepository: NewsApiRepository): GetNewsUseCase = GetNewsUseCase(newsApiRepository = newsApiRepository)

    @Provides
    @Singleton
    fun providesGetTopNewsUseCase(newsApiRepository: NewsApiRepository): GetTopNewsUseCase = GetTopNewsUseCase(newsApiRepository = newsApiRepository)

}