package com.dinesh.kotlinstructure.common

import android.text.Editable
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

//used for gson
inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

//used for normal json iterator
operator fun <T> JSONArray.iterator(): Iterator<T> = (0 until length()).asSequence().map { get(it) as T }.iterator()

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)