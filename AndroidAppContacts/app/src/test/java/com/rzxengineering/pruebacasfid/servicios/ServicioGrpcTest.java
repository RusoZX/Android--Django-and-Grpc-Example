package com.rzxengineering.pruebacasfid.servicios;

import com.rzxengineering.pruebacasfid.objetos.Contacto;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import generated.General;

public class ServicioGrpcTest extends TestCase {

    ServicioGrpc underTest = new ServicioGrpc();
    public void testGuardarContacto() {
        Contacto contacto = new Contacto("idk","idk","idk","idk",
                "idk","idk","idk",
                "idk","idk", new ArrayList<>(List.of("idk","idk")),
                "idk","idk","idk","idk","idk",
                "idk","idk","idk","idk");

        assertEquals(General.StatusCode.OK,underTest.guardarContacto(contacto));
    }
    @Test
    public void testConseguirContactos() {
        int cantidad = 2;
        ArrayList<Contacto> esperado =new ArrayList<>();
        esperado.add(
                new Contacto(
                        "Male",
                        "Mr.",
                        "John",
                        "Doe",
                        "123 Main St",
                        "Ontario",
                        "Toronto",
                        "Canada",
                        "M1R 3A5",
                        new ArrayList<>(List.of("43.6532 N","79.3832 W")),
                        "Eastern Time (ET)",
                        "-0500",
                        "john.doe@example.com",
                        "johndoe",
                        "1980-01-01",
                        "23",
                        "416-555-1234",
                        "416-555-5678",
                        "https://example.com/johndoe.jpg"));
        esperado.add(
                new Contacto(
                        "Female",
                        "Ms.",
                        "Jane",
                        "Smith",
                        "456 Oak Ave",
                        "British Columbia",
                        "Vancouver",
                        "Canada",
                        "V6Z 1L3",
                        new ArrayList<>(List.of("49.2827 N","123.1207 W")),
                        "Pacific Time (PT)",
                        "-0800",
                        "jane.smith@example.com",
                        "janesmith",
                        "1990-02-14",
                        "25",
                        "604-555-2345",
                        "604-555-6789",
                        "https://example.com/janesmith.jpg"));

        ArrayList<Contacto> recibido = underTest.conseguirContactos(cantidad);

        assertEquals(esperado,recibido);
    }
}