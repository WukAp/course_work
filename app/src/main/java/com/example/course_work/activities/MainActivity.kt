package com.example.course_work.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.course_work.R
import com.example.course_work.adapters.Adapter
import com.example.course_work.adapters.RecyclerItemClickListener
import com.example.course_work.models.Athlete
import com.example.course_work.utils.AthleteSerializationUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val FILE_NAME = "AthleteList"

    var athleteList = mutableListOf<Athlete>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        athleteList = openText()
        updateContent(recyclerView)

        //makeToast(AthleteSerializationUtils.serialization(athleteList))

        //сделать только на текст(??) + добавить выпадающую кнопку с информацией
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        goToAthleteNoteActivity(position)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        //TODO("information")
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("Подтверждение")
                            setMessage("Вы уверены, что хотите удалить спорсмена?")

                            setPositiveButton("Удалить") { _, _ ->
                                athleteList.removeAt(position)
                                updateContent(recyclerView)
                            }

                            setNegativeButton("Отмена") { _, _ ->
                                // if user press no, then return the activity
                                Toast.makeText(
                                    this@MainActivity, "Thank you",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            setCancelable(true)
                        }.create().show()

                    }
                })
        )

        val addButton: FloatingActionButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            athleteList.add(Athlete(null))
            goToAthleteNoteActivity(athleteList.size - 1)
        }

    }

    fun goToAthleteNoteActivity(position: Int) {
        val intent = Intent(this, AthleteNoteActivity::class.java)

        // указываем первым параметром ключ, а второе значение
        // по ключу мы будем получать значение с Intent
        intent.putExtra("position", position.toString())
        intent.putExtra("athleteList", AthleteSerializationUtils.serialization(athleteList))

        // запуск SecondActivity
        startActivity(intent)
    }

    private fun updateContent(recyclerView: RecyclerView) {
        saveText()
        recyclerView.adapter = Adapter(athleteList)
    }

    fun updateAthlete(athlete: Athlete, position: Int) {
        athleteList[position] = athlete
        saveText()
    }

    private fun makeToast(text: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    // сохранение файла
    private fun saveText() {
        var fos: FileOutputStream? = null
        try {
            val text = AthleteSerializationUtils.serialization(athleteList)
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            fos.write(text.toByteArray())
            //Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show()
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

    // открытие файла
    private fun openText(): MutableList<Athlete> {
        var fin: FileInputStream? = null
        try {
            fin = openFileInput(FILE_NAME)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            val text = String(bytes)
            return AthleteSerializationUtils.deserializationAthleteList(text).toMutableList()
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fin?.close()
            } catch (ex: IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        makeToast("Файл не найден")
        return mutableListOf()
    }
}