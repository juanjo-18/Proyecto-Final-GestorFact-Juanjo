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
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.ItemSpacingDecoration
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirVentaBinding
import com.google.android.material.textfield.TextInputEditText
import recyclers.anadirProductosVenta.LineaVentaAdapter
import java.time.LocalDate
import java.util.*
import java.util.logging.Handler

class ActividadAnadirVenta : AppCompatActivity() {
    private lateinit var binding: LayoutAnadirVentaBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAnadirVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val valores = arrayListOf<Producto>()

        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.recyclerLineasProductos)
        recyclerView.adapter= LineaVentaAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager


        //boton que añade linea
        val añadirLinea:ImageButton = findViewById<ImageButton>(R.id.botonAnadirVenta)
        añadirLinea.setOnClickListener {
            val producto=Producto()
            producto.nombre=binding.campoNombreAnadirLineaVenta.text.toString()
            producto.cantidad=binding.campoCantidadAnadirLineaVenta.text.toString().toIntOrNull()?:0
            producto.precio=binding.campoPrecioAnadirLineaVenta.text.toString().toIntOrNull()?:0


            valores.add(producto) // Agrega un nuevo Producto vacío a la lista de valores
            recyclerView.adapter?.notifyItemInserted(valores.indexOf(producto)) // Notifica al Adapter del cambio en la lista de valores
            binding.campoNombreAnadirLineaVenta.setText("")
            binding.campoPrecioAnadirLineaVenta.setText("")
            binding.campoCantidadAnadirLineaVenta.setText("")
            binding.campoTotalLineaAnadirVentaLinea.text="0"
        }


        var cantidadLlena=false
        var precioLleno=false
       binding.campoCantidadAnadirLineaVenta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (cantidadLlena && precioLleno) {
                    val cantidad = binding.campoCantidadAnadirLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaVenta.text.toString().toInt()
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
                if (cantidadLlena && precioLleno) {
                    val cantidad = binding.campoCantidadAnadirLineaVenta.text.toString().toInt()
                    val precio = binding.campoPrecioAnadirLineaVenta.text.toString().toInt()
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
        binding.checkBoxPresupuesto.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxPedido.isChecked = false
                binding.checkBoxAlbaran.isChecked = false
            }
        }
        binding.checkBoxAlbaran.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxPedido.isChecked = false
                binding.checkBoxPresupuesto.isChecked = false
            }
        }
        binding.checkBoxPedido.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxPresupuesto.isChecked = false
                binding.checkBoxAlbaran.isChecked = false
            }
        }
        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val hoy: LocalDate = LocalDate.now()
                val fechaElegida: LocalDate = LocalDate.of(year, month, day);
                binding.textoFechaDesdeVenta.text = fechaElegida.toString()

            }

        binding.botonCalendarioCambiarFecha.setOnClickListener {
            val calendario: Calendar = Calendar.getInstance()
            val datePicker: DatePickerDialog =
                DatePickerDialog(
                    this, dateSetListener,
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                )
            datePicker.setIcon(R.drawable.caja)
            datePicker.setMessage(this.resources.getString(R.string.fecha))
            datePicker.show()
        }

        binding.botonTerminadoAAdiendoVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }


    }
}