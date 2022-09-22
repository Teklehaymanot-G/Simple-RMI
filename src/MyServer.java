
import java.rmi.*;
import java.rmi.registry.*;

public class MyServer {
    public static void main(String args[]){       
        
        try{            
            FunctionsInterface stub=new RemoteFunctions();
            LocateRegistry.createRegistry(1099);
            Registry regisry=LocateRegistry.getRegistry();
            regisry.rebind("server", stub);
            Naming.rebind("localhost", stub);
            
//            Naming.rebind("rmi://localhost:1099/",stub);  
            System.out.println("Server is running on localhost");
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
