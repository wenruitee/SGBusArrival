package com.buffkatarina.busarrival.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusTimings
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription
import com.buffkatarina.busarrival.model.ActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    private val favouriteTimings = MutableLiveData<MutableList<BusTimings>>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val composeView: androidx.compose.ui.platform.ComposeView =
            view.findViewById(R.id.compose_view)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.app_name)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val databaseBuildState by viewModel.databaseState
                val dialogState by viewModel.dialogState
                loadFavouritesAndTimings(databaseBuildState)
                HomeCompose(
                    databaseBuildState = databaseBuildState,
                    dialogState = dialogState,
                    favouriteTimings = favouriteTimings,
                    viewModel = viewModel
                )
            }
        }
        return view
    }


    private fun parseBusTimings(result: List<FavouriteBusServicesWithDescription>) {
        /*Adds bus timings for favourite bus services to a favourite timings list */
        val mutableList = mutableListOf<BusTimings>()
        viewLifecycleOwner.lifecycleScope.launch {
            for (favourite in result) {
                viewModel.getBusTimingsByServiceNo(favourite.busStopCode, favourite.serviceNo)
                    ?.let { mutableList.add(it) }
            }
            favouriteTimings.value = mutableList

        }
    }

    private fun loadFavouritesAndTimings(databaseBuildState: Boolean) {
        if (databaseBuildState) {
            //Get all the favourite bus services records from the database
            viewModel.getAllFavouriteBusServices().observe(viewLifecycleOwner) { result ->
                viewLifecycleOwner.lifecycleScope.launch {
                    while (true) {
                        //get new bus timings every 1 minute
                        parseBusTimings(result)
                        delay(60000)
                    }
                }
            }
        }
    }
}