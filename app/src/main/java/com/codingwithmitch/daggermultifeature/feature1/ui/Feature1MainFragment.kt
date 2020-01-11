package com.codingwithmitch.daggermultifeature.feature1.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.codingwithmitch.daggermultifeature.app.ui.MainNavController

import com.codingwithmitch.daggermultifeature.R
import com.codingwithmitch.daggermultifeature.app.BaseApplication
import com.codingwithmitch.daggermultifeature.app.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_feature1_main.*
import javax.inject.Inject

class Feature1MainFragment : Fragment(R.layout.fragment_feature1_main) {

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    val viewModel: Feature1ViewModel by viewModels {
        viewModelFactory
    }

    lateinit var mainNavController: MainNavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_go_next.setOnClickListener {
            findNavController().navigate(R.id.action_feature1MainFragment_to_feature1NextFragment)
        }

        subscribeObservers()
        initUI()
    }

    private fun subscribeObservers(){
        viewModel.feature1MainString.observe(viewLifecycleOwner, Observer { mainString ->
            fragment_name.text = mainString
        })
    }

    private fun initUI(){
        mainNavController.setDrawerItemChecked(R.id.nav_feature1)
        viewModel.retrieveMainString()
    }

    override fun onAttach(context: Context) {
        ((activity?.application) as BaseApplication)
            .getAppComponent()
            .feature1Component()
            .create()
            .inject(this)
        super.onAttach(context)
        try{
            mainNavController = context as MainNavController
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement MainNavController" )
        }
    }
}