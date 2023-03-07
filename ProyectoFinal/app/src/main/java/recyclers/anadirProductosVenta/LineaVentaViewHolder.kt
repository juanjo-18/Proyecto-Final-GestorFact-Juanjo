package recyclers.anadirProductosVenta

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LineaVentaViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val contenedorNombre: TextInputLayout = itemView.findViewById(R.id.contenedorCampoNombreAnadirLineaVenta)
    val campoNombre: TextInputEditText = itemView.findViewById(R.id.campoNombreAnadirLineaVenta)
    val contenedorPrecio: TextInputLayout = itemView.findViewById(R.id.contenedorCampoPrecioAnadirLineaVenta)
    val campoPrecio: TextInputEditText = itemView.findViewById(R.id.campoPrecioLineaAnadirVenta)
    val contenedorCantidad: TextInputLayout = itemView.findViewById(R.id.contenedorCampoCantidadAnadirLineaVenta)
    val campoCantidad: TextInputEditText = itemView.findViewById(R.id.campoCantidadAnadirLineaVenta)
    val contenedorTotal: TextInputLayout = itemView.findViewById(R.id.contenedorCampoTotalLineaAnadirLineaVenta)
    val campoTotal: TextInputEditText = itemView.findViewById(R.id.campoTotalAnadirLineaVenta)
    val botonBorrar: ImageButton by lazy{ view.findViewById<ImageButton>(R.id.botonBorrarAnadirVenta) }
}