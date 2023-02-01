package com.example.miprimeraappandroid.pruebas

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class ActividadInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_inicio)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_desplegable,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.catalogo->{
                setContentView(R.layout.layout_registro)
            }
            R.id.inicio->{
                setContentView(R.layout.layout_inicio)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}