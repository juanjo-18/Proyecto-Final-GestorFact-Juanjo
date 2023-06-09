package recyclers.facturacion

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.*
import com.example.proyectofinal.ActividadEditarFactura
import com.example.proyectofinal.ActividadEditarVenta
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de factura.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo factura que se utilizarán para poblar la vista de la lista
 */
class FacturacionAdapter (val actividadMadre: Activity, var datos: ArrayList<Factura>):
    RecyclerView.Adapter<FacturacionViewHolder>() {
    private lateinit var db: AppDataBase

    /***
     * Este método crea una nueva instancia de la vista de elemento del adaptador
     * y devuelve un nuevo objeto FacturacionViewHolder que mantiene la referencia
     * de cada vista de elemento del adaptador.
     ***/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturacionViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return FacturacionViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_facturacion,
                parent,
                false
            )
        )
    }

    /***
     * Este método establece el contenido de cada elemento del RecyclerView.
     * Recibe como parámetros el objeto FacturacionViewHolder y la posición actual.
     * El contenido se establece utilizando los datos de la lista de facturas que se
     * pasó al constructor del adaptador.
     ***/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FacturacionViewHolder, position: Int) {
        val factura: Factura = datos.get(position)
        holder.titulo.text=factura.titulo
        holder.nombreCliente.text=factura.nombreCliente
        holder.fecha.text= factura.fecha.toString()
        if(factura.cobrada==true){
            holder.cobrada.text="Cobrada"
            val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto1)
            holder.cobrada.background = backgroundDrawable
        }else{
            holder.cobrada.text="Pendiente"
            val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto)
            holder.cobrada.background = backgroundDrawable
        }
        holder.precio.text=""+factura.precioTotal

        /***
         * Este listener se activa cuando se hace clic en el botón de borrar.
         * Elimina el elemento correspondiente de la lista de facturas y actualiza el RecyclerView.
         ***/
        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    // Se elimina la factura de la base de datos utilizando la función 'delete' de la clase 'FacturaDAO'
                    db.factura_ProductoDAO().borrarTodosPorTitulo(factura.titulo)
                    db.facturaDAO().delete(factura)
                }
            }
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }
        holder.botonEditar.setOnClickListener{
            // Se crea un intent para iniciar la actividad de editar factura, y se le pasan algunos datos
            val intent: Intent = Intent(actividadMadre, ActividadEditarFactura::class.java)
            intent.putExtra("titulo", factura.titulo)
            actividadMadre.startActivity(intent)
        }

        // Agrega un listener al botón "crear factura"
        holder.crearPDF.setOnClickListener{

            // Crea y lanza un hilo en segundo plano para obtener los datos de la factura
            var factura_producto = arrayListOf<Factura_Producto>()
            var facturaBusqueda= arrayListOf<Factura>()
            var productos = arrayListOf<Producto>()
            var tipo: String=""
            var total: Float=0f
            var numero:String=""
            var fecha: LocalDate= LocalDate.now()
            var referencia:Int=0
            var datosUsuario:DatosUsuario
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //Obtengo la factura
                    facturaBusqueda = db.facturaDAO().buscarFacturaPorTitulo(holder.titulo.text.toString()) as ArrayList<Factura>
                    // Obtiene los datos de la tabla de Factura_Producto de la base de datos para el título correspondiente
                    factura_producto = db.factura_ProductoDAO()
                        .buscarFacturaProductoPorTitulo(holder.titulo.text.toString()) as ArrayList<Factura_Producto>
                    datosUsuario=db.datosUsuarioDAO().getAll()
                    // Obtiene los datos del cliente correspondiente de la base de datos
                    var cliente= db.clienteDAO().buscarClientePorNombre(factura.nombreCliente.toString())
                    withContext(Dispatchers.Main) {
                        // Crea una lista de productos a partir de los datos de Factura_Producto
                        for (factura in factura_producto) {
                            // Se agregan los productos a la lista 'productos', se utiliza la clase Producto y se llenan sus propiedades
                            productos.add(
                                Producto(
                                    nombre = factura.nombreProducto,
                                    precio = factura.precio,
                                    cantidad = factura.cantidad
                                )
                            )
                        }

                        for(factura in facturaBusqueda){
                            fecha= factura.fecha!!
                            numero=factura.titulo
                            total=factura.precioTotal
                            tipo= factura.tipoFactura.toString()
                            referencia=factura.referencia

                        }
                        if (cliente != null) {
                            // Se verifica si se tienen los permisos de almacenamiento, para esto se llama a la función 'checkStoragePermissions'
                            checkStoragePermissions(actividadMadre)
                            // Se crea una instancia de la clase CrearPDF
                            var personalizado = CrearPDF()
                            if (checkPermission()) {
                                // Si se tienen los permisos de almacenamiento, se muestra un mensaje en pantalla
                                Toast.makeText(actividadMadre, "Ha entrado en pdf", Toast.LENGTH_SHORT).show()
                                // Se genera el PDF llamando al método 'generarPdf' de la instancia de CrearPDF
                                personalizado.generarPdf(
                                    actividadMadre.resources,
                                    actividadMadre,
                                    referencia,tipo,numero,fecha, total,
                                    productos,
                                    cliente,datosUsuario
                                )
                            } else {
                                // Si no se tienen los permisos de almacenamiento, se solicitan llamando a la función 'requestPermissions'
                                requestPermissions()
                            }
                        } else {
                            // Si no se tiene cliente, se muestra un mensaje con el nombre del cliente del albarán
                            Toast.makeText(actividadMadre, "Cliente: " + factura.nombreCliente, Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }
        }


    }

    /***
     * Este método devuelve la cantidad total de elementos que se muestran en el RecyclerView.
     * Se utiliza para saber cuántos elementos hay en la lista de facturas.
     ***/
    override fun getItemCount(): Int {
        return datos.size
    }
    /**
     *Función que verifica si se tienen los permisos de almacenamiento necesarios.
     */
    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(actividadMadre,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ContextCompat.checkSelfPermission(actividadMadre,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    /**
     *  Función que solicita los permisos de almacenamiento necesarios:
     */
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

    /**
     *  Función que comprueba si se han concedido permisos de almacenamiento a la aplicación
     */
    private fun checkStoragePermissions(activity: Activity) {
        // Comprueba si la aplicación tiene permiso para escribir en el almacenamiento externo
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED // Si no se han concedido permisos de escritura en el almacenamiento externo
        ) {
            ActivityCompat.requestPermissions( // Solicita los permisos de almacenamiento externo al usuario
                activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, // Se solicita permiso de escritura en el almacenamiento externo
                    Manifest.permission.READ_EXTERNAL_STORAGE // Se solicita permiso de lectura en el almacenamiento externo
                ),
                200 // Código de petición para identificar la solicitud
            )
        }
    }
    fun filtrar(listaFiltrada: ArrayList<Factura>){
        this.datos=listaFiltrada
        notifyDataSetChanged()
    }

}