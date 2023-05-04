package com.example.projectdraft1

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditorActivityTest{

    //Получение прав для создания экземпляров класса экрана "Создание лекарства"
    @JvmField
    @Rule
    val activityRule = ActivityTestRule<EditorActivity>(
        EditorActivity::class.java
    )

    //Тест на постепенное появление форм экрана "Создание лекарства"
    @Test
    fun testCardVisible(){
        onView(withId(R.id.cardStart))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btAddEditName))
            .perform(click())
        onView(withId(R.id.cardTime))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btAddEditTime))
            .perform(click())
        onView(withId(R.id.cardTime))
            .check(matches(isDisplayed()))
    }

    //Тест для проверки заполнения поля ввода названия лекарства экрана "Создание лекарства"
    @Test
    fun testFieldNameMedication(){
        onView(withId(R.id.edEdit))
            .perform(typeText("Medication 1"))
        onView(withId(R.id.edEdit))
            .check(matches(withText("Medication 1")))
    }
}
