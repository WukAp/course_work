package com.example.course_work.utils

import com.example.course_work.models.Athlete
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AthleteSerializationUtils {

    private val json = Json

    fun serialization(athleteList: List<Athlete>) =
        json.encodeToString(athleteList)

    fun deserializationAthleteList(stringToDecoder: String) =
        json.decodeFromString<List<Athlete>>(stringToDecoder)

    fun serialization(athleteList: Athlete) =
        json.encodeToString(athleteList)

    fun deserializationAthlete(stringToDecoder: String) =
        json.decodeFromString<Athlete>(stringToDecoder)

}