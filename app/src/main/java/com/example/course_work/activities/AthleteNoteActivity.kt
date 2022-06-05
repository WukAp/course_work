package com.example.course_work.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.course_work.R
import com.example.course_work.models.Athlete
import com.example.course_work.utils.AthleteSerializationUtils
import java.io.FileOutputStream
import java.io.IOException

class AthleteNoteActivity : AppCompatActivity() {

    val FILE_NAME = "AthleteList"
    var athleteList = mutableListOf<Athlete>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athlete_note)
        val name: EditText = findViewById(R.id.note_input_name)
        val group: EditText = findViewById(R.id.note_input_group)
        val note: EditText = findViewById(R.id.note_input_note)

        athleteList =
            intent.getStringExtra("athleteList")?.let {
                AthleteSerializationUtils.deserializationAthleteList(it)
                    .toMutableList()
            }!!

        val position = intent.getStringExtra("position")!!.toInt()
        setText(athleteList[position])

        val savingButton: Button = findViewById(R.id.saving_button)
        savingButton.setOnClickListener {
            athleteList[position] =
                Athlete(name.text.toString(), group.text.toString(), note.text.toString())
            saveText()


        }
    }

    override fun onBackPressed() {
        setContentView(R.id.recyclerView)
    }

    private fun setText(athlete: Athlete) {
        val name: EditText = findViewById(R.id.note_input_name)
        val group: EditText = findViewById(R.id.note_input_group)
        val note: EditText = findViewById(R.id.note_input_note)

        name.setText(athlete.name)
        group.setText(athlete.group)
        note.setText(athlete.note)
    }


    // сохранение файла
    private fun saveText() {
        var fos: FileOutputStream? = null
        try {
            val text = AthleteSerializationUtils.serialization(athleteList)
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            fos.write(text.toByteArray())
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fos?.close()
            } catch (ex: IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}