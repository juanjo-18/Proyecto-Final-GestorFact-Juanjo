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

/**
 * Esta es la clase que representa la actividad para editar un producto aqui es donde apareceran los datos y
 * se podran modificar, una vez modificados se actualizaran a la base de datos los cambios realizados.
 *
 * @author Juanjo Medina
 */
class ActividadEditarProducto : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutEditarProductoBinding

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase

    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutEditarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        val nombre = intent.getStringExtra("nombre")

        //Traigo los datos de el producto seleccionado a traves del nombre
        var id: Int = 0
        if (nombre != null) {
            var valores = arrayListOf<Producto>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    valores =
                        db.productoDAO().buscarProductosPorNombre(nombre) as ArrayList<Producto>
                    withContext(Dispatchers.Main) {
                        for (valor in valores) {
                            id = valor.referencia
                            binding.campoNombreProductoEditar.setText(valor.nombre)
                            binding.campoPrecioProductoEditar.setText(valor.precio.toString())
                            binding.campoStockEditar.setText(valor.cantidad.toString())
                        }
                    }
                }
            }

        }


        /**
         * Boton que al clikarlo comprueba que todos los datos esten correctos y actualiza los cambios en la base de datos
         */
        binding.botonTerminadoEdicionProducto.setOnClickListener {
            var camposVacios: Boolean = false
            var numerosMal: Boolean = false
            var numeroNegativo: Boolean = false

            if (binding.campoNombreProductoEditar.text.toString().isBlank()) {
                camposVacios = true
            }
            if (binding.campoPrecioProductoEditar.text.toString().isBlank()) {
                camposVacios = true
            }
            if (binding.campoStockEditar.text.toString().isBlank()) {
                camposVacios = true
            }
            if (!camposVacios) {
                if (binding.campoPrecioProductoEditar.text.toString().toFloatOrNull() == null) {
                    numerosMal = true
                }
                if (binding.campoStockEditar.text.toString().toIntOrNull() == null) {
                    numerosMal = true
                }
                if (!numerosMal) {
                    if (binding.campoPrecioProductoEditar.text.toString().toFloat() < 0) {
                        numeroNegativo = true
                    }
                    if (binding.campoStockEditar.text.toString().toInt() < 0) {
                        numeroNegativo = true
                    }
                    if (!numeroNegativo) {
                        //Creo un producto con los datos que estan en los campos
                        val producto1 = Producto(
                            nombre = binding.campoNombreProductoEditar.text.toString(),
                            precio = binding.campoPrecioProductoEditar.text.toString().toFloat(),
                            cantidad = binding.campoStockEditar.text.toString().toInt()
                        )
                        //Inserto en la base de datos el producto editado
                        CoroutineScope(Dispatchers.IO).launch {
                            db.productoDAO().actualizarProducto(
                                id,
                                producto1.nombre.toString(),
                                producto1.cantidad,
                                producto1.precio
                            )

                            withContext(Dispatchers.Main) {

                            }
                        }

                        val intent: Intent = Intent(
                            this, ActividadCatalogo::class.java
                        )
                        this.startActivity(intent)
                    } else {
                        Toast.makeText(this, R.string.minimoProducto, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, R.string.contieneTexto, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.camposVacios, Toast.LENGTH_SHORT).show()
            }
        }
    }
}