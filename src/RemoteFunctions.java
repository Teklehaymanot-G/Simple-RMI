
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RemoteFunctions extends UnicastRemoteObject implements FunctionsInterface{

    public RemoteFunctions() throws RemoteException{
        super();
    }
    
    ArrayList<StudentInfo> list = new ArrayList<StudentInfo>();
    ArrayList<StudentAssessment> listAss = new ArrayList<StudentAssessment>();

    public void register(String id, String name){
        StudentInfo i = new StudentInfo();
        i.setID(id);
        i.setName(name);
        list.add(i);
    } 
    
    @Override
    public void add_assessment(String id, float ass1, float ass2, float ass3, float finalAss) {
        StudentAssessment a = new StudentAssessment();
        a.setID(id);
        a.setAss1(ass1);
        a.setAss2(ass2);
        a.setAss3(ass3);
        a.setFinalAss(finalAss);
        listAss.add(a);
    }

    @Override
    public String view_student() throws RemoteException {
        String result = "";
        for (int i = 0; i < list.size(); i++) { 		      
            result += list.get(i).getID()+ ":" + list.get(i).getName()+ "-";
        }

        return result;
    }

    @Override
    public boolean check_id_in_info(String id) {
        for (int i = 0; i < list.size(); i++) {
            
            if(list.get(i).getID().trim().equalsIgnoreCase(id.trim())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String view_assessments_60(String id) throws RemoteException {
        AtomicReference<String> value = new AtomicReference<>("");
        listAss.forEach((t) -> {                
            if(t.getID() == null ? id == null : t.getID().equals(id)){
                String result = t.getAss1() + "-" + t.getAss2() + "-" + t.getAss3() +"-"+ (t.getAss1()+t.getAss2()+t.getAss3());
                value.set(value.get() + result);
            }
        });
                
        return value.toString();
    }
    
    @Override
    public String view_assessments(String id) throws RemoteException {
        AtomicReference<String> value = new AtomicReference<>("");
        listAss.forEach((t) -> {                
            if(t.getID() == null ? id == null : t.getID().equals(id)){
                String result = t.getAss1() +"-"+ t.getAss2() +"-"+ t.getAss3() 
                        +"-"+ t.getFinalAss() +"-"+ (t.getAss1()+t.getAss2()+t.getAss3()+t.getFinalAss());
                value.set(value.get() + result);
            }
        });
                
        return value.toString();
    }
    
    @Override
    public String grade(String id) throws RemoteException {
        AtomicReference<String> value = new AtomicReference<>("");
        listAss.forEach((t) -> {                
            if(t.getID() == null ? id == null : t.getID().equals(id)){
                float result = t.getAss1() + t.getAss2() + t.getAss3() + t.getFinalAss();
                
                if(result >= 90)
                    value.set("A+");
                else if(result >= 85 && result < 90)
                    value.set("A");
                else if(result >= 80 && result < 85)
                    value.set("A-");
                else if(result >= 75 && result < 80)
                    value.set("B+");
                else if(result >= 70 && result < 75)
                    value.set("B");
                else if(result >= 60 && result < 70)
                    value.set("B-");
                else if(result >= 50 && result < 60)
                    value.set("C+");
                else if(result < 50)
                    value.set("Fail");
            }
        });
                
        return value.toString();
    }

    @Override
    public boolean check_id_in_ass(String id) throws RemoteException {
        for (int i = 0; i < listAss.size(); i++) {
            
            if(listAss.get(i).getID().trim().equalsIgnoreCase(id.trim())){
                return true;
            }
        }
        return false;        
    }
}
