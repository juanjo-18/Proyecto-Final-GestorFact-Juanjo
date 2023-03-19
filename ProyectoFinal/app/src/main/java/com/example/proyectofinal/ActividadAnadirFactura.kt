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

/**
 * Esta es la clase que representa la actividad para añadir una nueva factura.
 * Esta clase se encarga de poder rellenar todos los campos de la factura  y comprobar que los datos esten correctos.
 * Ademas de guardar la factura en la base de datos.
 * @author Juanjo Medina
 */
class ActividadAnadirFactura : AppCompatActivity() {

    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutAnadirFacturaBinding


    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutAnadirFacturaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Comprueba si el checkBox abono a sido marcardo si lo a sido desmarca el de factura.
        binding.checkBoxAbonoFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxFacturaFactura.isChecked = false
            }
        }

        //Comprueba si el checkBox factura a sido marcardo si lo a sido desmarca el de abono.
        binding.checkBoxFacturaFactura.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.checkBoxAbonoFactura.isChecked = false
            }
        }

        //boton que cambia de pantalla
        binding.botonTerminadoAnadiendoFactura.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }
    }
}