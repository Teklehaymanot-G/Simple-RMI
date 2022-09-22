
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
public interface FunctionsInterface extends Remote{
    
    public void register(String id, String name) throws RemoteException;
//    public Object test() throws RemoteException;
    public String view_student() throws RemoteException;
    public boolean check_id_in_info(String id) throws RemoteException;
    public boolean check_id_in_ass(String id) throws RemoteException;
    public void add_assessment(String id, float ass1, float ass2, float ass3, float finalAss) throws RemoteException;
    public String view_assessments_60(String id) throws RemoteException;
    public String view_assessments(String id) throws RemoteException;
    public String grade(String id) throws RemoteException;

}
    
