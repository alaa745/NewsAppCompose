package com.example.newsappcompose.di

import android.content.Context
import androidx.room.Room
import com.example.newsappcompose.data.local.AppDatabase
import com.example.newsappcompose.data.local.Converters
import com.example.newsappcompose.data.local.NewsDao
import com.example.newsappcompose.data.remote.api.LoggingInterceptor
import com.example.newsappcompose.data.remote.api.NewsApiManager
import com.example.newsappcompose.data.remote.datasource.NewsApiDataSourceImpl
import com.example.newsappcompose.data.remote.repository.NewsRepositoryImpl
import com.example.newsappcompose.domain.Constants.BASE_URL
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.repository.NewsRepository
import com.example.newsappcompose.domain.usecase.GetNewsUseCase
import com.example.newsappcompose.domain.usecase.GetSavedArticlesUseCase
import com.example.newsappcompose.domain.usecase.GetTopNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesNewsApiRepository(newsApiDataSource: NewsApiDataSource , newsDao: NewsDao): NewsRepository = NewsRepositoryImpl(newsApiDataSource , newsDao)

    @Provides
    @Singleton
    fun providesGetNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase = GetNewsUseCase(newsRepository = newsRepository)

    @Provides
    @Singleton
    fun providesGetTopNewsUseCase(newsRepository: NewsRepository): GetTopNewsUseCase = GetTopNewsUseCase(newsRepository = newsRepository)

    @Provides
    @Singleton
    fun providesGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedArticlesUseCase = GetSavedArticlesUseCase(repository = newsRepository)

//    @Provides
//    @Singleton
//    fun providesMyApp() = MyApp()

    @Provides
    @Singleton
    fun providesAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase{
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java, "news-database"
        ).addTypeConverter(Converters())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsDao(database: AppDatabase): NewsDao{
        return database.newsDao()
    }
}