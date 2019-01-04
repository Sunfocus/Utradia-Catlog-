package com.utradia.catalogueappv2.api

import android.content.Context
import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.utils.PreferenceManager
import com.utradia.catalogueappv2.utils.ProgressBarHandler
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*

@Module
class NetModule(internal var context: Context) {


    internal var mPrefs: PreferenceManager

    //get retrofit instance
    val restService: RestService
        @Singleton
        @Provides
        get() {

            val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(unsafeOkHttpClient)
                    .build()

            return retrofit.create(RestService::class.java)
        }


    private// Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.interceptors().add(httpLoggingInterceptor)
                builder.readTimeout(30, TimeUnit.SECONDS)
                builder.connectTimeout(30, TimeUnit.SECONDS)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }


    init {
        mPrefs = PreferenceManager(context)

    }

   /* private fun getHeadersForApis(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (mPrefs.isUserLoggedIn()) {

                request = request.newBuilder()
                        .addHeader("authorization", "Bearer " + String.valueOf(mPrefs.getAccessToken()))
                        .build()
            }
            chain.proceed(request)
        }
    }*/
}
