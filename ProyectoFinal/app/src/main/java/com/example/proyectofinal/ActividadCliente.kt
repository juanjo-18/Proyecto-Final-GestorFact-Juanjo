package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Cliente
import clases.ItemSpacingDecoration
import clases.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.catalogo.ProductosAdapter
import recyclers.clientes.ClientesAdapter

/**
 * Esta es la clase que representa la actividad cliente donde se muestran todos los clientes en un recycler view,
 * abajo a la deracha hay un boton para añadir un nuevo cliente.
 * @author Juanjo Medina
 */
class ActividadCliente : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase
    var listaCliente = arrayListOf<Cliente>()

    private lateinit var adaptador: ClientesAdapter

    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        setContentView(R.layout.layout_cliente)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db= Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        var valores = arrayListOf<Cliente>()
        val context = this

        //Aqui recogo todos los clientes de la base de datos y los muestro en el recycler view
        GlobalScope.launch {
           listaCliente= db.clienteDAO().getAll() as ArrayList<Cliente>
            if(listaCliente.size>0) {

                setupRecyclerView()

        val buscadorCliente: EditText =findViewById(R.id.buscadorCliente)

        buscadorCliente.addTextChangedListener(object: TextWatcher {
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

        //Boton que cambia la panatalla a añadir cliente
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
        val botonIrAFacturacion: ImageButton =findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeCliente)
        val botonIrACompra: ImageButton =findViewById<ImageButton>(R.id.botonIrAComprasDesdeCliente)
        val botonIrAInformes: ImageButton =findViewById<ImageButton>(R.id.botonIrAInformesDesdeCliente)



        //Boton que cambia la panatalla a la inicial
        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a catalogo
        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a venta
        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
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
        botonIrACompra.setOnClickListener{
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
    fun setupRecyclerView(){
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerCliente)
        recyclerView.layoutManager= LinearLayoutManager(this)
        adaptador = ClientesAdapter(this,listaCliente)
        recyclerView.adapter=adaptador

        //ajustamos el recycler a la pantalla
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        recyclerView.addItemDecoration(itemSpacingDecoration)
    }

    fun filtrar(texto: String){
        var listaFiltrada= arrayListOf<Cliente>()
        listaCliente.forEach{
            if(it.nombre?.toLowerCase()?.contains(texto.toLowerCase()) == true){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
}