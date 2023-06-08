package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import clases.Usuario
import com.example.proyectofinal.databinding.LayoutInicioBinding
import com.example.proyectofinal.databinding.LayoutLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import emergentes.Alerta
import java.time.LocalDate

/**
 * Esta es la clase que representa la actividad para iniciar sesion el usuario donde te pedira el correo electronico
 * y el usuario, despues de iniciar sesion entraras en la pantalla de incio.
 *
 * @author Juanjo Medina
 */
class ActividadPrincipal : AppCompatActivity() {

    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutLoginBinding

    private lateinit var auth: FirebaseAuth
    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrAInico: Button =findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrARegistro: Button =findViewById<Button>(R.id.botonRegistrarte)

        //Boton que se encarga de iniciar sesion comprueba los datos con fire base
        botonIrAInico.setOnClickListener {
            if(binding.campoCorreo.text.isNotBlank() && binding.campoContrasena.text.isNotBlank()) {
                auth= FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(
                    binding.campoCorreo.text.toString(),
                    binding.campoContrasena.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            FirebaseFirestore.getInstance().collection("usuarios")
                                .whereEqualTo("email", binding.campoCorreo.text.toString()).get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        var usuarioLogado = Usuario(
                                            binding.campoContrasena.text.toString(),
                                            "" + document.getString("email"),
                                            LocalDate.now(),
                                        )
                                        val alerta: Alerta =
                                            Alerta(this.resources.getString(R.string.nuestraApp),
                                                "Bienvenido " + usuarioLogado!!.email,
                                                this.resources.getString(R.string.ok), this, {

                                                    val intent: Intent = Intent(
                                                        this, ActividadInicio::class.java
                                                    )
                                                    this.startActivity(intent)
                                                })
                                        alerta.mostrar()


                                    }

                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                        } else {
                            it.exception?.printStackTrace()
                            Toast.makeText(
                                this,
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else{
                Toast.makeText(this,R.string.camposVacios,Toast.LENGTH_SHORT).show()
            }
        }


        //Boton que cambia la panatalla registro
        botonIrARegistro.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadRegistro::class.java
            )
            this.startActivity(intent)
        }

    }
}