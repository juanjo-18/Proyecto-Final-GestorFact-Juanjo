package com.example.proyectofinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import clases.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dataBase.AppDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recyclers.albaranes.AlbaranesAdapter
import recyclers.catalogo.ProductosAdapter

class ActividadVenta : AppCompatActivity() {
    private lateinit var db: AppDataBase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_venta)
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var valores = arrayListOf<Albaran>()
        val context = this

        GlobalScope.launch {
            valores = db.albaranDAO().getAll() as ArrayList<Albaran>
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.reciclerVenta)
            recyclerView.adapter = AlbaranesAdapter( context, valores)
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

        val botonIrAnadirVenta: FloatingActionButton = findViewById(R.id.botonAnadirVenta)
        botonIrAnadirVenta.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadAnadirVenta::class.java
            )
            this.startActivity(intent)
        }

        val botonIrAInicio: ImageButton = findViewById<ImageButton>(R.id.botonIrAInicioDesdeVenta)
        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAClientesDesdeVenta)
        val botonIrACatalogo: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACatalogoDesdeVenta)
        val botonIrAFacturacion: ImageButton =
            findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeVenta)


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

        botonIrACatalogo.setOnClickListener {
            val intent: Intent = Intent(
                this, ActividadCatalogo::class.java
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
}