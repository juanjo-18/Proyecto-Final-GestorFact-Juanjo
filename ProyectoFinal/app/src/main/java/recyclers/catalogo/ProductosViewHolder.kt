package recyclers.catalogo

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

/**
 * clase  ProductosViewHolder extiende de RecyclerView.ViewHolder y se utiliza para
 * representar cada elemento de la lista de Producto en una vista de recyclerview.
 * @author Juanjo Medina
 */
class ProductosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // TextView para mostrar el nombre del producto en el catálogo
    val nombre: TextView by lazy{ view.findViewById<TextView>(R.id.textoCatalogo)}

    // TextView para mostrar el precio del producto en el catálogo
    val precio: TextView by lazy{ view.findViewById<TextView>(R.id.textoPrecio)}

    // TextView para mostrar la cantidad disponible del producto en el catálogo
    val cantidad: TextView by lazy{ view.findViewById<TextView>(R.id.textoStock)}

    // Botón para editar el producto en el catálogo
    val botonEditar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEditarCatalogo) }

    // Botón para borrar el producto del catálogo
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonEliminarCatalogo) }
 }