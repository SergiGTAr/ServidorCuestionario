package com.example.servidorcuestionario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = initDatabase()
        //btnGenerarPartida
        val btnGenerarPartida = findViewById<Button>(R.id.btnGenerarPartida)
        btnGenerarPartida.setOnClickListener {
                val numeroPartida = (10000..99999).random()
                val txtNumPartida = findViewById<TextView>(R.id.txtIDPartida)
                txtNumPartida.text = numeroPartida.toString()
                escriuredb(numeroPartida, database)
            }
        }



    private fun initDatabase(): DatabaseReference {
        val dbinstance = FirebaseDatabase.getInstance("https://servidorquestionari-default-rtdb.europe-west1.firebasedatabase.app")
        database = dbinstance.getReference("")

        return database
    }

    private fun escriuredb(numeroPartida: Int, database: DatabaseReference) {
        database.child("partida").child("id").setValue(numeroPartida)
    }

    fun llegirUsuaris(){

    }
}