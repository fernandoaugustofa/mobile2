package br.edu.computacaoifg.Carga

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.view.*
import org.jetbrains.anko.toast

class viewActivty : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this@viewActivty, MainActivity::class.java)
                intent.putExtra("key", "Kotlin")
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard ->{
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_add
        button2.setOnClickListener {
            val extras = intent.extras
            val currentLatitude = extras.getString("A")
            val currentLongitude = extras.getString("O")
            val Nameval = name.text.toString()
            val cpf = input_cpf.text.toString()
            val RG = input_RG.text.toString()
            val fone = input_fone.text.toString()
            val antt = input_antt.text.toString()
            sync_truck().execute("http://192.168.137.1/geo/position?_format=json",
                    currentLatitude.toString(), currentLongitude.toString(), Nameval, cpf,RG,fone,antt)
            val intent = Intent(this@viewActivty, MainActivity::class.java)
            intent.putExtra("key", "Kotlin")
            toast(Nameval + " - Salvo !!!")
            startActivity(intent)

        }
    }

}