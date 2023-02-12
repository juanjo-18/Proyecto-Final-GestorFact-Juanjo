package com.example.proyectofinal

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.Producto
import recyclers.catalogo.ProductosAdapter

class ActividadCatalogo : AppCompatActivity(),SearchView.OnQueryTextListener  {
    /*val textoBuscar:SearchView=findViewById(R.id.buscadorDeProductos)*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogo)



        val valores = arrayListOf<Producto>()
        for (i in 100 downTo 1) {
            var producto: Producto = Producto()
            valores.add(producto)
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.reciclerCatalogo)
        recyclerView.adapter= ProductosAdapter(this,valores)
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager

        val botonIrAInicio: ImageButton =findViewById<ImageButton>(R.id.botonIrAInicioDesdeCatalogo)
        val botonIrACliente: ImageButton =findViewById<ImageButton>(R.id.botonIrAClientesDesdeCatalogo)

        botonIrAInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)

        }

        botonIrACliente.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)

        }

        /*
        textoBuscar.setOnQueryTextListener(this)

         */

    }



    override fun onQueryTextSubmit(query: String?): Boolean {
     return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }
}