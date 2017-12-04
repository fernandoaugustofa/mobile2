package br.edu.computacaoifg.Carga

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Alessandro on 26/10/2017.
 */
class ToDoAdapter(context: Context, cursor:Cursor):
        CursorAdapter(context,cursor,0){
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
      return LayoutInflater.from(p0).inflate(R.layout.card,p2,false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        (view?.findViewById<ImageView>(R.id.Map))?.visibility = View.GONE;
        (view?.findViewById<ImageView>(R.id.trail))?.visibility = View.GONE;
        (view?.findViewById<TextView>(R.id.score))?.visibility = View.GONE;
        val name =  cursor?.getString(cursor.getColumnIndexOrThrow("nome"))
        val fone = cursor?.getString(cursor.getColumnIndexOrThrow("fone"))
        val car_poss = cursor?.getString(cursor.getColumnIndexOrThrow("pos"))
       // var a = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=%20AIzaSyDCiEzfmQwI8xAWy_y__Cko-U6ThY4xG3o";
        (view?.findViewById<TextView>(R.id.name))?.text=name
        (view?.findViewById<TextView>(R.id.call))?.text="Contato:" + fone
        (view?.findViewById<TextView>(R.id.km))?.text= "Posição:" + car_poss
    }
}