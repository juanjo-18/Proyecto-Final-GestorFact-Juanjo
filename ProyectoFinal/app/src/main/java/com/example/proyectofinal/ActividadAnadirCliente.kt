package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
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
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        val botonIrACliente: ImageButton = findViewById(R.id.botonTerminadoAñadiendoCliente)
        botonIrACliente.setOnClickListener {
            var camposVacios: Boolean = false
            var camposNumericosMal:Boolean=false
            var numeroMal:Boolean=false

            //Comprobar campos vacios
            val campos = listOf(
                binding.campoNombreClienteAAdir,
                binding.campoEmailAAndir,
                binding.campoNifAAdirCliente,
                binding.campoTelefonoAAdirCliente,
                binding.campoDireccionAAdirCliente,
                binding.campoLocalidadClienteAAdir,
                binding.campoProvinciaCliente,
                binding.campoCodigoPostalCliente
            )

            for (campo in campos) {
                if (campo.text.toString().isBlank()) {
                    camposVacios = true
                    break
                }
            }

            //Comprobar si algun numero contiene "," o "."
            if (binding.campoTelefonoAAdirCliente.text.toString().contains(",")||binding.campoTelefonoAAdirCliente.text.toString().contains(".")||binding.campoTelefonoAAdirCliente.text.toString().toIntOrNull()==null) {
                camposNumericosMal = true;
            }
            if (binding.campoCodigoPostalCliente.text.toString().contains(",")||binding.campoCodigoPostalCliente.text.toString().contains(".")||binding.campoCodigoPostalCliente.text.toString().toIntOrNull()==null) {
                camposNumericosMal = true;
            }



            if (!camposVacios) {
                if (!camposNumericosMal) {
                    //Comprobar que el numero de telefono tenga mas de 3 digitos y el codigo postal sea positivo
                    if (binding.campoTelefonoAAdirCliente.text.toString().toInt()<1000) {
                        numeroMal = true;
                    }
                    if (binding.campoCodigoPostalCliente.text.toString().toInt()<0) {
                        numeroMal = true;
                    }
                    if (!numeroMal) {
                        //Hago el registro
                        GlobalScope.launch {
                            db.clienteDAO().insert(
                                Cliente(
                                    nombre = binding.campoNombreClienteAAdir.text.toString(),
                                    email = binding.campoEmailAAndir.text.toString(),
                                    nif = binding.campoNifAAdirCliente.text.toString(),
                                    telefono = binding.campoTelefonoAAdirCliente.text.toString()
                                        .toInt(),
                                    direccion = binding.campoDireccionAAdirCliente.text.toString(),
                                    localidad = binding.campoLocalidadClienteAAdir.text.toString(),
                                    provincia = binding.campoProvinciaCliente.text.toString(),
                                    codigoPostal = binding.campoCodigoPostalCliente.text.toString()
                                        .toInt()
                                )
                            )
                        }
                        val intent: Intent = Intent(
                            this, ActividadCliente::class.java
                        )
                        this.startActivity(intent)
                    }else{
                        Toast.makeText(this,R.string.numeroMal,Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,R.string.contieneTexto,Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,R.string.camposVacios,Toast.LENGTH_SHORT).show()
            }
        }
    }
}