package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Albaran
import clases.Factura
import clases.ItemSpacingDecoration
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding
import com.example.proyectofinal.databinding.LayoutFacturacionBinding
import recyclers.albaranes.AlbaranesAdapter
import recyclers.facturacion.FacturacionAdapter

class ActividadFacturacion : AppCompatActivity() {
    private lateinit var binding: LayoutFacturacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LayoutFacturacionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val valores = arrayListOf<Factura>()
        for (i in 3 downTo 1) {
            var factura: Factura = Factura()
            valores.add(factura)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.reciclerFactura)
        recyclerView.adapter= FacturacionAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        recyclerView.addItemDecoration(itemSpacingDecoration)

        binding.botonIrACatalogoDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }

        binding.botonIrAClientesDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        binding.botonIrAVentasDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        binding.botonIrAInicioDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        binding.botonAnadirFactura.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadAnadirFactura::class.java
            )
            this.startActivity(intent)
        }

    }
}