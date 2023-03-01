package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarClienteBinding
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding

class ActividadEditarCliente : AppCompatActivity() {
    private lateinit var binding: LayoutEditarClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LayoutEditarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? =this.intent.extras
        var cliente: Cliente?=null

        if(bundle!=null){
            cliente=bundle.getParcelable<Cliente>("cliente")
            if(cliente!=null) {
                binding.campoReferenciaClienteEditar.setText(cliente.referencia)
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
        binding.botonTerminadoAAdiendoClienteEditar.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }
    }
}