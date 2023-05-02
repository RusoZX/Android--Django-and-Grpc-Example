package services;

import generated.General;
import io.grpc.stub.StreamObserver;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest extends TestCase {
    @Mock
    private StreamObserver<General.ServerResponseGetContacts> responseObserverGetContacts;
    @Mock
    private StreamObserver<General.ServerResponseSaveContact> responseObserverSaveContact;

    private ContactService contactService;

    @Before
    public void setUp() {
        contactService = new ContactService();
    }

    @Test
    public void testGetContactsWithCorrectData() {
        // Create a request object with the desired quantity
        General.ClientPetitionGetContacts request = General.ClientPetitionGetContacts.newBuilder().setQuantity(2).build();

        // Create a list of expected contacts
        ArrayList<General.Contact> expectedContacts = new ArrayList<>();
        expectedContacts.add(General.Contact.newBuilder()
                .setGender("Male")
                .setTitle("Mr.")
                .setFirstName("John")
                .setLastName("Doe")
                .setStreet("123 Main St")
                .setProvince("Ontario")
                .setCity("Toronto")
                .setCountry("Canada")
                .setPostalCode("M1R 3A5")
                .setLongitudeCoor("43.6532 N")
                .setLatitudeCoor("79.3832 W")
                .setTimeZone("Eastern Time (ET)")
                .setTimeDesc("-0500")
                .setEmail("john.doe@example.com")
                .setUserName("johndoe")
                .setBirthDay("1980-01-01")
                .setLandLinePhone("416-555-1234")
                .setPhoneNumber("416-555-5678")
                .setAge("23")
                .setUrlImage("https://example.com/johndoe.jpg")
                .build());
        expectedContacts.add(General.Contact.newBuilder()
                .setGender("Female")
                .setTitle("Ms.")
                .setFirstName("Jane")
                .setLastName("Smith")
                .setStreet("456 Oak Ave")
                .setProvince("British Columbia")
                .setCity("Vancouver")
                .setCountry("Canada")
                .setPostalCode("V6Z 1L3")
                .setLongitudeCoor("49.2827 N")
                .setLatitudeCoor("123.1207 W")
                .setTimeZone("Pacific Time (PT)")
                .setTimeDesc("-0800")
                .setEmail("jane.smith@example.com")
                .setUserName("janesmith")
                .setBirthDay("1990-02-14")
                .setLandLinePhone("604-555-2345")
                .setPhoneNumber("604-555-6789")
                .setAge("25")
                .setUrlImage("https://example.com/janesmith.jpg")
                .build());
        System.out.println(expectedContacts);
        // Invoke the getContacts method with the request object and mock response observer
        contactService.getContacts(request, responseObserverGetContacts);

        // Verify that the response observer is called with the expected response object
        verify(responseObserverGetContacts).onNext(General.ServerResponseGetContacts.newBuilder()
                .setStatusCode(General.StatusCode.OK)
                .addAllContacts(expectedContacts)
                .build());
        verify(responseObserverGetContacts).onCompleted();
    }
    @Test
    public void testGetContactsWithBadData() {
        // Create a request object with bad quantity
        General.ClientPetitionGetContacts request =
                General.ClientPetitionGetContacts.newBuilder().setQuantity(-1).build();


        contactService.getContacts(request, responseObserverGetContacts);

        // Verify that the response observer is called with the expected response object
        verify(responseObserverGetContacts).onNext(General.ServerResponseGetContacts.newBuilder()
                .setStatusCode(General.StatusCode.SERVER_ERROR)
                .build());
        verify(responseObserverGetContacts).onCompleted();
    }
    @Test
    public void testSaveContactWithCorrectData() {
        //We create the request with the contact to insert
        General.ClientPetitionSaveContact request = General.ClientPetitionSaveContact.newBuilder()
                .setContact(General.Contact.newBuilder()
                    .setGender("Male")
                    .setTitle("Mr.")
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setStreet("123 Main St")
                    .setProvince("Ontario")
                    .setCity("Toronto")
                    .setCountry("Canada")
                    .setPostalCode("M1R 3A5")
                    .setLongitudeCoor("43.6532 N")
                    .setLatitudeCoor("79.3832 W")
                    .setTimeZone("Eastern Time (ET)")
                    .setTimeDesc("-0500")
                    .setEmail("john.doe@example.com")
                    .setUserName("example01")
                    .setBirthDay("1980-01-01")
                    .setLandLinePhone("416-555-1234")
                    .setPhoneNumber("416-555-5678")
                    .setAge("23")
                    .setUrlImage("https://example.com/johndoe.jpg").build()
                ).build();
        contactService.saveContact(request, responseObserverSaveContact);
        // Verify that the response observer is called with the expected response object
        verify(responseObserverSaveContact).onNext(General.ServerResponseSaveContact.newBuilder()
                .setStatusCode(General.StatusCode.OK)
                .build());
        verify(responseObserverSaveContact).onCompleted();
        //now we get the object from the db and assert them
        DataBase db = new DataBase();
        try {
            ArrayList<ArrayList<Object>> res = db.consult("select gender,title, firstName,lastName,street," +
                    "province,city,country,postalCode,longitudeCoor,latitudeCoor,timeZone,timeDesc,email,userName,birthDay," +
                    "landLinePhone,phoneNumber,age, urlImage from contacts where username = 'example01'");
            General.Contact contactInserted = General.Contact.newBuilder()
                            .setGender(res.get(0).get(0).toString())
                            .setTitle(res.get(0).get(1).toString())
                            .setFirstName(res.get(0).get(2).toString())
                            .setLastName(res.get(0).get(3).toString())
                            .setStreet(res.get(0).get(4).toString())
                            .setProvince(res.get(0).get(5).toString())
                            .setCity(res.get(0).get(6).toString())
                            .setCountry(res.get(0).get(7).toString())
                            .setPostalCode(res.get(0).get(8).toString())
                            .setLongitudeCoor(res.get(0).get(9).toString())
                            .setLatitudeCoor(res.get(0).get(10).toString())
                            .setTimeZone(res.get(0).get(11).toString())
                            .setTimeDesc(res.get(0).get(12).toString())
                            .setEmail(res.get(0).get(13).toString())
                            .setUserName(res.get(0).get(14).toString())
                            .setBirthDay(res.get(0).get(15).toString())
                            .setLandLinePhone(res.get(0).get(16).toString())
                            .setPhoneNumber(res.get(0).get(17).toString())
                            .setAge(res.get(0).get(18).toString())
                            .setUrlImage(res.get(0).get(19).toString()).build();
            assertEquals(request.getContact(),contactInserted);
            db.modify("delete from contacts where username = 'example01'");
        }catch (Exception e){
            fail("Exception caught: " + e.getMessage());
        }
    }
    @Test
    public void testSaveContactWithBadData() {
        // Create a request object with bad quantity
        General.ClientPetitionSaveContact request =
                General.ClientPetitionSaveContact.newBuilder().build();


        contactService.saveContact(request, responseObserverSaveContact);

        // Verify that the response observer is called with the expected response object
        verify(responseObserverSaveContact).onNext(General.ServerResponseSaveContact.newBuilder()
                .setStatusCode(General.StatusCode.SERVER_ERROR)
                .build());
        verify(responseObserverSaveContact).onCompleted();
    }

}