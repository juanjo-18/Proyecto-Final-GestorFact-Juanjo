package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Producto

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
        recyclerView.adapter= ProductosAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicio)
        val botonIrACatalogo: ImageButton =findViewById<ImageButton>(R.id.botonIrACatalogo)

        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)

        }

        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }
    }

}