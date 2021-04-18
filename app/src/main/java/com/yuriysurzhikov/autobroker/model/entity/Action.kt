package com.yuriysurzhikov.autobroker.model.entity

import android.os.Parcel
import android.os.Parcelable

data class Action(
    var label: String?,
    var reference: String?,
    var badge: String?,
    var iconRes: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeString(reference)
        parcel.writeString(badge)
        parcel.writeInt(iconRes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Action> {
        override fun createFromParcel(parcel: Parcel): Action {
            return Action(parcel)
        }

        override fun newArray(size: Int): Array<Action?> {
            return arrayOfNulls(size)
        }
    }
}