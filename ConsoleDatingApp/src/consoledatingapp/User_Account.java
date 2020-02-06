/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoledatingapp;

import java.sql.*;
import java.text.*;
import java.util.Scanner;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hinal
 */
public class User_Account {
    
     private String user_id;
    private String first_name; 
    private String last_name;
    private String gender;
    private int age;
    private String city;
    private String interests1;
    private String interests2;
    private String interests3;
    private String password;
    private Timestamp   datetime;
    private int ViewsCount;

   
     
   
    User_Account()//empty constructor 
    {
        
    }
    //create static variable of user
    public static User_Account curr = new User_Account();
    
    // public constructor of User_Account
    
    public User_Account(String uid, String fnam,String lname,String gend, int ag, String cit, String int1, String int2, String int3,Timestamp  dt, int vc ) 
    {
        this.user_id = uid;
        this.first_name= fnam;
        this.last_name = lname;
        this.gender = gend;
        this.age = ag;
        this.city = cit;
        this.interests1 = int1;
        this.interests2 = int2;
        this.interests3 = int3;
        this.datetime = dt;
        this.ViewsCount = vc;
          
    }

  

   

    /////constructor for curent user
    
    public User_Account(String curruid,String currfname,String currpswd){
        this.first_name = currfname;
        this.password = currpswd;
        this.user_id = curruid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInterests1() {
        return interests1;
    }

    public void setInterests1(String interests1) {
        this.interests1 = interests1;
    }

    public String getInterests2() {
        return interests2;
    }

    public void setInterests2(String interests2) {
        this.interests2 = interests2;
    }

    public String getInterests3() {
        return interests3;
    }

    public void setInterests3(String interests3) {
        this.interests3 = interests3;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
    
    
    public int getViewsCount() {
        return ViewsCount;
    }

    public void setViewsCount(int ViewsCount) {
        this.ViewsCount = ViewsCount;
    }

   
 //////////////////////////////////////////Register////////////////////////////////////////////////////////   
    public void createNewUser_Account(){
        
//    String user_id,first_name, last_name, gender, age, city ;
//    String interests1, interests2, interests3;
//    String password;
    
     
    Scanner input = new Scanner(System.in);
    
    System.out.println("Please enter following details for signUp:");
    
     System.out.println("User id:");
        //user_id = input.next();
       setUser_id(input.nextLine()); 
      CheckUserID(user_id) ; 
        
    System.out.println("first name: ");
        setFirst_name(input.nextLine());
    
    System.out.println("last name:");
        setLast_name(input.nextLine());    
        
    System.out.println("Gender (F/M/None) :");
        setGender(input.next());
        
        input.nextLine();
     //   int age = Integer.parseInt(input.nextLine());
    System.out.println("Age : ");
        setAge(Integer.parseInt(input.nextLine())); 
        
    System.out.println("City : ");
        setCity(input.nextLine());
         
    System.out.println("Tell about your interest :");
     setInterests1(input.nextLine()); 
     
    System.out.println("Tell about your 2nd interest :");
     setInterests2(input.nextLine());   
     
    System.out.println("Tell about your 3rd interest :");
     setInterests3(input.nextLine());  
     
     System.out.println("Enter a valid password and cannot be same as userID :");
     setPassword(input.next());
        
    final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";
     
     Connection connection = null;
     Statement  statement = null;
     ResultSet  resultSet = null;
     String success  = " ********************Registration Successfull!! ************************";
             
     try{
         connection =  DriverManager.getConnection(DATABASE_URL,"patelh0486", "1839363");
          statement = connection.createStatement();
          
          System.out.println("Registration Successfull!!");
         // String s = DateAndTime.DateTime()+ ": "
          //           + "last login " + "\n";
      
           String sql = "Insert into user_account values('"+getUser_id()+"', '"+getFirst_name()+"', '"+ getLast_name() +"', '"+getGender()+"', '"+getAge()+"', '"+getCity()+"','"+getInterests1()+"','"+getInterests2()+"', '"+getInterests3()+"', '"+getPassword()+"',CURRENT_TIMESTAMP,0,0)";
            statement.executeUpdate(sql);
             System.out.println(success);
          
         
     }
        
      catch(SQLException e) 
                {
                   e.printStackTrace();
                }
      finally 
                {
                    try
                    {
                         resultSet.close();
                         statement.close();
                         connection.close();
                    }
                    catch(Exception e) 
                    {
                        e.printStackTrace();
                    }
           }
                
  }
    
    
///////////////////////////////////////////////////////////Show main page////////////////////////////////    
    public void showMainPage() {
        //declare varaibles
        Scanner input = new Scanner(System.in);
        String selection = "";

        while (!selection.equals("x")) {
            //display the menu
            System.out.println("***************Welcome back to Finder :) ********************\n");
            System.out.println("Please make your selection");
            System.out.println("1: Login your Finder account");
            System.out.println("x: Finish the simulation");

            //get the selection from the user
            selection = input.nextLine();
            System.out.println();

            if (selection.equals("1")) {
                //login
                login();
            } else if (selection.equals("x")) {
                break;
            }
        }
    }
    
    
        ////////////////////////////////////////////Login module//////////////////////////////////////////   

    public void login() {
        //we need id and password
        Scanner input = new Scanner(System.in);
        // String  user_id = "";
       String  first_name = "";
       String password = "";
       int islogin = 1;
       String sql;
       
        boolean idFound = false;
        
        //get the login info.
        System.out.println("Please enter your first name");
        first_name = input.next();
        System.out.println("Please enter your password");
        password = input.next();

        //Complete the database part below
        //database location
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            // create s sql statement object for query
            st = conn.createStatement();           
           
            rs = st.executeQuery("Select * from user_account where first_name = '" + first_name
                    + "' and password = '" + password + "';");
            
            if (rs.next()) {              
                
                System.out.println("*** Yay! Login successful   ****");
                System.out.println("");
                sql = "update user_account set isLogin = 1 where first_name = '" + first_name + "' and password = '" + password + "'";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                //// set current user details
                curr = new User_Account(rs.getString("user_id"), rs.getString("first_name"),rs.getString("password"));
                After_login up = new After_login();
                up.test();         ////send currents user to test function
                
            }
            else {
                System.out.println("*** sorry your Login is unsuccessful, Please try again!!   ****");
            }

        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            //close the database
            try {
                conn.close();
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    
    public void CheckUserID(String user_id){
        
        Scanner input = new Scanner(System.in);
        String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";        
        Connection conn = null;
        Statement st= null;
        ResultSet rs = null;
        String sql;
        try{
            conn = DriverManager.getConnection(DB_URL, "patelh0486","1839363");
            
           
            st = conn.createStatement();            
            rs =  st.executeQuery("select user_id from user_account where user_id = '" +user_id+ "'");
            if(rs.next()){
            System.out.println("*** You can't use this user ID as it already exist, choose another user ID****");
            System.out.println("");
              System.out.println("User id:");             
               setUser_id(input.nextLine());       
            }
            else{
                 System.out.println(" cool user_id seems unique !!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try
            {
                conn.close();
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        
    }

    @Override
    public String toString() {
       // System.out.printf("UserId \t First name \t Last Name \n");
        String str = getUser_id()+" \t\t "+ getFirst_name() + " \t\t" + getLast_name();
        return str;
    }
    
    
   
    
}
