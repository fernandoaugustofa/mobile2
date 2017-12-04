package br.edu.computacaoifg.Carga

import android.content.Intent
import android.database.Cursor
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.card.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity(), LocationProvider.LocationCallback {
    var Id:Int? = null
    var name:String? = null
    var date:String?= null
    var done:String?= null
    var CA:ToDoAdapter?= null
    var database:MyDatabaseOpenHelper?=null
    var todoCursor:Cursor?=null
    fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start
    var currentLatitude:Double?= null
    var currentLongitude:Double?= null
    private var mLocationProvider: LocationProvider? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard ->{
               return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {
                val intent = Intent(this@MainActivity, viewActivty::class.java)
                intent.putExtra("A", currentLatitude.toString())
                intent.putExtra("O", currentLongitude.toString())
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    fun mont(){
        todoCursor= database!!.writableDatabase.rawQuery("Select * from driver",null)
        CA?.swapCursor(todoCursor)
        CA?.notifyDataSetChanged()
        lvItems.invalidateViews();
        sync()
    }
    fun sync(){
        val timer = Timer()
        timer.schedule(timerTask {
        async {
            val A = URL("http://192.168.137.1/geo/position?_format=json").readText()
            var E = A.replace("}]", "")
            E = E.replace("[{\"", "")
            E = E.replace("\"", "")
            val AA = E.split("},{")
            database?.use {
                delete("driver")
            }
            AA.forEach {
                val B = it
                val C = B.split(",")
                val truck_id = C[1].replace("truck_id", "").replace(":", "")
                val latitide = C[2].replace("latitude", "").replace(":", "")
                val longitude = C[3].replace("longitude", "").replace(":", "")
                val Nome = C[5].replace("nome", "").replace(":", "")
                database?.use {
                    insert("driver", "nome" to Nome,
                            "pos" to latitide + "|" + longitude
                    )
                }
            }
            mont()
        }
        }, 3000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sync()
        mLocationProvider = LocationProvider(this, this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        database = MyDatabaseOpenHelper.getInstance(applicationContext)
        todoCursor= database!!.writableDatabase.rawQuery("Select * from driver",null)
        CA= ToDoAdapter(this,todoCursor!!)
        lvItems.adapter=CA
        lvItems.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val ID2 = lvItems.getAdapter().getItemId(position)
            val Map = (view.findViewById<ImageButton>(R.id.Map))
            val KM = (view.findViewById<TextView>(R.id.km))
            val Name = (view.findViewById<TextView>(R.id.name))
            val Trail =(view.findViewById<ImageButton>(R.id.trail))
            val Score = (view.findViewById<TextView>(R.id.score))

            if (Map?.visibility == View.VISIBLE) {
                Map.visibility = View.GONE
                Trail.visibility = View.GONE
                Score.visibility = View.GONE
            }
                Map.visibility = View.VISIBLE
                Trail.visibility = View.VISIBLE
                Score.visibility = View.VISIBLE
            
           Map.setOnClickListener {
               val intent = Intent(this@MainActivity, MyMapActivity::class.java)
               val K = KM.text.toString().replace("Posição:", "")
               val KM1 = K.split("|")
               intent.putExtra("A", KM1[0])
               intent.putExtra("O", KM1[1])
               intent.putExtra("L", Name.text.toString())
               startActivity(intent)
           }
        }
    }
    override fun onResume() {
        super.onResume()
        mLocationProvider!!.connect()
    }
    override fun onPause() {
        super.onPause()
        mLocationProvider!!.disconnect()
    }
   override fun handleNewLocation(location: Location) {
        Log.d(TAG, location.toString())
        currentLatitude = location.latitude
        currentLongitude = location.longitude
       toast(currentLatitude.toString() +" | "+currentLongitude.toString() )
      //  var jason = sync_truck().execute("http://192.168.1.102/geo/position?_format=json",currentLatitude.toString(), currentLongitude.toString())

    }
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

}
