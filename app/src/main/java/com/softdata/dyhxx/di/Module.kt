package com.softdata.dyhxx.di

import com.softdata.dyhxx.helper.db.dataRepository.CarRepositoryImpl
import com.softdata.dyhxx.helper.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.db.dataSource.CarDataSourceImpl
import com.softdata.dyhxx.helper.db.dataSource.ICarDataSource
import com.softdata.dyhxx.helper.network.dataSource.CarApiDataSourceImpl
import com.softdata.dyhxx.helper.network.dataSource.ICarApiDataSource
import com.softdata.dyhxx.helper.network.repository.CarApiRepositoryImpl
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
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

    @Singleton
    @Binds
    abstract fun bindCarApiDataSource(carApiDataSourceImpl: CarApiDataSourceImpl): ICarApiDataSource

    @Singleton
    @Binds
    abstract fun bindCarApiRepository(carApiRepositoryImpl: CarApiRepositoryImpl): ICarApiRepository

}