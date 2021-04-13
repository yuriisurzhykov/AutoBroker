package com.yuriysurzhikov.autobroker.repository

interface ITask<Model> {
    suspend fun run() : Result<Model>
}