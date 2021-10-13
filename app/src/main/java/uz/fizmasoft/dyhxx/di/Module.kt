package uz.fizmasoft.dyhxx.di

import uz.fizmasoft.dyhxx.helper.db.dataRepository.CarRepositoryImpl
import uz.fizmasoft.dyhxx.helper.db.dataRepository.ICarRepository
import uz.fizmasoft.dyhxx.helper.db.dataSource.CarDataSourceImpl
import uz.fizmasoft.dyhxx.helper.db.dataSource.ICarDataSource
import uz.fizmasoft.dyhxx.helper.network.dataSource.CarApiDataSourceImpl
import uz.fizmasoft.dyhxx.helper.network.dataSource.ICarApiDataSource
import uz.fizmasoft.dyhxx.helper.network.repository.CarApiRepositoryImpl
import uz.fizmasoft.dyhxx.helper.network.repository.ICarApiRepository
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