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

/***
 * Esta es la clase que representa la actividad para añadir un nuevo cliente.
 * Esta clase se encarga de poder rellenar todos los campos de un cliente y comprobar que los datos esten correctos.
 * Ademas de guardar el cliente en la base de datos.
 * @author Juanjo Medina
 */
class ActividadAnadirCliente : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase

    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutAnadirClienteBinding


    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutAnadirClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        // Obtener la referencia al botón de ImageButton en el layout
        val botonIrACliente: ImageButton = findViewById(R.id.botonTerminadoAñadiendoCliente)

        // Asignar un listener para el evento onClick del botón
        botonIrACliente.setOnClickListener {

            // Declarar y asignar valores iniciales para las variables que se utilizarán para comprobar los campos
            var camposVacios: Boolean = false
            var camposNumericosMal: Boolean = false
            var numeroMal: Boolean = false

            // Obtener referencias a los campos de entrada de texto en el layout
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

            // Comprobar si alguno de los campos está vacío
            for (campo in campos) {
                if (campo.text.toString().isBlank()) {
                    camposVacios = true
                    break
                }
            }

            // Comprobar si alguno de los campos numéricos contiene una coma o un punto, o si no es un número válido
            if (binding.campoTelefonoAAdirCliente.text.toString().contains(",") ||
                binding.campoTelefonoAAdirCliente.text.toString().contains(".") ||
                binding.campoTelefonoAAdirCliente.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }
            if (binding.campoCodigoPostalCliente.text.toString().contains(",") ||
                binding.campoCodigoPostalCliente.text.toString().contains(".") ||
                binding.campoCodigoPostalCliente.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }



            // Si no hay campos vacíos, no hay campos numéricos mal formados y los números son válidos, entonces se guarda el cliente en la base de datos y se muestra la pantalla de clientes
            if (!camposVacios) {
                if (!camposNumericosMal) {
                    // Comprobar si el número de teléfono tiene al menos 4 dígitos y el código postal es positivo
                    if (binding.campoTelefonoAAdirCliente.text.toString().toInt() < 1000) {
                        numeroMal = true
                    }
                    if (binding.campoCodigoPostalCliente.text.toString().toInt() < 0) {
                        numeroMal = true
                    }
                    if (!numeroMal) {
                        if (binding.campoEmailAAndir.text.toString().endsWith("@gmail.com") || binding.campoEmailAAndir.text.toString().endsWith("@hotmail.com") || binding.campoEmailAAndir.text.toString().endsWith("@hotmail.es")) {
                            // Guardar el cliente en la base de datos
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

                        // Mostrar la pantalla de clientes
                        val intent: Intent = Intent(
                            this, ActividadCliente::class.java
                        )
                        this.startActivity(intent)
                        }else{
                            Toast.makeText(this, "Correo electronico no valido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, R.string.numeroMal, Toast.LENGTH_SHORT).show()
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