package recyclers.albaranes

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import org.w3c.dom.Text

class AlbaranesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nombreCliente: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreClienteVenta)}
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarVenta) }
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarVenta) }
    val titulo: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaVenta)}
    val fecha:TextView by lazy { view.findViewById<TextView>(R.id.textoFechaVenta) }
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecioVenta)}
    val estado: TextView by lazy{ view.findViewById<TextView>(R.id.textoPendienteVenta)}
    val crearFactura: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonCrearFactura)}



}