package com.sysoliatina.jewelrystore.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sysoliatina.jewelrystore.common.Constants.SHARED_PREFERENCES_NAME
import com.sysoliatina.jewelrystore.data.*
import com.sysoliatina.jewelrystore.data.client.ClientDataSource
import com.sysoliatina.jewelrystore.data.client.ClientRepository
import com.sysoliatina.jewelrystore.data.client.ClientService
import com.sysoliatina.jewelrystore.data.common.CommonDataSource
import com.sysoliatina.jewelrystore.data.common.CommonRepository
import com.sysoliatina.jewelrystore.data.common.CommonService
import com.sysoliatina.jewelrystore.data.moderator.ModeratorDataSource
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository
import com.sysoliatina.jewelrystore.data.moderator.ModeratorService
import com.sysoliatina.jewelrystore.sharedPreferences.PrefsUtils
import com.sysoliatina.jewelrystore.sharedPreferences.PrefsUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() : String = "http://192.168.1.8/"

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun getHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(OAuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String, httpBuilder: OkHttpClient.Builder) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(httpBuilder.build())
        .build()

    @Provides
    @Singleton
    fun provideCommonService(retrofit : Retrofit) : CommonService = retrofit.create(CommonService::class.java)

    @Provides
    @Singleton
    fun provideCommonDataSource(commonService : CommonService) =
        CommonDataSource(commonService)

    @Singleton
    @Provides
    fun provideCommonRepository(commonDataSource: CommonDataSource) =
        CommonRepository(commonDataSource)

    @Provides
    @Singleton
    fun provideModeratorService(retrofit : Retrofit) : ModeratorService = retrofit.create(ModeratorService::class.java)

    @Provides
    @Singleton
    fun provideModeratorDataSource(moderatorService: ModeratorService) =
        ModeratorDataSource(moderatorService)

    @Singleton
    @Provides
    fun provideModeratorRepository(moderatorDataSource: ModeratorDataSource) =
        ModeratorRepository(moderatorDataSource)

    @Provides
    @Singleton
    fun provideClientService(retrofit : Retrofit) : ClientService = retrofit.create(ClientService::class.java)

    @Provides
    @Singleton
    fun provideClientDataSource(clientService: ClientService) =
        ClientDataSource(clientService)

    @Singleton
    @Provides
    fun provideClientRepository(clientDataSource: ClientDataSource) =
        ClientRepository(clientDataSource)

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun providePrefsUtils(
        sharedPreferences: SharedPreferences
    ) = PrefsUtilsImpl(sharedPreferences) as PrefsUtils
}