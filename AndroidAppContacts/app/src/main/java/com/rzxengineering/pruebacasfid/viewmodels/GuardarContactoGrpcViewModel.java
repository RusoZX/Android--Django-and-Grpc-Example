package com.rzxengineering.pruebacasfid.viewmodels;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.rzxengineering.pruebacasfid.manager.Manager;
import com.rzxengineering.pruebacasfid.objetos.Contacto;

import generated.General;

public class GuardarContactoGrpcViewModel extends ViewModel {
    public final MutableLiveData<Contacto> contacto = new MutableLiveData<>();
    private Context contexto;
    public void setContexto(Context contexto){
        this.contexto=contexto;
    }
    public final LiveData<General.StatusCode> respuesta = Transformations.switchMap(contacto,
            new Function<Contacto, LiveData<General.StatusCode>>() {
        @Override
        public LiveData<General.StatusCode> apply(Contacto input) {
            try {
                return new Manager(contexto).guardarContactoGrpc(input);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    });
    public LiveData<General.StatusCode> getRespuesta(){
        return respuesta;
    }

    public void setContacto(Contacto contacto){
        if(this.contacto.getValue() == null){
            this.contacto.setValue(contacto);
        }else if(this.contacto.getValue() != contacto){
            this.contacto.setValue(contacto);
        }

    }


}
