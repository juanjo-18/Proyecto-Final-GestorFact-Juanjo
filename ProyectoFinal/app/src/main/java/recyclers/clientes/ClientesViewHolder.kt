package recyclers.clientes

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R


/**
 * clase  ClientesViewHolder extiende de RecyclerView.ViewHolder y se utiliza para
 * representar cada elemento de la lista de clientes en una vista de recyclerview.
 * @author Juanjo Medina
 */
class ClientesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del cliente
    val nombreCliente: TextView by lazy { view.findViewById<TextView>(R.id.textoNombreCliente) }

    // Botón para editar el cliente
    val botonEditar: ImageButton by lazy { view.findViewById<ImageButton>(R.id.botonEditarCliente) }

    // Botón para borrar el cliente
    val botonBorrar: ImageButton by lazy { view.findViewById<ImageButton>(R.id.botonBorrarCliente) }
}