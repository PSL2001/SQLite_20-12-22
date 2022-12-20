package com.example.sqlite_20_12_22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.sqlite_20_12_22.databinding.ActivityMainBinding
import com.example.sqlite_20_12_22.db.BaseDatos
import com.example.sqlite_20_12_22.models.User

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: BaseDatos
    var email = ""
    var password = ""
    var perfil = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = BaseDatos(this)
        //Creamos una funcion que comprueba si el usuario admin existe y si no existe lo crea
        comprobarAdmin()
        //Seteamos listeners
        setListeners()
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener { login() }
        binding.btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        //Comprobamos que los campos no esten vacios
        if (email.isEmpty()) {
            binding.etEmail.setError("El email no puede estar vacio")
            binding.etEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("La contraseña no puede estar vacia")
            binding.etPassword.requestFocus()
            return
        }
        //Comprobamos que el email no exista
        if (db.existeUsuario(email)) {
            binding.etEmail.setError("El email ya existe")
            binding.etEmail.requestFocus()
            return
        }
        //Creamos el usuario
        val user = User(0, email, password , 0)
        //Insertamos el usuario en la base de datos
        db.insertar(user)
        //Una vez registrado lo mandamos a la actividad dos
        val intent = Intent(this, DosActivity::class.java)
        intent.putExtra("EMAIL", email)
        intent.putExtra("PASSWORD", password)
        intent.putExtra("PERFIL", perfil)
        startActivity(intent)
    }

    private fun login() {
        //Obtenermos los datos del formulario
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        //Comprobamos que los campos no esten vacios
        if (email.isEmpty()) {
            binding.etEmail.setError("El campo email no puede estar vacio")
            binding.etEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("El campo password no puede estar vacio")
            binding.etPassword.requestFocus()
            return
        }
        //Comprobamos que el usuario exista
        if (!db.existeUsuario(email, password)) {
            mostrarError("El usuario no existe")
        } else {
            //Obtenemos el perfil del usuario
            perfil = db.getPerfil(email, password)
            //Mandamos los datos a la siguiente actividad
            val intent = Intent(this, DosActivity::class.java)
            intent.putExtra("EMAIL", email)
            intent.putExtra("PASSWORD", password)
            intent.putExtra("PERFIL", perfil)
            startActivity(intent)
        }
    }

    private fun mostrarError(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun comprobarAdmin() {
        //Creamos un objeto usuario con los siguientes datos: admin, admin@gmail.com, secret0, perfil 1 (admin)
        val admin = User(0, "admin@gmail.com", "secret0", 1)
        //Comprobamos si el usuario admin existe
        if (!db.existeUsuario(admin.email, admin.password)) {
            //Si no existe, lo creamos
            db.insertar(admin)
        }
    }
}