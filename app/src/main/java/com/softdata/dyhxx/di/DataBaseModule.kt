package com.softdata.dyhxx.di

import android.content.Context
import androidx.room.Room
import com.softdata.dyhxx.helper.util.db.CarDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context):CarDataBase{
        return Room.databaseBuilder(context,CarDataBase::class.java,"car.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}