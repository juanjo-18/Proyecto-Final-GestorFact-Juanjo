package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.proyectofinal.databinding.LayoutRegistroBinding
import emergentes.Alerta

class ActividadRegistro : AppCompatActivity() {
    private lateinit var binding: LayoutRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrAIniciar: Button = findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrAInicio: Button = findViewById<Button>(R.id.botonRegistrarte)

        botonIrAIniciar.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadPrincipal::class.java
            )
            this.startActivity(intent)
        }

        botonIrAInicio.setOnClickListener {

            if (binding.campoCorreo.toString().isNotEmpty()) {
                val alerta: Alerta = Alerta(this.resources.getString(R.string.registroFallido),
                    this.resources.getString(R.string.rellenarCampos),
                    this.resources.getString(R.string.ok), this, {
                        Toast.makeText(
                            this, R.string.rellenarCampos,
                            Toast.LENGTH_LONG
                        ).show()
                    })
                alerta.mostrar()
            } else {
                val intent: Intent = Intent(
                    this, ActividadInicio::class.java
                )

                this.startActivity(intent)
            }

        }


    }
}