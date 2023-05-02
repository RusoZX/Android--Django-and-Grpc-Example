package com.rzxengineering.pruebacasfid.servicios;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.rzxengineering.pruebacasfid.objetos.Contacto;


import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
public class ServicioDjangoTest {
    @Mock
    ServicioDjango underTest;
    AutoCloseable autoCloseable;
    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ServicioDjango();
    }
    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    public void guardarContacto() {
        Contacto contacto = new Contacto("idk3","idk3","idk3","idk3",
                "idk3","idk3","idk3",
                "idk3","idk3", new ArrayList<>(List.of("idk3","idk3")),
                "idk3","idk3","idk3","idk3","idk3",
                "idk3","idk3","idk3","idk3");
        String expected = "201";
        String actualResult = underTest.guardarContacto(contacto);

        assertEquals(expected,actualResult);
    }


}