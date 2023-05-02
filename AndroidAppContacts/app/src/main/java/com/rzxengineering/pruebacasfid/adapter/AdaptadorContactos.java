package com.rzxengineering.pruebacasfid.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rzxengineering.pruebacasfid.BR;
import com.rzxengineering.pruebacasfid.R;
import com.rzxengineering.pruebacasfid.databinding.CardContactoBinding;
import com.rzxengineering.pruebacasfid.objetos.Contacto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorContactos extends RecyclerView.Adapter<AdaptadorContactos.MyViewHolder> {

    private ArrayList<Contacto> lista;
    private OnContactoListener listener;

    public AdaptadorContactos(ArrayList<Contacto> lista, OnContactoListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @Override
    public AdaptadorContactos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardContactoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.card_contacto,parent,false);
        return new MyViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacto contacto = lista.get(position);
        holder.bind(contacto);
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CardContactoBinding binding;
        public OnContactoListener listener;

        public MyViewHolder(CardContactoBinding binding, OnContactoListener listener){
            super(binding.getRoot());
            this.binding=binding;
            this.listener=listener;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactoListener(getAdapterPosition());
                }
            });
        }
        public void bind(Object obj){
            binding.setVariable(BR.contacto, obj);
            binding.executePendingBindings();

            Contacto contacto = (Contacto) obj;
            Picasso.get().load(contacto.getUrlImagen()).into(binding.imagenCardContacto);
        }

    }
    public interface OnContactoListener{
        void onContactoListener(int position);
    }
}
