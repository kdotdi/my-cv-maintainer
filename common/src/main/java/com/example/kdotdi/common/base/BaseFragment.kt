package com.example.kdotdi.common.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.kdotdi.presenter.base.BaseView

abstract class BaseFragment : Fragment(), BaseView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            view.findNavController()
        } catch (e: IllegalStateException) {
            Navigation.setViewNavController(view, findNavController())
        }
    }

    override fun onStart() {
        if(activity is BaseActivity) {
            val baseActivity = activity as BaseActivity
            baseActivity.onFragmentStart(this)
        }
        super.onStart()
    }
}
