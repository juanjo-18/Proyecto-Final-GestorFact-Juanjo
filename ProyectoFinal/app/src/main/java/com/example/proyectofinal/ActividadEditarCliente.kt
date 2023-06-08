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
import kotlinx.coroutines.*

/**
 * Esta es la clase que representa la actividad para editar un cliente donde podremos cambiar los valores del cliente
 * y despues cuando este modificado actualizarlo a la base de datos.
 *
 * @author Juanjo Medina
 */
class ActividadEditarCliente : AppCompatActivity() {

    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutEditarClienteBinding

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
        binding = LayoutEditarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()


        val nombre = intent.getStringExtra("nombre")



        //Aqui lo que hago a traves del nombre del cliente cargo los otros datos y los escribo en los campos
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

            // Declarar y asignar valores iniciales para las variables que se utilizarán para comprobar los campos
            var camposVacios: Boolean = false
            var camposNumericosMal: Boolean = false
            var numeroMal: Boolean = false

            // Obtener referencias a los campos de entrada de texto en el layout
            val campos = listOf(
                binding.campoNombreClienteEditar,
                binding.campoEmailEditar,
                binding.campoPrecioNifEditar,
                binding.campoTelefonoEditarCliente,
                binding.campoDireccionEditarCliente,
                binding.campoLocalidadClienteEditar,
                binding.campoProvinciaClienteEditar,
                binding.campoCodigoPostalClienteEditar
            )

            // Comprobar si alguno de los campos está vacío
            for (campo in campos) {
                if (campo.text.toString().isBlank()) {
                    camposVacios = true
                    break
                }
            }

            // Comprobar si alguno de los campos numéricos contiene una coma o un punto, o si no es un número válido
            if (binding.campoTelefonoEditarCliente.text.toString().contains(",") ||
                binding.campoTelefonoEditarCliente.text.toString().contains(".") ||
                binding.campoTelefonoEditarCliente.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }
            if (binding.campoCodigoPostalClienteEditar.text.toString().contains(",") ||
                binding.campoCodigoPostalClienteEditar.text.toString().contains(".") ||
                binding.campoCodigoPostalClienteEditar.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }






            // Si no hay campos vacíos, no hay campos numéricos mal formados y los números son válidos, entonces se guarda el cliente en la base de datos y se muestra la pantalla de clientes
            if (!camposVacios) {
                if (!camposNumericosMal) {
                    // Comprobar si el número de teléfono tiene al menos 4 dígitos y el código postal es positivo
                    if (binding.campoTelefonoEditarCliente.text.toString().toInt() < 1000) {
                        numeroMal = true
                    }
                    if (binding.campoCodigoPostalClienteEditar.text.toString().toInt() < 0) {
                        numeroMal = true
                    }
                    if (!numeroMal) {
                        //Crea un cliente despues de las comprobacions para insertarlo en la base de datoss
                        val cliente = Cliente(
                            nombre = binding.campoNombreClienteEditar.text.toString(),
                            email = binding.campoEmailEditar.text.toString(),
                            nif = binding.campoPrecioNifEditar.text.toString(),
                            telefono = binding.campoTelefonoEditarCliente.text.toString().toInt(),
                            direccion = binding.campoDireccionEditarCliente.text.toString(),
                            localidad = binding.campoLocalidadClienteEditar.text.toString(),
                            provincia = binding.campoProvinciaClienteEditar.text.toString(),
                            codigoPostal = binding.campoCodigoPostalClienteEditar.text.toString().toInt()
                        )
                        // Guardar el cliente en la base de datos
                        CoroutineScope(Dispatchers.IO).launch {
                            db.clienteDAO().updateCliente(
                                id,
                                cliente.nombre.toString(),
                                cliente.email.toString(),
                                cliente.nif.toString(),
                                cliente.telefono.toString().toInt(),
                                cliente.direccion.toString(),
                                cliente.localidad.toString(),
                                cliente.provincia.toString(),
                                cliente.codigoPostal.toString().toInt()
                            )

                            withContext(Dispatchers.Main) {

                            }
                        }

                        // Mostrar la pantalla de clientes
                        val intent: Intent = Intent(
                            this, ActividadCliente::class.java
                        )
                        this.startActivity(intent)

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