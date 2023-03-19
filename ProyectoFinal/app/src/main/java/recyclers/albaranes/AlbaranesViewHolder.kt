package recyclers.albaranes

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import org.w3c.dom.Text

/**
 * una clase AlbaranesViewHolder que extiende de RecyclerView.ViewHolder y se utiliza para
 * representar cada elemento de la lista de albaranes en una vista de recyclerview.
 * @author Juanjo Medina
 */
class AlbaranesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del cliente
    val nombreCliente: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreClienteVenta)}
    // Botón para editar el albarán
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarVenta) }
    // Botón para borrar el albarán
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarVenta) }
    // TextView para mostrar el título o referencia del albarán
    val titulo: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaVenta)}
    // TextView para mostrar la fecha del albarán
    val fecha:TextView by lazy { view.findViewById<TextView>(R.id.textoFechaVenta) }
    // TextView para mostrar el precio total del albarán
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecioVenta)}
    // TextView para mostrar el estado del albarán (pendiente o completado)
    val estado: TextView by lazy{ view.findViewById<TextView>(R.id.textoPendienteVenta)}
    // Botón para crear una factura a partir del albarán
    val crearFactura: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonCrearFactura)}
}