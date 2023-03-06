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

class ActividadInicio : AppCompatActivity() {

    private lateinit var binding: LayoutInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACLienteDesdeInicio)
        val botonIrACatalogo: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACatalogoDesdeInicio)
        val botonIrAVenta:ImageButton=findViewById<ImageButton>(R.id.botonIrAVentaDedeInicio)
        val botonIrAFacturacion:ImageButton=findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeInicio)


        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }

        botonIrACliente.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

    }

}