package com.rzxengineering.pruebacasfid.servicios;

import com.rzxengineering.pruebacasfid.objetos.Contacto;

import java.util.ArrayList;
import java.util.Iterator;

import generated.serviceContactGrpc;
import generated.General;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ServicioGrpc {
    ManagedChannel channel=ManagedChannelBuilder.forAddress("192.168.0.11",9090)
            .usePlaintext().build();

    public General.StatusCode guardarContacto(Contacto contacto){
        serviceContactGrpc.serviceContactBlockingStub stub =
                serviceContactGrpc.newBlockingStub(channel);
        General.ClientPetitionSaveContact peticion = General.ClientPetitionSaveContact.newBuilder().setContact(
                General.Contact.newBuilder()
                        .setGender(contacto.getGenero())
                        .setTitle(contacto.getTitulo())
                        .setFirstName(contacto.getNombre())
                        .setLastName(contacto.getApellidos())
                        .setStreet(contacto.getCalle())
                        .setProvince(contacto.getProvincia())
                        .setCity(contacto.getCiudad())
                        .setCountry(contacto.getPais())
                        .setPostalCode(contacto.getCodigoPostal())
                        .setLongitudeCoor(contacto.getCoordenadas().get(1))
                        .setLatitudeCoor(contacto.getCoordenadas().get(0))
                        .setTimeZone(contacto.getZonaHoraria())
                        .setTimeDesc(contacto.getDescripcionZonaHoraria())
                        .setEmail(contacto.getEmail())
                        .setUserName(contacto.getUserName())
                        .setBirthDay(contacto.getNacimiento())
                        .setLandLinePhone(contacto.getTelefonoFijo())
                        .setPhoneNumber(contacto.getTelefonoMovil())
                        .setUrlImage(contacto.getUrlImagen())
                        .setAge(contacto.getEdad())
                        .build()
        ).build();

        General.ServerResponseSaveContact respuesta = stub.saveContact(peticion);
        channel.shutdown();
        return respuesta.getStatusCode();
    }
    public ArrayList<Contacto> conseguirContactos(int cantidad){
        serviceContactGrpc.serviceContactBlockingStub stub =
                serviceContactGrpc.newBlockingStub(channel);
        General.ClientPetitionGetContacts peticion = General.ClientPetitionGetContacts.newBuilder()
                .setQuantity(cantidad).build();
        General.ServerResponseGetContacts respuesta = stub.getContacts(peticion);
        channel.shutdown();
        Iterator<General.Contact> iterator = respuesta.getContactsList().iterator();
        ArrayList<Contacto> res = new ArrayList<>();
        while(iterator.hasNext()){
            General.Contact contacto = iterator.next();
            ArrayList<String> coor = new ArrayList<>();
            coor.add(contacto.getLongitudeCoor());
            coor.add(contacto.getLatitudeCoor());
            res.add(new Contacto(contacto.getGender(),contacto.getTitle(),contacto.getFirstName(),
                    contacto.getLastName(),contacto.getStreet(),contacto.getProvince(),
                    contacto.getCity(),contacto.getCountry(),contacto.getPostalCode(),coor,
                    contacto.getTimeZone(),contacto.getTimeDesc(),contacto.getEmail(),
                    contacto.getUserName(),contacto.getBirthDay(), contacto.getAge(),
                    contacto.getLandLinePhone(), contacto.getPhoneNumber(),contacto.getUrlImage()
            ));
        }
        if(respuesta.getStatusCode().equals(General.StatusCode.SERVER_ERROR))
            res.add(new Contacto(true));
        return res;
    }
}
