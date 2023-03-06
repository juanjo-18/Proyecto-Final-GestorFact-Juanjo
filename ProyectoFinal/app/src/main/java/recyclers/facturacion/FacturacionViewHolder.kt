package recyclers.facturacion

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class FacturacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nombreCliente: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreClienteFactura)}
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarFactura) }
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarFactura) }
    val titulo: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaFactura)}
    val fecha: TextView by lazy { view.findViewById<TextView>(R.id.textoFechaFactura) }
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecioFactura)}
    val cobrada: TextView by lazy{ view.findViewById<TextView>(R.id.textoPendienteFactura)}
}