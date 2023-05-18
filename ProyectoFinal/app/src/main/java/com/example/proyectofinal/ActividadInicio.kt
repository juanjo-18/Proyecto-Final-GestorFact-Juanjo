package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import clases.Cliente
import clases.Usuario
import com.example.proyectofinal.databinding.LayoutInicioBinding
import com.google.firebase.firestore.FirebaseFirestore
import emergentes.Alerta
import java.time.LocalDate

/**
 * Esta es la pantalla inicial donde una vez iniciado sesion o registrarte entraras aqui se muestran los datos
 * generales de las facturas y albaranes.
 * @author Juanjo Medina
 */
class ActividadInicio : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutInicioBinding

    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACLienteDesdeInicio)
        val botonIrACatalogo: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACatalogoDesdeInicio)
        val botonIrAVenta:ImageButton=findViewById<ImageButton>(R.id.botonIrAVentaDedeInicio)

        //Boton que cambia la panatalla de catalogo
        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }

        //Boton que cambia la panatalla de cliente
        botonIrACliente.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla de ventas
        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a facturacion
        val botonIrAFacturacion:ImageButton=findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeInicio)
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

    }

}