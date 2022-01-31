package com.andreadematteis.assignments.restcountriesapplication.room.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "currencies_table",
    indices = [ Index(value = ["identifier"], unique = true) ]
)
@Parcelize
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "currency_id")
    val id: Long,
    @ColumnInfo(name = "identifier")
    val identifier: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "symbol")
    val symbol: String
): Parcelable