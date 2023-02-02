package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Producto
import recyclers.catalogo.ProductosAdapter

class ActividadCatalogo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogo)

        val valores = arrayListOf<Producto>()
        for (i in 100 downTo 1) {
            var producto: Producto = Producto()
            valores.add(producto)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.reciclerCatalogo)
        recyclerView.adapter=ProductosAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.HORIZONTAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

    }
}