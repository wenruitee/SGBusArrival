package com.buffkatarina.busarrival.ui.fragments.bus_routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.data.entities.BusRoutesFiltered
import com.buffkatarina.busarrival.model.ActivityViewModel
import com.buffkatarina.busarrival.ui.fragments.bus_timings.BusTimingFragment

class BusRoutesFragment : Fragment(), BusRoutesAdapter.ToBusTimings {
    private val model: ActivityViewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bus_routes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getBusRoutes(view)
        super.onViewCreated(view, savedInstanceState)
    }


    private fun getBusRoutes(view: View) {
        /*Gets the bus routes from the view model */
        model.busServiceNo.observe(viewLifecycleOwner) {
            model.getBusRoutes(it)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = it
            model.busRoutesList.observe(viewLifecycleOwner) { busRoutesList ->
                setUpLayout(view, busRoutesList)
            }
        }
    }

    private fun setUpLayout(view: View, busRoutesList: List<List<BusRoutesFiltered>>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.bus_routes_recycler_view)
        val currentDirection = view.findViewById<TextView>(R.id.direction)
        val directionChange = view.findViewById<ImageView>(R.id.change_direction)
        val busRoutesAdapter = BusRoutesAdapter(this)
        val directionOne = resources.getString(R.string.direction_1)
        val directionTwo = resources.getString(R.string.direction_2)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = busRoutesAdapter
        busRoutesAdapter.setData(busRoutesList[0]) // index 0 for bus routes at direction 1
        currentDirection.text = directionOne  //Assume direction 1 on fragment open for now
        directionChange.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))

        //when there is no direction 2 for this bus service, bus routes for
        // direction 2 are stored at index 1
        if (busRoutesList[1].isEmpty()) {
            directionChange.visibility = View.GONE
        } else {
            //Changes the displayed list of direction when button is pressed
            directionChange.setOnClickListener {
                if (currentDirection.text == directionOne) {
                    busRoutesAdapter.setData(busRoutesList[1])
                    currentDirection.text = directionTwo
                } else {
                    busRoutesAdapter.setData(busRoutesList[0])
                    currentDirection.text = directionOne
                }
            }
        }
    }

    override fun toBusTimings(query: String) {
        /*Displays bus routes fragments on the screen
      * Implementation for ToBusRoutes interface in BusServicesSearchAdapter
      * */
        model.setBusStopCode(query)
        parentFragmentManager.popBackStack() //consumes the previous transaction
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentHolder, BusTimingFragment(), "BusTimingFragment")
            .commit()
    }

}