package recyclers.compras

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
import com.example.proyectofinal.ActividadEditarCompra
import com.example.proyectofinal.ActividadEditarFactura
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import recyclers.facturacion.FacturacionViewHolder
import java.time.LocalDate

class ComprasAdapter(val actividadMadre: Activity, var datos: ArrayList<Compra>):
    RecyclerView.Adapter<ComprasViewHolder>() {
        private lateinit var db: AppDataBase

        /***
         * Este método crea una nueva instancia de la vista de elemento del adaptador
         * y devuelve un nuevo objeto ComprasViewHolder que mantiene la referencia
         * de cada vista de elemento del adaptador.
         ***/
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComprasViewHolder {
            db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
                .addMigrations(AppDataBase.MIGRATION_1_2)
                .build()
            return ComprasViewHolder(
                actividadMadre.layoutInflater.inflate(
                    R.layout.elementos_recycler_facturacion,
                    parent,
                    false
                )
            )
        }

        /***
         * Este método establece el contenido de cada elemento del RecyclerView.
         * Recibe como parámetros el objeto ComprasViewHolder y la posición actual.
         * El contenido se establece utilizando los datos de la lista de compras que se
         * pasó al constructor del adaptador.
         ***/
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: ComprasViewHolder, position: Int) {
            val compra: Compra = datos.get(position)
            holder.titulo.text=compra.titulo
            holder.nombreProveedor.text=compra.nombreProveedor
            holder.fecha.text= compra.fecha.toString()
            if(compra.pagada==true){
                holder.pagada.text="Pagada"
                val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto1)
                holder.pagada.background = backgroundDrawable
            }else{
                holder.pagada.text="Pendiente"
                val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto)
                holder.pagada.background = backgroundDrawable
            }
            holder.precio.text=""+compra.precioTotal

            /***
             * Este listener se activa cuando se hace clic en el botón de borrar.
             * Elimina el elemento correspondiente de la lista de comrpas y actualiza el RecyclerView.
             ***/
            holder.botonBorrar.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    launch(Dispatchers.IO) {
                        // Se elimina la compra de la base de datos utilizando la función 'delete' de la clase 'CompraDAO'
                        db.compra_ProductoDAO().borrarTodosPorTitulo(compra.titulo)
                        db.compraDAO().delete(compra)
                     }
                }
                datos.removeAt(position)
                this.notifyDataSetChanged()
            }
            holder.botonEditar.setOnClickListener{
                // Se crea un intent para iniciar la actividad de editar comrpa, y se le pasan algunos datos
                val intent: Intent = Intent(actividadMadre, ActividadEditarCompra::class.java)
                intent.putExtra("titulo", compra.titulo)
                actividadMadre.startActivity(intent)
            }

            // Agrega un listener al botón "crear compra"
            holder.crearPDF.setOnClickListener{

                // Crea y lanza un hilo en segundo plano para obtener los datos de la compra
                var compra_producto = arrayListOf<Compra_Producto>()
                var compraBusqueda= arrayListOf<Compra>()
                var productos = arrayListOf<Producto>()
                var tipo: String=""
                var total: Float=0f
                var numero:String=""
                var fecha: LocalDate = LocalDate.now()
                var referencia:Int=0
                var datosUsuario:DatosUsuario
                CoroutineScope(Dispatchers.IO).launch {
                    launch(Dispatchers.IO) {
                        //Obtengo la compra
                        compraBusqueda = db.compraDAO().buscarCompraPorTitulo(holder.titulo.text.toString()) as ArrayList<Compra>
                        // Obtiene los datos de la tabla de Compra_Producto de la base de datos para el título correspondiente
                        compra_producto = db.compra_ProductoDAO()
                            .buscarCompraProductoPorTitulo(holder.titulo.text.toString()) as ArrayList<Compra_Producto>
                        datosUsuario=db.datosUsuarioDAO().getAll()
                        // Obtiene los datos del cliente correspondiente de la base de datos
                        var cliente= db.clienteDAO().buscarClientePorNombre(compra.nombreProveedor.toString())
                        withContext(Dispatchers.Main) {
                            // Crea una lista de productos a partir de los datos de Compra_Producto
                            for (compra in compra_producto) {
                                // Se agregan los productos a la lista 'productos', se utiliza la clase Producto y se llenan sus propiedades
                                productos.add(
                                    Producto(
                                        nombre = compra.nombreProducto,
                                        precio = compra.precio,
                                        cantidad = compra.cantidad
                                    )
                                )
                            }

                            for(compra in compraBusqueda){
                                fecha= compra.fecha!!
                                numero=compra.titulo
                                total=compra.precioTotal
                                tipo= compra.tipoCompra.toString()
                                referencia=compra.referencia

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
                                Toast.makeText(actividadMadre, "Cliente: " + compra.nombreProveedor, Toast.LENGTH_SHORT).show()
                            }


                        }
                    }
                }
            }


        }

        /***
         * Este método devuelve la cantidad total de elementos que se muestran en el RecyclerView.
         * Se utiliza para saber cuántos elementos hay en la lista de compras.
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
        fun filtrar(listaFiltrada: ArrayList<Compra>){
            this.datos=listaFiltrada
            notifyDataSetChanged()
        }

    }