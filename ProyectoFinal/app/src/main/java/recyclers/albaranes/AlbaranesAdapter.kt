package recyclers.albaranes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import clases.Albaran
import clases.CrearPDF
import clases.Producto
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import recyclers.catalogo.ProductosViewHolder

class AlbaranesAdapter(val actividadMadre: Activity, val datos: ArrayList<Albaran>) :
    RecyclerView.Adapter<AlbaranesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbaranesViewHolder {
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

            if(checkPermission()) {

            } else {
                requestPermissions()
            }
            var personalizado=CrearPDF()
            personalizado.generarPdf(actividadMadre.resources,actividadMadre)

        }


        holder.botonBorrar.setOnClickListener {
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

}