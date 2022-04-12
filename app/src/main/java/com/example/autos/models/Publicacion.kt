package com.example.autos.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import java.util.*

class Publicacion(val id: String, val marca: String, val modelo: String, val precio: Int, val detalle: String, val urlImg: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {}

    constructor() : this("","","",0,"","")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(marca)
        parcel.writeString(modelo)
        parcel.writeInt(precio)
        parcel.writeString(detalle)
        parcel.writeString(urlImg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Publicacion> {
        override fun createFromParcel(parcel: Parcel): Publicacion {
            return Publicacion(parcel)
        }

        override fun newArray(size: Int): Array<Publicacion?> {
            return arrayOfNulls(size)
        }
    }

}