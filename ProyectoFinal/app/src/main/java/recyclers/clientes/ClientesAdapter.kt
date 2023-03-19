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

class ClientesAdapter (val actividadMadre: Activity, val datos:ArrayList<Cliente>) : RecyclerView.Adapter<ClientesViewHolder>() {
    private lateinit var db: AppDataBase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return ClientesViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_clientes,parent,false))
    }

    override fun onBindViewHolder(holder: ClientesViewHolder, position: Int) {
        val cliente:Cliente = datos.get(position)
        holder.nombreCliente.text=cliente.nombre


        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    db.clienteDAO().delete(cliente)
                }
            }
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.botonEditar.setOnClickListener {
            val intent: Intent = Intent(actividadMadre, ActividadEditarCliente::class.java)
            intent.putExtra("nombre", cliente.nombre)
            actividadMadre.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}