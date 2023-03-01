package recyclers.clientes

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class ClientesViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val nombreCliente: TextView by lazy{ view.findViewById<TextView>(R.id.textoNombreCliente)}
    val referencia: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferenciaCliente)}

    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarCliente) }
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarCliente) }
}