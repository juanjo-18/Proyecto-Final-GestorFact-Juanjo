package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.ItemSpacingDecoration
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirVentaBinding
import recyclers.anadirProductosVenta.LineaVentaAdapter
import recyclers.catalogo.ProductosAdapter
import java.time.LocalDate
import java.util.*

class ActividadAnadirVenta : AppCompatActivity() {
    private lateinit var binding: LayoutAnadirVentaBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAnadirVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val valores = arrayListOf<Producto>()
        for (i in 3 downTo 1) {
            var producto: Producto = Producto()
            valores.add(producto)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.recyclerLineasProductos)
        recyclerView.adapter= LineaVentaAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager




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