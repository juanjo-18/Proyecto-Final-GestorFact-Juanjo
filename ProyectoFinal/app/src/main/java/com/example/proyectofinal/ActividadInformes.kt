package com.example.proyectofinal

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        val grafica = findViewById<View>(R.id.graphBar) as BarGraph
        var cantidad1=20
        val barra = Bar()
        barra.color= Color.parseColor("#4FFF5C")
        barra.value=cantidad1.toFloat()
        puntos.add(barra)
        grafica.bars=puntos

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


    }

    fun generarColorHexAleatorio(): String{
        val letras = arrayOf("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F")
        var color ="#"
        for(i in 0..5){
            color += letras[(Math.random()*15).roundToInt()]
        }
        return color
    }
}