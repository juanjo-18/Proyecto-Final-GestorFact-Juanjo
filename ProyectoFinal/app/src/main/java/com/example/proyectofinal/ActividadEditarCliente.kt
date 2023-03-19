package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarClienteBinding
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActividadEditarCliente : AppCompatActivity() {
    private lateinit var binding: LayoutEditarClienteBinding
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutEditarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()


        val nombre = intent.getStringExtra("nombre")



        var id: Int = 0
        if (nombre != null) {
            var valores = arrayListOf<Cliente>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    valores =
                        db.clienteDAO().buscarClientesPorNombre(nombre) as ArrayList<Cliente>
                    withContext(Dispatchers.Main) {
                        for (cliente in valores) {
                            id = cliente.referencia
                            binding.campoNombreClienteEditar.setText(cliente.nombre)
                            binding.campoEmailEditar.setText(cliente.email)
                            binding.campoPrecioNifEditar.setText(cliente.nif)
                            binding.campoTelefonoEditarCliente.setText(cliente.telefono.toString())
                            binding.campoDireccionEditarCliente.setText(cliente.direccion)
                            binding.campoLocalidadClienteEditar.setText(cliente.localidad)
                            binding.campoProvinciaClienteEditar.setText(cliente.provincia)
                            binding.campoCodigoPostalClienteEditar.setText(cliente.codigoPostal.toString())
                        }
                    }
                }
            }
        }

        binding.botonTerminadoAAdiendoClienteEditar.setOnClickListener {
            val cliente = Cliente(
                nombre = binding.campoNombreClienteEditar.text.toString(),
                email =  binding.campoEmailEditar.text.toString(),
                nif = binding.campoPrecioNifEditar.text.toString(),
                telefono= binding.campoTelefonoEditarCliente.text.toString().toInt(),
                direccion =  binding.campoDireccionEditarCliente.text.toString(),
                localidad = binding.campoLocalidadClienteEditar.text.toString(),
                provincia = binding.campoProvinciaClienteEditar.text.toString(),
                codigoPostal= binding.campoCodigoPostalClienteEditar.text.toString().toInt()
            )

            CoroutineScope(Dispatchers.IO).launch {
                db.clienteDAO().updateCliente(id,cliente.nombre.toString(),cliente.email.toString(),cliente.nif.toString(),
                cliente.telefono.toString().toInt(),cliente.direccion.toString(),cliente.localidad.toString(),
                cliente.provincia.toString(),cliente.codigoPostal.toString().toInt())

                withContext(Dispatchers.Main) {

                }
            }
            val intent: Intent = Intent(
                this, ActividadCliente::class.java
            )
            this.startActivity(intent)
        }
    }
}