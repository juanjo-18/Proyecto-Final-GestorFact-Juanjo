package recyclers.albaranes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.TextPaint
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.*
import com.example.proyectofinal.ActividadEditarVenta
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class AlbaranesAdapter(val actividadMadre: Activity, val datos: ArrayList<Albaran>) :
    RecyclerView.Adapter<AlbaranesViewHolder>() {
    private lateinit var db: AppDataBase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbaranesViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return AlbaranesViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_ventas,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbaranesViewHolder, position: Int) {
        val albaran: Albaran = datos.get(position)
        holder.titulo.text=albaran.titulo
        holder.nombreCliente.text=albaran.nombreCliente
        holder.fecha.text= albaran.fecha.toString()
        holder.estado.text=albaran.estado
        holder.precio.text=""+albaran.precioTotal

        holder.crearFactura.setOnClickListener{
            var albaran_producto = arrayListOf<Albaran_Producto>()
            var productos = arrayListOf<Producto>()

            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    albaran_producto = db.albaran_ProductoDAO()
                        .buscarAlbaranProductoPorTitulo(holder.titulo.text.toString()) as ArrayList<Albaran_Producto>
                    var cliente= db.clienteDAO().buscarClientePorNombre(albaran.nombreCliente.toString())
                    withContext(Dispatchers.Main) {
                        for (albaranes in albaran_producto) {
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
                        }
                        if (cliente != null) {
                            checkStoragePermissions(actividadMadre)
                            var personalizado=CrearPDF()
                            if(checkPermission()) {
                                Toast.makeText(actividadMadre,"Ha entrado en pdf",Toast.LENGTH_SHORT).show()

                                personalizado.generarPdf(
                                        actividadMadre.resources,
                                        actividadMadre,
                                        albaran,
                                        productos,
                                        cliente
                                    )

                            } else {
                                requestPermissions()
                            }
                         }else{
                            Toast.makeText(actividadMadre,"Cliente: "+albaran.nombreCliente,Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }


        }
        holder.botonEditar.setOnClickListener{
            val intent: Intent = Intent(actividadMadre, ActividadEditarVenta::class.java)
            intent.putExtra("titulo", albaran.titulo)
            actividadMadre.startActivity(intent)
        }


        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    db.albaranDAO().delete(albaran)
                }
            }

            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return datos.size
    }


    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(actividadMadre,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ContextCompat.checkSelfPermission(actividadMadre,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            actividadMadre,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            200
        )
    }
    private fun checkStoragePermissions(activity: Activity) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                200
            )
        }
    }



}