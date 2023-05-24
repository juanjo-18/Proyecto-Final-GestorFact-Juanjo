package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Factura
import clases.ItemSpacingDecoration
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirCompraBinding
import com.example.proyectofinal.databinding.LayoutAnadirFacturaBinding
import com.example.proyectofinal.databinding.LayoutCompraBinding
import com.example.proyectofinal.databinding.LayoutFacturacionBinding
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.catalogo.ProductosAdapter
import recyclers.facturacion.FacturacionAdapter

class ActividadCompra : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase
    private lateinit var binding: LayoutCompraBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding= LayoutCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Boton que cambia la panatalla catalogo
        binding.botonIrACatalogoDesdeCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla cliente
        binding.botonIrAClientesDesdeCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla ventas
        binding.botonIrAVentasDesdeCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla inicio
        binding.botonIrAInicioDesdeCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla a facturacion
        binding.botonIrAFacturacionDesdeCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a informes
        binding.botonIrAInformesDesdeCompras.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInformes::class.java
            )
            this.startActivity(intent)
        }



        //Boton que cambia la panatalla a añadir compra
        binding.botonAnadirCompra.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadAnadirCompra::class.java
            )
            this.startActivity(intent)
        }
    }
}