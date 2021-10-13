package uz.fizmasoft.dyhxx.helper.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import uz.fizmasoft.dyhxx.R
import java.util.*

object LocaleHelper {

    fun getLanguage(activity: Activity): String {
        return getPersistedData(activity)
    }

    fun getNetworkLang(activity: Activity): String {
        val lang =
            getPersistedData(activity)
        return if (lang == "en") "uz"
        else "ru"
    }

    fun getLangStringRes(activity: Activity): Int {
        val lang =
            getPersistedData(activity)
        return if (lang == "uz") R.string.ozbek else R.string.russian
    }

    fun getCurrentLocale(activity: Activity) {
        setLocale(activity,getPersistedData(activity))
    }
    private fun getPersistedData(activity: Activity): String {
        return getPref(activity).getString(PREF_LANG_KEY,"uz")!!
    }

    fun setLocale(activity: Activity, language: String) {
        persist(activity, language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(activity, language)
        }
        updateResourcesLegacy(activity, language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
//        context.createConfigurationContext(configuration)
        context.resources.updateConfiguration(configuration,context.resources.displayMetrics)
    }

    private fun updateResourcesLegacy(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration = Configuration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun persist(activity: Activity, language: String) {
        val preferences = getPref(activity)
        preferences.edit().putString(PREF_LANG_KEY,language).apply()
    }
}