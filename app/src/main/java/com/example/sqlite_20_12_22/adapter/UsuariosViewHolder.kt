package com.example.sqlite_20_12_22.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_20_12_22.databinding.LayoutUsersBinding
import com.example.sqlite_20_12_22.models.User
import com.example.sqlite_20_12_22.prefs.Prefs

class UsuariosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    lateinit var prefs: Prefs
    private val binding = LayoutUsersBinding.bind(v)

    fun render(user: User, onItemEdit: (User) -> Unit) {
        prefs = Prefs(itemView.context)
        binding.tvEmail.text = user.email
        binding.tvPassword.text = user.password
        binding.tvPerfil.text = user.perfil.toString()
        binding.btnEditar.setOnClickListener {
            onItemEdit(user)
        }
        //Si el perfil del usuario es 0 o el email es distinto al que se ha logueado, el boton de editar estara deshabilitado, si no, habilitado
        var perfil = prefs.getPerfil()
        var email = prefs.getEmail()
        binding.btnEditar.isEnabled = (perfil == 1 || email == user.email)
    }

}
