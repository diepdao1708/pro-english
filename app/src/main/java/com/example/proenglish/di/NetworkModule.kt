package com.example.proenglish.di

import com.example.proenglish.BuildConfig
import com.example.proenglish.data.core.Const
import com.example.proenglish.data.models.SavedAccount
import com.example.proenglish.utils.AppEvent
import com.example.proenglish.utils.GsonUTCDateAdapter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAccountData(): SavedAccount {
        return SavedAccount()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        savedAccount: SavedAccount,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authorization = savedAccount.accessToken?.let { "${Const.JWT_PREFIX}$it" }
                val request = chain.request().let {
                    if (authorization == null) it
                    else it.newBuilder()
                        .addHeader(Const.AUTHORIZATION_KEY, authorization).build()
                }

                chain.proceed(request).apply {
                    if (code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                        EventBus.getDefault().post(AppEvent.LogOut)
                    }
                }
            }
            .dispatcher(Dispatcher().apply { maxRequests = 1 })
            .apply {
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(interceptor)
                }
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter())
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}