package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Albaran
import clases.Albaran_Producto
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarVentaBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recyclers.anadirProductosVenta.LineaVentaAdapter
import java.util.*
import kotlin.collections.ArrayList


class ActividadEditarVenta : AppCompatActivity() {
    private lateinit var binding: LayoutEditarVentaBinding
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutEditarVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var contieneTexto = false
        var cantidadNegativa = false
        var campoVacio = false
        var precioLleno = false
        var cantidadLlena = false
        var checkBoxVacio = true

        binding.checkBoxPresupuestoEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPedidoEditar.isChecked = false
                binding.checkBoxAlbaranEditar.isChecked = false
            } else if (!binding.checkBoxPedidoEditar.isChecked && !binding.checkBoxAlbaranEditar.isChecked) {
                checkBoxVacio = true
            }
        }
        binding.checkBoxAlbaranEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPedidoEditar.isChecked = false
                binding.checkBoxPresupuestoEditar.isChecked = false
            } else if (!binding.checkBoxPedidoEditar.isChecked && !binding.checkBoxPresupuestoEditar.isChecked) {
                checkBoxVacio = true
            }
        }
        binding.checkBoxPedidoEditar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPresupuestoEditar.isChecked = false
                binding.checkBoxAlbaranEditar.isChecked = false
            } else if (!binding.checkBoxPresupuestoEditar.isChecked && !binding.checkBoxAlbaranEditar.isChecked) {
                checkBoxVacio = true
            }
        }

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

        if (titulo != null) {
            var valores = arrayListOf<Albaran>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    valores =
                        db.albaranDAO().buscarAlbaranPorTitulo(titulo!!) as ArrayList<Albaran>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {
                            binding.campoTituloEditarVenta.setText(valor.titulo)
                            binding.campoClienteNombreEditarVenta.setText(valor.nombreCliente)
                            binding.textoFechaDesdeVentaEditar.setText(valor.fecha.toString())
                            binding.textoNumeroTotalBaseEditarVenta.setText(
                                (valor.precioTotal / 1.21).toString()
                            )
                            binding.textoNumeroIvaEditarVenta.setText(


                                (valor.precioTotal - (valor.precioTotal / 1.21)).toString()

                            )
                            binding.textoNumeroTotalEditarVenta.setText(

                                valor.precioTotal.toString()

                            )

                            /**
                             * El estado falta no se como lo voy ha hacer
                            @ColumnInfo("estado") var estado: String?,
                             */
                        }
                    }
                }
            }


            val contexto = this

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    albaran_producto = db.albaran_ProductoDAO()
                        .buscarAlbaranProductoPorTitulo(titulo!!) as ArrayList<Albaran_Producto>
                    withContext(Dispatchers.Main) {
                        for (albaranes in albaran_producto) {
                            titulo = albaranes.tituloAlbaran.toString()
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
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

        //boton que añade linea
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



        binding.botonTerminadoAAdiendoVenta.setOnClickListener {

            var totalAlbaranFinal: Float = 0f
            CoroutineScope(Dispatchers.IO).launch {
                db.albaran_ProductoDAO().borrarTodosPorTitulo(titulo.toString())
                for (producto in productos) {
                    db.albaran_ProductoDAO().insert(
                        Albaran_Producto(
                            tituloAlbaran = binding.campoTituloEditarVenta.text.toString(),
                            nombreProducto = producto.nombre.toString(),
                            precio = producto.precio,
                            cantidad = producto.cantidad,
                            total = producto.precio * producto.cantidad
                        )
                    )
                    totalAlbaranFinal += producto.precio * producto.cantidad
                }

                db.albaranDAO().updateAlbaran(
                    binding.campoTituloEditarVenta.text.toString(),
                    binding.campoClienteNombreEditarVenta.text.toString(),
                    binding.textoFechaDesdeVentaEditar.text.toString(),
                    "Pendiente",
                    (totalAlbaranFinal*1.21).toFloat()
                )




            }


            val intent: Intent = Intent(
                this, ActividadVenta::class.java
            )
            this.startActivity(intent)
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                actualizarTotal(productos)
            }
        }, 0, 100)
    }

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