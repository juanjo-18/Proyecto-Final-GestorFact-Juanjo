package com.example.proyectofinal

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import clases.Compra
import clases.Factura
import com.echo.holographlibrary.Bar
import com.echo.holographlibrary.BarGraph
import com.example.proyectofinal.databinding.LayoutInformesBinding
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ActividadInformes : AppCompatActivity() {
    private lateinit var binding: LayoutInformesBinding
    private lateinit var db: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutInformesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Se inicializa la instancia de la base de datos y se actualiza si hubiera nueva version.
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()

        var facturas= arrayListOf<Factura>()
        var compras = arrayListOf<Compra>()
        var totalFacturado=0f
        var totalCobradoFactura=0f
        var totalComprado=0f
        GlobalScope.launch {
            facturas = db.facturaDAO().getAll() as ArrayList<Factura>
            compras = db.compraDAO().getAll() as ArrayList<Compra>
            for(factura in facturas){
                if(factura.tipoFactura.equals("Factura")){
                    totalFacturado+=factura.precioTotal
                    if(factura.cobrada){
                        totalCobradoFactura+=factura.precioTotal
                    }
                }
                if(factura.titulo.equals("Abono")){
                    totalFacturado-=factura.precioTotal
                }

            }
            for(compra in compras){
                if(compra.tipoCompra.equals("Compra")){
                    totalComprado+=compra.precioTotal
                }
                if(compra.tipoCompra.equals("Devolucion")){
                    totalComprado-=compra.precioTotal
                }

            }
            val formato = DecimalFormat("#.##")


            val puntos =ArrayList<Bar>()
            graficarBarras(puntos,formato.format(totalFacturado).toFloat(),formato.format(totalComprado).toFloat())

            val puntos1 =ArrayList<Bar>()
            graficarBarras1(puntos1,formato.format(totalFacturado).toFloat(),formato.format(totalCobradoFactura).toFloat())

        }




        //Boton que cambia la panatalla catalogo
        binding.botonIrACatalogoDesdeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCatalogo::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla cliente
        binding.botonIrACLienteDesdeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCliente::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla ventas
        binding.botonIrAVentaDedeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadVenta::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla inicio
        binding.botonIrAInicioDesdeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadInicio::class.java
            )
            this.startActivity(intent)
        }
        //Boton que cambia la panatalla a compra
        binding.botonIrAComprasDesdeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadCompra::class.java
            )
            this.startActivity(intent)
        }

        //Boton que cambia la panatalla a informes
        binding.botonIrAFacturacionDesdeInformes.setOnClickListener{
            val intent: Intent = Intent(
                this,ActividadFacturacion::class.java
            )
            this.startActivity(intent)
        }
    }
    fun graficarBarras(puntos:ArrayList<Bar>,totalFacturado:Float,totalComprado:Float){
        val grafica = findViewById<View>(R.id.graphBarPrimera) as BarGraph
        var cantidad1=totalFacturado
        var cantidad2=totalComprado

        var barra = Bar()

        barra.color= Color.parseColor("#4FFF5C")
        barra.value=cantidad1.toFloat()
        puntos.add(barra)
        grafica.bars=puntos


        var barra2 = Bar()
        barra2.color= Color.parseColor("#FF3232")
        barra2.value=cantidad2.toFloat()
        puntos.add(barra2)

        var cantidad3=cantidad1-cantidad2
        var barra3 = Bar()

        barra3.color= Color.parseColor("#FF8932")
        barra3.value=cantidad3.toFloat()
        puntos.add(barra3)

        grafica.bars=puntos
    }

    fun graficarBarras1(puntos:ArrayList<Bar>,totalFacturado:Float,totalCobrado:Float){
        val grafica1 = findViewById<View>(R.id.graphBarSegunda) as BarGraph
        var cantidad1=totalFacturado

         var barra = Bar()

        barra.color= Color.parseColor("#4FFF5C")
        barra.value=cantidad1.toFloat()
        puntos.add(barra)
        grafica1.bars=puntos

        var cantidad2=totalCobrado
        var barra2 = Bar()
        barra2.color= Color.parseColor("#FF3232")
        barra2.value=cantidad2.toFloat()
        puntos.add(barra2)

        var cantidad3=cantidad1-cantidad2
        var barra3 = Bar()

        barra3.color= Color.parseColor("#FF8932")
        barra3.value=cantidad3.toFloat()
        puntos.add(barra3)

        grafica1.bars=puntos


    }




}