import io.grpc.ServerBuilder;
import services.ContactService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcServer {
    io.grpc.Server srv;
    int port;
    //We create the constructor of the server in which we indicate the port and the services that it will use
    public GrpcServer(int port){
        this.port = port;
        srv = ServerBuilder.forPort(port)
                .addService( new ContactService())
                .build();
    }
    //Here we create a method to start the server
    public void start() {
        try {
            srv.start();
            System.out.println("Starting the server listening to the  "+port +" port");
            srv.awaitTermination();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(GrpcServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Here we create a method to shutdown the server
    public void stop() {
        srv.shutdown();
    }
    //Here we create a method for the server to work until shutdown
    public void blockUntilShutdown() {
        try {
            srv.awaitTermination();
        } catch (InterruptedException ex) {
            Logger.getLogger(GrpcServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Here we start the server
    public static void main(String[] args) {
        GrpcServer server = new GrpcServer(9090);

        server.start();
        server.blockUntilShutdown();
    }
}
