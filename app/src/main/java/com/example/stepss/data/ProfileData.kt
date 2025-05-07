package com.example.stepss.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class ProfileData(
    val name: String?,
    val password: String?,
    val email: String?,
    val contact: String?,
    val location: String?,
    val imageUri: Uri?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(contact)
        parcel.writeString(location)
        parcel.writeParcelable(imageUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileData> {
        override fun createFromParcel(parcel: Parcel): ProfileData {
            return ProfileData(parcel)
        }

        override fun newArray(size: Int): Array<ProfileData?> {
            return arrayOfNulls(size)
        }
    }
}
