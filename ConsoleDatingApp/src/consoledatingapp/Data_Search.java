/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoledatingapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hinal
 */
public class Data_Search {
    
//    public Data_Search( User_Account user )
//    {
//        this.curr = user;
//    }
    
    User_Account user;
    public Data_Search(){//constuctor
        this.user = User_Account.curr;
    }
    
   /////////////////////////search by just age///////////////////////////////////////
    public void SearchByAge(String first_name, String password){
        String sql = "";
        String ViewProfID ;
        Scanner input = new Scanner(System.in);
        int Minage = 0;
        int Maxage = 0;
        String Age = "";
        String city = "";
        String gender = "";
        String interest = "";
        
        ArrayList<User_Account> userlist = new ArrayList<>(); /// array list of users from database
        
         System.out.println("Which gender you want:");
        gender = input.next();
        
         input.nextLine();
         
        System.out.println("Enter Minimum Age :");
        Minage = Integer.parseInt(input.nextLine());
        
        // input.nextLine();
        
        System.out.println("Enter Maximum Age:");
        Maxage = Integer.parseInt(input.nextLine());
                
        System.out.println("In which city you wanna date:");
        city = input.nextLine();        
        
        System.out.println("What are your intrests:");
        interest = input.nextLine();
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
            rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where gender = '"+gender+"' AND  Age between '" + Minage + "' AND '" + Maxage + "' AND city = '"+city+"' And (interests1 = '"+interest+"' or interests2 = '"+interest+"' or interests3 = '"+interest+"') AND  first_name != '" + first_name + "' And password != '" + password + "'");
                                 
            while(rs.next()){               
            User_Account users = new User_Account(         rs.getString("user_id"),
                                                           rs.getString("first_name"),
                                                           rs.getString("last_name"),
                                                           rs.getString("gender"),
                                                           rs.getInt("age"),
                                                           rs.getString("city"),
                                                           rs.getString("interests1"),
                                                           rs.getString("interests2"), 
                                                           rs.getString("interests3"),
                                                           rs.getTimestamp("last_login"),
                                                           rs.getInt("ViewsCount"));    
                      userlist.add(users);   

            }
            System.out.println("UserId \t\t First name \t\t Last Name ");
            System.out.println("-----------------------------------------------------------");
            
            for(User_Account user1: userlist)
                     {                        
                    //  System.out.println(user1.getUser_id()+"\t\t"+user1.getFirst_name() +"\t \t"+ user1.getLast_name() +"\t \t"+ user1.getGender() +"\t \t"+ user1.getAge() + "\t \t"+ user1.getCity() +"\t \t"+ user1.getInterests1() +"\t \t"+ user1.getInterests2() +"\t \t"+ user1.getInterests3() +"\t"+ user1.getDatetime() +"\t"+ user1.getViewsCount() );
                     System.out.println(user1.toString());

                      
                     }
           if(userlist.isEmpty()){
               System.out.println("***************sorry! you have no match ***************");
           }
           else{
                System.out.println("Enter ID to view his/her profile");
                ViewProfID = input.next();
                FriendList fl = new FriendList();
                fl.ViewFriendProfile(ViewProfID);

           }
            
            
        } 
        catch (SQLException e) 
        {
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
    
    public void SearchByAny(String User_id, String first_name){
      //  User_Account ua = new User_Account();
         String dateID = "";
         String sql = "";
        String ViewProfID ;
        Scanner input = new Scanner(System.in);
        int Minage = 0;
        int Maxage = 0;
        String Age = "";
        String city = "";
        String gender = "";
        String interest = "";
        
        ArrayList<User_Account> userlist = new ArrayList<>(); /// array list of users from database
         System.out.println("----------Type A capital for Any-------------");
         System.out.println("Which gender you want:");
        gender = input.next();
        
        input.nextLine();
        
        System.out.println("Enter Minimum Age :");
        Minage = Integer.parseInt(input.nextLine());
      
        System.out.println("Enter Maximum Age:");
        Maxage = Integer.parseInt(input.nextLine());
        
         
        System.out.println("In which city you wanna date :");
        city = input.nextLine();    
      
        
        System.out.println("What are your intrests:");
        interest = input.nextLine();
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();
          if(city.equals("Any") && interest.equals("Any")){
              rs = st.executeQuery("select  user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where gender = '"+gender+"' AND age between '" + Minage + "' AND '" + Maxage + "' AND user_id != '"+ User_id+"'");
          }
          else if(interest.equals("Any")){
             rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where gender = '"+gender+"' AND  Age between '" + Minage + "' AND '" + Maxage + "' AND city = '"+city+"' AND user_id != '"+ User_id+"'"); 
          }
          else if(city.equals("Any")){
              rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where gender = '"+gender+"' AND  Age between '" + Minage + "' AND '" + Maxage + "' And (interests1 = '"+interest+"' or interests2 = '"+interest+"' or interests3 = '"+interest+"') AND user_id != '"+ User_id+"'");
          }
          
          else if(gender.equals("Any")){
              rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where  Age between '" + Minage + "' AND '" + Maxage + "' AND city = '"+city+"' And (interests1 = '"+interest+"' or interests2 = '"+interest+"' or interests3 = '"+interest+"') AND user_id != '"+ User_id+"'");
          }
          
          else if(gender.equals("Any") && city.equals("Any") && interest.equals("Any")){
              rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where   Age between '" + Minage + "' AND '" + Maxage + "'  AND user_id != '"+ User_id+"'");
          }
          else  { 
           rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where gender = '"+gender+"' AND  Age between '" + Minage + "' AND '" + Maxage + "' AND city = '"+city+"' And (interests1 = '"+interest+"' or interests2 = '"+interest+"' or interests3 = '"+interest+"') AND user_id != '"+ User_id+"'");
          }          
            while(rs.next()){     
                dateID= rs.getString("user_id");
            User_Account users = new User_Account(         rs.getString("user_id"),
                                                           rs.getString("first_name"),
                                                           rs.getString("last_name"),
                                                           rs.getString("gender"),
                                                           rs.getInt("age"),
                                                           rs.getString("city"),
                                                           rs.getString("interests1"),
                                                           rs.getString("interests2"), 
                                                           rs.getString("interests3"),
                                                           rs.getTimestamp("last_login"),
                                                           rs.getInt("ViewsCount"));    
                      userlist.add(users);   

            }
        
            System.out.println("UserId \t\t First name \t\t Last Name ");
            System.out.println("-----------------------------------------------------------");
            
            for(User_Account user1: userlist)
                     {                        
                   // System.out.println(user1.getUser_id()+"\t\t"+user1.getFirst_name() +"\t \t"+ user1.getLast_name() +"\t \t"+ user1.getGender() +"\t \t"+ user1.getAge() + "\t \t"+ user1.getCity() +"\t \t"+ user1.getInterests1() +"\t \t"+ user1.getInterests2() +"\t \t"+ user1.getInterests3() +"\t"+ user1.getDatetime() +"\t"+ user1.getViewsCount() );
                    System.out.println(user1.toString());
                    
                      
                     }
           if(userlist.isEmpty()){
               System.out.println("***************sorry! you have no match ***************");
           }
           else{
                System.out.println("Enter ID to view his/her profile");
                ViewProfID = input.next();
                boolean isPresent = false;
                for(User_Account u : userlist){
                        if(u.getUser_id().equals(ViewProfID)){
                            isPresent = true;
                            FriendList fl = new FriendList();
                            fl.ViewFriendProfile(u.getUser_id());
                            break;
                        }
                   
                }
                
                if(!isPresent){
                                    System.out.println("$$$$$$$$$$$$$$$$$$$ Enter valid Id");

                }
                
                
           }
            
            
        } 
        catch (SQLException e) 
        {
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
    
    public void RankUsers(String User_id){
        Scanner input = new Scanner(System.in);
       ArrayList  <User_Account>  RankList = new ArrayList<>();
        String ViewProfID = "";
        String gender;
        System.out.println("Enter Gender for dating most popular human ;) ");
        gender = input.next();
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();  
            
            if(gender.equals("f") || gender.equals("F"))
            {
            rs = st.executeQuery("select * from girls where  user_id != '"+User_id+"' LIMIT 3");
            }           
            else 
            if(gender.equals("m") || gender.equals("M"))    
            {
                 rs = st.executeQuery("select * from male where  user_id != '"+User_id+"' LIMIT 3");
            }
            while(rs.next()){               
            User_Account users = new User_Account(         rs.getString("user_id"),
                                                           rs.getString("first_name"),
                                                           rs.getString("last_name"),
                                                           rs.getString("gender"),
                                                           rs.getInt("age"),
                                                           rs.getString("city"),
                                                           rs.getString("interests1"),
                                                           rs.getString("interests2"), 
                                                           rs.getString("interests3"),
                                                           rs.getTimestamp("last_login"),
                                                           rs.getInt("ViewsCount"));    
                      RankList.add(users);   

            }
            System.out.println("UserId \t First name \t\t Last Name \t Views ");
            System.out.println("-----------------------------------------------------------");
            
            for(User_Account user1: RankList)
                     {                        
                     System.out.println(user1.getUser_id()+"\t"+user1.getFirst_name() +"\t\t"+ user1.getLast_name() +"\t\t"+ user1.getViewsCount() );
                     //System.out.println(user1.toString());

                      
                     }
           if(RankList.isEmpty()){
               System.out.println("***************sorry! you have no match ***************");
           }
           else{
                System.out.println("Enter ID to view his/her profile");
                ViewProfID = input.next();
                
                boolean isPresent = false;
                for(User_Account u : RankList){
                        if(u.getUser_id().equals(ViewProfID)){
                            isPresent = true;
                            FriendList fl = new FriendList();
                            fl.ViewFriendProfile(u.getUser_id());
                            break;
                        }
                   
                }
                
                if(!isPresent){
                                    System.out.println("$$$$$$$$$$$$$$$$$$$ Enter valid Id $$$$$$$$$$$$$$$$$");

                }
           }
            
            
        } 
        catch (SQLException e) 
        {
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
}
