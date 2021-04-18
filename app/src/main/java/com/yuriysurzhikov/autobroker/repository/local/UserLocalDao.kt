package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.model.local.UserWithLocation
import com.yuriysurzhikov.autobroker.repository.ICrudRepository

@Dao
abstract class UserLocalDao : ICrudRepository<UserRoom> {

    @Query("SELECT * FROM usr WHERE userId=:id")
    abstract fun getUserBy(id: String?): UserRoom?

    @Query("SELECT * FROM usr WHERE email=:email")
    abstract fun getUserByEmail(email: String?): UserRoom?

    @Query("SELECT * FROM usr LIMIT 1")
    abstract fun getFirstUser(): UserRoom?

    @Transaction
    @Query("SELECT * FROM usrLocation LIMIT 1")
    abstract fun getUserWithLocation(): UserWithLocation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createLocation(locationRoom: UserLocationRoom)

    @Query("SELECT * FROM usrLocation WHERE region = :region AND city = :city")
    abstract fun getLocationByRegionAndCity(region: String, city: String): UserLocationRoom

    @Query("SELECT * FROM usrLocation WHERE locationId = :id")
    abstract fun getLocationById(id: Long): UserLocationRoom

}