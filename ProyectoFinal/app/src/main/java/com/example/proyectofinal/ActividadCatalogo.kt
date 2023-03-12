package com.example.proyectofinal

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.ItemSpacingDecoration
import clases.Producto
import clases.random
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.*
import recyclers.catalogo.ProductosAdapter

class ActividadCatalogo : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogo)
        db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()



        var valores= arrayListOf<Producto>()
        val context = this
        GlobalScope.launch {
             valores = db.productoDAO().getAll() as ArrayList<Producto>
                val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerCatalogo)
            recyclerView.adapter = ProductosAdapter(context, valores)
            val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
            staggeredManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.layoutManager = staggeredManager

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            val itemSpacingDecoration = ItemSpacingDecoration(spacingInPixels)
            recyclerView.addItemDecoration(itemSpacingDecoration)




        }





        val botonIrAnadirProducto: FloatingActionButton = findViewById(R.id.botonAnadirProducto)
        botonIrAnadirProducto.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadAnadirProducto::class.java
            )
            this.startActivity(intent)
        }


        val botonIrAInicio: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAInicioDesdeCatalogo)
        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAClientesDesdeCatalogo)
        val botonIrAVenta: ImageButton = findViewById<ImageButton>(R.id.botonIrAVentaDesdeCatalogo)
        val botonIrAFacturacion: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeCatalogo)


        botonIrAInicio.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadInicio::class.java
            )
            this.startActivity(intent)
        }

        botonIrACliente.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        botonIrAVenta.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadVenta::class.java
            )
            this.startActivity(intent)
        }

        botonIrAFacturacion.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }




    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }
}