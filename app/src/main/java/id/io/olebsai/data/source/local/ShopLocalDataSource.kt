package id.io.olebsai.data.source.local

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import id.io.olebsai.di.SharedPrefModule
import id.io.olebsai.domain.model.shop.ShopDetail
import javax.inject.Inject

class ShopLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    private val prefEditor: SharedPreferences.Editor
) {
    private val moshi by lazy { Moshi.Builder().build() }

    fun saveShop(shopDetail: ShopDetail) {
        val adapter = moshi.adapter(ShopDetail::class.java)
        prefEditor.putString(SharedPrefModule.KEY_SHOP, adapter.toJson(shopDetail))
        prefEditor.commit()
    }

    fun getShop(): ShopDetail? {
        val adapter = moshi.adapter(ShopDetail::class.java)
        val json = pref.getString(SharedPrefModule.KEY_SHOP, null)
        if (json != null) return adapter.fromJson(json)
        return null
    }
}