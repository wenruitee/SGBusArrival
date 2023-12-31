package com.buffkatarina.busarrival.ui.fragments.home.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buffkatarina.busarrival.R
import com.buffkatarina.busarrival.arrivalTime
import com.buffkatarina.busarrival.data.entities.BusTimings


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritesRow(
    serviceNo: String,
    description: String,
    timings: BusTimings.BusData,
    modifier: Modifier,
) {

    val nextBus = arrivalTime(timings.nextBus.estimatedArrival)
    val nextBus2 = arrivalTime(timings.nextBus2.estimatedArrival)
    val nextBus3 = arrivalTime(timings.nextBus3.estimatedArrival)
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
    ) {
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = serviceNo, fontSize = 20.sp)
                Text(
                    text = description,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            delayMillis = 0,
                            initialDelayMillis = 0
                        ),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.next),
                    fontSize = 20.sp
                )
                Text(
                    text = nextBus,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.second),
                    fontSize = 20.sp
                )
                Text(
                    text = nextBus2,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.third),
                    fontSize = 20.sp
                )
                Text(
                    text = nextBus3,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

        }
    }
}




