package com.example.sqlite_20_12_22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite_20_12_22.adapter.UsuariosAdapter
import com.example.sqlite_20_12_22.databinding.ActivityDosBinding
import com.example.sqlite_20_12_22.db.BaseDatos
import com.example.sqlite_20_12_22.models.User

class DosActivity : AppCompatActivity() {
    lateinit var binding: ActivityDosBinding
    lateinit var bd: BaseDatos
    lateinit var adapter: UsuariosAdapter
    var lista = mutableListOf<User>()
    var emailRe = ""
    var passwordRe = ""
    var perfilRe = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bd = BaseDatos(this)
        //Seteamos el recycler view
        setRecyclerView()
        //Seteamos listeners
        setListeners()
        //Recogemos el email y el perfil del usuario que ha iniciado sesion
        recogerDatos()

    }

    private fun recogerDatos() {
        val datos = intent.extras
        emailRe = datos?.getString("EMAIL").toString()
        binding.tvEmailRe.text = emailRe
    }

    private fun setListeners() {

    }

    private fun setRecyclerView() {
        //Obtenemos la lista de usuarios
        lista = bd.leer()
        //Creamos el adapter
        binding.recUsers.layoutManager = LinearLayoutManager(this)
        adapter = UsuariosAdapter(lista)
        binding.recUsers.adapter = adapter
    }
}