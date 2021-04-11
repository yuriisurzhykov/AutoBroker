package com.yuriysurzhikov.autobroker.util

interface IEntityMapper<Entity, Domain> {
    fun mapFromEntity(entity: Entity): Domain
    fun mapToEntity(domain: Domain): Entity
    fun mapListFromEntity(entities: List<Entity>): List<Domain>
    fun mapListToEntity(domains: List<Domain>): List<Entity>
}