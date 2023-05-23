package com.example.proyectofinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.*
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding
import com.example.proyectofinal.databinding.LayoutFacturacionBinding
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.albaranes.AlbaranesAdapter
import recyclers.facturacion.FacturacionAdapter

/**
 * Esta es la clase que representa la actividad facturacion donde estan todos los objetos factura y se muestran
 * en un recycler view, tambien hay un boton para añadir una nueva factura.
 * @author Juanjo Medina
 */
class ActividadFacturacion : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutFacturacionBinding
    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase

    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding= LayoutFacturacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var valores = arrayListOf<Factura>()
        val context = this

        /**
         * Cargo todas las facturas en el recycler view y las muestra
         *
         */
        GlobalScope.launch {
            valores = db.facturaDAO().getAll() as ArrayList<Factura>
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerFactura)
            recyclerView.adapter = FacturacionAdapter(context, valores)
            val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
            staggeredManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.layoutManager = staggeredManager

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
            recyclerView.addItemDecoration(itemSpacingDecoration)
        }

        //Boton que cambia la panatalla catalogo
        binding.botonIrACatalogoDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla cliente
        binding.botonIrAClientesDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla ventas
        binding.botonIrAVentasDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla inicio
        binding.botonIrAInicioDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla a compra
        binding.botonIrAComprasDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCompra::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a informes
        binding.botonIrAInformesDesdeFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInformes::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a añadir factura
        binding.botonAnadirFactura.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadAnadirFactura::class.java
            )
            this.startActivity(intent)
        }

    }
}