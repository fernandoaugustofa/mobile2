package br.edu.computacaoifg.Carga

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MyMapActivity :FragmentActivity(),OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapf)

        val mapFragment = fragmentManager
                .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(p0: GoogleMap?) {
        val extras = intent.extras
        val currentLatitude = extras.getString("A")
        val currentLongitude = extras.getString("O")
        val Name = extras.getString("L")
        p0?.addMarker(MarkerOptions()
                .position(LatLng(currentLatitude.toDouble(),currentLongitude.toDouble()))
                .title(Name))
    }
}
