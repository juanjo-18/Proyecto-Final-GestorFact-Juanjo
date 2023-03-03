package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Cliente
import clases.ItemSpacingDecoration
import clases.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        //ajustamos el recycler a la pantalla
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        recyclerView.addItemDecoration(itemSpacingDecoration)



        val botonIrAnadirCliente: FloatingActionButton =findViewById(R.id.botonAnadirCliente)
        botonIrAnadirCliente.setOnClickListener{
            val intent:Intent=Intent(
                this,ActividadAnadirCliente::class.java
            )
            this.startActivity(intent)
        }

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicioDesdeCliente)
        val botonIrACatalogo: ImageButton =findViewById<ImageButton>(R.id.botonIrACatalogoDesdeCliente)
        val botonIrAVenta: ImageButton =findViewById<ImageButton>(R.id.botonIrAVentaDedeCliente)

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
        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
    }
}