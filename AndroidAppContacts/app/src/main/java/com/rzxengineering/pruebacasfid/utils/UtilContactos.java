package com.rzxengineering.pruebacasfid.utils;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import com.rzxengineering.pruebacasfid.objetos.Contacto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class UtilContactos {
    public boolean addContacto(Context context, Contacto contacto) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Creamos un contacto vacio
        int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // Le ponemos el nombre
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        contacto.getNombre())
                .build());

        // Le ponemos el numero de telefono
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        contacto.getTelefonoMovil())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        //Le ponemos el email
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS,
                        contacto.getEmail()).build());
        //Le añadimos una foto
        try {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Photo.PHOTO,
                        getBytes(new URL(contacto.getUrlImagen()).openStream())).build());



            // Añadimos el contacto
            ContentProviderResult[] results = context.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, ops);

            // Y lo mostramos por el Log
            long contactId = ContentUris.parseId(results[0].uri);
            String contactUri = ContactsContract.Contacts
                    .getLookupUri(contactId, ContactsContract.Contacts.LOOKUP_KEY).toString();
            Log.d("Contacto", "Nuevo contacto " + contactUri);

            return true;

        } catch (RemoteException | OperationApplicationException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private byte[] getBytes(InputStream inputStream){
        try{
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] byteArray = new byte[1024];
            int len;
            while((len = inputStream.read(byteArray)) > 0)
                buffer.write(byteArray, 0, len);
            return buffer.toByteArray();
        }catch (Exception e){
            Log.d("ERROR",e.getLocalizedMessage());
        }
        return new byte[0];
    }
}
