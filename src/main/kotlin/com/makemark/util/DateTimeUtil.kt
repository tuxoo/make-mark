package com.makemark.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateTimeUtil {

    fun getYear(now: Instant): Int = LocalDate.ofInstant(now, ZoneId.systemDefault()).year

    fun getMonth(now: Instant): Int = LocalDate.ofInstant(now, ZoneId.systemDefault()).monthValue - 1

    fun getDay(now: Instant): Int = LocalDate.ofInstant(now, ZoneId.systemDefault()).dayOfMonth
}