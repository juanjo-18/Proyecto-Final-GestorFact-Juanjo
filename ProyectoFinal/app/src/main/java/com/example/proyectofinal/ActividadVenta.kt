package com.example.proyectofinal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.albaranes.AlbaranesAdapter
import recyclers.catalogo.ProductosAdapter

/**
 * Esta es la clase que representa la actividad venta donde estaran todos los albaranes en un recycler view a
 * bajo a la dereche abra un boton para crear un nuevo albaran y te cambiara de pantalla.
 * @author Juanjo Medina
 */
class ActividadVenta : AppCompatActivity() {

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
        setContentView(R.layout.layout_venta)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var valores = arrayListOf<Albaran>()
        val context = this

        //Aqui recogo todos los albaranes de la base de datos y lo muestro en un recycler view
        GlobalScope.launch {
            valores = db.albaranDAO().getAll() as ArrayList<Albaran>
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerVenta)
            recyclerView.adapter = AlbaranesAdapter( context, valores)
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

        //Boton que cambia la panatalla para añadir una nueva venta
        val botonIrAnadirVenta: FloatingActionButton = findViewById(R.id.botonAnadirVenta)
        botonIrAnadirVenta.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadAnadirVenta::class.java
            )
            this.startActivity(intent)
        }

        val botonIrAInicio: ImageButton = findViewById<ImageButton>(R.id.botonIrAInicioDesdeVenta)
        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAClientesDesdeVenta)
        val botonIrACatalogo: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACatalogoDesdeVenta)
        val botonIrAFacturacion: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeVenta)
        val botonIrACompras: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAComprasDesdeVentas)
        val botonIrAInformes: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAInformesDesdeVentas)

        //Boton que cambia la panatalla a la inicial
        botonIrAInicio.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla cliente
        botonIrACliente.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla catalogo
        botonIrACatalogo.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a compra
        botonIrACompras.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCompra::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a informes
        botonIrAInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInformes::class.java
            )
            this.startActivity(intent)
        }



    }

}