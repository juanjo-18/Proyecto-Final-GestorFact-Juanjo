package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Albaran
import clases.ItemSpacingDecoration
import clases.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import recyclers.albaranes.AlbaranesAdapter
import recyclers.catalogo.ProductosAdapter

class ActividadVenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_venta)

        val valores = arrayListOf<Albaran>()
        for (i in 3 downTo 1) {
            var albaran: Albaran = Albaran()
            valores.add(albaran)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.reciclerVenta)
        recyclerView.adapter= AlbaranesAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        recyclerView.addItemDecoration(itemSpacingDecoration)


        val botonIrAnadirVenta: FloatingActionButton =findViewById(R.id.botonAnadirVenta)
        botonIrAnadirVenta.setOnClickListener{
            val intent:Intent=Intent(
                this,ActividadAnadirVenta::class.java
            )
            this.startActivity(intent)
        }

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicioDesdeVenta)
        val botonIrACliente: ImageButton =findViewById<ImageButton>(R.id.botonIrAClientesDesdeVenta)
        val botonIrACatalogo: ImageButton =findViewById<ImageButton>(R.id.botonIrACatalogoDesdeVenta)
        val botonIrAFacturacion: ImageButton =findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeVenta)


        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        botonIrACliente.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

    }
}