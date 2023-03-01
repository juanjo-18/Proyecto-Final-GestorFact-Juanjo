package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding

class ActividadEditarProducto : AppCompatActivity() {
    private lateinit var binding: LayoutEditarProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LayoutEditarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle: Bundle? =this.intent.extras
        var producto: Producto?=null

        if(bundle!=null){
            producto=bundle.getParcelable<Producto>("producto")
            if(producto!=null) {
                binding.campoReferenciaProductoEditarEditar.setText(producto.referencia)
                binding.campoNombreProductoEditar.setText(producto.nombre)
                binding.campoStockEditar.setText(producto.cantidad.toString())
                binding.campoPrecioProductoEditar.setText(producto.precio.toString())


            }
        }
        binding.botonTerminadoEdicionProducto.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

    }
}