package com.johncodeos.asktorate

import android.app.Application
import com.github.stkent.amplify.IApp
import com.github.stkent.amplify.IDevice
import com.github.stkent.amplify.IEnvironment
import com.github.stkent.amplify.feedback.BaseEmailFeedbackCollector
import com.github.stkent.amplify.feedback.GooglePlayStoreFeedbackCollector
import com.github.stkent.amplify.tracking.Amplify
import com.github.stkent.amplify.tracking.PromptInteractionEvent
import com.github.stkent.amplify.tracking.rules.GooglePlayStoreRule
import com.github.stkent.amplify.tracking.rules.MaximumCountRule

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        Amplify.initSharedInstance(this)
            .setPositiveFeedbackCollectors(GooglePlayStoreFeedbackCollector())
            .setCriticalFeedbackCollectors(object : BaseEmailFeedbackCollector("feedback@domain.com") {
                override fun getSubjectLine(iApp: IApp, iEnvironment: IEnvironment, iDevice: IDevice): String =
                    "Feedback for the Social App"

                override fun getBody(iApp: IApp, iEnvironment: IEnvironment, iDevice: IDevice): String =
                    ""
            })
            .addEnvironmentBasedRule(GooglePlayStoreRule()) // Prompt never shown if Google Play Store not installed.
            .setInstallTimeCooldownDays(5)   // Prompt not shown within 5 days of initial install.
            .setLastUpdateTimeCooldownDays(3) // Prompt not shown within 3 days of most recent update.
            .setLastCrashTimeCooldownDays(7) // Prompt not shown within one week of most recent crash.
            .addTotalEventCountRule(
                PromptInteractionEvent.USER_GAVE_POSITIVE_FEEDBACK,
                MaximumCountRule(1)
            ) // Never ask the user for feedback again if they already responded positively.
            .addTotalEventCountRule(
                PromptInteractionEvent.USER_GAVE_CRITICAL_FEEDBACK,
                MaximumCountRule(1)
            ) // Never ask the user for feedback again if they already responded critically.
            .addTotalEventCountRule(
                PromptInteractionEvent.USER_DECLINED_FEEDBACK,
                MaximumCountRule(1)
            ).setAlwaysShow(BuildConfig.DEBUG)
    }
}