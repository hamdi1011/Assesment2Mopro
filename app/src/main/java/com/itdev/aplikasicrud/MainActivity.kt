package com.itdev.aplikasicrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNama: EditText
    private lateinit var etAsal: EditText
    private lateinit var btnSimpan: Button
    private lateinit var listWrg:  ListView
    private lateinit var ref: DatabaseReference
    private lateinit var wrgList : MutableList<Warga>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("warga")

        etNama = findViewById(R.id.et_nama)
        etAsal = findViewById(R.id.et_asal)
        btnSimpan = findViewById(R.id.btn_simpan)
        listWrg = findViewById(R.id.lvDataWarga)
        btnSimpan.setOnClickListener(this)

        wrgList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    wrgList.clear()
                    for (h in snapshot.children){
                        val warga = h.getValue(Warga::class.java)
                        if (warga != null) {
                            wrgList.add(warga)
                        }
                    }

                    val adapter = WargaAdapter(this@MainActivity, R.layout.item_warga, wrgList)
                    listWrg.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData() {
        val name = etNama.text.toString().trim()
        val asal = etAsal.text.toString().trim()

        if (name.isEmpty()) {
            etNama.error = "Tolong ketik nama anda"
            return
        }

        if (asal.isEmpty()) {
            etAsal.error = "Tolong ketik asal anda"
            return
        }

        val wrgId = ref.push().key
        val wrg = Warga(wrgId!!,name,asal)

        ref.child(wrgId).setValue(wrg).addOnCompleteListener {
            Toast.makeText(applicationContext, "Data berhasil di tambahkan", Toast.LENGTH_SHORT).show()
        }
    }
}