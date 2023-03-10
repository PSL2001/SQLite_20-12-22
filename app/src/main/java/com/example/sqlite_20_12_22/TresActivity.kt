package com.example.sqlite_20_12_22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqlite_20_12_22.databinding.ActivityTresBinding
import com.example.sqlite_20_12_22.db.BaseDatos
import com.example.sqlite_20_12_22.models.User
import com.example.sqlite_20_12_22.prefs.Prefs

class TresActivity : AppCompatActivity() {
    lateinit var binding: ActivityTresBinding
    lateinit var baseDatos: BaseDatos
    lateinit var prefs: Prefs
    var id = 0
    var email = ""
    var password = ""
    var perfil = 0
    var perfilLog = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseDatos = BaseDatos(this)
        prefs = Prefs(this)
        //Recogemos los datos del usuario que queremos editar
        recogerDatos()
        //Seteamos los listeners
        setListeners()
    }

    private fun setListeners() {
        binding.btnVolver4.setOnClickListener {
            finish()
        }
        binding.btnEnviar4.setOnClickListener { editarUsuario() }
    }

    private fun editarUsuario() {
        //Obtenemos los datos del formulario
        val email = binding.etEmail2.text.toString()
        val password = binding.etPass2.text.toString()
        //Perfil dependera si el switch esta activado o no
        val perfil = if (binding.sAdmin.isChecked) 1 else 0
        //Comprobamos que los campos no esten vacios
        if (email.isEmpty()) {
            binding.etEmail2.error = "El email no puede estar vacio"
            binding.etEmail2.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPass2.error = "La contraseña no puede estar vacia"
            binding.etPass2.requestFocus()
            return
        }
        //Comprobamos que el email no exista en otros usuarios salvo en el usuario que estamos editando
        if (baseDatos.existeUsuario(email, id)) {
            binding.etEmail2.error = "El email ya existe"
            binding.etEmail2.requestFocus()
            return
        }
        //Creamos el usuario
        val user = User(id, email, password, perfil)
        //Editamos el usuario
        val edit = baseDatos.editar(user)
        if (edit != -1) {
            finish()
        } else {
            mostrarMensaje("Error al editar el usuario")
        }
    }

    private fun mostrarMensaje(s: String) {
        //Creamos un toast
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun recogerDatos() {
        val datos = intent.extras
        id = datos?.getInt("ID")!!
        email = datos?.getString("EMAIL").toString()
        password = datos?.getString("PASSWORD").toString()
        perfil = datos?.getInt("PERFIL")!!
        binding.etEmail2.setText(email)
        binding.etPass2.setText(password)
        binding.sAdmin.isChecked = perfil == 1
        perfilLog = prefs.getPerfil()
        //Si el perfil es 1, es admin, por lo que hacemos el switch visible (preferencias)
        if (perfilLog == 1) {
            binding.sAdmin.visibility = View.VISIBLE
            binding.textView5.visibility = View.VISIBLE
        } else {
            binding.sAdmin.visibility = View.INVISIBLE
            binding.textView5.visibility = View.INVISIBLE
        }

    }
}