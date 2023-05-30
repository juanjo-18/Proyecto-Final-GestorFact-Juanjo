package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.room.Room
import clases.*
import com.example.proyectofinal.databinding.LayoutInicioBinding
import com.google.firebase.firestore.FirebaseFirestore
import dataBase.AppDataBase
import emergentes.Alerta
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Esta es la pantalla inicial donde una vez iniciado sesion o registrarte entraras aqui se muestran los datos
 * generales de las facturas y albaranes.
 * @author Juanjo Medina
 */
class ActividadInicio : AppCompatActivity() {

    /**
     * Variable para la instancia de la base de datos.
     */
    private lateinit var binding: LayoutInicioBinding

    private lateinit var db: AppDataBase

    /**
     * Método onCreate() de la actividad, se llama al crear la actividad.
     * @param savedInstanceState estado de la actividad si se restaura.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla el layout y se establece como el contenido de la actividad.
        binding = LayoutInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var listaVenta= arrayListOf<Albaran>()
        var listaFactura= arrayListOf<Factura>()
        var productos_venta= arrayListOf<Albaran_Producto>()
        var totalVentaPresupuesto=0f
        var totalVentaPedido=0f
        var totalVentaAlbaran=0f
        var totalFactura=0f
        var totalAbono=0f
        GlobalScope.launch {
            listaVenta= db.albaranDAO().getAll() as ArrayList<Albaran>
            listaFactura= db.facturaDAO().getAll() as ArrayList<Factura>


            if(listaVenta.size>0){
                for(venta in listaVenta){
                    productos_venta= db.albaran_ProductoDAO().buscarAlbaranProductoPorTitulo(venta.titulo.toString()) as ArrayList<Albaran_Producto>
                   if(productos_venta[0].tipoAlbaran.equals("Presupuesto")){
                        totalVentaPresupuesto+=venta.precioTotal
                    }
                    if(productos_venta[0].tipoAlbaran.equals("Pedido")){
                        totalVentaPedido+=venta.precioTotal
                    }
                    if(productos_venta[0].tipoAlbaran.equals("Albaran")){
                        totalVentaAlbaran+=venta.precioTotal
                    }
                }
            }
            if(listaFactura.size>0){
                for(factura in listaFactura){
                    if(factura.tipoFactura.equals("Factura")){
                        totalFactura+=factura.precioTotal
                    }
                    if(factura.tipoFactura.equals("Abono")){
                        totalAbono+=factura.precioTotal
                    }
                }
            }
            binding.textoNumeroAbono.setText(totalAbono.toString()+"€")
            binding.textoNumerosFacturas.setText(totalFactura.toString()+"€")
            binding.textoNumeroAlbaranVenta.setText(totalVentaAlbaran.toString()+"€")
            binding.textoNumeroPedidoVenta.setText(totalVentaPedido.toString()+"€")
            binding.textoNumeroPresupuestoVenta.setText(totalVentaPresupuesto.toString()+"€")
        }





        val botonIrACliente: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACLienteDesdeInicio)
        val botonIrACatalogo: ImageButton =
            findViewById<ImageButton>(R.id.botonIrACatalogoDesdeInicio)
        val botonIrAVenta:ImageButton=findViewById<ImageButton>(R.id.botonIrAVentaDedeInicio)

        //Boton que cambia la panatalla de catalogo
        botonIrACatalogo.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)

        }

        //Boton que cambia la panatalla de cliente
        botonIrACliente.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla de ventas
        botonIrAVenta.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a facturacion
        val botonIrAFacturacion:ImageButton=findViewById<ImageButton>(R.id.botonIrAFacturacionDesdeInicio)
        botonIrAFacturacion.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a compra
        binding.botonIrAComprasDesdeInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCompra::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla a informes
        binding.botonIrAInformesDesdeInicio.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInformes::class.java
            )
            this.startActivity(intent)
        }

    }

}