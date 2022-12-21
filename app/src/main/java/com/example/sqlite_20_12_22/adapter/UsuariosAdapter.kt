package com.example.sqlite_20_12_22.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_20_12_22.R
import com.example.sqlite_20_12_22.models.User

class UsuariosAdapter(private val lista: MutableList<User>, val onItemEdit: (User) -> Unit): RecyclerView.Adapter<UsuariosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_users, parent, false)
        return UsuariosViewHolder(v)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        holder.render(lista[position], onItemEdit)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}