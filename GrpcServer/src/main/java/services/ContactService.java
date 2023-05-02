package services;
import generated.General;
import generated.serviceContactGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ContactService extends serviceContactGrpc.serviceContactImplBase{
    DataBase db = new DataBase();

    @Override
    public void saveContact(General.ClientPetitionSaveContact request,
                            StreamObserver<General.ServerResponseSaveContact> responseObserver) {
        System.out.println("<-------------------------SAVE CONTACT REQUEST-------------------------------------->");
        General.ServerResponseSaveContact response;
        try{
            System.out.println("Contact: "+request.getContact().toString());
            General.Contact contact = request.getContact();
            if(contact.equals(General.Contact.newBuilder().build())) {
                response = General.ServerResponseSaveContact.newBuilder()
                        .setStatusCode(General.StatusCode.SERVER_ERROR)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }else {
                db.insert("Insert into contacts(gender,title, firstName,lastName,street,province,city,country," +
                        "postalCode,longitudeCoor,latitudeCoor,timeZone,timeDesc,email,userName,birthDay,age,landLinePhone," +
                        "phoneNumber, urlImage) values('" + contact.getGender() + "','" + contact.getTitle() + "','"
                        + contact.getFirstName() + "','" + contact.getLastName() + "','" + contact.getStreet() + "','"
                        + contact.getProvince() + "','" + contact.getCity() + "','" + contact.getCountry() + "','"
                        + contact.getPostalCode() + "','" + contact.getLongitudeCoor() + "','" + contact.getLatitudeCoor() + "','"
                        + contact.getTimeZone() + "','" + contact.getTimeDesc() + "','" + contact.getEmail() + "','"
                        + contact.getUserName() + "','" + contact.getBirthDay() + "','" + contact.getAge() + "','"
                        + contact.getLandLinePhone() + "','" + contact.getPhoneNumber() + "','" + contact.getUrlImage() + "');");

                response = General.ServerResponseSaveContact.newBuilder().setStatusCode(General.StatusCode.OK).build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }catch(Exception e){
            System.out.println("/////////////////////////////////SAVE CONTACT ERROR//////////////////////////////////\n"+
                    e.getLocalizedMessage());
            //As there was some kind of error we create a response with and Status Code explaining to the Client that
            //there was an error in the server
            response = General.ServerResponseSaveContact.newBuilder()
                    .setStatusCode(General.StatusCode.SERVER_ERROR)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getContacts(General.ClientPetitionGetContacts request,
                            StreamObserver<General.ServerResponseGetContacts> responseObserver) {
        System.out.println("<-------------------------GET CONTACTS REQUEST-------------------------------------->");
        General.ServerResponseGetContacts response;
        try{
            System.out.println("Quantity "+request.getQuantity());
            ArrayList<ArrayList<Object>> result= new ArrayList<ArrayList<Object>>();
            result = db.consult("Select gender,title, firstName,lastName,street,province,city,country," +
                    "postalCode,longitudeCoor,latitudeCoor,timeZone,timeDesc,email,userName,birthDay,age," +
                    "landLinePhone,phoneNumber, urlImage from contacts limit "+request.getQuantity());
            System.out.println("RESULT_>>>>>>>>>>>>>>"+result);
            ArrayList<General.Contact> contacts = new ArrayList<>();
            Iterator<ArrayList<Object>> iterator = result.iterator();
            while(iterator.hasNext()){
                ArrayList<Object> row = iterator.next();
                contacts.add(General.Contact.newBuilder()
                        .setGender(row.get(0).toString())
                        .setTitle(row.get(1).toString())
                        .setFirstName(row.get(2).toString())
                        .setLastName(row.get(3).toString())
                        .setStreet(row.get(4).toString())
                        .setProvince(row.get(5).toString())
                        .setCity(row.get(6).toString())
                        .setCountry(row.get(7).toString())
                        .setPostalCode(row.get(8).toString())
                        .setLongitudeCoor(row.get(9).toString())
                        .setLatitudeCoor(row.get(10).toString())
                        .setTimeZone(row.get(11).toString())
                        .setTimeDesc(row.get(12).toString())
                        .setEmail(row.get(13).toString())
                        .setUserName(row.get(14).toString())
                        .setBirthDay(row.get(15).toString())
                        .setAge(row.get(16).toString())
                        .setLandLinePhone(row.get(17).toString())
                        .setPhoneNumber(row.get(18).toString())
                        .setUrlImage(row.get(19).toString())
                    .build());
            }

            response = General.ServerResponseGetContacts.newBuilder()
                    .setStatusCode(General.StatusCode.OK)
                    .addAllContacts(contacts)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch(Exception e){
            System.out.println("/////////////////////////////////GET CONTACTS ERROR//////////////////////////////////\n"+
                    e.getLocalizedMessage());
            //As there was some kind of error we create a response with and Status Code explaining to the Client that
            //there was an error in the server
            response = General.ServerResponseGetContacts.newBuilder()
                    .setStatusCode(General.StatusCode.SERVER_ERROR)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
