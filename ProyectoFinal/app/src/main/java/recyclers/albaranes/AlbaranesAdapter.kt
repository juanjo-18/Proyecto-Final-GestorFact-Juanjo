package recyclers.albaranes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.text.TextPaint
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
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate


/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de albaranes.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo Albaran que se utilizarán para poblar la vista de la lista
 */
class AlbaranesAdapter(val actividadMadre: Activity, var datos: ArrayList<Albaran>) :
    RecyclerView.Adapter<AlbaranesViewHolder>() {

    private lateinit var db: AppDataBase

    /**
     * Método que crea y devuelve una instancia de AlbaranesViewHolder para cada elemento del RecyclerView.
     *
     * @param parent el ViewGroup en el que se va a agregar la vista recién inflada después de crearla
     * @param viewType el tipo de vista de la nueva vista que se va a crear
     * @return una instancia de AlbaranesViewHolder que contiene la nueva vista para un elemento del RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbaranesViewHolder {
        // Crea una instancia de la base de datos utilizando Room
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        // Infla la vista del elemento de la lista a partir de un archivo XML
        return AlbaranesViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_ventas,
                parent,
                false
            )
        )

    }

    /**
     * Método que se llama para actualizar la vista de un elemento de la lista del RecyclerView.
     *
     * @param holder instancia de AlbaranesViewHolder que contiene la vista de un elemento de la lista
     * @param position la posición del elemento de la lista en la que se encuentra la vista que se va a actualizar
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AlbaranesViewHolder, position: Int) {
        // Obtiene el objeto Albaran correspondiente a la posición actual
        val albaran: Albaran = datos.get(position)
        // Actualiza la vista con los datos del objeto Albaran
        holder.titulo.text=albaran.titulo
        holder.nombreCliente.text=albaran.nombreCliente
        holder.fecha.text= albaran.fecha.toString()
        holder.estado.text=albaran.estado
        holder.precio.text=""+albaran.precioTotal

        //cambiar color
        if(holder.estado.text.equals("Cerrado")) {
            val backgroundDrawable =
                ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto1)
            holder.estado.background = backgroundDrawable
        }

        holder.crearFactura.setOnClickListener {
            //Lo que tengo que hacer una vez saco el objeto en el que estoy lo inserto en factura y luego lo borro
            // Y despues abrir la pantalla editar factura
            var albaran_producto = arrayListOf<Albaran_Producto>()
            var productos = arrayListOf<Producto>()
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    // Obtiene los datos de la tabla de Albaran_Producto de la base de datos para el título correspondiente
                    albaran_producto = db.albaran_ProductoDAO()
                        .buscarAlbaranProductoPorTitulo(holder.titulo.text.toString()) as ArrayList<Albaran_Producto>
                    withContext(Dispatchers.Main) {
                        // Crea una lista de productos a partir de los datos de Albaran_Producto
                        for (albaranes in albaran_producto) {
                            // Se agregan los productos a la lista 'productos', se utiliza la clase Producto y se llenan sus propiedades
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
                        }
                    }

                    var totalAlbaranFinal=0f
                    //Inserto los datos del albaran a la factura
                    for (producto in productos) {
                        db.factura_ProductoDAO().insert(
                            Factura_Producto(
                                tituloFactura = albaran.titulo.toString(),
                                nombreProducto = producto.nombre.toString(),
                                precio = producto.precio,
                                cantidad = producto.cantidad,
                                total = producto.precio * producto.cantidad
                            )
                        )
                        totalAlbaranFinal += producto.precio * producto.cantidad
                    }

                    //Aqui insertamos la factura a la base de datos factura
                    db.facturaDAO().insert(
                        Factura(
                            titulo = albaran.titulo,
                            nombreCliente = albaran.nombreCliente,
                            fecha = LocalDate.now(),
                            tipoFactura = "Factura",
                            cobrada = false,
                            precioTotal = (totalAlbaranFinal * 1.21).toFloat()
                        )
                    )

                    holder.estado.text="Cerrado"
                    // hacer update al albaran
                    db.albaranDAO().updateAlbaran(albaran.titulo,albaran.titulo,albaran.nombreCliente.toString(),
                        LocalDate.now(),"Cerrado",(totalAlbaranFinal * 1.21).toFloat())
                    val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto1)
                    holder.estado.background = backgroundDrawable

                    // Se crea un intent para iniciar la actividad de editar factura, y se le pasan algunos datos
                    val intent: Intent = Intent(actividadMadre, ActividadEditarFactura::class.java)
                    intent.putExtra("titulo", albaran.titulo)
                    actividadMadre.startActivity(intent)
                }
            }
        }

        // Agrega un listener al botón "crear factura"
        holder.crearPDF.setOnClickListener{
            // Crea y lanza un hilo en segundo plano para obtener los datos de la factura
            var albaran_producto = arrayListOf<Albaran_Producto>()
            var productos = arrayListOf<Producto>()
            var albaranBusqueda= arrayListOf<Albaran>()
            var tipo: String=""
            var total: Float=0f
            var numeroAlbaran:String=""
            var fecha: LocalDate= LocalDate.now()
            var referencia:Int=0
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    //Obtengo la factura
                    albaranBusqueda = db.albaranDAO().buscarAlbaranPorTitulo(holder.titulo.text.toString()) as ArrayList<Albaran>
                    // Obtiene los datos de la tabla de Albaran_Producto de la base de datos para el título correspondiente
                    albaran_producto = db.albaran_ProductoDAO()
                        .buscarAlbaranProductoPorTitulo(holder.titulo.text.toString()) as ArrayList<Albaran_Producto>
                    // Obtiene los datos del cliente correspondiente de la base de datos
                    var cliente= db.clienteDAO().buscarClientePorNombre(albaran.nombreCliente.toString())
                    withContext(Dispatchers.Main) {
                        // Crea una lista de productos a partir de los datos de Albaran_Producto
                        for (albaranes in albaran_producto) {
                            // Se agregan los productos a la lista 'productos', se utiliza la clase Producto y se llenan sus propiedades
                           tipo=albaranes.tipoAlbaran.toString()
                            productos.add(
                                Producto(
                                    nombre = albaranes.nombreProducto,
                                    precio = albaranes.precio,
                                    cantidad = albaranes.cantidad
                                )
                            )
                        }
                        for(albaran in albaranBusqueda){
                            fecha= albaran.fecha!!
                            numeroAlbaran=albaran.titulo
                            total=albaran.precioTotal
                            referencia=albaran.referencia

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
                                    referencia,tipo,numeroAlbaran,fecha, total,
                                    productos,
                                    cliente
                                )
                            } else {
                                // Si no se tienen los permisos de almacenamiento, se solicitan llamando a la función 'requestPermissions'
                                requestPermissions()
                            }
                        } else {
                            // Si no se tiene cliente, se muestra un mensaje con el nombre del cliente del albarán
                            Toast.makeText(actividadMadre, "Cliente: " + albaran.nombreCliente, Toast.LENGTH_SHORT).show()
                        }



                    }
                }
            }
        }
        holder.botonEditar.setOnClickListener{
            // Se crea un intent para iniciar la actividad de editar venta, y se le pasan algunos datos
            val intent: Intent = Intent(actividadMadre, ActividadEditarVenta::class.java)
            intent.putExtra("titulo", albaran.titulo)
            actividadMadre.startActivity(intent)
        }


        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    // Se elimina el albarán de la base de datos utilizando la función 'delete' de la clase 'AlbaranDAO'
                    db.albaranDAO().delete(albaran)
                }
            }
            // Se remueve el albarán de la lista 'datos'
            datos.removeAt(position)
            // Se notifica al adaptador que los datos han cambiado
            this.notifyDataSetChanged()
        }

    }

    /**
     * Muestra la función que devuelve la cantidad de elementos que hay en la lista:
     */
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
    fun filtrar(listaFiltrada: ArrayList<Albaran>){
        this.datos=listaFiltrada
        notifyDataSetChanged()
    }



}