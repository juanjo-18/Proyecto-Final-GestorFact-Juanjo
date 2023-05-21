package com.example.proyectofinal

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.ItemSpacingDecoration
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirClienteBinding
import com.example.proyectofinal.databinding.LayoutAnadirProductoBinding
import com.example.proyectofinal.databinding.LayoutCatalogoBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.*
import recyclers.catalogo.ProductosAdapter

/**Esta es la clase que representa la actividad Catalogo aqui se mostraran todos los objetos Producto en un recycler
 * view,  abajo a la derecha hay un boton para añadir un nuevo producto.
 *
 * @author Juanjo Medina
 */
class ActividadCatalogo : AppCompatActivity(), SearchView.OnQueryTextListener {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutCatalogoBinding


    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()


        var valores = arrayListOf<Producto>()
        val context = this
        //Aqui traigo todos los productos de la base de datos y los muestro en un recyclerview
        GlobalScope.launch {
            valores = db.productoDAO().getAll() as ArrayList<Producto>
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerCatalogo)
            recyclerView.adapter = ProductosAdapter(context, valores)
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

        //Este boton lo que hace es cambiar de pantalla a añadir producto
        binding.botonAnadirProducto.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadAnadirProducto::class.java
            )
            this.startActivity(intent)
        }


        val botonIrAInicio: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAInicioDesdeCatalogo)
        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAClientesDesdeCatalogo)
        val botonIrAVenta: ImageButton = findViewById<ImageButton>(R.id.botonIrAVentaDesdeCatalogo)

        //Boton que cambia la panatalla a la inicial
        botonIrAInicio.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a ir a cliente
        botonIrACliente.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a venta
        botonIrAVenta.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadVenta::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a facturacion
        val botonIrAFacturacion:ImageButton=findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeCatalogo)
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

        val botonIrACompras:ImageButton=findViewById<ImageButton>(R.id.botonIrAComprasDesdeCatalogo)
        //Boton que cambia la panatalla a compra
        botonIrACompras.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCompra::class.java
            )
            this.startActivity(intent)
        }


    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }
}