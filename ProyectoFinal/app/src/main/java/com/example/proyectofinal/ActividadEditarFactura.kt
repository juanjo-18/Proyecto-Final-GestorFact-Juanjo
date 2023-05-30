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

class ActividadEditarFactura : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutEditarFacturaBinding

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
        binding = LayoutEditarFacturaBinding.inflate(layoutInflater)
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
        var tipoFactura = ""
        //Si el checkBox Abono a sido marcado desmarco factura.
        binding.checkBoxAbono.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoFactura = "Abono"
                binding.checkBoxFactura.isChecked = false
            } else if (!binding.checkBoxFactura.isChecked) {
                checkBoxVacio = true
            }
        }

        //Si el checkBox factura a sido marcado desmarco abono.
        binding.checkBoxFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                tipoFactura = "Factura"
                binding.checkBoxAbono.isChecked = false
            } else if (!binding.checkBoxAbono.isChecked) {
                checkBoxVacio = true
            }
        }


        /**
         * Aqui si en el campo cantidad ha habido algun cambio compruebo si el precio tiene algun dato valido
         *  y si lo tiene lo multiplico el precio y la cantidad, para ponerlo en el total.
         */
        binding.campoCantidadEditarLineaFactura.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaFactura.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaFactura.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaFactura.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaFactura.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaFactura.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaFactura.text.toString().toFloat()
                    binding.campoTotalLineaEditarFacturaLinea.text = (cantidad * precio).toString()
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
        binding.campoPrecioEditarLineaFactura.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadEditarLineaFactura.text.toString().isNotEmpty()
                    && binding.campoCantidadEditarLineaFactura.text.toString().toIntOrNull() != null
                    && binding.campoPrecioEditarLineaFactura.text.toString().isNotEmpty()
                    && binding.campoPrecioEditarLineaFactura.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadEditarLineaFactura.text.toString().toInt()
                    val precio = binding.campoPrecioEditarLineaFactura.text.toString().toFloat()
                    binding.campoTotalLineaEditarFacturaLinea.text = (cantidad * precio).toString()
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
        var factura_producto = arrayListOf<Factura_Producto>()
        var productos = arrayListOf<Producto>()
        val recyclerView: RecyclerView =
            findViewById<RecyclerView>(R.id.recyclerLineasProductos)

        //Compruebo si el titulo esta relleno
        if (titulo != null) {
            var valores = arrayListOf<Factura>()
            var nombreCliente2 = ""
            var tipoFactura = ""
            var cobradaComprobar=false

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //hago un select a la base de datos de factura con el titulo para cargar los datos al layout de editar

                    valores =
                        db.facturaDAO().buscarFacturaPorTitulo(titulo!!) as ArrayList<Factura>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {
                            nombreCliente2 = valor.nombreCliente.toString()
                            tipoFactura = valor.tipoFactura.toString()
                            cobradaComprobar=valor.cobrada
                            binding.campoTituloEditarFactura.setText(valor.titulo)
                            binding.textoFechaDesdeFactura.setText((LocalDate.now()).toString())
                            binding.textoNumeroTotalBaseEditarFactura.setText(
                                (valor.precioTotal / 1.21).toString()
                            )
                            binding.textoNumeroIvaEditarFactura.setText(
                                (valor.precioTotal - (valor.precioTotal / 1.21)).toString()
                            )
                            binding.textoNumeroTotalEditarFactura.setText(

                                valor.precioTotal.toString()

                            )

                        }
                        if(cobradaComprobar==true){
                            binding.checkBoxCobrada.isChecked=true
                        }
                       if (tipoFactura.contains("Factura")) {
                            binding.checkBoxFactura.isChecked = true
                        }
                        if (tipoFactura.contains("Abono")) {
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
                        factura_producto = db.factura_ProductoDAO()
                            .buscarFacturaProductoPorTitulo(titulo!!) as ArrayList<Factura_Producto>
                        withContext(Dispatchers.Main) {

                            for (albaranes in factura_producto) {

                                titulo = albaranes.tituloFactura.toString()
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
            val añadirLinea: ImageButton = findViewById<ImageButton>(R.id.botonEditarFactura)
            añadirLinea.setOnClickListener {
                contieneTexto = false
                cantidadNegativa = false
                campoVacio = false
                cantidadLlena = false
                precioLleno = false

                if (binding.campoNombreEditarLineaFactura.text.toString().isBlank()) {
                    campoVacio = true
                }
                if (binding.campoCantidadEditarLineaFactura.text.toString().isBlank()) {
                    campoVacio = true
                }
                if (binding.campoPrecioEditarLineaFactura.text.toString().isBlank()) {
                    campoVacio = true
                }
                if (!campoVacio) {
                    if (binding.campoCantidadEditarLineaFactura.text.toString()
                            .toIntOrNull() == null
                    ) {
                        contieneTexto = true
                    }
                    if (binding.campoPrecioEditarLineaFactura.text.toString()
                            .toFloatOrNull() == null
                    ) {
                        contieneTexto = true
                    }
                    if (!contieneTexto) {
                        if (binding.campoCantidadEditarLineaFactura.text.toString().toInt() < 1) {
                            cantidadNegativa = true
                        }
                        if (binding.campoPrecioEditarLineaFactura.text.toString()
                                .toFloat() < 0.00001
                        ) {
                            cantidadNegativa = true
                        }
                        if (!cantidadNegativa) {
                            val producto = Producto()
                            producto.nombre = binding.campoNombreEditarLineaFactura.text.toString()
                            producto.cantidad =
                                binding.campoCantidadEditarLineaFactura.text.toString()
                                    .toIntOrNull() ?: 0
                            producto.precio =
                                binding.campoPrecioEditarLineaFactura.text.toString()
                                    .toFloatOrNull()
                                    ?: 0f



                            productos.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
                            recyclerView.adapter?.notifyItemInserted(productos.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
                            binding.campoNombreEditarLineaFactura.setText("")
                            binding.campoPrecioEditarLineaFactura.setText("")
                            binding.campoCantidadEditarLineaFactura.setText("")
                            binding.campoTotalLineaEditarFacturaLinea.text = "0"
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
            binding.botonTerminadoAAdiendoFactura.setOnClickListener {
                if (nombreCliente != "Añadir nombre") {
                    clienteIncorrecto = false
                }
                var camposVacios: Boolean = false

                if (binding.campoTituloEditarFactura.text.toString().isBlank()) {
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
                                var totalFacturaFinal: Float = 0f
                                CoroutineScope(Dispatchers.IO).launch {
                                    db.factura_ProductoDAO().borrarTodosPorTitulo(titulo.toString())
                                    for (producto in productos) {
                                        //Inserto los productos
                                        db.factura_ProductoDAO().insert(
                                            Factura_Producto(
                                                tituloFactura = binding.campoTituloEditarFactura.text.toString(),
                                                nombreProducto = producto.nombre.toString(),
                                                precio = producto.precio,
                                                cantidad = producto.cantidad,
                                                total = producto.precio * producto.cantidad
                                            )
                                        )
                                        totalFacturaFinal += producto.precio * producto.cantidad
                                    }

                                    //Inserto la factura
                                    db.facturaDAO().updateFactura(
                                        binding.campoTituloEditarFactura.text.toString(),
                                        titulo.toString(),
                                        nombreCliente,
                                        LocalDate.now(),
                                        tipoFactura,
                                        cobrada,
                                        (totalFacturaFinal * 1.21).toFloat()
                                    )
                                }

                                val intent: Intent = Intent(
                                    this, ActividadCompra::class.java
                                )
                                this.startActivity(intent)
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
            binding.textoNumeroTotalBaseEditarFactura.setText("" + numeroTotalBase)
            var numeroIVA =
                (binding.textoNumeroTotalBaseEditarFactura.text.toString()
                    .toFloat() / 100) * 21
            binding.textoNumeroIvaEditarFactura.setText("" + numeroIVA)
            var numeroTotalFinal = numeroIVA + numeroTotalBase
            binding.textoNumeroTotalEditarFactura.setText("" + numeroTotalFinal)
        }
    }
    }
