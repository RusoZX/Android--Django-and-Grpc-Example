package com.rzxengineering.pruebacasfid.manager;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rzxengineering.pruebacasfid.objetos.Contacto;
import com.rzxengineering.pruebacasfid.servicios.ServicioContactos;
import com.rzxengineering.pruebacasfid.servicios.ServicioDjango;
import com.rzxengineering.pruebacasfid.servicios.ServicioGrpc;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import generated.General;


public class Manager {
    Context contexto;

    public Manager(Context contexto) {
        this.contexto = contexto;
    }

    public LiveData<ArrayList<Contacto>> getContactos(int cantidad) throws Exception{
        Executor executor = Executors.newSingleThreadExecutor();
        MutableLiveData<ArrayList<Contacto>> liveData = new MutableLiveData<>();
        LiveData<ArrayList<Contacto>> result;
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    liveData.postValue(
                            new ServicioContactos(contexto).conseguirResultadoContactos(cantidad)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result = liveData;
    }
    public LiveData<ArrayList<Contacto>> getContactosDjango(int cantidad) throws Exception{
        Executor executor = Executors.newSingleThreadExecutor();
        MutableLiveData<ArrayList<Contacto>> liveData = new MutableLiveData<>();
        LiveData<ArrayList<Contacto>> result;
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    liveData.postValue(
                            new ServicioDjango().conseguirContactos(cantidad, contexto)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result = liveData;
    }
    public LiveData<String> guardarContactoDjango(Contacto contacto) throws Exception{
        Executor executor = Executors.newSingleThreadExecutor();
        MutableLiveData<String> liveData = new MutableLiveData<>();
        LiveData<String> result;
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    liveData.postValue(
                            new ServicioDjango().guardarContacto(contacto)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result = liveData;
    }
    public LiveData<ArrayList<Contacto>> getContactosGrpc(int cantidad) throws Exception{
        Executor executor = Executors.newSingleThreadExecutor();
        MutableLiveData<ArrayList<Contacto>> liveData = new MutableLiveData<>();
        LiveData<ArrayList<Contacto>> result;
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    liveData.postValue(
                            new ServicioGrpc().conseguirContactos(cantidad)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result = liveData;
    }
    public LiveData<General.StatusCode> guardarContactoGrpc(Contacto contacto) throws Exception{
        Executor executor = Executors.newSingleThreadExecutor();
        MutableLiveData<General.StatusCode> liveData = new MutableLiveData<>();
        LiveData<General.StatusCode> result;
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    liveData.postValue(
                            new ServicioGrpc().guardarContacto(contacto)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result = liveData;
    }



}
