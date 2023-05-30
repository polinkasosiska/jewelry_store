package com.sysoliatina.jewelrystore.data

import okhttp3.Interceptor
import okhttp3.Response
import com.sysoliatina.jewelrystore.common.AccountSession

class OAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val token = AccountSession.instance.token
        if (!token.isNullOrEmpty()) {
            val finalToken = "Bearer $token"
            request = request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        }
        return chain.proceed(request)
    }
}