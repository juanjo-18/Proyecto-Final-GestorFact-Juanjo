package emergentes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.proyectofinal.R

/**
 * Clase emergente que es una aleta que contiene un titulo y un mensaje
 * @author Juanjo Medina
 */
class Alerta(val titulo:String, val mensaje:String, val textoBoton:String, val contexto: AppCompatActivity,
             val funcionCallback : (() -> Unit)?=null): DialogFragment() {
    private val layoutInflado: LinearLayout by lazy{
        contexto.layoutInflater.inflate(R.layout.layout_alerta,null,false) as LinearLayout
    }
    private val textoTitulo: TextView by lazy{ layoutInflado.findViewById<TextView>(R.id.tituloDialogo) }
    private val textoMensaje: TextView by lazy{ layoutInflado.findViewById<TextView>(R.id.mensajeDialogo)}
    val boton: Button by lazy{ layoutInflado.findViewById<Button>(R.id.botonDialogo)}
    private lateinit var emergente: Dialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder: AlertDialog.Builder= AlertDialog.Builder(contexto)
        textoTitulo.setText(titulo)
        textoMensaje.setText(mensaje)
        boton.setText(textoBoton)
        dialogBuilder.setView(layoutInflado)
        this.emergente=dialogBuilder.create()
        boton.setOnClickListener {
            if(funcionCallback!=null){
                funcionCallback.invoke()
            }
            emergente.dismiss()
        }
        return emergente

    }

    fun mostrar():Unit{
        val fManager: FragmentManager =contexto.supportFragmentManager
        this.show(fManager,"alerta"+titulo)
    }





}