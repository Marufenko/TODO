package com.todo.todo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Goal(val key: String, var value: String) : Parcelable