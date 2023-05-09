package com.example.projectdraft1

data class SpinnerMedication(val image: Int, val name: String)

object Medications{
    private val images = intArrayOf(
        R.drawable.pill_1,
        R.drawable.pill_2,
        R.drawable.pill_3,
        R.drawable.pill_4,
        R.drawable.pill_5,
        R.drawable.pill_6,
        R.drawable.pill_7,
    )

    private val medications = arrayOf(
        "Таблетка",
        "Капсула",
        "Укол",
        "Капли",
        "Мазь/крем/гель",
        "Спрей",
        "Сироп"
    )

    var list: ArrayList<SpinnerMedication>? = null
        get() {

            if(field != null)
                return field

            field = ArrayList()
            for(i in images.indices){
                val imageId = images[i]
                val medicationName = medications[i]

                val medication = SpinnerMedication(imageId, medicationName)
                field!!.add(medication)
            }

            return field
        }
}
