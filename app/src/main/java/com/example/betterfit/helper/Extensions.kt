package com.example.betterfit.helper

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
fun String.parseAndFormatAsDate(): String {
    val date = LocalDate.parse(this.subSequence(0, 10), DateTimeFormatter.ISO_DATE)
    return date.format(DateTimeFormatter.ofPattern("MMMM dd"))
}