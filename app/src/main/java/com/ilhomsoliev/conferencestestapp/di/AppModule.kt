package com.ilhomsoliev.conferencestestapp.di

import android.content.Context
import androidx.room.Room
import com.ilhomsoliev.conferencestestapp.core.Constants
import com.ilhomsoliev.conferencestestapp.data.local.ConferenceDao
import com.ilhomsoliev.conferencestestapp.data.local.ConferenceDatabase
import com.ilhomsoliev.conferencestestapp.data.local.RemoteKeysDao
import com.ilhomsoliev.conferencestestapp.data.remote.ConferenceApi
import com.ilhomsoliev.conferencestestapp.data.repostitory.ConferenceRepositoryImpl
import com.ilhomsoliev.conferencestestapp.domain.repository.ConferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConferenceApi(): ConferenceApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
            .create(ConferenceApi::class.java)
    }

    @Singleton
    @Provides
    fun provideConferenceRepository(
        api: ConferenceApi,
        database: ConferenceDatabase
    ): ConferenceRepository = ConferenceRepositoryImpl(api, database)

    @Singleton
    @Provides
    fun provideConferenceDatabase(@ApplicationContext context: Context): ConferenceDatabase =
        Room
            .databaseBuilder(context, ConferenceDatabase::class.java, "conference_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideConferencesDao(database: ConferenceDatabase): ConferenceDao =
        database.getConferenceDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: ConferenceDatabase): RemoteKeysDao =
        database.getRemoteKeysDao()

}