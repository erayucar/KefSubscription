package com.erayucar.kefabonelik.core.database.subscription

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscription_table")
data class SubscriptionEntity(
    @ColumnInfo("brand_id")
    val brandId: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("start_date")
    val billingDate: String,
    @ColumnInfo("end_date")
    val paymentDate: String,
    @ColumnInfo("remaining_date")
    val remainingDate: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("subscription_type")
    val subscriptionType: String,
    @ColumnInfo("currency")
    val currency: String

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}