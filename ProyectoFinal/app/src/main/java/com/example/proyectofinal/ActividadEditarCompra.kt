package com.example.proyectofinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.*
import com.example.proyectofinal.databinding.LayoutEditarCompraBinding
import com.example.proyectofinal.databinding.LayoutEditarFacturaBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recyclers.anadirProductosFactura.LineaFacturaAdapter
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ActividadEditarCompra : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutEditarCompraBinding

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
        binding = LayoutEditarCompraBinding.inflate(layoutInflater)
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
        var tipoCompra = ""
        //Si el checkBox devolucion a sido marcado desmarco compra.
        binding.checkBoxAbono.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoCompra = "Devolucion"
                binding.checkBoxCompra.isChecked = false
            } else if (!binding.checkBoxCompra.isChecked) {
                checkBoxVacio = true
            }
        }

        //Si el checkBox compra a sido marcado desmarco devolucion.
        binding.checkBoxCompra.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoCompra = "Compra"
                binding.checkBoxAbono.isChecked = false
            } else if (!binding.checkBoxAbono.isChecked) {
                checkBoxVacio = true
            }
        }


        /**
         * Aqui si en el campo cantidad ha habido algun cambio compruebo si el precio tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoCantidadEditarLineaCompra.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaCompra.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaCompra.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaCompra.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaCompra.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaCompra.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaCompra.text.toString().toFloat()
                    binding.campoTotalLineaEditarCompraLinea.text = (cantidad * precio).toString()
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
        binding.campoPrecioEditarLineaCompra.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaCompra.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaCompra.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaCompra.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaCompra.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaCompra.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaCompra.text.toString().toFloat()
                    binding.campoTotalLineaEditarCompraLinea.text = (cantidad * precio).toString()
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
        var compra_producto = arrayListOf<Compra_Producto>()
        var productos = arrayListOf<Producto>()
        val recyclerView: RecyclerView =
            findViewById<RecyclerView>(R.id.recyclerLineasProductos)
        var tituloActual=""
        var estadoActual=false

        //Compruebo si el titulo esta relleno
        if (titulo != null) {
            var valores = arrayListOf<Compra>()
            var nombreCliente2 = ""
            var tipoCompra = ""
            var pagadaComprobar=false

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //hago un select a la base de datos de compra con el titulo para cargar los datos al layout de editar

                    valores =
                        db.compraDAO().buscarCompraPorTitulo(titulo!!) as ArrayList<Compra>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {
                            estadoActual= valor.pagada
                            tituloActual=valor.titulo
                            nombreCliente2 = valor.nombreProveedor.toString()
                            tipoCompra = valor.tipoCompra.toString()
                            pagadaComprobar=valor.pagada
                            binding.campoTituloEditarCompra.setText(valor.titulo)
                            binding.textoFechaDesdeCompra.setText((LocalDate.now()).toString())
                            binding.textoNumeroTotalBaseEditarCompra.setText(
                                (valor.precioTotal / 1.21).toString()
                            )
                            binding.textoNumeroIvaEditarCompra.setText(
                                (valor.precioTotal - (valor.precioTotal / 1.21)).toString()
                            )
                            binding.textoNumeroTotalEditarCompra.setText(

                                valor.precioTotal.toString()

                            )

                        }
                        if(pagadaComprobar==true){
                            binding.checkBoxCobrada.isChecked=true
                        }
                        if (tipoCompra.contains("Compra")) {
                            binding.checkBoxCompra.isChecked = true
                        }
                        if (tipoCompra.contains("Devolucion")) {
                            binding.checkBoxAbono.isChecked = true
                        }

                        for (i in 0 until nombres.size) {
                            if (nombres[i] == nombreCliente2) {
                                spinner.setSelection(i)
                                break
                            }
                        }
                    }

                }
            }

            val contexto = this

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //Aqui traigo todos los productos que tiene ese titulo y los cargo al layout
                    compra_producto = db.compra_ProductoDAO()
                        .buscarCompraProductoPorTitulo(titulo!!) as ArrayList<Compra_Producto>
                    withContext(Dispatchers.Main) {

                        for (albaranes in compra_producto) {

                            titulo = albaranes.tituloComrpa.toString()
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
                        }


                        recyclerView.adapter = LineaFacturaAdapter(contexto, productos)

                        val staggeredManager: StaggeredGridLayoutManager =
                            StaggeredGridLayoutManager(
                                1,
                                StaggeredGridLayoutManager.VERTICAL
                            )
                        staggeredManager.gapStrategy =
                            StaggeredGridLayoutManager.GAP_HANDLING_NONE
                        recyclerView.layoutManager = staggeredManager


                    }
                }
            }
        }

        /**
         * Este boton se encarga de añadir una nueva linea al recyclerview y comprobar todos los valores
         * esten correctos
         */
        val añadirLinea: ImageButton = findViewById<ImageButton>(R.id.botonEditarCompra)
        añadirLinea.setOnClickListener {
            contieneTexto = false
            cantidadNegativa = false
            campoVacio = false
            cantidadLlena = false
            precioLleno = false

            if (binding.campoNombreEditarLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoCantidadEditarLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoPrecioEditarLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (!campoVacio) {
                if (binding.campoCantidadEditarLineaCompra.text.toString()
                        .toIntOrNull() == null
                ) {
                    contieneTexto = true
                }
                if (binding.campoPrecioEditarLineaCompra.text.toString()
                        .toFloatOrNull() == null
                ) {
                    contieneTexto = true
                }
                if (!contieneTexto) {
                    if (binding.campoCantidadEditarLineaCompra.text.toString().toInt() < 1) {
                        cantidadNegativa = true
                    }
                    if (binding.campoPrecioEditarLineaCompra.text.toString()
                            .toFloat() < 0.00001
                    ) {
                        cantidadNegativa = true
                    }
                    if (!cantidadNegativa) {
                        val producto = Producto()
                        producto.nombre = binding.campoNombreEditarLineaCompra.text.toString()
                        producto.cantidad =
                            binding.campoCantidadEditarLineaCompra.text.toString()
                                .toIntOrNull() ?: 0
                        producto.precio =
                            binding.campoPrecioEditarLineaCompra.text.toString()
                                .toFloatOrNull()
                                ?: 0f



                        productos.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
                        recyclerView.adapter?.notifyItemInserted(productos.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
                        binding.campoNombreEditarLineaCompra.setText("")
                        binding.campoPrecioEditarLineaCompra.setText("")
                        binding.campoCantidadEditarLineaCompra.setText("")
                        binding.campoTotalLineaEditarCompraLinea.text = "0"
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
        binding.botonTerminadoAAdiendoCompra.setOnClickListener {
            if (nombreCliente != "Añadir nombre") {
                clienteIncorrecto = false
            }
            var camposVacios: Boolean = false

            if (binding.campoTituloEditarCompra.text.toString().isBlank()) {
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
                            var cobrada=false
                            if(binding.checkBoxCobrada.isChecked){
                                cobrada=true
                            }
                            var totalCompraFinal: Float = 0f
                            CoroutineScope(Dispatchers.IO).launch {
                                var compras = db.compraDAO().getAll()
                                var tituloRepetido = false
                                var titulo = ""

                                for (albaran in compras) {
                                    if (albaran.titulo == binding.campoTituloEditarCompra.text.toString()) {
                                        tituloRepetido = true
                                    }
                                }
                                if(tituloActual==binding.campoTituloEditarCompra.text.toString()){
                                    tituloRepetido = false

                                }

                                if (!tituloRepetido) {
                                    //Aqui inserto todos los productos a la tabla intermedia compra_producto
                                    launch(Dispatchers.IO) {
                                        db.compra_ProductoDAO().borrarTodosPorTitulo(tituloActual)
                                        for (producto in productos) {
                                            //Inserto los productos
                                            db.compra_ProductoDAO().insert(
                                                Compra_Producto(
                                                    tituloComrpa = binding.campoTituloEditarCompra.text.toString(),
                                                    nombreProducto = producto.nombre.toString(),
                                                    precio = producto.precio,
                                                    cantidad = producto.cantidad,
                                                    total = producto.precio * producto.cantidad
                                                )
                                            )
                                            totalCompraFinal += producto.precio * producto.cantidad
                                        }

                                        //Inserto la compra
                                        db.compraDAO().updateCompra(
                                            binding.campoTituloEditarCompra.text.toString(),
                                            tituloActual,
                                            nombreCliente,
                                            LocalDate.now(),
                                            tipoCompra,
                                            cobrada,
                                            (totalCompraFinal * 1.21).toFloat()
                                        )
                                    }.join()

                                    withContext(Dispatchers.Main) {
                                        val intent: Intent = Intent(
                                            this@ActividadEditarCompra, ActividadCompra::class.java
                                        )
                                        startActivity(intent)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@ActividadEditarCompra,
                                            R.string.tituloRepetido,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(this, R.string.camposVacios, Toast.LENGTH_SHORT)
                                .show()
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
            binding.textoNumeroTotalBaseEditarCompra.setText("" + numeroTotalBase)
            var numeroIVA =
                (binding.textoNumeroTotalBaseEditarCompra.text.toString()
                    .toFloat() / 100) * 21
            binding.textoNumeroIvaEditarCompra.setText("" + numeroIVA)
            var numeroTotalFinal = numeroIVA + numeroTotalBase
            binding.textoNumeroTotalEditarCompra.setText("" + numeroTotalFinal)
        }
    }
}
