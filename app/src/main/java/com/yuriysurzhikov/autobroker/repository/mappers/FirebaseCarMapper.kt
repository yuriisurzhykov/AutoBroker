package com.yuriysurzhikov.autobroker.repository.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import com.yuriysurzhikov.autobroker.util.Const
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class FirebaseCarMapper
@Inject
constructor(
    private val modelMapper: IEntityMapper<CarModel, DocumentSnapshot>,
    private val firebaseSyncRepository: FirebaseSyncRepository
) : IEntityMapper<Car, DocumentSnapshot> {
    override fun mapFromEntity(entity: Car): DocumentSnapshot {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: DocumentSnapshot): Car {
        return Car(domain.id, domain[Const.CarConst.CAR_NAME_FIELD].toString(), "", emptyList())
    }

    override fun mapListFromEntity(entities: List<Car>): List<DocumentSnapshot> {
       return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<DocumentSnapshot>): List<Car> {
        return domains.map { mapToEntity(it) }
    }
}