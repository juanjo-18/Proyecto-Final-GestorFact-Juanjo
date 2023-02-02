package com.example.proyectofinal

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Producto

class ProductosAdapter (val actividadMadre: Activity, val datos:ArrayList<Producto>) : RecyclerView.Adapter<ProductosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        return ProductosViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_catalogo,parent,false))
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto:Producto = datos.get(position)
        holder.nombre.text=producto.nombre
        holder.referencia.text=producto.referencia
        holder.precio.text=""+producto.precio
        holder.cantidad.text=""+producto.cantidad

        holder.botonBorrar.setOnClickListener {
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.botonEditar.setOnClickListener {
           //tengo que hacerlo
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}
