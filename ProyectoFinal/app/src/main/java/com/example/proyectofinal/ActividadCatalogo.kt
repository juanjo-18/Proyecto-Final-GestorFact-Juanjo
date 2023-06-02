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
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.w3c.dom.Text
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

    var listaProducto = arrayListOf<Producto>()

    private lateinit var adaptador: ProductosAdapter
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
           listaProducto= db.productoDAO().getAll() as ArrayList<Producto>
            if(listaProducto.size>0) {
                setupRecyclerView()



        binding.buscadorProductos.addTextChangedListener(object:TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    filtrar(s.toString())
                }

            })
            }
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

       //Boton que cambia la panatalla a compra
        val botonIrAInformes:ImageButton=findViewById<ImageButton>(R.id.botonIraInformesDesdeCatalogo)
        botonIrAInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInformes::class.java
            )
            this.startActivity(intent)
        }


    }
    fun filtrar(texto: String){
        var listaFiltrada= arrayListOf<Producto>()
        listaProducto.forEach{
            if(it.nombre?.toLowerCase()?.contains(texto.toLowerCase()) == true){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }

    fun setupRecyclerView(){
        binding.reciclerCatalogo.layoutManager=LinearLayoutManager(this)
        adaptador = ProductosAdapter(this,listaProducto)
        binding.reciclerCatalogo.adapter=adaptador
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        binding.reciclerCatalogo.addItemDecoration(itemSpacingDecoration)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }
}