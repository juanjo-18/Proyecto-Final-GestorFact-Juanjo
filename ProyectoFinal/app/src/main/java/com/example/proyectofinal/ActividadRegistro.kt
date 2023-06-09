package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import clases.Usuario
import com.example.proyectofinal.databinding.LayoutRegistroBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import emergentes.Alerta
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.util.*

/**
 *Esta es la clase que representa la actividad para registrarse teniendo que poner todos tus datos, despues se te
 * registrara en la base de datos una vez que rellenes correctamente todos los campos.
 *
 * @author Juanjo Medina
 */
class ActividadRegistro : AppCompatActivity() {


    /**
     * Variable para el binding del layout.
     */
    private lateinit var binding: LayoutRegistroBinding

    /**
     * Variable que se utiliza para solicitar permisos de cámara. Es de tipo Int y su valor es 5432345.
     */
    private val PERMISO_CAMARA: Int = 5432345

    /**
     * Variable que utiliza el método registerForActivityResult() de la clase ActivityResultContracts para
     * lanzar un selector de imagen y obtener la imagen seleccionada. Después de seleccionar una imagen,
     * la imagen se establece en binding.imagenPerfilUsuario.
     */
    val lanzadorElegirImagen = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imagenPerfilUsuario.setImageURI(it);
    }

    /**
     * Variable para la instancia de la base de datos.
     */
    private val db = FirebaseFirestore.getInstance()


    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonIrAIniciar: Button = findViewById<Button>(R.id.botonIniciarSesion)
        val botonIrAInicio: Button = findViewById<Button>(R.id.botonRegistrarte)

        //Boton que cambia la panatalla inicio
        botonIrAIniciar.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadPrincipal::class.java
            )
            this.startActivity(intent)
        }

        //Boton que te registra en firebase y comprueba que esten todos los valores bien rellenos
        botonIrAInicio.setOnClickListener {
            var camposVacios: Boolean = false;

            if (binding.campoCorreo.text.toString().isBlank()) {
                camposVacios = true;
            }
            if (binding.campoContrasena.text.toString().isBlank()) {
                camposVacios = true;
            }
            if (!camposVacios) {
                //Hago el registro
                val email: String = binding.campoCorreo.text.toString()
                val contraseña: String = binding.campoContrasena.text.toString()


                try {
                    val fechaNacimiento: LocalDate = LocalDate.parse(binding.textoFecha.text)

                    val preferencias: SharedPreferences = getSharedPreferences(
                        "preferenciasPersonalizadas", Context.MODE_PRIVATE
                    )

                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(email, contraseña)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                var usuarioLogado = Usuario(
                                    contraseña,
                                    email,
                                    fechaNacimiento,

                                    )
                                val date = Date.from(
                                    usuarioLogado!!.fechaNacimiento!!.atStartOfDay(ZoneId.systemDefault())
                                        .toInstant()
                                )
                                db.collection("usuarios")
                                    .document(binding.campoCorreo.text.toString()).set(
                                        hashMapOf(
                                            "email" to usuarioLogado!!.email,
                                            "fechaNacimiento" to Timestamp(date),
                                            "contraseña" to usuarioLogado!!.contraseña
                                        )
                                    )

                                Toast.makeText(
                                    this,
                                    R.string.registradoConExito,
                                    Toast.LENGTH_LONG
                                ).show()
                                Toast.makeText(
                                    this,
                                    "Bienvenido " + usuarioLogado.email,
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent: Intent = Intent(
                                    this, ActividadDatosUsuario::class.java
                                )
                                this.startActivity(intent)
                            } else {
                                it.exception?.printStackTrace()
                                Toast.makeText(
                                    this,
                                    it.exception.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }


                    //Aquí todos los campos están bien rellenos, podemos registrar (y guardar imagen)
                    val imagen: Bitmap? =
                        (binding.imagenPerfilUsuario.drawable as BitmapDrawable).bitmap
                    var escritor: FileOutputStream =
                        openFileOutput(email + ".jpg", MODE_PRIVATE)
                    imagen?.compress(
                        Bitmap.CompressFormat.JPEG,
                        100,
                        escritor
                    );
                    escritor.flush(); // Not really required
                    escritor.close();


                } catch (e: DateTimeParseException) {
                    Toast.makeText(this, R.string.fechaValida, Toast.LENGTH_LONG)
                        .show()
                }
            }


        }

        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->

                val hoy: LocalDate = LocalDate.now()
                val fechaElegida: LocalDate = LocalDate.of(year, month, day);
                if (!fechaElegida.isBefore(hoy.minusYears(18))) {
                    Toast.makeText(this, R.string.tienesQueSerMayorEdad, Toast.LENGTH_LONG).show()
                } else {
                    binding.textoFecha.text = fechaElegida.toString()
                }
            }

        //Boton para que salga un calendario y poder cambiar la fecha
        binding.botonCambiarFecha.setOnClickListener {
            val calendario: Calendar = Calendar.getInstance()
            val datePicker: DatePickerDialog =
                DatePickerDialog(
                    this, dateSetListener,
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                )
            datePicker.setIcon(R.drawable.caja)
            datePicker.setMessage(this.resources.getString(R.string.fechaNacimiento))
            datePicker.show()
        }

        //Boton que se encarga de acceder a tu galeria para poder añadir una foto
        binding.botonCambiarFoto.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                cogeImagen()
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(
                        this,
                        R.string.noPermisoCamara,
                        Toast.LENGTH_LONG
                    ).show()
                }
                ActivityCompat.requestPermissions(
                    this,
                    Array<String>(1) { android.Manifest.permission.CAMERA },
                    this.PERMISO_CAMARA
                )

            }
        }


    }

    //Esta funcion la llamo para dar los permisos a la aplicacion
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.PERMISO_CAMARA && grantResults.size >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cogeImagen()
        } else {
            Toast.makeText(
                this,
                R.string.noPermisoCamara,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun cogeImagen(): Unit {
        lanzadorElegirImagen.launch("image/*")
    }
}