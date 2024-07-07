package com.example.vasugram.DataSource.DataModels

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList


data class Story(
    val userId: String? = null,
    val userAvatarUrl: String? = null,
    val timestamp: Long = 0,
    val imageUrls: ArrayList<String>? = null,
    val stablility: Long = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.createStringArrayList(),
        parcel.readLong()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userAvatarUrl)
        parcel.writeLong(timestamp)
        parcel.writeStringList(imageUrls)
        parcel.writeLong(stablility)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Story> {
        override fun createFromParcel(parcel: Parcel): Story {
            return Story(parcel)
        }

        override fun newArray(size: Int): Array<Story?> {
            return arrayOfNulls(size)
        }
    }
}
