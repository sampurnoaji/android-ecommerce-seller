package id.io.android.olebsai.presentation.user.landing

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.user.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        lifecycleScope.launch {
            delay(1_000)
            mainViewModel.setFirstLaunchAppTrue()
            startActivity(Intent(this@LandingActivity, LoginActivity::class.java))
            finish()
        }
    }
}