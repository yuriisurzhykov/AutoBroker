package com.yuriysurzhikov.autobroker.repository.works

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncUsersCarsWork
constructor(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        return Result.success()
    }
}