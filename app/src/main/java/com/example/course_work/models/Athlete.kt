package com.example.course_work.models

/*var date = LocalDate.parse("2018-12-12")
var date = LocalDate.of(2018, 12, 31)
var period = Period.between(date1, date2)
https://ru.minecraftfullmod.com/2026-working-with-dates-in-kotlin*/

import kotlinx.serialization.Serializable

data class Contacts(var number: String, var Parent: String)


@Serializable
data class Athlete(
    var name: String?,
    var group: String? = null,
    var note: String? = null,
    //var year: LocalDate? = null, - проблемы с сериализацией
    var weight: Int? = null,
    //var contacts: List<Contacts>? = null
    var contact: String? = null
)