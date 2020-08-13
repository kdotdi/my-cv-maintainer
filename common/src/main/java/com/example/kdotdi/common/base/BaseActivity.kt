package com.example.kdotdi.common.base

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.kdotdi.presenter.base.BaseView
import com.example.kdotdi.common.utils.widget.snackbar.SnackbarSetup
import com.example.kdotdi.common.utils.widget.snackbar.SnackbarAccess
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseView, SnackbarAccess {

    @Inject
    lateinit var snackbarApi: SnackbarSetup

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isEmpty()) {
            return
        }
        if (supportFragmentManager.fragments[0] is NavHostFragment) {
            supportFragmentManager.fragments[0]
                .childFragmentManager
                .fragments
                .forEach {
                    if (it.isAdded && it is BackAware) {
                        it.onBackPressed()
                    }
                }
        }
    }

    fun onFragmentStart(fragment: BaseFragment) {
        requestedOrientation =
            when {
                (fragment.isAdded && fragment is OrientationOwner) -> fragment.provideOrientation()
                else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
    }
}
