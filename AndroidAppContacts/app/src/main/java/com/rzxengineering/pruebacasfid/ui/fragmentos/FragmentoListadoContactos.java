package com.rzxengineering.pruebacasfid.ui.fragmentos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzxengineering.pruebacasfid.R;
import com.rzxengineering.pruebacasfid.adapter.AdaptadorContactos;
import com.rzxengineering.pruebacasfid.identificadores.Identificadores;
import com.rzxengineering.pruebacasfid.objetos.Contacto;
import com.rzxengineering.pruebacasfid.utils.UtilContactos;
import com.rzxengineering.pruebacasfid.viewmodels.ContactosDjangoViewModel;
import com.rzxengineering.pruebacasfid.viewmodels.ContactosGrpcViewModel;
import com.rzxengineering.pruebacasfid.viewmodels.ContactosViewModel;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FragmentoListadoContactos extends Fragment {
    NavController navController;
    ContactosViewModel contactosVM;
    RecyclerView recyclerView;
    AdaptadorContactos adaptador;
    ArrayList<Contacto> listaContactos;

    Spinner spinnerCantidad;
    ImageButton btnCantidad;
    Button btnGuardar;

    Button btnContactosGrpc;
    Button btnContactosDjango;
    ContactosDjangoViewModel djangoViewModel;
    ContactosGrpcViewModel grpcViewModel;
    public FragmentoListadoContactos() {
        super(R.layout.fragmento_lista_contactos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactosVM= new ViewModelProvider(this).get(ContactosViewModel.class);
        contactosVM.setContexto(this.getContext());
        djangoViewModel= new ViewModelProvider(this).get(ContactosDjangoViewModel.class);
        djangoViewModel.setContexto(this.getContext());
        grpcViewModel= new ViewModelProvider(this).get(ContactosGrpcViewModel.class);
        grpcViewModel.setContexto(this.getContext());

        navController = Navigation.findNavController(view);
        prepararSpinner();


        contactosVM.setCantidad(5);
        try{
            final Observer<ArrayList<Contacto>> observer = lista -> {
                listaContactos=lista;
                if(lista.get(0).getErrorServidor())
                    alert(getResources().getString(R.string.errorServidor));
                else{
                    setDecorador();
                    setAdaptador();
                }
            };
            contactosVM.getRespuesta().observe(getViewLifecycleOwner(),observer);
            djangoViewModel.getRespuesta().observe(getViewLifecycleOwner(),observer);
            grpcViewModel.getRespuesta().observe(getViewLifecycleOwner(),observer);
        }catch (Exception e){
            alert(getResources().getString(R.string.errorServidor));
        }

        btnContactosDjango = view.findViewById(R.id.btnMostrarDjango);
        btnContactosDjango.setOnClickListener(v -> {
            djangoViewModel.setCantidad(Integer.parseInt(spinnerCantidad.getSelectedItem().toString()));
        });

        btnContactosGrpc = view.findViewById(R.id.btnMostrarGrpc);
        btnContactosGrpc.setOnClickListener(v -> {
            grpcViewModel.setCantidad(Integer.parseInt(spinnerCantidad.getSelectedItem().toString()));
        });

        btnCantidad = view.findViewById(R.id.btnCambiarCantidad);
        btnCantidad.setOnClickListener(v -> {
            contactosVM.setCantidad(Integer.parseInt(spinnerCantidad.getSelectedItem().toString()));
        });
        btnGuardar = view.findViewById(R.id.btnGuardarContactos);
        btnGuardar.setOnClickListener(v ->{
            guardarContactos();
        });

    }
    private void prepararSpinner(){
        spinnerCantidad = requireActivity().findViewById(R.id.cantidadSpinner);
        String[] listaStrings=new String[]{"5","25","50","75","100"};
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, listaStrings);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCantidad.setAdapter(adaptadorSpinner);
    }
    private void setAdaptador(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adaptador = new AdaptadorContactos(listaContactos,new ContactoListener());
        recyclerView.setAdapter(adaptador);
    }
    private void setDecorador(){
        recyclerView= requireView().findViewById(R.id.recyViewContactos);
        recyclerView.addItemDecoration(
                new MarginItemDecoration(10)
        );

    }
    private void alert(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setCancelable(false);
        alert.setTitle("");
        alert.setMessage(msg);
        alert.setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
    private boolean pedirPermisos(){
        final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 100;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    PERMISSIONS_REQUEST_WRITE_CONTACTS);
            return false;
        }
        else return true;
    }
    private void guardarContactos(){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean res;
                    boolean error = false;
                    UtilContactos util = new UtilContactos();
                    for(Contacto contacto : listaContactos) {
                       res = util.addContacto(requireContext(), contacto);
                       if(!res)
                           error = true;
                    }
                    boolean finalError = error;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!finalError) {
                                alert(getResources().getString(R.string.contactoGuardado));
                            } else {
                                alert(getResources().getString(R.string.errorGuradarContacto));
                            }
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public class MarginItemDecoration extends RecyclerView.ItemDecoration {
        private int spaceHeight;

        public MarginItemDecoration(int spaceHeight) {
            this.spaceHeight = spaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = spaceHeight;
            }
            outRect.left = spaceHeight;
            outRect.right = spaceHeight;
            outRect.bottom = spaceHeight;
        }
    }
    class ContactoListener implements AdaptadorContactos.OnContactoListener {

        @Override
        public void onContactoListener(int position) {
            Bundle bundle= new Bundle();
            bundle.putSerializable(new Identificadores().getObjeto(),listaContactos.get(position));
            navController.navigate(R.id.action_fragmentoListadoContactos_to_fragmentoContacto, bundle);
        }
    }
}
