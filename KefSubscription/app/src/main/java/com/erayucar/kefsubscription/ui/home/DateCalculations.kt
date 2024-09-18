package com.erayucar.kefabonelik.ui.home

import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit

fun calculateEndDate(billingDate: String, subType: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val billingDate = LocalDate.parse(billingDate, formatter)

    return if (subType == MONTHLY_SUBSCRIPTION) {
        val endDate = billingDate.plusMonths(1)
        endDate.format(formatter)
    } else {
        val endDate = billingDate.plusYears(1)
        endDate.format(formatter)
    }
}

fun calculateRemainingDays(endDate: String): Int {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val endLocalDate = LocalDate.parse(endDate, formatter)

    // Bitiş tarihine kadar kalan gün sayısını hesapla
    val daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), endLocalDate)

    return daysBetween.toInt()


}


fun isDateValid(startDate: String, subType: String): Boolean {
    val formatter = (DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val selectedDate = LocalDate.parse(startDate, formatter)
    val currentDate = LocalDate.now()
    val maxPastDate = selectedDate.minusMonths(1)

    val period = Period.between(maxPastDate, currentDate)

    return  if (subType == MONTHLY_SUBSCRIPTION) {
        (period.days >= 0 && (period.months == 0 || period.months == 1) && period.years == 0)
    }else{
        (period.days >= 0 || period.months < 12 || period.years == 1)
    }


    // Başlangıç tarihi mevcut tarihten önceyse false döndür
    // Başlangıç tarihi mevcut tarihten fazla bir tarihse false döndür
}

const val MONTHLY_SUBSCRIPTION = "Monthly"
const val YEARLY_SUBSCRIPTION = "Yearly"
