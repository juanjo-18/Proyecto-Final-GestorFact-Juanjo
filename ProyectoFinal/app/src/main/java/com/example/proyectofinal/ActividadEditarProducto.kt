package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import clases.Factura
import clases.Producto
import com.example.proyectofinal.databinding.LayoutEditarProductoBinding
import dataBase.AppDataBase
import kotlinx.coroutines.*

class ActividadEditarProducto : AppCompatActivity() {
    private lateinit var binding: LayoutEditarProductoBinding
    private lateinit var db: AppDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutEditarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        val nombre = intent.getStringExtra("nombre")

        val producto = Producto()

        var id:Int=0
        if (nombre != null) {
            var valores = arrayListOf<Producto>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    valores =
                        db.productoDAO().buscarProductosPorNombre(nombre) as ArrayList<Producto>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {
                            id=valor.referencia
                            binding.campoNombreProductoEditar.setText(valor.nombre)
                            binding.campoPrecioProductoEditar.setText(valor.precio.toString())
                            binding.campoStockEditar.setText(valor.cantidad.toString())
                        }
                    }
                }
            }

        }


        binding.botonTerminadoEdicionProducto.setOnClickListener {
            val producto1 = Producto(
                nombre = binding.campoNombreProductoEditar.text.toString(),
                precio = binding.campoPrecioProductoEditar.text.toString().toFloat(),
                cantidad = binding.campoStockEditar.text.toString().toInt()
            )

            val contexto=this

            CoroutineScope(Dispatchers.IO).launch {
                db.productoDAO().actualizarProducto(id,producto1.nombre.toString(),producto1.cantidad,producto1.precio)

                withContext(Dispatchers.Main) {

                }
            }

            val intent: Intent = Intent(
                this, ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

    }
}