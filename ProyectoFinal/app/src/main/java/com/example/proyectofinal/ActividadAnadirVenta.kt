package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.*
import com.example.proyectofinal.databinding.LayoutAnadirVentaBinding
import dataBase.AppDataBase
import kotlinx.coroutines.*
import recyclers.anadirProductosVenta.LineaVentaAdapter
import java.time.LocalDate
import java.util.*
import java.util.logging.Handler

class ActividadAnadirVenta : AppCompatActivity() {
    private lateinit var binding: LayoutAnadirVentaBinding
    private lateinit var db: AppDataBase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAnadirVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()



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
        binding.textoFechaDesdeVenta.text = hoy.toString()

        var contieneTexto = false
        var cantidadNegativa = false
        var campoVacio = false
        var precioLleno = false
        var cantidadLlena = false
        var checkBoxVacio = true

        binding.checkBoxPresupuesto.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPedido.isChecked = false
                binding.checkBoxAlbaran.isChecked = false
            } else if (!binding.checkBoxPedido.isChecked && !binding.checkBoxAlbaran.isChecked) {
                checkBoxVacio = true
            }
        }
        binding.checkBoxAlbaran.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPedido.isChecked = false
                binding.checkBoxPresupuesto.isChecked = false
            } else if (!binding.checkBoxPedido.isChecked && !binding.checkBoxPresupuesto.isChecked) {
                checkBoxVacio = true
            }
        }
        binding.checkBoxPedido.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxVacio = false
                binding.checkBoxPresupuesto.isChecked = false
                binding.checkBoxAlbaran.isChecked = false
            } else if (!binding.checkBoxPresupuesto.isChecked && !binding.checkBoxAlbaran.isChecked) {
                checkBoxVacio = true
            }
        }



        binding.campoCantidadAnadirLineaVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadAnadirLineaVenta.text.toString().isNotEmpty()
                    && binding.campoCantidadAnadirLineaVenta.text.toString().toIntOrNull() != null
                    && binding.campoPrecioAnadirLineaVenta.text.toString().isNotEmpty()
                    && binding.campoPrecioAnadirLineaVenta.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadAnadirLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaVenta.text.toString().toFloat()
                    binding.campoTotalLineaAnadirVentaLinea.text = (cantidad * precio).toString()
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

        binding.campoPrecioAnadirLineaVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.campoCantidadAnadirLineaVenta.text.toString().isNotEmpty()
                    && binding.campoCantidadAnadirLineaVenta.text.toString().toIntOrNull() != null
                    && binding.campoPrecioAnadirLineaVenta.text.toString().isNotEmpty()
                    && binding.campoPrecioAnadirLineaVenta.text.toString().toFloatOrNull() != null
                ) {
                    val cantidad = binding.campoCantidadAnadirLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaVenta.text.toString().toFloat()
                    binding.campoTotalLineaAnadirVentaLinea.text = (cantidad * precio).toString()
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


        //boton que añade linea
        val añadirLinea: ImageButton = findViewById<ImageButton>(R.id.botonAnadirVenta)
        añadirLinea.setOnClickListener {
            contieneTexto = false
            cantidadNegativa = false
            campoVacio = false
            cantidadLlena = false
            precioLleno = false

            if (binding.campoNombreAnadirLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoCantidadAnadirLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (binding.campoPrecioAnadirLineaVenta.text.toString().isBlank()) {
                campoVacio = true
            }
            if (!campoVacio) {
                if (binding.campoCantidadAnadirLineaVenta.text.toString().toIntOrNull() == null) {
                    contieneTexto = true
                }
                if (binding.campoPrecioAnadirLineaVenta.text.toString().toFloatOrNull() == null) {
                    contieneTexto = true
                }
                if (!contieneTexto) {
                    if (binding.campoCantidadAnadirLineaVenta.text.toString().toInt() < 1) {
                        cantidadNegativa = true
                    }
                    if (binding.campoPrecioAnadirLineaVenta.text.toString().toFloat() < 0.00001) {
                        cantidadNegativa = true
                    }
                    if (!cantidadNegativa) {
                        val producto = Producto()
                        producto.nombre = binding.campoNombreAnadirLineaVenta.text.toString()
                        producto.cantidad =
                            binding.campoCantidadAnadirLineaVenta.text.toString().toIntOrNull() ?: 0
                        producto.precio =
                            binding.campoPrecioAnadirLineaVenta.text.toString().toFloatOrNull()
                                ?: 0f

                        //Modificar los valores totales de abajo
                        var numeroTotalBase =
                            binding.textoNumeroTotalBaseAnadirVenta.text.toString()
                                .toFloat() + producto.cantidad * producto.precio
                        binding.textoNumeroTotalBaseAnadirVenta.setText("" + numeroTotalBase)
                        var numeroIVA =
                            (binding.textoNumeroTotalBaseAnadirVenta.text.toString()
                                .toFloat() / 100) * 21
                        binding.textoNumeroIvaAnadirVenta.setText("" + numeroIVA)
                        var numeroTotalFinal = numeroIVA + numeroTotalBase
                        binding.textoNumeroTotalAnadirVenta.setText("" + numeroTotalFinal)


                        productos.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
                        recyclerView.adapter?.notifyItemInserted(productos.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
                        binding.campoNombreAnadirLineaVenta.setText("")
                        binding.campoPrecioAnadirLineaVenta.setText("")
                        binding.campoCantidadAnadirLineaVenta.setText("")
                        binding.campoTotalLineaAnadirVentaLinea.text = "0"
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
            var camposVacios: Boolean = false

            if (binding.campoTituloAnadirVenta.text.toString().isBlank()) {
                camposVacios = true
            }
            if (binding.campoClienteNombreAnadirVenta.text.toString().isBlank()) {
                camposVacios = true
            }
            if (binding.campoClienteNombreAnadirVenta.text.toString().isBlank()) {
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
                if (!camposVacios) {
                    if(productos.size>=1) {
                        CoroutineScope(Dispatchers.IO).launch {
                            var albaranes = db.albaranDAO().getAll()
                            var tituloRepetido = false
                            var titulo = ""

                            for (albaran in albaranes) {
                                if (albaran.titulo == binding.campoTituloAnadirVenta.text.toString()) {
                                    tituloRepetido = true
                                    titulo = albaran.titulo
                                }
                            }

                            if (!tituloRepetido) {
                                launch(Dispatchers.IO) {
                                    for (producto in productos) {
                                        db.albaran_ProductoDAO().insert(
                                            Albaran_Producto(
                                                tituloAlbaran = binding.campoTituloAnadirVenta.text.toString(),
                                                nombreProducto = producto.nombre.toString(),
                                                precio = producto.precio,
                                                cantidad = producto.cantidad,
                                                total = producto.precio * producto.cantidad
                                            )
                                        )
                                        totalAlbaranFinal += producto.precio * producto.cantidad
                                    }

                                    db.albaranDAO().insert(
                                        Albaran(
                                            titulo = binding.campoTituloAnadirVenta.text.toString(),
                                            nombreCliente = binding.campoClienteNombreAnadirVenta.text.toString(),
                                            fecha = LocalDate.now(),
                                            estado = "Pendiente",
                                            precioTotal = (totalAlbaranFinal*1.21).toFloat()
                                         )
                                    )
                                }.join()

                                withContext(Dispatchers.Main) {
                                    val intent: Intent = Intent(
                                        this@ActividadAnadirVenta, ActividadVenta::class.java
                                    )
                                    startActivity(intent)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@ActividadAnadirVenta,
                                        R.string.tituloRepetido,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }else{
                        Toast.makeText(this, R.string.minimoProducto, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, R.string.camposVacios, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.completarCheck, Toast.LENGTH_SHORT).show()
            }
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
            binding.textoNumeroTotalBaseAnadirVenta.setText("" + numeroTotalBase)
            var numeroIVA =
                (binding.textoNumeroTotalBaseAnadirVenta.text.toString()
                    .toFloat() / 100) * 21
            binding.textoNumeroIvaAnadirVenta.setText("" + numeroIVA)
            var numeroTotalFinal = numeroIVA + numeroTotalBase
            binding.textoNumeroTotalAnadirVenta.setText("" + numeroTotalFinal)
        }
    }
}