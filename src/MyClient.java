
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClient {

    private static char c;
    public static void main(String args[]){
        
        try{
            Scanner reader=new Scanner(System.in);
            FunctionsInterface stub=(FunctionsInterface)Naming.lookup("localhost");
            System.out.println("=======================");
            System.out.println("======= WELCOME =======");
            System.out.println("=======================");
            
            do {
//                System.out.print("\033[H\033[2J");  
//    System.out.flush(); 
                System.out.println("---------- Menu -------");
                System.out.println("1 - Register Students");
                System.out.println("2 - View Students");
                System.out.println("3 - Add Assesments");
                System.out.println("4 - View Assesments out of 60");
                System.out.println("5 - View All Assesments");
                System.out.println("6 - Check Grade");
                System.out.println("7 - Exit");
                System.out.print("---------> ");

                int ch = reader.nextInt();
                switch(ch){
                    case 1:
                        registerStudent(stub);
                        break;
                    case 2:
                        view_students(stub);
                        System.out.print("\nPress Any Key To Continue...");
                        new Scanner(System.in).nextLine();
                        break;
                    case 3:
                        add_assesments(stub);
                        System.out.print("\nPress Any Key To Continue...");
                        new Scanner(System.in).nextLine();
                        break;
                    case 4:
                        view_assesments_60(stub);
                        System.out.print("\nPress Any Key To Continue...");
                        new Scanner(System.in).nextLine();                      
                        break;
                    case 5:
                        view_all_assesments(stub);
                        System.out.print("\nPress Any Key To Continue...");
                        new Scanner(System.in).nextLine();                       
                        break;
                    case 6:
                        check_grade(stub);
                        System.out.print("\nPress Any Key To Continue...");
                        new Scanner(System.in).nextLine();                        
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Wrong Choice");
                }
            } while(true);
                        
            
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private static void registerStudent(FunctionsInterface stub) {
        try {            

            Scanner reader=new Scanner(System.in);

            System.out.println("How many students do you want to register");
            System.out.print("---------> ");
            int size = reader.nextInt();
            reader.nextLine();
            
            for(int i=0; i<size; i++){
                boolean id_found = false;
                String id = "";
                do{
                    System.out.print(id_found ? "ID Found. Please enter again: " : "Student "+(i+1)+" ID: ");
                    id = reader.nextLine();
                    id_found = stub.check_id_in_info(id);
                }while(id_found);
                
                
                System.out.print("Student "+(i+1)+" Name: ");
                String name = reader.nextLine();
                
                stub.register(id, name);
            }

            System.out.println("\nSuccessfuly Inserted");
        } catch (RemoteException ex) {
            System.out.println("An error occurred");
            ex.printStackTrace();
        }
        
    }

    private static void view_students(FunctionsInterface stub) {
        try {            
            String result = stub.view_student();
            System.out.println(result);
            String list[] = result.split("-");
            int len = list.length;          

            if("".equals(result)){
                System.out.println("\tThere is no student data.");
            }
            else {
                System.out.println("\t   ID\tName");
                System.out.println("\t   --\t----");
                
                for (int i = 0; i < len; i++) {;
                    String info[] = list[i].split(":");
                    System.out.println("\t"+(i+1)+"  "+info[0]+"\t"+info[1]);
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void add_assesments(FunctionsInterface stub) {
        try {
            Scanner reader=new Scanner(System.in);
            System.out.print("Enter student ID: ");
            
            String id = reader.nextLine();
            boolean id_found_in_info = stub.check_id_in_info(id);
            boolean id_found_in_ass = stub.check_id_in_ass(id);
                        
            if(!id_found_in_info) {
                System.out.println("Student info not found");                
            }
            else if(id_found_in_ass) {
                System.out.println("Student assessment already found");                
            }
            else {
                float ass1 = -100;
                do{
                    System.out.print(ass1 == -100 ? "\tAssessment 1: " : "\tAssessment 1(20%): ");
                    ass1 = reader.nextFloat();
                } while(ass1<0 || ass1>20);
                
                float ass2 = -100;
                do{
                    System.out.print(ass2 == -100 ? "\tAssessment 2: " : "\tAssessment 2(20%): ");
                    ass2 = reader.nextFloat();
                } while(ass2<0 || ass2>20);
                
                float ass3 = -100;
                do{
                    System.out.print(ass3 == -100 ? "\tAssessment 3: " : "\tAssessment 3(20%): ");
                    ass3 = reader.nextFloat();
                } while(ass3<0 || ass3>20);
                
                float finalAss = -100;
                do{
                    System.out.print(finalAss == -100 ? "\tFinal Assessment: " : "\tFinal Assessment (40%): ");
                    finalAss = reader.nextFloat();
                } while(finalAss<0 || finalAss>40);
                
                stub.add_assessment(id, ass1, ass2, ass3, finalAss);
                System.out.println("Assessment Successfully Added");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void view_assesments_60(FunctionsInterface stub) {
        try {
            Scanner reader=new Scanner(System.in);
            System.out.print("Enter student ID: ");
            String id = reader.nextLine();
            boolean id_found = stub.check_id_in_info(id);
            
            if(id_found){
                String result = stub.view_assessments_60(id);
                String ass[] = result.split("-");
                System.out.println("\tAssessment 1: " + ass[0]);
                System.out.println("\tAssessment 2: " + ass[1]);
                System.out.println("\tAssessment 3: " + ass[2]);                
                System.out.println("\t------------------");                
                System.out.println("\tTotal: " + ass[3]);                
            }
            else {
                System.out.println("ID not found");                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void view_all_assesments(FunctionsInterface stub) {
        try {
            Scanner reader=new Scanner(System.in);
            System.out.print("Enter student ID: ");
            String id = reader.nextLine();
            boolean id_found = stub.check_id_in_info(id);
            
            if(id_found){
                String result = stub.view_assessments(id);
                String ass[] = result.split("-");
                System.out.println("\tAssessment 1: " + ass[0]);
                System.out.println("\tAssessment 2: " + ass[1]);
                System.out.println("\tAssessment 3: " + ass[2]);                
                System.out.println("\tFinal Assessment: " + ass[3]);             
                System.out.println("\t------------------");                             
                System.out.println("\tTotal: " + ass[4]);             
            }
            else {
                System.out.println("ID not found");                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void check_grade(FunctionsInterface stub) {
        try {
            Scanner reader=new Scanner(System.in);
            System.out.print("Enter student ID: ");
            String id = reader.nextLine();
            boolean id_found = stub.check_id_in_info(id);
            
            if(id_found){
                String result = stub.grade(id);
                System.out.println("\tGrade: " + result);               
            }
            else {
                System.out.println("ID not found");                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
