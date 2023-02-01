package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActividadRegistro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_registro)

        val botonIrAIniciar: Button =findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrAInicio: Button =findViewById<Button>(R.id.botonRegistrarte)

        botonIrAIniciar.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadPrincipal::class.java
            )
            this.startActivity(intent)
        }

        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }
    }
}