package com.dinesh.kotlinstructure.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LocaleUtils {

    fun transform(langCode: String): Locale {

        return if (langCode.length > 2) {
            Locale(extractLocaleLanguage(langCode), extractLocaleCountry(langCode))
        } else {
            Locale(extractLocaleLanguage(langCode))
        }
    }

    fun extractLocaleCountry(langCode: String): String {
        return langCode.substring(3, 5)
    }

    fun extractLocaleLanguage(langCode: String): String {
        return langCode.substring(0, 2)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun updateLocaleConfiguration(activity: Context, newLocale: Locale): Configuration {
        val resources = activity.resources
        Locale.setDefault(newLocale)
        val config = Configuration(resources.configuration)
        config.setLocale(newLocale)
        resources.updateConfiguration(config, resources.displayMetrics)
        return config
    }
}