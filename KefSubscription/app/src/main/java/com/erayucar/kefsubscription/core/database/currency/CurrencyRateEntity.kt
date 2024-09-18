package com.erayucar.kefabonelik.core.database.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rate_table")
data class CurrencyRateEntity(
    @ColumnInfo(name = "currency_id")
    val name : String,
    @ColumnInfo(name = "rate")
    val rate : Double,
    @ColumnInfo(name = "date")
    val date : String
)

{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}