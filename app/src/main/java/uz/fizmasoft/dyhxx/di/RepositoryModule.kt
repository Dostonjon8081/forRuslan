package uz.fizmasoft.dyhxx.di

import android.content.Context
import androidx.room.Room
//import com.chuckerteam.chucker.api.Chucker
//import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import uz.fizmasoft.dyhxx.helper.db.CarDataBase
import uz.fizmasoft.dyhxx.helper.network.CarApiService
import uz.fizmasoft.dyhxx.helper.util.BASE_URL
import uz.fizmasoft.dyhxx.helper.util.PREF_TOKEN_KEY
import uz.fizmasoft.dyhxx.helper.util.getPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): CarDataBase =
        Room.databaseBuilder(context, CarDataBase::class.java, "car.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
//            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor {
                chain ->
                val original = chain.request()
                val requestBulder = original.newBuilder()
//                    .addHeader("authorization", "1638094110:479914aa8c9afbb1e27f7a716488ae85")
                    .addHeader("authorization", getPref(context).getString(PREF_TOKEN_KEY,"")!!)
//                    .addHeader("type", "base64")
                val request = requestBulder.build()
                chain.proceed(request)
            }
//            .addInterceptor(ChuckerInterceptor(context))
//            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(gsonConverterFactory)
        .build()



    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CarApiService =
        retrofit.create(CarApiService::class.java)
}