package consoledatingapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author hinal
 */
public class Profile_page {

    private String user_id;
    private String first_name;
    private String password;
    //constructor

    

    public Profile_page(String uid, String fname, String psw) {
        this.user_id = uid;
        this.first_name = fname;
        this.password = psw;
        }
    
     User_Account user;
    public Profile_page(){//constuctor
        this.user = User_Account.curr;
    }

    ////////////////////////////////////////////////////////////////Reset pasword///////////////////////////////////////////  

    public void ResetPassword(String password, String first_name) {
        //varaibles
        User_Account u = new User_Account();
        
        Scanner input = new Scanner(System.in);
        String oldpassword, new1, new2;

        System.out.println("Please enter your current password");
        oldpassword = input.next();

        System.out.println("Please enter your new password");
        new1 = input.next();

        System.out.println("Please confirm your new password");
        new2 = input.next();

        String sql;

        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");

            st = conn.createStatement();
            rs = st.executeQuery(" select password from user_account where password ='"+ password+ "' and first_name = '"+ first_name +"';");
            
            
            //check the current password
            if (oldpassword.equals(password)) {
                //good to go to check new psw
                if (new1.equals(new2)) {
                    //good to go to change it
                    oldpassword = new1;

                    sql = "update user_account set password = '" + new1 + "' where password = '" + password + "' and first_name = '"+ first_name +"'";
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();
                
                    
                    System.out.println("Your new password is " + new1 + " and it is successfully changed");
                   

                } else {
                    System.out.println("The new passwords did not match!\n");
                }
            } else {
                System.out.println("Your old password is not correct!\n");
            }
            // st.executeUpdate("Update user_account set password = '" + new1 + "' where password = '"+ oldpassword + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                st.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    ////////////////////////////////////////////////View profile ///////////////////////////////////////////////////////    

    public void ViewPofile(String userid, String first_name, String password) {

        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            // create s sql statement object for query
            st = conn.createStatement();

            String Profilegender;
            String Profileage;
            String Profilecity;
            String Profileinterests1;
            String Profileinterests2;
            String Profileinterests3;

            rs = st.executeQuery("Select * from user_account  where first_name = '" + first_name + "' and user_id = '" + userid + "'");

            if (rs.next()) {
                
                System.out.println("***** Your Profile  ********");
                System.out.println("");

                Profilegender = rs.getString("gender");
                System.out.println("Your gender is : " + Profilegender);

                Profileage = rs.getString("age");
                System.out.println("Your age is : " + Profileage);

                Profilecity = rs.getString("city");
                System.out.println("Your city is : " + Profilecity);

                Profileinterests1 = rs.getString("interests1");
                System.out.println("Your interest 1 is : " + Profileinterests1);

                Profileinterests2 = rs.getString("interests2");
                System.out.println("Your interest 2 is : " + Profileinterests2);

                Profileinterests3 = rs.getString("interests3");
                System.out.println("Your interest 3 is : " + Profileinterests3);

                System.out.println("");
                System.out.println("*******************************************");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    ///////////////////////////////////////////////////////Data search////////////////////////////////////////////////////// 

    public void Search(String User_id,String first_name, String password) {
        
    ArrayList<User_Account> userlist = new ArrayList<>();     //define userlist from useer_account

        Scanner input = new Scanner(System.in);
        String interest = "";
        String selection = "";
        
              while (!selection.equals("4")) {

                System.out.println("+++++++++++++++++++++++++++++++++");
                System.out.println(" Search for your best match using:- :");
                System.out.println("+++++++++++++++++++++++++++++++++");
                System.out.println("1: specific Age, city, gender, interest ?");
                System.out.println("2: Any gender ?? Any city ?? Any ?? interest ?");
                System.out.println("3: Wanna date most popular male/female? ");            
                System.out.println("4: Go back :( ");

                selection = input.nextLine();
                System.out.println();

                if (selection.equals("1")) {
                      Data_Search ds = new Data_Search();
                      ds.SearchByAge(first_name, password);                      
              } 
             if (selection.equals("2")) {
                    Data_Search  ds = new Data_Search();
                    ds.SearchByAny(User_id, first_name);
                } 
              else if (selection.equals("3")) {
                       Data_Search ds = new Data_Search();
                       ds.RankUsers(User_id);
                } 
                 else if (selection.equals("4")) {
                    break;
                }
            }
        } 
      
    
    ///////////////////////////////////////////////////logout/////////////////////////////////  
     public void logout(String fname, String pswd){
        
        String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";        
        Connection conn = null;
        PreparedStatement ps = null;
        String sql;
        try{
            conn = DriverManager.getConnection(DB_URL, "patelh0486","1839363");
            sql = "update user_account set isLogin = 0, last_login = CURRENT_TIMESTAMP where first_name = '" + fname + "' and password = '" + pswd + "'";
            ps = conn.prepareStatement(sql);            
            ps.executeUpdate();
            System.out.println("*** Thank you for using Finder, Love to see you again!!   ****");
            System.out.println("");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try
            {
                conn.close();
                ps.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        

}
 
     
     
     
    ///////////////////////////////////////////////getter setter methods/////////////////////////////////
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
