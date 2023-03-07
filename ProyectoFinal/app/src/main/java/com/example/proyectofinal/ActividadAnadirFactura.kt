package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import com.example.proyectofinal.databinding.LayoutAnadirFacturaBinding
import com.example.proyectofinal.databinding.LayoutAnadirVentaBinding
import java.time.LocalDate
import java.util.*

class ActividadAnadirFactura : AppCompatActivity() {
    private lateinit var binding: LayoutAnadirFacturaBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LayoutAnadirFacturaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBoxAbonoFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxFacturaFactura.isChecked = false
            }
        }
        binding.checkBoxFacturaFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxAbonoFactura.isChecked = false
            }
        }

        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val hoy: LocalDate = LocalDate.now()
                val fechaElegida: LocalDate = LocalDate.of(year, month, day);
                binding.textoFechaFacturaFacturacion.text = fechaElegida.toString()

            }

        binding.botonCalendarioCambiarFechaFactura.setOnClickListener {
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

        binding.botonTerminadoAnadiendoFactura.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }
    }
}