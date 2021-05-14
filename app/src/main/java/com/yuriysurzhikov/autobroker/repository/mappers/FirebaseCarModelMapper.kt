package com.yuriysurzhikov.autobroker.repository.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class FirebaseCarModelMapper @Inject constructor() : IEntityMapper<CarModel, DocumentSnapshot> {
    override fun mapFromEntity(entity: CarModel): DocumentSnapshot {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: DocumentSnapshot): CarModel {
        TODO("Not yet implemented")
    }

    override fun mapListFromEntity(entities: List<CarModel>): List<DocumentSnapshot> {
        TODO("Not yet implemented")
    }

    override fun mapListToEntity(domains: List<DocumentSnapshot>): List<CarModel> {
        TODO("Not yet implemented")
    }
}