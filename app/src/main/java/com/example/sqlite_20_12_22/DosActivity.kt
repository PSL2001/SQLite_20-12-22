package com.example.sqlite_20_12_22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite_20_12_22.adapter.UsuariosAdapter
import com.example.sqlite_20_12_22.databinding.ActivityDosBinding
import com.example.sqlite_20_12_22.db.BaseDatos
import com.example.sqlite_20_12_22.models.User
import com.example.sqlite_20_12_22.prefs.Prefs

class DosActivity : AppCompatActivity() {
    lateinit var binding: ActivityDosBinding
    lateinit var bd: BaseDatos
    lateinit var adapter: UsuariosAdapter
    lateinit var prefs: Prefs
    var lista = mutableListOf<User>()
    var emailRe = ""
    var passwordRe = ""
    var perfilRe = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bd = BaseDatos(this)
        prefs = Prefs(this)
        //Seteamos el recycler view
        setRecyclerView()
        //Recogemos el email y el perfil del usuario que ha iniciado sesion
        recogerDatos()
    }

    private fun recogerDatos() {
        val datos = intent.extras
        emailRe = datos?.getString("EMAIL").toString()
        binding.tvEmailRe.text = emailRe
    }

    private fun setRecyclerView() {
        //Obtenemos la lista de usuarios
        lista = bd.leer()
        //Creamos el adapter
        binding.recUsers.layoutManager = LinearLayoutManager(this)
        adapter = UsuariosAdapter(lista) { onItemEdit(it) }
        binding.recUsers.adapter = adapter
    }

    private fun onItemEdit(it: User) {
        //Mandamos a la actividad tres el usuario que queremos editar
        val intent = Intent(this, TresActivity::class.java)
        intent.putExtra("ID", it.id)
        intent.putExtra("EMAIL", it.email)
        intent.putExtra("PASSWORD", it.password)
        intent.putExtra("PERFIL", it.perfil)
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        //Cuando volvamos de la actividad tres actualizamos el recycler view
        setRecyclerView()
    }

    //Funcion para cerrar sesion
    fun cerrarSesion() {
        prefs.clear()
        finish()
    }

    //Funcion para salir de la aplicacion
    fun salir() {
        finishAffinity()
    }

    //Funcion para el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Funcion para el item del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemCerrarSesion -> {
                cerrarSesion()
            }
            R.id.itemSalir -> {
                salir()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}