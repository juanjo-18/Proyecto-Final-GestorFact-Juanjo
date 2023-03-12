package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.room.Room
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirClienteBinding
import com.example.proyectofinal.databinding.LayoutAnadirProductoBinding
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActividadAnadirCliente : AppCompatActivity() {
    private lateinit var db: AppDataBase
    private lateinit var binding: LayoutAnadirClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAnadirClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()

        val botonIrACliente: ImageButton = findViewById(R.id.botonTerminadoAñadiendoCliente)
        botonIrACliente.setOnClickListener {
            GlobalScope.launch {
                db.clienteDAO().insert(
                    Cliente(
                        nombre = binding.campoNombreClienteAAdir.text.toString(),
                        email = binding.campoEmailAAndir.text.toString(),
                        nif = binding.campoNifAAdirCliente.text.toString(),
                        telefono = binding.campoTelefonoAAdirCliente.text.toString().toInt(),
                        direccion = binding.campoDireccionAAdirCliente.text.toString(),
                        localidad = binding.campoLocalidadClienteAAdir.text.toString(),
                        provincia = binding.campoProvinciaCliente.text.toString(),
                        codigoPostal = binding.campoCodigoPostalCliente.text.toString().toInt()
                    )
                )
            }
            val intent: Intent = Intent(
                this, ActividadCliente::class.java
            )
            this.startActivity(intent)
        }
    }
}