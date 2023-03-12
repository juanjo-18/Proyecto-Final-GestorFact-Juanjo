package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.Cliente
import clases.ItemSpacingDecoration
import clases.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.catalogo.ProductosAdapter
import recyclers.clientes.ClientesAdapter

class ActividadCliente : AppCompatActivity() {
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_cliente)
        db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()

        var valores = arrayListOf<Cliente>()
        val context = this
        GlobalScope.launch {
            valores = db.clienteDAO().getAll() as ArrayList<Cliente>
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerCliente)
            recyclerView.adapter = ClientesAdapter(context, valores)
            val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
            staggeredManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.layoutManager = staggeredManager

            //ajustamos el recycler a la pantalla
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
            recyclerView.addItemDecoration(itemSpacingDecoration)
        }



        val botonIrAnadirCliente: FloatingActionButton =findViewById(R.id.botonAnadirCliente)
        botonIrAnadirCliente.setOnClickListener{
            val intent:Intent=Intent(
                this,ActividadAnadirCliente::class.java
            )
            this.startActivity(intent)
        }

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicioDesdeCliente)
        val botonIrACatalogo: ImageButton =findViewById<ImageButton>(R.id.botonIrACatalogoDesdeCliente)
        val botonIrAVenta: ImageButton =findViewById<ImageButton>(R.id.botonIrAVentaDedeCliente)
        val botonIrAFacturacion: ImageButton =findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeCliente)


        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }
        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }
    }
}