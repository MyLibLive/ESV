package com.ivanojok.esv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.esv.admin.AddPoliceActivity
import com.ivanojok.esv.model.PrefsManager
import com.ivanojok.esv.model.VictimChatAdapter

class ConsultingCounsellor : AppCompatActivity() {

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulting_counsellor)
        prefsManager.setContext(this.application)

        val Id = prefsManager.getVictim().phone

        //dialog = SpotsDialog.Builder().setContext(this).build()

        val rView = findViewById<RecyclerView>(R.id.recyclerView)
        rView.layoutManager = LinearLayoutManager(this)


        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, Services::class.java))
        }

        val add = findViewById<ImageView>(R.id.add)
        add.setOnClickListener{
            startActivity(Intent(this, SelectCounsellor::class.java))
        }



        val p = ArrayList<String>()
        val dbRef = FirebaseDatabase.getInstance().getReference("/chats").child(Id!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (y in snapshot.children){

                    Log.d("y", "$y")
                    val z = y.key   //getValue(String::class.java)
                    //Log.d("z", "${z!!.imgUrl}")

                    val index = p.size - 1
                    p.add(z!!)
                    //p.add(index,  z!!)
                    Log.d("p", "$p")
                }
                val adapter = VictimChatAdapter(this@ConsultingCounsellor, p)
                rView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@ConsultingCounsellor, "An $error occured", Toast.LENGTH_LONG).show()
            }

        })


    }
}