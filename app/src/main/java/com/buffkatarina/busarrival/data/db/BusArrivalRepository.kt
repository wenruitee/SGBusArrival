package com.buffkatarina.busarrival.data.db

import com.buffkatarina.busarrival.data.entities.BuildDate
import com.buffkatarina.busarrival.data.entities.BusRoutes
import com.buffkatarina.busarrival.data.entities.BusRoutesFiltered
import com.buffkatarina.busarrival.data.entities.BusServices
import com.buffkatarina.busarrival.data.entities.BusStops
import com.buffkatarina.busarrival.data.entities.FavouriteBusServices
import com.buffkatarina.busarrival.data.entities.FavouriteBusServicesWithDescription
import kotlinx.coroutines.flow.Flow

class BusArrivalRepository(private val busArrivalDao: BusArrivalDao) {
    /*Abstracts BusArrivalDatabase(), BusArrivalDao()*/

    fun searchBusStops(searchQuery: String?): Flow<List<BusStops.BusStopData>> {
        return busArrivalDao.searchBusStops(searchQuery, searchQuery)
    }

    fun insertBusStops(busStops: BusStops.BusStopData) {
        busArrivalDao.insertBusStops(busStops)
    }

    fun searchBusServices(searchQuery: String?): Flow<List<String>> {
        return busArrivalDao.searchBusServices(searchQuery)
    }

    fun insertBusServices(busServices: BusServices.BusServicesData) {
        busArrivalDao.insertBusServices(busServices)
    }

    fun insertBusRoutes(busRoutes: BusRoutes.BusRoutesData) {
        busArrivalDao.insertBusRoutes(busRoutes)
    }

    fun searchBusRoutes(searchQuery: String?, direction: String): List<BusRoutesFiltered> {
        return busArrivalDao.getBusRoutes(searchQuery, direction)
    }

    fun insertFavouriteBusService(favouriteBusServices: FavouriteBusServices) {
        return busArrivalDao.insertFavouriteBusService(favouriteBusServices)
    }

    fun removeFavouriteBusService(busStopCode: String, serviceNo: String) {
        busArrivalDao.removeFavouriteBusService(busStopCode, serviceNo)
    }

    fun getFavouriteBusService(busStopCode: String): List<String> {
        return busArrivalDao.getFavouriteBusService(busStopCode)
    }

    fun getAllFavouriteBusServices(): Flow<List<FavouriteBusServicesWithDescription>> {
        return busArrivalDao.getAllFavouriteBusServices()
    }

    fun getBuildDate(): BuildDate? {
        return busArrivalDao.getBuildDate()
    }

    fun insertBuildDate(date: String) {
        return busArrivalDao.insertBuildDate(BuildDate(1, date))
    }

    fun getAllBusStops(): List<BusStops.BusStopData> {
        return busArrivalDao.getAllBusStops()
    }

}