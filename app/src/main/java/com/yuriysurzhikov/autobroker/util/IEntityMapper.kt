package com.yuriysurzhikov.autobroker.util

import androidx.annotation.WorkerThread

interface IEntityMapper<Entity, Domain> {
    @WorkerThread
    fun mapFromEntity(entity: Entity): Domain
    @WorkerThread
    fun mapToEntity(domain: Domain): Entity
    @WorkerThread
    fun mapListFromEntity(entities: List<Entity>): List<Domain>
    @WorkerThread
    fun mapListToEntity(domains: List<Domain>): List<Entity>
}