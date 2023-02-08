package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Cliente
import clases.Producto
import recyclers.catalogo.ProductosAdapter
import recyclers.clientes.ClientesAdapter

class ActividadCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_cliente)

        val valores = arrayListOf<Cliente>()
        for (i in 100 downTo 1) {
            var cliente: Cliente = Cliente()
            valores.add(cliente)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.reciclerCliente)
        recyclerView.adapter= ClientesAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicioDesdeCliente)
        val botonIrACatalogo: ImageButton =findViewById<ImageButton>(R.id.botonIrACatalogoDesdeCliente)

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