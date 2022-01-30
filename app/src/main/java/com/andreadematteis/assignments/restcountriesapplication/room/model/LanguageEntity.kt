package com.andreadematteis.assignments.restcountriesapplication.room.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(
    tableName = "languages_table",
    indices = [Index(value = ["identifier"], unique = true)]
)
@Parcelize
class LanguageEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "identifier")
    val identifier: String,
    @ColumnInfo(name = "name")
    val name: String
) : Parcelable