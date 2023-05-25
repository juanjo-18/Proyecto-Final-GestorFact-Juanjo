package com.example.proyectofinal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Albaran
import clases.Albaran_Producto
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarVentaBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recyclers.anadirProductosVenta.LineaVentaAdapter
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

/**
 * Esta es la clase que representa la actividad para editar una venta aqui podremos modifcar el albaran tanto
 * el nombre de cliente, titulo y los productos añadidos una vez realizado los cambios se actualizara a la base de datos
 *
 *
 * @author Juanjo Medina
 */
class ActividadEditarVenta : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutEditarVentaBinding

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
        binding = LayoutEditarVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var contieneTexto = false
        var cantidadNegativa = false
        var campoVacio = false
        var precioLleno = false
        var cantidadLlena = false
        var checkBoxVacio = true
        var nombreCliente = ""
        var clienteIncorrecto = false

        var clientes = ArrayList<Cliente>()
        var nombres = arrayListOf("Añadir nombre")

        //Recogo todos los clientes de la base de datos para poder mostrarlos despues en un spinner
        CoroutineScope(Dispatchers.IO).launch {
            launch(Dispatchers.IO) {
                clientes = db.clienteDAO().getAll() as ArrayList<Cliente>
                for (cliente in clientes) {
                    nombres.add(cliente.nombre.toString())
                }
            }
        }


        //Creo un spinner de los clientes que he recogido previamente
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
        val spinner = findViewById<Spinner>(R.id.spinnerNombreClienteEditar)
        spinner.adapter = adapter


        //Si algun elemento del spinner es seleccionado añade el nombre al nombreCliente ademas comprueba si esta marcado
        //añadir nombre para poner clienteIncorrecto en true para que no deje guardar asi.
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val nombreSeleccionado = nombres[position]
                if (nombreSeleccionado == "Añadir nombre") {
                    clienteIncorrecto = true
                }
                nombreCliente = nombreSeleccionado
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ningún elemento
            }
        }
        var tipoAlbaran=""
        //Si el checkBox presupuesto a sido marcado desmarco pedido y albaran.
        binding.checkBoxPresupuestoEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoAlbaran="Presupuesto"
                binding.checkBoxPedidoEditar.isChecked = false
                binding.checkBoxAlbaranEditar.isChecked = false
            } else if (!binding.checkBoxPedidoEditar.isChecked && !binding.checkBoxAlbaranEditar.isChecked) {
                checkBoxVacio = true
            }
        }

        //Si el checkBox albaran a sido marcado desmarco pedido y albaran.
        binding.checkBoxAlbaranEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoAlbaran="Albaran"
                binding.checkBoxPedidoEditar.isChecked = false
                binding.checkBoxPresupuestoEditar.isChecked = false
            } else if (!binding.checkBoxPedidoEditar.isChecked && !binding.checkBoxPresupuestoEditar.isChecked) {
                checkBoxVacio = true
            }
        }

        //Si el checkBox pedido a sido marcado desmarco presupuesto y albaran.
        binding.checkBoxPedidoEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoAlbaran="Pedido"
                binding.checkBoxPresupuestoEditar.isChecked = false
                binding.checkBoxAlbaranEditar.isChecked = false
            } else if (!binding.checkBoxPresupuestoEditar.isChecked && !binding.checkBoxAlbaranEditar.isChecked) {
                checkBoxVacio = true
            }
        }

        /**
         * Aqui si en el campo cantidad ha habido algun cambio compruebo si el precio tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoCantidadEditarLineaVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaVenta.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaVenta.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaVenta.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaVenta.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaVenta.text.toString().toFloat()
                    binding.campoTotalLineaEditarVentaLinea.text = (cantidad * precio).toString()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                cantidadLlena = s.toString().isNotEmpty()

            }
        })

        /**
         * Aqui si en el campo cantidad ha habido algun cambio compruebo si el precio tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoPrecioEditarLineaVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaVenta.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaVenta.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaVenta.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaVenta.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaVenta.text.toString().toFloat()
                    binding.campoTotalLineaEditarVentaLinea.text = (cantidad * precio).toString()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                precioLleno = s.toString().isNotEmpty()

            }
        })


        var titulo = intent.getStringExtra("titulo")
        var albaran_producto = arrayListOf<Albaran_Producto>()
        var productos = arrayListOf<Producto>()
        val recyclerView: RecyclerView =
            findViewById<RecyclerView>(R.id.recyclerLineasProductosEditar)

        //Compruebo si el titulo esta relleno
        if (titulo != null) {
            var valores = arrayListOf<Albaran>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //hago un select a la base de datos del albaran con el titulo para cargar los datos al layout de editar
                    valores =
                        db.albaranDAO().buscarAlbaranPorTitulo(titulo!!) as ArrayList<Albaran>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {


                            binding.campoTituloEditarVenta.setText(valor.titulo)
                            binding.textoFechaDesdeVentaEditar.setText((LocalDate.now()).toString())
                            binding.textoNumeroTotalBaseEditarVenta.setText(
                                (valor.precioTotal / 1.21).toString()
                            )
                            binding.textoNumeroIvaEditarVenta.setText(
                                (valor.precioTotal - (valor.precioTotal / 1.21)).toString()
                            )
                            binding.textoNumeroTotalEditarVenta.setText(

                                valor.precioTotal.toString()

                            )
                        }
                    }
                }
            }


            val contexto = this

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //Aqui traigo todos los productos que tiene ese titulo y los cargo al layout
                    albaran_producto = db.albaran_ProductoDAO()
                        .buscarAlbaranProductoPorTitulo(titulo!!) as ArrayList<Albaran_Producto>
                    withContext(Dispatchers.Main) {
                        var nombreCliente2=""
                        var tipoAlbaran=""
                        for (albaranes in albaran_producto) {
                            nombreCliente2= albaranes.nombreCliente.toString()
                            tipoAlbaran=albaranes.tipoAlbaran.toString()
                            titulo = albaranes.tituloAlbaran.toString()
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
                        }
                        if(tipoAlbaran.contains("Presupuesto")){
                            binding.checkBoxPresupuestoEditar.isChecked=true
                        }
                        if(tipoAlbaran.contains("Pedido")){
                            binding.checkBoxPedidoEditar.isChecked=true
                        }
                        if(tipoAlbaran.contains("Albaran")){
                            binding.checkBoxAlbaranEditar.isChecked=true
                        }
                        for (i in 0 until nombres.size) {
                            if (nombres[i] == nombreCliente2) {
                                spinner.setSelection(i)
                                break
                            }
                        }

                        recyclerView.adapter = LineaVentaAdapter(contexto, productos)

                        val staggeredManager: StaggeredGridLayoutManager =
                            StaggeredGridLayoutManager(
                                1,
                                StaggeredGridLayoutManager.VERTICAL
                            )
                        staggeredManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                        recyclerView.layoutManager = staggeredManager


                    }
                }
            }
        }

        /**
         * Este boton se encarga de añadir una nueva linea al recyclerview y comprobar todos los valores
         * esten correctos
         */
        val añadirLinea: ImageButton = findViewById<ImageButton>(R.id.botonEditarVenta)
        añadirLinea.setOnClickListener {
            contieneTexto = false
            cantidadNegativa = false
            campoVacio = false
            cantidadLlena = false
            precioLleno = false

            if (binding.campoNombreEditarLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoCantidadEditarLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoPrecioEditarLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (!campoVacio) {
                if (binding.campoCantidadEditarLineaVenta.text.toString().toIntOrNull() == null) {
                    contieneTexto = true
                }
                if (binding.campoPrecioEditarLineaVenta.text.toString().toFloatOrNull() == null) {
                    contieneTexto = true
                }
                if (!contieneTexto) {
                    if (binding.campoCantidadEditarLineaVenta.text.toString().toInt() < 1) {
                        cantidadNegativa = true
                    }
                    if (binding.campoPrecioEditarLineaVenta.text.toString().toFloat() < 0.00001) {
                        cantidadNegativa = true
                    }
                    if (!cantidadNegativa) {
                        val producto = Producto()
                        producto.nombre = binding.campoNombreEditarLineaVenta.text.toString()
                        producto.cantidad =
                            binding.campoCantidadEditarLineaVenta.text.toString().toIntOrNull() ?: 0
                        producto.precio =
                            binding.campoPrecioEditarLineaVenta.text.toString().toFloatOrNull()
                                ?: 0f



                        productos.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
                        recyclerView.adapter?.notifyItemInserted(productos.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
                        binding.campoNombreEditarLineaVenta.setText("")
                        binding.campoPrecioEditarLineaVenta.setText("")
                        binding.campoCantidadEditarLineaVenta.setText("")
                        binding.campoTotalLineaEditarVentaLinea.text = "0"
                    } else {
                        Toast.makeText(this, R.string.numeroBajo, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, R.string.contieneTexto, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.camposVacios, Toast.LENGTH_SHORT).show()
            }
        }


        /**
         * En este boton nos encargamos de comprobar que todos los valores esten correctos y se actualiza en la base
         * de datos tanto todos los productos como la venta.
         */
        binding.botonTerminadoAAdiendoVenta.setOnClickListener {
            if (nombreCliente != "Añadir nombre") {
                clienteIncorrecto = false
            }
            var camposVacios: Boolean = false

            if (binding.campoTituloEditarVenta.text.toString().isBlank()) {
                camposVacios = true
            }

            for (producto in productos) {
                if (producto.nombre.toString().isBlank()) {
                    camposVacios = true
                }
                if (producto.precio.toString().isBlank()) {
                    camposVacios = true
                }
                if (producto.cantidad.toString().isBlank()) {
                    camposVacios = true
                }
            }
            if (!checkBoxVacio) {
                if (!clienteIncorrecto) {
                    if (!camposVacios) {
                        if (productos.size >= 1) {
                            var totalAlbaranFinal: Float = 0f
                            CoroutineScope(Dispatchers.IO).launch {
                                db.albaran_ProductoDAO().borrarTodosPorTitulo(titulo.toString())
                                for (producto in productos) {
                                    //Inserto los productos
                                    db.albaran_ProductoDAO().insert(
                                        Albaran_Producto(
                                            tituloAlbaran = binding.campoTituloEditarVenta.text.toString(),
                                            nombreProducto = producto.nombre.toString(),
                                            nombreCliente= nombreCliente,
                                            tipoAlbaran=tipoAlbaran,
                                            precio = producto.precio,
                                            cantidad = producto.cantidad,
                                            total = producto.precio * producto.cantidad
                                        )
                                    )
                                    totalAlbaranFinal += producto.precio * producto.cantidad
                                }

                                //Inserto los albaranes
                                db.albaranDAO().updateAlbaran(
                                    binding.campoTituloEditarVenta.text.toString(),
                                    nombreCliente,
                                    LocalDate.now(),
                                    "Pendiente",
                                    (totalAlbaranFinal * 1.21).toFloat()
                                )
                            }

                            val intent: Intent = Intent(
                                this, ActividadVenta::class.java
                            )
                            this.startActivity(intent)
                        } else {
                            Toast.makeText(this, R.string.camposVacios, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, R.string.minimoProducto, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, R.string.seleccionarCliente, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.completarCheck, Toast.LENGTH_SHORT).show()
            }
        }

        //Esto es un actualizador de los datos de abajo y laza la funcion actualizarDatos cada 0,1 segundo
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                actualizarTotal(productos)
            }
        }, 0, 100)
    }

    /**
     * Esta funcion recibe un arraylist de Productos con todos los productos añadido al recycler para añadir
     * los valores actualizados a la base, iva y precio total
     */
    private fun actualizarTotal(productos: ArrayList<Producto>) {
        runOnUiThread {
            var total: Float = 0f
            for (producto in productos) {
                total += (producto.cantidad * producto.precio)
            }
            //Modificar los valores totales de abajo
            var numeroTotalBase = total
            binding.textoNumeroTotalBaseEditarVenta.setText("" + numeroTotalBase)
            var numeroIVA =
                (binding.textoNumeroTotalBaseEditarVenta.text.toString()
                    .toFloat() / 100) * 21
            binding.textoNumeroIvaEditarVenta.setText("" + numeroIVA)
            var numeroTotalFinal = numeroIVA + numeroTotalBase
            binding.textoNumeroTotalEditarVenta.setText("" + numeroTotalFinal)
        }
    }


}