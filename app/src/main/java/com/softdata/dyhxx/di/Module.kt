package com.softdata.dyhxx.di

import com.softdata.dyhxx.helper.util.db.dataRepository.CarRepositoryImpl
import com.softdata.dyhxx.helper.util.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.util.db.dataSource.CarDataSourceImpl
import com.softdata.dyhxx.helper.util.db.dataSource.ICarDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {


    @Singleton
    @Binds
    abstract fun bindCarDataSource(carDataSourceImpl: CarDataSourceImpl): ICarDataSource

    @Singleton
    @Binds
    abstract fun bindCarRepository(carRepositoryImpl: CarRepositoryImpl): ICarRepository

}