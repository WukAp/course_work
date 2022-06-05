package com.example.course_work.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.course_work.R
import com.example.course_work.models.Athlete

class Adapter(private val names: List<Athlete>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
    }

    //описания взяты с http://developer.alexanderklimov.ru/android/views/recyclerview-kot.php

    /*Нам нужно указать идентификатор макета для отдельного элемента списка,
    созданный нами ранее в файле recyclerview_item.xml.
    А также вернуть наш объект класса ViewHolder.
    Допустим, устройство может отобразить на экране 9 элементов списка.
    RecyclerView создаст 11-12 элементов (с запасом).
    Неважно, каким большим будет ваш список, но все данные
    будут размещаться в тех же 11 элементах, автоматически меняя содержимое при прокрутке.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.athlete_item, parent, false)
        return MyViewHolder(itemView)
    }

    /*В методе адаптера onBindViewHolder() связываем используемые текстовые метки
    с данными - в одном случае это значения из списка,
    во втором используется одна и та же строка.
    Параметр position отвечает за позицию в списке (индекс),
    по которой можно получить нужные данные.*/
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = names[position].name
        holder.smallTextView.text = names[position].group

    }

    /*Возвращает количество элементов списка. Как правило данные являются однотипными,
    например, список или массив строк.
    Адаптеру нужно знать, сколько элементов нужно предоставить компоненту,
    чтобы распределить ресурсы и подготовиться к отображению на экране.
    При работе с коллекциями или массивом мы можем просто вычислить
    его длину и передать это значение методу адаптера getItemCount().
    В простых случаях мы можем записать код в одну строчку.*/
    override fun getItemCount() = names.size
}