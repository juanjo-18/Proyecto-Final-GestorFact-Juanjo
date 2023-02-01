package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class ActividadPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_primera)

        val botonIrAInico: Button =findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrARegistro: Button =findViewById<Button>(R.id.botonRegistrarte)

        botonIrAInico.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        botonIrARegistro.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadRegistro::class.java
            )
            this.startActivity(intent)
        }

    }
}