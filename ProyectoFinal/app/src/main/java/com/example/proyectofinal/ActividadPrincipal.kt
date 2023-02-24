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
import com.example.proyectofinal.databinding.LayoutPrimeraBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import emergentes.Alerta
import java.time.LocalDate


class ActividadPrincipal : AppCompatActivity() {
    private lateinit var binding: LayoutPrimeraBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutPrimeraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrAInico: Button =findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrARegistro: Button =findViewById<Button>(R.id.botonRegistrarte)

        botonIrAInico.setOnClickListener {
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
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
                                    val alerta: Alerta = Alerta(this.resources.getString(R.string.nuestraApp),
                                        "Bienvenido "+usuarioLogado!!.email,
                                        this.resources.getString(R.string.ok), this, {

                                            val intent: Intent = Intent(
                                                this,ActividadInicio::class.java
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
        }


        botonIrARegistro.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadRegistro::class.java
            )
            this.startActivity(intent)
        }

    }
}