package com.sysoliatina.jewelrystore.sharedPreferences

interface PrefsUtils {
    fun getEmail(): String
    fun setEmail(string: String)

    fun getPassword(): String
    fun setPassword(string: String)

    fun clearData()

}