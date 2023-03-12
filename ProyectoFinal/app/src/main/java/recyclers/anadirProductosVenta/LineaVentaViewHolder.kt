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
    val campoNombre: TextView by lazy { itemView.findViewById(R.id.textoNombreAnadirLineaVenta) }
    val campoPrecio: TextView by lazy { itemView.findViewById(R.id.textoPrecioAnadirLineaVenta) }
    val campoCantidad: TextView by lazy { itemView.findViewById(R.id.textoCantidadAnadirLineaVenta) }
    val campoTotal: TextView by lazy { itemView.findViewById(R.id.textoTotalLineaAnadirVentaLinea) }
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


    fun bindProducto(producto: Producto) {
        campoNombre.text=producto.nombre
        campoCantidad.text=producto.cantidad.toString()
        campoPrecio.text=producto.precio.toString()
        campoTotal.text=(producto.precio*producto.cantidad).toString()




    }
}