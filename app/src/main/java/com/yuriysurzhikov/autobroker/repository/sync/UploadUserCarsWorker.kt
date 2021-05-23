package com.yuriysurzhikov.autobroker.repository.sync

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yuriysurzhikov.autobroker.util.Const
import java.util.*

class UploadUserCarsWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val uploadData = extractCarData(inputData)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun extractCarData(workerParams: Data): Map<String, Any> {
        return mapOf(
            Const.CarConst.CAR_NAME_FIELD to UUID.randomUUID().toString()
        )
    }

    private fun uploadFiles() {

    }
}