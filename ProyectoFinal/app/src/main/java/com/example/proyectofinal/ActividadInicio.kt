package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ActividadInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_inicio)

        val botonIrAInicio:ImageButton=findViewById<ImageButton>(R.id.botonIrAInicio)
        val botonIrACatalogo:ImageButton=findViewById<ImageButton>(R.id.botonIrACatalogo)

        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)

        }

        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }

    }

}