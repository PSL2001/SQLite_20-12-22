package com.example.sqlite_20_12_22.prefs

import android.content.Context

class Prefs(c: Context) {
    val storage = c.getSharedPreferences("Datos", 0)

    //Funciones para guardar y leer email
    fun saveEmail(email: String) {
        storage.edit().putString("email", email).apply()
    }

    fun getEmail(): String {
        return storage.getString("email", "")!!
    }

    //Funciones para guardar y leer password
    fun savePassword(password: String) {
        storage.edit().putString("password", password).apply()
    }

    fun getPassword(): String {
        return storage.getString("password", "")!!
    }

    //Funciones para guardar y leer perfil
    fun savePerfil(perfil: Int) {
        storage.edit().putInt("perfil", perfil).apply()
    }

    fun getPerfil(): Int {
        return storage.getInt("perfil", 0)
    }

    //Funcion para borrar los datos
    fun clear() {
        storage.edit().clear().apply()
    }
}