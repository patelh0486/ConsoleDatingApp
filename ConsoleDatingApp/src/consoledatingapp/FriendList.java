
package consoledatingapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author hinal
 */
public class FriendList {
    private String sendID;
    private String receivID;
    private String status;
    private String first_name;
    private String last_name;
    
    
    public FriendList (User_Account user){
        
    }
    User_Account user;
    public FriendList(){
        this.user = User_Account.curr;
    }
    public FriendList(String s, String r, String stat){
        this.sendID = s;
        this.receivID = r;
        this.status = stat;
    }
    public FriendList(String send, String st){
        this.sendID = send;
        this.status = st; 
    }    
    public FriendList(String send, String fname, String lname, String st){
        this.sendID = send;
        this.status = st;
        this.first_name = fname;
        this.last_name = lname;
    }

 
    
 ////////////////////////////////////////////////  Display Table to view all Friends///////////////////////////// 
    public void DisplayAll(String sendID, String receivID){
        
       Scanner input = new Scanner(System.in);
       ArrayList<User_Account> userlist = new ArrayList<>(); 
       
       final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();                        
            rs = st.executeQuery("select user_id, first_name, last_name, gender, age, city, interests1, interests2, interests3, last_login, ViewsCount from user_account where user_id != '"+ sendID +"'");
             
            System.out.println("UserId \t firstname \t Lastname  \t  Gender \t  Age \t  City  \t  Interest  \t  Interest \t   Interest   \t  Last login  \t   views");
                 
                 
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
         
            for(User_Account user1: userlist)
                     {                        
                       System.out.println(user1.getUser_id()+ "\t" +user1.getFirst_name() +"\t \t"+ user1.getLast_name() +"\t \t \t"+ user1.getGender() +"\t \t"+ user1.getAge() + "\t \t"+ user1.getCity() +"\t \t"+ user1.getInterests1() +"\t \t"+ user1.getInterests2() +"\t \t"+ user1.getInterests3() +"\t"+ user1.getDatetime() +"\t"+ user1.getViewsCount() );
                     }            
           System.out.println(" \n");
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
    
///////////////////////////////////////////////Request friend///////////////////////////////////////    
     public void RequestFriends(String sendID, String receivID){
         
         FriendList fl = new FriendList();
         fl.DisplayAll(sendID, receivID);
         sendID = user.getUser_id();
         setSendID(sendID);
         String status = "";
         
         Scanner input = new Scanner(System.in);
         
         System.out.println("Enter userid to whom you want to send request:");
         setReceivID(input.next());
        
     //     curr = sendID;
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
            rs = st.executeQuery("select Receiver_Id, Sender_Id from friendrequest where (Receiver_id = '" + getReceivID()+"' AND Sender_Id = '"+ getSendID()+"') OR (Receiver_id = '" + getSendID()+"' AND Sender_Id = '"+ getReceivID()+"')  ");
            if(rs.next()){
                System.out.println("***************You have already send request or you are friend to him/her, Please send request to other person !!!!!");
                System.out.println("");
                System.out.println("");
            }     
            else{
            String sql = "Insert into friendrequest values('"+getReceivID()+"','"+ getSendID()+"','Pending' )";
            st.executeUpdate(sql);            
            System.out.println("-----Your request has been sent-------");
            System.out.println("");
            System.out.println("");
            }
            
         //  conn.commit();
         //  conn.setAutoCommit(true);
         
           
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
/////////////////////////////////////////// Notification///////////////////////////////////////////////     
    public void ViewNotification(String receivID){
            ArrayList <FriendList> requestList =  new ArrayList <>(); 
         User_Account u = new User_Account();
        first_name = u.getFirst_name();
        setFirst_name(first_name);
        last_name =  u.getLast_name();
        setFirst_name(last_name);
         String Changestatus = "";
         Scanner input = new Scanner(System.in);
         
         receivID = user.getUser_id();
         setReceivID(receivID);
         System.out.println("***************N  O  T  I  F  I  C  A  T  I  O  N  S************************  ");
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
              System.out.println("***************You have  request from   !!!!!");
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
            rs = st.executeQuery("select  f.Sender_id, first_name, last_name, status from  friendrequest f, user_account u where f.Sender_id = u.user_id and  status = 'pending' AND Receiver_id = '" +getReceivID()+"' ");
            while(rs.next()){ 
                
               FriendList friends = new FriendList( rs.getString("Sender_Id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("status"));
                 requestList.add(friends);
            }   
            for(FriendList rl:requestList){
                System.out.println( "#" +rl.getSendID()+"  "+ rl.getFirst_name() +" "+ rl.getLast_name() +" "+  rl.getStatus());
                System.out.println(" ? accept or reject or keep pending ?");
                Changestatus = input.next();
                if(Changestatus.equals("accept")){
                    
                String sql = "update friendrequest set status = 'accepted' where  Receiver_Id = '" + receivID + "' and Sender_Id = '" + rl.getSendID() + "'";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println(" Yay! Its a MATCH :) ?");
                
                }
                else if(Changestatus.equals("reject")){                    
                String sql = "update friendrequest set status = 'rejected' where  Receiver_Id = '" + receivID + "' and Sender_Id = '" + rl.getSendID() + "'";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println(" not A Match :( ?");
                
                }
            }
            
            if(requestList.isEmpty()){           
               System.out.println("-----You don't have any  request -------");
               System.out.println("");
               System.out.println("");
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
////////////////////////////////////////////////////List of accepted friends ///////////////////////////////////////////    
    public void ViewFriends(String receivID, String sendID){
         ArrayList <FriendList> friendarray1 =  new ArrayList <>(); 
         ArrayList <FriendList> friendarray2 = new ArrayList<>();
         
         Scanner input = new Scanner(System.in);
         
         receivID = user.getUser_id();
         setReceivID(receivID);
         sendID = user.getUser_id();
         setSendID(sendID);
         
         String ViewProfID = "";
         
         System.out.println("***************your friendlist   !!!!!");
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st1 = null;     
        ResultSet rs1 = null;
      

        try {
             
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st1 = conn.createStatement();  
            rs1 = st1.executeQuery("select Sender_Id, status from  friendrequest "
                            + "where status = 'accepted' AND Receiver_id = '" +getReceivID()+"' "
                            + "UNION "
                            + "select Receiver_Id, status from  friendrequest "
                            + "where status = 'accepted' AND Sender_id = '" +getSendID()+"'  ");
          
            while(rs1.next() ){ 
                
               FriendList friends1 = new FriendList( rs1.getString("Sender_Id"),rs1.getString("status"));
                 friendarray1.add(friends1); 
                  
            }   
            for(FriendList rl:friendarray1){
                System.out.println( "#" +rl.getSendID()+"  "+ rl.getStatus());
               }  
            
           if(friendarray1.isEmpty()){
               System.out.println("***************sorry! you have no match ***************");
           }
           else{
                System.out.println("Enter ID to view his/her profile");
                ViewProfID = input.next();
                ViewFriendProfile(ViewProfID);
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
                st1.close();
              //  st2.close();
                rs1.close();
              //  rs2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }     
    }
    
    
    
  //////////////////////////////////////////Accept reject/////////////////////////////////////////
    public void CheckRequestStatus(String userid){
        String sql = "";
        Scanner input = new Scanner(System.in);
        String selection = "";
       
        while(!selection.equals("3"))
        {
        System.out.println("+++++++++++++++++++++++++++++++++");
        System.out.println(" Choose what you want to do :");
        System.out.println("+++++++++++++++++++++++++++++++++");
        System.out.println("1: Request sent status ?");
        System.out.println("2: Request recieved status ?");
        System.out.println("3: Go back ?");


        selection = input.nextLine();
        System.out.println();
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();
            String rid="";
            String sid = "";
            String fname = "";
            String lname = "";
            String stat = "";
           
         if(selection.equals("1") ){                
          rs = st.executeQuery("select f.Receiver_Id, first_name, last_name, status from friendrequest f, user_account u "
                   + "where f.Receiver_Id = u.user_id and Sender_Id = '"+ userid +"' ");
                 if(!rs.isBeforeFirst()){
                     System.out.println("you did not sent any request");
                     System.out.println(" ");
                 }                   
                while(rs.next()){
                    rid = rs.getString("Receiver_Id");
                    fname = rs.getString("first_name");
                    lname = rs.getString("last_name");
                    stat = rs.getString("status");
                    System.out.println(rid +" "+ fname + " " + lname +" "+ stat);                    
                }  
                System.out.println("you  sent request to above people : ");
             }
         
         else  
         if(selection.equals("2") ){                
         rs = st.executeQuery("select f.Sender_Id, first_name, last_name, status from friendrequest f, user_account u "
                   + "where f.Sender_Id = u.user_id and Receiver_Id = '"+ userid +"' ");
            if(!rs.isBeforeFirst()){
                     System.out.println("you did not receive any request");
                     System.out.println(" ");
                 }
 
                while(rs.next()){
                    sid = rs.getString("Sender_Id");
                    fname = rs.getString("first_name");
                    lname = rs.getString("last_name");
                    stat = rs.getString("status");
                    System.out.println(sid +" "+ fname + " " + lname +" "+ stat);                    
                }        
                System.out.println("you  received request from above people : ");
             }
         else if(selection.equals("3")){
             break;
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
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }       
        
        } 
    }
    
    ///////////////////////////////////////////View friend profile/////////////////////////////////////////
    public  void ViewFriendProfile(String ViewProfID){
       //  String sql="";
      
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";
        
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();
            String ProfileFname;
            String ProfileLname;
            String Profilegender;
            String Profileage;
            String Profilecity;
            String Profileinterests1;
            String Profileinterests2;
            String Profileinterests3;
            String ProfileLastOnline;
           // String ProfileViews;

            rs = st.executeQuery("Select * from user_account  where user_id = '"+ViewProfID+"' ");

            if (rs.next()) {
                //  rs.getString("first_name");
                // rs.getString("last_name");
               String  sql = " update user_account set ViewsCount = ViewsCount + 1 where user_id = '"+ ViewProfID+"' ";
                      ps = conn.prepareStatement(sql);
                      ps.executeUpdate(sql);
                
                System.out.println("***** Your Match's Profile  ********");
                System.out.println("");

                ProfileFname = rs.getString("first_name");
                System.out.println("First name is : " + ProfileFname);
                
                ProfileLname = rs.getString("Last_name");
                System.out.println("Last Name is : " + ProfileLname);
                
                Profilegender = rs.getString("gender");
                System.out.println("gender is : " + Profilegender);

                Profileage = rs.getString("age");
                System.out.println("age is : " + Profileage);

                Profilecity = rs.getString("city");
                System.out.println(" city is : " + Profilecity);

                Profileinterests1 = rs.getString("interests1");
                System.out.println(" interest 1 is : " + Profileinterests1);

                Profileinterests2 = rs.getString("interests2");
                System.out.println("interest 2 is : " + Profileinterests2);

                Profileinterests3 = rs.getString("interests3");
                System.out.println(" interest 3 is : " + Profileinterests3);

                ProfileLastOnline = rs.getString("last_login");
                System.out.println(" last seen at : " + ProfileLastOnline);
                
//                ProfileViews = rs.getString("ViewsCount");
//                System.out.println(" Number of Views : " + ProfileViews);
                
                System.out.println("");
                System.out.println("*******************************************");

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
    
 ///////////////////////////////////////////////////////Getter setter method////////////////////////////////   
    public String getSendID() {
        return sendID;
    }

    public void setSendID(String sendID) {
        this.sendID = sendID;
    }

    public String getReceivID() {
        return receivID;
    }

    public void setReceivID(String receivID) {
        this.receivID = receivID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
 }
