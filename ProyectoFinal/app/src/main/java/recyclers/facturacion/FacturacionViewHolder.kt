package recyclers.facturacion

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

/**
 * clase  FacturacionViewHolder extiende de RecyclerView.ViewHolder y se utiliza para
 * representar cada elemento de la lista de facturas en una vista de recyclerview.
 * @author Juanjo Medina
 */
class FacturacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del cliente
    val nombreCliente: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreClienteFactura)}

    // Botón para editar la factura
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarFactura) }

    // Botón para borrar la factura
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarFactura) }

    // TextView para mostrar el título de la factura
    val titulo: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaFactura)}

    // TextView para mostrar la fecha de la factura
    val fecha: TextView by lazy { view.findViewById<TextView>(R.id.textoFechaFactura) }

    // TextView para mostrar el precio total de la factura
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecioFactura)}

    // TextView para indicar si la factura ha sido cobrada o no
    val cobrada: TextView by lazy{ view.findViewById<TextView>(R.id.textoPendienteFactura)}
    // Botón para crear una factura a partir de la factura
    val crearPDF: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonDescargarPDF)}
}