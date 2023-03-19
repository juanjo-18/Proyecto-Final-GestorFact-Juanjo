package recyclers.clientes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.ActividadEditarCliente
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import recyclers.catalogo.ProductosViewHolder

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de clientes.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo cliente que se utilizarán para poblar la vista de la lista
 */
class ClientesAdapter(val actividadMadre: Activity, val datos: ArrayList<Cliente>) :
    RecyclerView.Adapter<ClientesViewHolder>() {
    /*** Adapter para el RecyclerView que muestra la lista de clientes ***/
    private lateinit var db: AppDataBase

    // Cuando se crea un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesViewHolder {

        // Se inicializa la base de datos
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        // Se infla la vista de los elementos del RecyclerView
        return ClientesViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_clientes,
                parent,
                false
            )
        )
    }

    /* Cuando se enlaza un ViewHolder con los datos de un elemento en particular */
    override fun onBindViewHolder(holder: ClientesViewHolder, position: Int) {
        // Se obtiene el cliente actual
        val cliente: Cliente = datos.get(position)
        // Se establecen los datos en la vista del ViewHolder
        holder.nombreCliente.text = cliente.nombre

        // Se agrega un OnClickListener para el botón de borrar
        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    // Se elimina el cliente de la base de datos en una corrutina
                    db.clienteDAO().delete(cliente)
                }
            }
            // Se remueve el cliente de la lista de datos y se notifica al adaptador
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        // Se agrega un OnClickListener para el botón de editar
        holder.botonEditar.setOnClickListener {
            // Se crea un Intent para abrir la actividad de edición de clientes y se le pasa el nombre del cliente
            val intent: Intent = Intent(actividadMadre, ActividadEditarCliente::class.java)
            intent.putExtra("nombre", cliente.nombre)
            actividadMadre.startActivity(intent)
        }
    }

    /* Devuelve la cantidad de elementos en la lista */
    override fun getItemCount(): Int {
        return datos.size
    }
}