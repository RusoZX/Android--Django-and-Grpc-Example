package com.rzxengineering.pruebacasfid.viewmodels;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.rzxengineering.pruebacasfid.manager.Manager;
import com.rzxengineering.pruebacasfid.objetos.Contacto;

import java.util.ArrayList;

public class ContactosViewModel extends ViewModel{
    public final MutableLiveData<Integer> cantidad = new MutableLiveData<>();
    private Context contexto;
    public void setContexto(Context contexto){
        this.contexto=contexto;
    }
    public final LiveData<ArrayList<Contacto>> respuesta = Transformations.switchMap(cantidad, new Function<Integer, LiveData<ArrayList<Contacto>>>() {
        @Override
        public LiveData<ArrayList<Contacto>> apply(Integer input) {
            try {
                return new Manager(contexto).getContactos(input);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    });
    public LiveData<ArrayList<Contacto>> getRespuesta(){
        return respuesta;
    }

    public void setCantidad(int cantidad){
        if(this.cantidad.getValue() == null){
            this.cantidad.setValue(cantidad);
        }else if(this.cantidad.getValue() != cantidad){
            this.cantidad.setValue(cantidad);
        }

    }
}
