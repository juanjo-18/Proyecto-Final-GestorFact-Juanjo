package recyclers.compras

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class ComprasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del cliente
    val nombreProveedor: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreClienteFactura)}

    // Botón para editar la compra
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarFactura) }

    // Botón para borrar la compra
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarFactura) }

    // TextView para mostrar el título de la compra
    val titulo: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaFactura)}

    // TextView para mostrar la fecha de la compra
    val fecha: TextView by lazy { view.findViewById<TextView>(R.id.textoFechaFactura) }

    // TextView para mostrar el precio total de la compra
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecioFactura)}

    // TextView para indicar si la compra ha sido pagada o no
    val pagada: TextView by lazy{ view.findViewById<TextView>(R.id.textoPendienteFactura)}
    // Botón para descargar un pdf a partir de la compra
    val crearPDF: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonDescargarPDF)}
}