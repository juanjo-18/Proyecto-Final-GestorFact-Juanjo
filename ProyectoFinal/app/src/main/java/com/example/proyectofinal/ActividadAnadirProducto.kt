package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.room.Room
import clases.Producto
import com.example.proyectofinal.databinding.LayoutAnadirProductoBinding
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActividadAnadirProducto : AppCompatActivity() {
    private lateinit var db: AppDataBase
    private lateinit var binding: LayoutAnadirProductoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LayoutAnadirProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        val botonIrACatalogo:ImageButton=findViewById(R.id.botonTerminadoAñadiendoProducto)
        botonIrACatalogo.setOnClickListener{
            GlobalScope.launch {
                db.productoDAO().insert(Producto(
                    nombre = binding.campoNombreProductoAAdir.text.toString(),
                    precio = binding.campoPrecioProductoAAdir.text.toString().toInt(),
                    cantidad = binding.campoStockAAndir.text.toString().toInt()
                ))
            }
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }
    }
}