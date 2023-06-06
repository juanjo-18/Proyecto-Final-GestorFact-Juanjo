package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import clases.Cliente
import clases.DatosUsuario
import com.example.proyectofinal.databinding.LayoutAnadirClienteBinding
import com.example.proyectofinal.databinding.LayoutDatosUsuarioBinding
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActividadDatosUsuario : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var db: AppDataBase

    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutDatosUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutDatosUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        binding.botonGuardar.setOnClickListener{
            // Declarar y asignar valores iniciales para las variables que se utilizarán para comprobar los campos
            var camposVacios: Boolean = false
            var camposNumericosMal: Boolean = false
            var numeroMal: Boolean = false

            // Obtener referencias a los campos de entrada de texto en el layout
            val campos = listOf(
                binding.campoNombreEmpresaDatos,
                binding.campoCorreoDatos,
                binding.campoNifDatos,
                binding.campoTelefonoDatos,
                binding.campoDireccionDatos,
                binding.campoLocalidadDatos,
                binding.campoProvinciaDatos,
                binding.campoCodigoPostalDatos,
                binding.CampoNumeroCuentaDatos
            )

            // Comprobar si alguno de los campos está vacío
            for (campo in campos) {
                if (campo.text.toString().isBlank()) {
                    camposVacios = true
                    break
                }
            }
            // Comprobar si alguno de los campos numéricos contiene una coma o un punto, o si no es un número válido
            if (binding.campoTelefonoDatos.text.toString().contains(",") ||
                binding.campoTelefonoDatos.text.toString().contains(".") ||
                binding.campoTelefonoDatos.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }
            if (binding.campoCodigoPostalDatos.text.toString().contains(",") ||
                binding.campoCodigoPostalDatos.text.toString().contains(".") ||
                binding.campoCodigoPostalDatos.text.toString().toIntOrNull() == null
            ) {
                camposNumericosMal = true
            }
            // Si no hay campos vacíos, no hay campos numéricos mal formados y los números son válidos, entonces se guarda el datosUsuario en la base de datos y se muestra la pantalla de clientes
            if (!camposVacios) {
                if (!camposNumericosMal) {
                    // Comprobar si el número de teléfono tiene al menos 4 dígitos y el código postal es positivo
                    if (binding.campoTelefonoDatos.text.toString().toInt() < 1000) {
                        numeroMal = true
                    }
                    if (binding.campoCodigoPostalDatos.text.toString().toInt() < 0) {
                        numeroMal = true
                    }
                    if (!numeroMal) {
                        // Guardar el usuario en la base de datos
                        GlobalScope.launch {
                            db.datosUsuarioDAO().insert(
                                DatosUsuario(
                                    nombre = binding.campoNombreEmpresaDatos.text.toString(),
                                    email = binding.campoCorreoDatos.text.toString(),
                                    nif = binding.campoNifDatos.text.toString(),
                                    numeroCuenta= binding.CampoNumeroCuentaDatos.text.toString(),
                                    telefono = binding.campoTelefonoDatos.text.toString()
                                        .toInt(),
                                    direccion = binding.campoDireccionDatos.text.toString(),
                                    localidad = binding.campoLocalidadDatos.text.toString(),
                                    provincia = binding.campoProvinciaDatos.text.toString(),
                                    codigoPostal = binding.campoCodigoPostalDatos.text.toString()
                                        .toInt()
                                )
                            )
                        }

                        // Mostrar la pantalla de clientes
                        val intent: Intent = Intent(
                            this, ActividadInicio::class.java
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