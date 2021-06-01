package com.lukmannudin.moviecatalogue.data.moviessource.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "genre")
@Parcelize
data class Genre(

    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name = "name")
    var name: String? = null
) : Parcelable