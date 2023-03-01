package clases

import android.os.Parcel
import android.os.Parcelable

class Albaran() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Albaran> {
        override fun createFromParcel(parcel: Parcel): Albaran {
            return Albaran(parcel)
        }

        override fun newArray(size: Int): Array<Albaran?> {
            return arrayOfNulls(size)
        }
    }
}