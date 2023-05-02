package com.rzxengineering.pruebacasfid.servicios;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rzxengineering.pruebacasfid.objetos.Contacto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ServicioDjango {
    public ServicioDjango() {
    }

    public String guardarContacto(Contacto contacto)  {
        try {
            URL url = new URL("http://192.168.0.11:8000/api/contacts/");
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = contacto.toJSON().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            return String.valueOf(connection.getResponseCode());
        }catch (MalformedURLException e){
            Log.d("ERROR->>>>>>>>>>>>>>>>>>>>>>>>>>>>>","Bad URL");
        } catch (ProtocolException e) {
            Log.d("ERROR->>>>>>>>>>>>>>>>>>>>>>>>>>>>>","PROTOCOL "+e.getLocalizedMessage());
        } catch (IOException ioe) {
            Log.d("ERROR->>>>>>>>>>>>>>>>>>>>>>>>>>>>>","IOE"+ioe.getLocalizedMessage());
        }
        return "error";
    }
    public ArrayList<Contacto> conseguirContactos(int cantidad, Context contexto) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(1);
        RequestQueue queue = Volley.newRequestQueue(contexto);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                "http://192.168.0.11:8000/api/contacts/?quantity="+String.valueOf(cantidad), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for(int i = 0; i< response.length();i++){
                                JSONObject responseObj = response.getJSONObject(i);

                                ArrayList<String> coordenadas = new ArrayList<>();
                                coordenadas.add(responseObj.getString("longitudeCoor"));
                                coordenadas.add(responseObj.getString("latitudeCoor"));
                                resultado.add(new Contacto(
                                        responseObj.getString("gender"),
                                        responseObj.getString("title"),
                                        responseObj.getString("firstName"),
                                        responseObj.getString("lastName"),
                                        responseObj.getString("street"),
                                        responseObj.getString("province"),
                                        responseObj.getString("city"),
                                        responseObj.getString("country"),
                                        responseObj.getString("postalCode"),
                                        coordenadas,
                                        responseObj.getString("timeZone"),
                                        responseObj.getString("timeDesc"),
                                        responseObj.getString("email"),
                                        responseObj.getString("userName"),
                                        responseObj.getString("birthDay"),
                                        responseObj.getString("age"),
                                        responseObj.getString("landLinePhone"),
                                        responseObj.getString("phoneNumber"),
                                        responseObj.getString("urlImage")
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
