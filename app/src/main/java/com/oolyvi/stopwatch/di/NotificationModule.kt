package com.oolyvi.stopwatch.di

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import com.oolyvi.stopwatch.R
import com.oolyvi.stopwatch.service.ServiceHelper
import com.oolyvi.stopwatch.util.Constants.NOTIFICATION_CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@ExperimentalAnimationApi
@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @SuppressLint("PrivateResource")
    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.stopwatch))
            .setContentText(context.getString(R.string._00_00_00))
                .setSmallIcon(R.drawable.stopwatch)
            .setOngoing(true)
            .addAction(0, context.getString(R.string.stop), ServiceHelper.stopPendingIntent(context))
            .addAction(0, context.getString(R.string.reset), ServiceHelper.cancelPendingIntent(context))
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}