package com.example.proyectofinal

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.echo.holographlibrary.Bar
import com.echo.holographlibrary.BarGraph
import com.example.proyectofinal.databinding.LayoutInformesBinding
import kotlin.math.roundToInt

class ActividadInformes : AppCompatActivity() {
    private lateinit var binding: LayoutInformesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutInformesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val puntos =ArrayList<Bar>()

        graficarBarras(puntos)

    }
    fun graficarBarras(puntos:ArrayList<Bar>){
        val grafica = findViewById<View>(R.id.graphBarPrimera) as BarGraph
        val grafica1 = findViewById<View>(R.id.graphBarSegunda) as BarGraph
        var cantidad1=20
        val barra = Bar()
        barra.color= Color.parseColor("#4FFF5C")
        barra.value=cantidad1.toFloat()
        puntos.add(barra)
        grafica.bars=puntos
        grafica1.bars=puntos

        var cantidad2=10
        val barra2 = Bar()
        barra2.color= Color.parseColor("#FF3232")
        barra2.value=cantidad2.toFloat()
        puntos.add(barra2)

        var cantidad3=cantidad1-cantidad2
        val barra3 = Bar()

        barra3.color= Color.parseColor("#FF8932")
        barra3.value=cantidad3.toFloat()
        puntos.add(barra3)

        grafica.bars=puntos
        grafica1.bars=puntos


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

}