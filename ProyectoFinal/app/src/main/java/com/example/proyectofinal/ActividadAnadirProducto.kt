package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
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
            var camposVacios:Boolean=false
            var numerosMal:Boolean=false
            var numeroNegativo:Boolean=false

            if(binding.campoNombreProductoAAdir.text.toString().isBlank()){
                camposVacios=true
            }
            if(binding.campoPrecioProductoAAdir.text.toString().isBlank()){
                camposVacios=true
            }
            if(binding.campoStockAAndir.text.toString().isBlank()){
                camposVacios=true
            }
            if(!camposVacios) {
                if(binding.campoPrecioProductoAAdir.text.toString().toFloatOrNull()==null){
                    numerosMal=true
                }
                if(binding.campoStockAAndir.text.toString().toIntOrNull()==null){
                    numerosMal=true
                }
                if(!numerosMal) {
                    if(binding.campoPrecioProductoAAdir.text.toString().toFloat()<0){
                        numeroNegativo=true
                    }
                    if(binding.campoStockAAndir.text.toString().toInt()<0){
                        numeroNegativo=true
                    }
                    if(!numeroNegativo) {
                        GlobalScope.launch {
                            db.productoDAO().insert(
                                Producto(
                                    nombre = binding.campoNombreProductoAAdir.text.toString(),
                                    precio = binding.campoPrecioProductoAAdir.text.toString()
                                        .toFloat(),
                                    cantidad = binding.campoStockAAndir.text.toString().toInt()
                                )
                            )
                        }
                        val intent: Intent = Intent(
                            this, ActividadCatalogo::class.java
                        )
                        this.startActivity(intent)
                    }else{
                        Toast.makeText(this,R.string.minimoProducto,Toast.LENGTH_SHORT).show()
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