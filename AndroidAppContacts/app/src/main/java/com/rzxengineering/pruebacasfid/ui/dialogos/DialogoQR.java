package com.rzxengineering.pruebacasfid.ui.dialogos;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rzxengineering.pruebacasfid.R;
import com.rzxengineering.pruebacasfid.objetos.Contacto;

public class DialogoQR extends DialogFragment {
    ImageView qr;
    Button volver;
    Contacto contacto;
    public DialogoQR(Contacto contacto){
        this.contacto = contacto;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_qr, container, false);
        qr = view.findViewById(R.id.qrIV);
        setQR();
        volver = view.findViewById(R.id.btnDialogoVolver);
        volver.setOnClickListener(v->{
            dismiss();
        });

        return view;
    }
    private void setQR(){
        String vcardString = "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "N:"+contacto.getNombre()+";"+contacto.getApellidos()+
                ";;"+contacto.getTitulo()+";\n" +
                "FN:"+contacto.getNombre()+" "+contacto.getApellidos()+"\n" +
                "ORG:IDK;\n" +
                "TITLE:IDK\n" +
                "TEL;TYPE=WORK,VOICE:"+contacto.getTelefonoMovil()+"\n" +
                "ADR;TYPE=WORK:;;"+contacto.getCalle()+";"+contacto.getCiudad()
                +";"+contacto.getProvincia()+";"+contacto.getCodigoPostal()
                +";"+contacto.getPais()+"\n" +
                "EMAIL:"+contacto.getEmail()+"\n" +
                "END:VCARD";
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(vcardString, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qr.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
