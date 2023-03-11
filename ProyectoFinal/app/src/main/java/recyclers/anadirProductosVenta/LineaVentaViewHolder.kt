package recyclers.anadirProductosVenta

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import clases.Producto
import com.example.proyectofinal.R
import com.google.android.material.textfield.TextInputEditText

class LineaVentaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val campoNombre: EditText by lazy { itemView.findViewById(R.id.campoNombreAnadirLineaVenta) }
    val campoPrecio: EditText by lazy { itemView.findViewById(R.id.campoPrecioAnadirLineaVenta) }
    val campoCantidad: EditText by lazy { itemView.findViewById(R.id.campoCantidadAnadirLineaVenta) }
    val campoTotal: TextView by lazy { itemView.findViewById(R.id.campoTotalLineaAnadirVentaLinea) }
    val botonBorrar: ImageButton =itemView.findViewById<ImageButton>(R.id.botonBorrarAnadirVenta)
/*
    init {
        campoCantidad.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    (itemView.context as? Activity)?.let {
                        (it.findViewById<View>(android.R.id.content))?.let { contentView ->
                            val adapter =
                                (contentView.findViewById<RecyclerView>(R.id.recyclerLineasProductos).adapter as? LineaVentaAdapter)
                            adapter?.getEscrito()?.set(adapterPosition, s.toString())
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }*/
    fun eliminarProducto(){
        campoCantidad.setText("")
        campoNombre.setText("")
        campoPrecio.setText("")
        campoTotal.text="0"
    }

    fun bindProducto(position: Int) {
        var cantidadLlena = false
        var precioLleno = false
        campoCantidad.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (cantidadLlena && precioLleno) {
                    val cantidad = campoCantidad.text.toString().toInt()
                    val precio = campoPrecio.text.toString().toInt()
                    campoTotal.text = (cantidad * precio).toString()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                cantidadLlena = s.toString().isNotEmpty()
            }
        })

        campoPrecio.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (cantidadLlena && precioLleno) {
                    val cantidad =campoCantidad.text.toString().toInt()
                    val precio = campoPrecio.text.toString().toInt()
                    campoTotal.text = (cantidad * precio).toString()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                precioLleno = s.toString().isNotEmpty()
            }
        })


    }
}