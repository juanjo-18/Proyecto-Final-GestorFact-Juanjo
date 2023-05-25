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
import clases.Albaran
import clases.Albaran_Producto
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirCompraBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recyclers.anadirProductosVenta.LineaVentaAdapter
import java.time.LocalDate
import java.util.*

class ActividadAnadirCompra : AppCompatActivity() {
    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutAnadirCompraBinding

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
        binding = LayoutAnadirCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

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
        val spinner = findViewById<Spinner>(R.id.spinnerNombreClienteCompra)
        spinner.adapter = adapter
        var nombreCliente = ""
        var clienteIncorrecto = false


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


        //Esto es el recycler view de productos que yo ire añadiendo con el boton
        val productos = arrayListOf<Producto>()
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerLineasProductos)
        recyclerView.adapter = LineaVentaAdapter(this, productos)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        staggeredManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = staggeredManager

        val hoy: LocalDate = LocalDate.now()
        binding.textoFechaDesdeCompra.text = hoy.toString()

        var contieneTexto = false
        var cantidadNegativa = false
        var campoVacio = false
        var precioLleno = false
        var cantidadLlena = false
        var checkBoxVacio = true

        var tipoAlbaran=""
        //Si el checkBox abono a sido marcado desmarco factura.
        binding.checkBoxDevolucion.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoAlbaran="Devolucion"
                binding.checkBoxFactura.isChecked = false
            } else if (!binding.checkBoxFactura.isChecked ) {
                checkBoxVacio = true
            }
        }
        //Si el checkBox factura a sido marcado desmarco abono.
        binding.checkBoxFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoAlbaran="Compra"
                binding.checkBoxDevolucion.isChecked = false
            } else if (!binding.checkBoxDevolucion.isChecked) {
                checkBoxVacio = true
            }
        }





        /**
         * Aqui si en el campo cantidad ha habido algun cambio compruebo si el precio tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoCantidadAnadirLineaCompra.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadAnadirLineaCompra.text.toString().isNotEmpty()
                    && binding.campoCantidadAnadirLineaCompra.text.toString().toIntOrNull() != null
                    && binding.campoPrecioAnadirLineaCompra.text.toString().isNotEmpty()
                    && binding.campoPrecioAnadirLineaCompra.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadAnadirLineaCompra.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaCompra.text.toString().toFloat()
                    binding.campoTotalLineaAnadirCompraLinea.text = (cantidad * precio).toString()
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
         * Aqui si en el campo precio ha habido algun cambio compruebo si la cantidad tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoPrecioAnadirLineaCompra.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadAnadirLineaCompra.text.toString().isNotEmpty()
                    && binding.campoCantidadAnadirLineaCompra.text.toString().toIntOrNull() != null
                    && binding.campoPrecioAnadirLineaCompra.text.toString().isNotEmpty()
                    && binding.campoPrecioAnadirLineaCompra.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadAnadirLineaCompra.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaCompra.text.toString().toFloat()
                    binding.campoTotalLineaAnadirCompraLinea.text = (cantidad * precio).toString()
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


        /**
         * Este boton se encarga de añadir una nueva linea al recyclerview y comprobar todos los valores esten correctos
         */
        val añadirLinea: ImageButton = findViewById<ImageButton>(R.id.botonAnadirCompra)
        añadirLinea.setOnClickListener {
            contieneTexto = false
            cantidadNegativa = false
            campoVacio = false
            cantidadLlena = false
            precioLleno = false

            if (binding.campoNombreAnadirLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoCantidadAnadirLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoPrecioAnadirLineaCompra.text.toString().isBlank()) {
                campoVacio = true
            }
            if (!campoVacio) {
                if (binding.campoCantidadAnadirLineaCompra.text.toString().toIntOrNull() == null) {
                    contieneTexto = true
                }
                if (binding.campoPrecioAnadirLineaCompra.text.toString().toFloatOrNull() == null) {
                    contieneTexto = true
                }
                if (!contieneTexto) {
                    if (binding.campoCantidadAnadirLineaCompra.text.toString().toInt() < 1) {
                        cantidadNegativa = true
                    }
                    if (binding.campoPrecioAnadirLineaCompra.text.toString().toFloat() < 0.00001) {
                        cantidadNegativa = true
                    }
                    if (!cantidadNegativa) {
                        val producto = Producto()
                        producto.nombre = binding.campoNombreAnadirLineaCompra.text.toString()
                        producto.cantidad =
                            binding.campoCantidadAnadirLineaCompra.text.toString().toIntOrNull() ?: 0
                        producto.precio =
                            binding.campoPrecioAnadirLineaCompra.text.toString().toFloatOrNull()
                                ?: 0f

                        //Modificar los valores totales de abajo
                        var numeroTotalBase =
                            binding.textoNumeroTotalBaseAnadirCompra.text.toString()
                                .toFloat() + producto.cantidad * producto.precio
                        binding.textoNumeroTotalBaseAnadirCompra.setText("" + numeroTotalBase)
                        var numeroIVA =
                            (binding.textoNumeroTotalBaseAnadirCompra.text.toString()
                                .toFloat() / 100) * 21
                        binding.textoNumeroIvaAnadirCompra.setText("" + numeroIVA)
                        var numeroTotalFinal = numeroIVA + numeroTotalBase
                        binding.textoNumeroTotalAnadirCompra.setText("" + numeroTotalFinal)


                        productos.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
                        recyclerView.adapter?.notifyItemInserted(productos.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
                        binding.campoNombreAnadirLineaCompra.setText("")
                        binding.campoPrecioAnadirLineaCompra.setText("")
                        binding.campoCantidadAnadirLineaCompra.setText("")
                        binding.campoTotalLineaAnadirCompraLinea.text = "0"
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
         * En este boton nos encargamos de comprobar que todos los valores esten correctos y se añadan a la base
         * de datos tanto todos los productos como la venta.
         */
        binding.botonTerminadoAAdiendoCompra.setOnClickListener {
            if (nombreCliente != "Añadir nombre") {
                clienteIncorrecto = false
            }


            var totalAlbaranFinal: Float = 0f
            var camposVacios: Boolean = false

            if (binding.campoTituloAnadirCompra.text.toString().isBlank()) {
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

                            CoroutineScope(Dispatchers.IO).launch {
                                var albaranes = db.albaranDAO().getAll()
                                var tituloRepetido = false
                                var titulo = ""

                                for (albaran in albaranes) {
                                    if (albaran.titulo == binding.campoTituloAnadirCompra.text.toString()) {
                                        tituloRepetido = true
                                        titulo = albaran.titulo
                                    }
                                }

                                if (!tituloRepetido) {
                                    //Aqui inserto todos los productos a la tabla intermedia albaran_producto
                                    launch(Dispatchers.IO) {
                                        for (producto in productos) {
                                            db.albaran_ProductoDAO().insert(
                                                Albaran_Producto(
                                                    tituloAlbaran = binding.campoTituloAnadirCompra.text.toString(),
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

                                        //Aqui insertamos la venta a la base de datos Albaran
                                        db.albaranDAO().insert(
                                            Albaran(
                                                titulo = binding.campoTituloAnadirCompra.text.toString(),
                                                nombreCliente = nombreCliente,
                                                fecha = LocalDate.now(),
                                                estado = "Pendiente",
                                                precioTotal = (totalAlbaranFinal * 1.21).toFloat()
                                            )
                                        )
                                    }.join()

                                    withContext(Dispatchers.Main) {
                                        val intent: Intent = Intent(
                                            this@ActividadAnadirCompra, ActividadVenta::class.java
                                        )
                                        startActivity(intent)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@ActividadAnadirCompra,
                                            R.string.tituloRepetido,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
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
            binding.textoNumeroTotalBaseAnadirCompra.setText("" + numeroTotalBase)
            var numeroIVA =
                (binding.textoNumeroTotalBaseAnadirCompra.text.toString()
                    .toFloat() / 100) * 21
            binding.textoNumeroIvaAnadirCompra.setText("" + numeroIVA)
            var numeroTotalFinal = numeroIVA + numeroTotalBase
            binding.textoNumeroTotalAnadirCompra.setText("" + numeroTotalFinal)
        }
    }
}
