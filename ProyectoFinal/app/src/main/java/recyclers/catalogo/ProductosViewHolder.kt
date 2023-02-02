package recyclers.catalogo

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class ProductosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val iconoStock: ImageView by lazy{ view.findViewById<ImageView>(R.id.imagenStock) }
    val iconoEuro: ImageView by lazy{ view.findViewById<ImageView>(R.id.imagenEuro) }
    val nombre: TextView by lazy{ view.findViewById<TextView>(R.id.textoCliente)}
    val referencia: TextView by lazy{ view.findViewById<TextView>(R.id.textoReferencia)}
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecio)}
    val cantidad: TextView by lazy{ view.findViewById<TextView>(R.id.textoStock)}
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditar) }
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEliminar) }
}