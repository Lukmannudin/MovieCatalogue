#include <jni.h>
#include <iostream>

extern "C" {

    JNIEXPORT jstring JNICALL
    Java_com_lukmannudin_moviecatalogue_utils_Keys_getApiKeyNative(JNIEnv *env, jobject instance) {

        std::string apiKey = "NzFhMGFhNzhiODk4MjU5OGJjMDQzZjk2YWMwYjM5Mzc=";

        return env->NewStringUTF(apiKey.c_str());
    }

    JNIEXPORT jstring JNICALL
    Java_com_lukmannudin_moviecatalogue_utils_Keys_getBaseUrlNative(JNIEnv *env, jobject instance) {

        std::string baseUrl = "aHR0cHM6Ly9hcGkudGhlbW92aWVkYi5vcmcvMy8=";

        return env->NewStringUTF(baseUrl.c_str());
    }
}