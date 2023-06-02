package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Compra
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
import recyclers.compras.ComprasAdapter
import recyclers.facturacion.FacturacionAdapter

class ActividadCompra : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase
    private lateinit var binding: LayoutCompraBinding

    var listaCompra= arrayListOf<Compra>()
    private lateinit var adaptador: ComprasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding= LayoutCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var valores = arrayListOf<Compra>()
        val context = this

        /**
         * Cargo todas las facturas en el recycler view y las muestra
         *
         */
        GlobalScope.launch {
            listaCompra= db.compraDAO().getAll() as ArrayList<Compra>
            if(listaCompra.size>0){
                setupRecyclerView()


        binding.buscadorCompra.addTextChangedListener(object: TextWatcher {
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

    fun setupRecyclerView(){
        binding.reciclerCompra.layoutManager= LinearLayoutManager(this)
        //(binding.reciclerFactura.layoutManager as LinearLayoutManager).reverseLayout = true
        adaptador = ComprasAdapter(this,listaCompra)
        binding.reciclerCompra.adapter=adaptador
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
        binding.reciclerCompra.addItemDecoration(itemSpacingDecoration)
    }
    fun filtrar(texto: String){
        var listaFiltrada= arrayListOf<Compra>()
        listaCompra.forEach{
            if(it.titulo?.toLowerCase()?.contains(texto.toLowerCase()) == true){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
}