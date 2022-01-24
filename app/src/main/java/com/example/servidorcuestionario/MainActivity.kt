package com.example.servidorcuestionario

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

private lateinit var database: DatabaseReference

class MainActivity : AppCompatActivity() {
    private var rondes = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var numeroPartida = 0
        val btnIniciarPartida = findViewById<Button>(R.id.btnIniciarPartida)
        database = initDatabase()
        //btnGenerarPartida
        val btnGenerarPartida = findViewById<Button>(R.id.btnGenerarPartida)
        btnGenerarPartida.setOnClickListener {
            numeroPartida = (10000..99999).random()
            val txtNumPartida = findViewById<TextView>(R.id.txtIDPartida)
            txtNumPartida.text = numeroPartida.toString()
            escriuredb(numeroPartida, database)
            btnGenerarPartida.visibility = View.INVISIBLE
            btnIniciarPartida.visibility = View.VISIBLE
        }
        btnIniciarPartida.setOnClickListener {
            iniciarPartida(numeroPartida, database)
        }
    }

    private fun initDatabase(): DatabaseReference {
        val dbinstance =
            FirebaseDatabase.getInstance("https://servidorquestionari-default-rtdb.europe-west1.firebasedatabase.app")
        database = dbinstance.getReference("")

        return database
    }

    private fun llegirUsuaris(postReference: DatabaseReference, numeroPartida: Int) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.child("partides").child("$numeroPartida").child("equips").value
                Toast.makeText(this@MainActivity, post.toString(), Toast.LENGTH_SHORT).show()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
    }

    private fun escriuredb(numeroPartida: Int, database: DatabaseReference) {
        database.child("partides").child("$numeroPartida").child("estatpartida")
            .setValue("esperant")
//        database.child("partides").child("$numeroPartida").child("numequips").setValue(0)
        database.child("partides").child("$numeroPartida").child("numronda").setValue(-1)
        database.child("partides").child("$numeroPartida").child("equips").child("numequips")
            .setValue(0)
        llegirUsuaris(database, numeroPartida)
    }

    fun iniciarPartida(numeroPartida: Int, database: DatabaseReference) {
        if (numeroPartida == 0) {
            Toast.makeText(
                this@MainActivity,
                "Per poder iniciar la partida, s'ha d'haver generat prèviament",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        database.child("partides").child("$numeroPartida").child("equips").child("numequips")
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    //mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val numEquipsPartida = dataSnapshot.value.toString()
                        if (numEquipsPartida != "2") {
                            Toast.makeText(
                                this@MainActivity,
                                "Per poder iniciar la partida, han d'haver 2 equips",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        } else {
                            database.child("partides").child("$numeroPartida").child("estatpartida")
                                .setValue("encurs")
                            partidaenCurs(numeroPartida, database)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                }
            )
    }

    private fun partidaenCurs(numeroPartida: Int, database: DatabaseReference) {
        setContentView(R.layout.partida_en_curs)
        database.child("partides").child("$numeroPartida").child("numronda").setValue(rondes)
        val txtNumPartida = findViewById<TextView>(R.id.txtRondes)
        when (rondes) {
            1 -> {
                txtNumPartida.text = "Ronda ${rondes}"
            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }
            6 -> {

            }
            7 -> {

            }
            8 -> {

            }
            9 -> {

            }
            10 -> {

            }
        }
    }
}