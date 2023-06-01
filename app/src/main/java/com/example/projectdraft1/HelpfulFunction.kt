package com.example.projectdraft1

import org.json.JSONObject

fun getDoseFromJson(medication: Medication): ArrayList<DosePerformance>{
    val doseList = ArrayList<DosePerformance>()
    val obj = JSONObject(medication.dose)
    val doseArray = obj.getJSONArray("dose")

    for (i in 0 until doseArray.length()) {
        val dose = doseArray.getJSONObject(i)

        doseList.add(
            DosePerformance(
                dose.getString("time"),
                dose.getInt("amount"),
                dose.getString("stringDose")
            )
        )
    }

    doseList.sort()

    return doseList
}