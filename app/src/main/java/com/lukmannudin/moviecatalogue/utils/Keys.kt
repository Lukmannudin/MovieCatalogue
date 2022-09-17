package com.lukmannudin.moviecatalogue.utils

import android.util.Base64

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    private external fun getApiKeyNative(): String
    private external fun getBaseUrlNative(): String

    fun getApiKey() = getDecodedString(getApiKeyNative())
    fun getBaseUrl() = getDecodedString(getBaseUrlNative())

    private fun getDecodedString(key: String): String {
        return String(Base64.decode(key, Base64.DEFAULT), Charsets.UTF_8)
    }
}