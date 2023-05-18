package recyclers.anadirProductosFactura

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import clases.Producto
import com.example.proyectofinal.R

class LineaFacturaViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del producto en la línea de factura
    val campoNombre: TextView by lazy { itemView.findViewById(R.id.textoNombreAnadirLineaVenta) }

    // TextView para mostrar el precio unitario del producto en la línea de factura
    val campoPrecio: TextView by lazy { itemView.findViewById(R.id.textoPrecioAnadirLineaVenta) }

    // TextView para mostrar la cantidad del producto en la línea de factura
    val campoCantidad: TextView by lazy { itemView.findViewById(R.id.textoCantidadAnadirLineaVenta) }

    // TextView para mostrar el total de la línea de venta (precio unitario x cantidad)
    val campoTotal: TextView by lazy { itemView.findViewById(R.id.textoTotalLineaAnadirVentaLinea) }

    // Botón para borrar la línea de factura
    val botonBorrar: ImageButton = itemView.findViewById<ImageButton>(R.id.botonBorrarAnadirVenta)
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