package br.edu.computacaoifg.Carga

import android.os.AsyncTask
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONObject
import java.net.URL


/**
 * Created by FA on 02/12/2017.
 */
class sync_truck :AsyncTask<String,String,String>(){
    override fun onPostExecute(result: String){
        super.onPostExecute(result)
    }
    override fun onPreExecute() {
        super.onPreExecute()
    }
    override fun doInBackground(vararg params: String):String {
        try {
            val httpclient = DefaultHttpClient()
            val method = HttpPost(params[0])
            method.setHeader("content-type","application/json")
            val data= JSONObject();
            data.put("truck_id",1)
            data.put("latitude",params[1])
            data.put("longitude",params[2])
            data.put("nome",params[3])
            data.put("cpf",params[4])
            data.put("rg",params[5])
            data.put("contato",params[6])
            data.put("antt",params[7])
            method.entity=StringEntity(data.toString(),"UTF-8")
            val response = httpclient.execute(method)
            val entity = response.entity

            return if (entity != null) {
                EntityUtils.toString(entity)
            } else {
                "No string."
            }
        } catch (e: Exception) {
            return "Network problem"
        }

    }

}