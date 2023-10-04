package com.oolyvi.stopwatch

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.stopwatch.service.ServiceHelper
import com.oolyvi.stopwatch.service.StopwatchService
import com.oolyvi.stopwatch.service.StopwatchState
import com.oolyvi.stopwatch.util.Constants.ACTION_SERVICE_CANCEL
import com.oolyvi.stopwatch.util.Constants.ACTION_SERVICE_START
import com.oolyvi.stopwatch.util.Constants.ACTION_SERVICE_STOP
import com.oolyvi.stopwatch.util.addAnimation

@ExperimentalAnimationApi
@Composable
fun MainScreen(stopwatchService: StopwatchService) {

    val context = LocalContext.current
    val hours by stopwatchService.hours
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds
    val currentState by stopwatchService.currentState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedContent(
                targetState = hours,
                transitionSpec = { addAnimation() },
                label = ""
            ) {
                Text(
                    text = "$hours :",
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            AnimatedContent(
                targetState = minutes,
                transitionSpec = { addAnimation() },
                label = ""
            ) {
                Text(
                    text = "$minutes :",
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            AnimatedContent(
                targetState = "$seconds :",
                transitionSpec = { addAnimation() },
                label = ""
            ) {
                Text(
                    text = seconds,
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action =
                        if (currentState == StopwatchState.Started) ACTION_SERVICE_STOP
                        else ACTION_SERVICE_START
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (currentState == StopwatchState.Idle) MaterialTheme.colorScheme.onPrimaryContainer
                    else if (currentState == StopwatchState.Stopped) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(
                    text =
                    if (currentState == StopwatchState.Started) stringResource(id = R.string.stop)
                    else if ((currentState == StopwatchState.Stopped)) stringResource(R.string.resume)
                    else stringResource(R.string.start),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Button(
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context, action = ACTION_SERVICE_CANCEL
                    )
                },
                enabled = seconds != stringResource(R.string._00) && currentState != StopwatchState.Started,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onErrorContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(0.2f)
                )
            ) {
                Text(
                    text = stringResource(R.string.reset),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}