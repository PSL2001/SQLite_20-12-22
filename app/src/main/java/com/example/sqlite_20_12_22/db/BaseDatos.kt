package com.example.sqlite_20_12_22.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite_20_12_22.models.User

class BaseDatos(c: Context): SQLiteOpenHelper(c, DATABASE, null, VERSION) {
    companion object {
        val DATABASE = "Users"
        val TABLE = "Users"
        val VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL UNIQUE, password TEXT NOT NULL, perfil INTEGER NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(p0)
    }

    //Metodos para insertar, actualizar, eliminar y consultar (CRUD)
    //Insertar
    fun insertar(user: User): Long {
        //Abrir la base de datos en modo escritura
        val db = this.writableDatabase
        //Crear un objeto de tipo ContentValues para almacenar los datos a insertar
        val valores = ContentValues().apply {
            put("email", user.email)
            put("password", user.password)
            put("perfil", user.perfil)
        }
        //Insertar los datos en la tabla
        val res = db.insert(TABLE, null, valores)
        //Cerrar la base de datos
        db.close()
        //Retornar el resultado
        //Si res es -1, hubo un error
        return res
    }

    //Leer
    fun leer(): ArrayList<User> {
        //Abrir la base de datos en modo lectura
        val db = this.readableDatabase
        //Crear un ArrayList para almacenar los datos leidos
        val lista = ArrayList<User>()
        //Crear un objeto de tipo Cursor para almacenar los datos leidos
        val cursor = db.rawQuery("SELECT * FROM $TABLE", null)
        //Recorrer el cursor y almacenar los datos en el ArrayList
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val email = cursor.getString(1)
                val password = cursor.getString(2)
                val perfil = cursor.getInt(3)
                val user = User(id, email, password, perfil)
                lista.add(user)
            } while (cursor.moveToNext())
            cursor.close()
        }
        //Cerrar la base de datos
        db.close()
        //Retornar el ArrayList
        return lista
    }

    fun existeUsuario(email: String, password: String = ""): Boolean {
        //Abrimos la base de datos en modo lectura
        val db = this.readableDatabase
        //Creamos una variable que contará el número de usuarios que coinciden con el email y password del usuario que pasamos por parámetro
        var count = 0
        //Creamos un objeto de tipo Cursor para almacenar los datos leidos
        //Si el password es vacío, solo comprobamos que exista el email
        //Si el password no es vacío, comprobamos que exista el email y que el password coincida
        val cursor = if (password == "") {
            db.rawQuery("SELECT * FROM $TABLE WHERE email = '$email'", null)
        } else {
            db.rawQuery("SELECT * FROM $TABLE WHERE email = '$email' AND password = '$password'", null)
        }
        //Contamos el numero de filas que devuelve el cursor
        count = cursor.count
        //Cerramos el cursor
        cursor.close()
        //Cerramos la base de datos
        db.close()
        //Si count es 0, no existe el usuario
        //Si count es 1, existe el usuario
        return count == 1

    }

    fun getPerfil(email: String, password: String): Int {
        //Abrimos la base de datos en modo lectura
        val db = this.readableDatabase
        //Creamos una variable que almacenará el perfil del usuario
        var perfil = 0
        //Creamos un objeto de tipo Cursor para almacenar los datos leidos
        val cursor = db.rawQuery("SELECT perfil FROM $TABLE WHERE email = '$email' AND password = '$password'", null)
        //Recorremos el cursor y almacenamos el perfil del usuario en la variable perfil
        if (cursor.moveToFirst()) {
            do {
                perfil = cursor.getInt(0)
            } while (cursor.moveToNext())
            cursor.close()
        }
        //Cerramos la base de datos
        db.close()
        //Retornamos el perfil del usuario
        return perfil
    }
}