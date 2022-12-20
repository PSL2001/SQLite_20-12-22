package com.example.sqlite_20_12_22.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_20_12_22.databinding.LayoutUsersBinding
import com.example.sqlite_20_12_22.models.User

class UsuariosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = LayoutUsersBinding.bind(v)

    fun render(user: User) {
        binding.tvEmail.text = user.email
        binding.tvPassword.text = user.password
        binding.tvPerfil.text = user.perfil.toString()
    }

}
