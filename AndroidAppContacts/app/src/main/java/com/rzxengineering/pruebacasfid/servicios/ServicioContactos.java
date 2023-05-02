package com.rzxengineering.pruebacasfid.servicios;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rzxengineering.pruebacasfid.objetos.Contacto;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ServicioContactos {
    private ArrayList<Contacto> resultado;
    private Context contexto;

    public ServicioContactos(Context contexto) {
        resultado = new ArrayList<Contacto>();
        this.contexto = contexto;
    }

    public ArrayList<Contacto> conseguirResultadoContactos(int cantidad) {
        final CountDownLatch latch = new CountDownLatch(1);
        RequestQueue queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://randomuser.me/api/?results="+String.valueOf(cantidad), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array=  response.getJSONArray("results");
                            for(int i = 0; i< array.length();i++){
                                JSONObject responseObj = array.getJSONObject(i);

                                JSONObject nombre = responseObj.getJSONObject("name");
                                JSONObject localizacion = responseObj.getJSONObject("location");
                                JSONObject calle = localizacion.getJSONObject("street");

                                ArrayList<String> coordenadas = new ArrayList<String>();
                                coordenadas.add(localizacion.getJSONObject("coordinates")
                                        .getString("latitude"));
                                coordenadas.add(localizacion.getJSONObject("coordinates")
                                        .getString("longitude"));

                                resultado.add(new Contacto(
                                        responseObj.getString("gender"),
                                        nombre.getString("title"),
                                        nombre.getString("first"),
                                        nombre.getString("last"),
                                        calle.getString("name")+" "
                                                +calle.getInt("number"),
                                        localizacion.getString("state"),
                                        localizacion.getString("city"),
                                        localizacion.getString("country"),
                                        localizacion.getString("postcode"),
                                        coordenadas,
                                        localizacion.getJSONObject("timezone")
                                                .getString("offset"),
                                        localizacion.getJSONObject("timezone")
                                                .getString("description"),
                                        responseObj.getString("email"),
                                        responseObj.getJSONObject("login")
                                                .getString("username"),
                                        responseObj.getJSONObject("dob")
                                                .getString("date"),
                                        responseObj.getJSONObject("dob")
                                                .getString("age"),
                                        responseObj.getString("phone"),
                                        responseObj.getString("cell"),
                                        responseObj.getJSONObject("picture")
                                                .getString("large")
                                ));

                            }
                            latch.countDown();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                resultado.add(new Contacto(true));
                latch.countDown();
            }
        });
        queue.add(request);
        try {
            latch.await(); // Wait for request to complete
        } catch (InterruptedException e) {
            Log.d("Error",e.toString());
            resultado.add(new Contacto(true));
        }
        return resultado;
    }

}
