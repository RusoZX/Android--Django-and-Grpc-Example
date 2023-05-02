package com.rzxengineering.pruebacasfid.ui.fragmentos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.rzxengineering.pruebacasfid.R;
import com.rzxengineering.pruebacasfid.databinding.FragmentoContactoBinding;
import com.rzxengineering.pruebacasfid.identificadores.Identificadores;
import com.rzxengineering.pruebacasfid.objetos.Contacto;
import com.rzxengineering.pruebacasfid.ui.dialogos.DialogoQR;
import com.rzxengineering.pruebacasfid.utils.UtilContactos;
import com.rzxengineering.pruebacasfid.viewmodels.GuardarContactoDjangoViewModel;
import com.rzxengineering.pruebacasfid.viewmodels.GuardarContactoGrpcViewModel;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import generated.General;

public class FragmentoContacto extends Fragment {
    NavController navController;
    FragmentoContactoBinding binding;
    Contacto contacto;
    ImageButton guardarContacto;
    ImageButton verMapa;
    ImageButton btnQr;
    Button btnEnviarGrpc;
    Button btnEnviarDjango;

    GuardarContactoDjangoViewModel guardarContactoDjangoVM;
    GuardarContactoGrpcViewModel guardarContactoGrpcVM;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public FragmentoContacto(){
        super(R.layout.fragmento_contacto);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragmento_contacto,container,
                false);
        if(requireArguments().getSerializable(new Identificadores().getObjeto()) != null) {
            contacto =(Contacto)requireArguments()
                    .getSerializable(new Identificadores().getObjeto());
                    binding.setContacto(contacto);
            Picasso.get().load(contacto.getUrlImagen()).into(binding.imagenContacto);
        }else{
            alert(getResources().getString(R.string.errorServidor));
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.executePendingBindings();
        guardarContactoDjangoVM = new ViewModelProvider(this).get(GuardarContactoDjangoViewModel.class);
        guardarContactoDjangoVM.setContexto(this.getContext());
        guardarContactoGrpcVM = new ViewModelProvider(this).get(GuardarContactoGrpcViewModel.class);
        guardarContactoGrpcVM.setContexto(this.getContext());
        guardarContacto = view.findViewById(R.id.btnGuardarContacto);

        try{
            final Observer<String> observer = respuesta -> {
                if(respuesta.equals("201"))
                    alert(getResources().getString(R.string.ok));
                else
                    alert(getResources().getString(R.string.errorServidor));
            };
            guardarContactoDjangoVM.getRespuesta().observe(getViewLifecycleOwner(),observer);
        }catch (Exception e){
            alert(getResources().getString(R.string.errorServidor));
        }

        try{
            final Observer<General.StatusCode> observer = respuesta -> {
                if(respuesta.equals(General.StatusCode.SERVER_ERROR))
                    alert(getResources().getString(R.string.errorServidor));
                else
                    alert(getResources().getString(R.string.ok));
            };
            guardarContactoGrpcVM.getRespuesta().observe(getViewLifecycleOwner(),observer);
        }catch (Exception e){
            alert(getResources().getString(R.string.errorServidor));
        }

        guardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pedirPermisos())
                    guardarContacto();
                else
                    Toast.makeText(requireContext(),
                            getResources().getString(R.string.errorPermisos),
                            Toast.LENGTH_LONG).show();

            }
        });
        verMapa = view.findViewById(R.id.btnVerMapa);
        verMapa.setOnClickListener(v->{
            abrirMapa();
        });
        btnQr = view.findViewById(R.id.btnQR);
        btnQr.setOnClickListener(v->{
            setDialogo();
        });
        btnEnviarGrpc = view.findViewById(R.id.btnEnviarDatosGrpc);
        btnEnviarGrpc.setOnClickListener(v->{
            guardarContactoGrpcVM.setContacto(contacto);
        });
        btnEnviarDjango = view.findViewById(R.id.btnEnviarDatosDjango);
        btnEnviarDjango.setOnClickListener(v->{
            guardarContactoDjangoVM.setContacto(contacto);
        });
    }
    private void alert(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setCancelable(false);
        alert.setTitle("");
        alert.setMessage(msg);
        alert.setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                navController.popBackStack(R.id.fragmentoListadoContactos, false);
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
    private void guardarContacto(){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UtilContactos util = new UtilContactos();
                    boolean res =util.addContacto(requireContext(),contacto);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (res) {
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
    private void abrirMapa(){
        Uri gmmIntentUri = Uri.parse("geo:" + contacto.getCoordenadas().get(0)
                + "," + contacto.getCoordenadas().get(1) + "?q=" + Uri.encode(contacto.getCiudad()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
    private void setDialogo(){
        DialogoQR dialogo = new DialogoQR(contacto);
        dialogo.show(getParentFragmentManager(),"QR");
    }

}
