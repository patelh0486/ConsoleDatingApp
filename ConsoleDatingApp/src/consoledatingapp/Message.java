/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Message {
    private String sender;
    private String receiver;
    private String first_name;
    private String last_name;
    private String messageDet;

   
    DateAndTime Date_Time;
    private String MessageStatus;
    
    public Message(String send, String fn, String ln ){
        this.sender = send;
        this.first_name = fn;
        this.last_name = ln;
    }
    public Message(String send, String fn, String ln, String md){
        this.sender = send;
        this.first_name = fn;
        this.last_name = ln;
        this.messageDet = md;
    }
 
    User_Account user;
    public  Message(){
    this.user = User_Account.curr;
    }
    
    public void messageMenu(String userid){
         Scanner input = new Scanner(System.in); 
         String selection = "";
         
          while(!selection.equals("3")){
                    System.out.println("+++++++++++++++++++++++++++++++++");
                    System.out.println(" Message your Date :>)");
                    System.out.println("+++++++++++++++++++++++++++++++++");
                    System.out.println("1: Send message to any friend?");
                    System.out.println("2: View message notification ?");
                    System.out.println("3: go back <--");
                    
                    selection = input.nextLine();
                    System.out.println();
                    
                     if (selection.equals("1")) {                      
                       MessageFriendList(userid);  
                     }
                     else if (selection.equals("2")) {                         
                        MessageNotification(userid);
                        
                     }
                    else if (selection.equals("3")) {
                       
                        break;
                    }
              
          }
    }
    
    
    //////////////////////////////////////////////View friendlist and then send message///////////////////////////////////////////
    public void MessageFriendList(String userid){
        
         Scanner input = new Scanner(System.in);        
               
         ArrayList <Message> messagelist =  new ArrayList <>(); 
         
         String mID = "";
         String fname = "";
         String  lname = "";
         String  sid = " ";
         String  rid = " ";
         
         System.out.println("***************your message friendlist**************************");
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;     
        ResultSet rs = null;
      

        try {
             
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();  
            rs = st.executeQuery("select s.Sender_Id, first_name, last_name from  friendrequest s, user_account u where s.Sender_Id = u.user_id AND status = 'accepted' AND Receiver_id = '" + userid+"' UNION select r.Receiver_Id, first_name, last_name from  friendrequest r, user_account a where r.Receiver_Id = a.user_id AND status = 'accepted' AND Sender_id = '" + userid+"'  ");
          if (!rs.isBeforeFirst() ) {    
                 System.out.println("***************sorry! you have no match to message ***************");
          }
          else{
          System.out.println( "USER ID  \tFIRST NAME  \tLast Name");
            while(rs.next() ){ 
                     sid = rs.getString("Sender_id");
                     fname = rs.getString("first_name");
                     lname = rs.getString("last_name");
                     System.out.println( sid +"\t\t"+ fname + "\t\t " + lname);
                      
                }   
                System.out.println("");
                System.out.println("Enter ID to Send message to your date ;]");
                receiver = input.next();
                SendMessage(receiver);
           
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
 /////////////////////////////////////////////////////////////////////Send message to your friend method//////////////////////////////////////////////////////////   
    public void SendMessage( String r){
        
        setReceiver(r);
        
        sender = user.getUser_id();
        setSender(sender);
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("###### Please Type your lovely message #######");
        setMessageDet(input.nextLine());
        
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
        //ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
          
            String sql = "Insert into messagetable values( '"+ getSender()+"', '"+getReceiver()+"', '"+ getMessageDet()+"', CURRENT_TIMESTAMP,'pending') ";
            st.executeUpdate(sql);            
            System.out.println("-----Your message has been sent-------");
            System.out.println("");
           
            
            
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
                //rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }       
     
        
       
        
        
    }
    ////////////////////////////////////////////////////////////////////Message notification//////////////////////////////////////
    public void MessageNotification(String userid){
        
        ArrayList<Message> MessNotifyList = new ArrayList<>();
        
         User_Account u = new User_Account();
        first_name = u.getFirst_name();
        setFirst_name(first_name);
        last_name =  u.getLast_name();
        setFirst_name(last_name);
         String Changestat = "";
         Scanner input = new Scanner(System.in);
         
         receiver= userid;
         setReceiver(receiver);
        
         System.out.println("*************** M E S S A G E    N  O  T  I  F  I  C  A  T  I  O  N  S************************  ");
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
       // Statement st1 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
              System.out.println("***************You have  message  from   !!!!!");
            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
            rs = st.executeQuery("select  M.Sender, first_name, last_name, Message from  messagetable m, user_account u where m.Sender = u.user_id and  MessageStatus = 'pending' AND Receiver = '" +getReceiver()+"' ");
          
            
            while(rs.next()){                 
               Message mess = new Message( rs.getString("Sender"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("Message"));
                 MessNotifyList.add(mess);
            }   
            for(Message m: MessNotifyList){
                System.out.println( "#" +m.getSender()+"  "+ m.getFirst_name() +" "+ m.getLast_name() +":- "+  m.getMessageDet());
                System.out.println(" ~~ Reply or  keep pending ? ");
                Changestat = input.next();                
                 
                if(Changestat.equals("Reply")|| Changestat.equals("reply"))
                {                    
                String sql = "update messagetable set MessageStatus = 'replied' where receiver = '"+getReceiver()+"' and sender = '"+ m.getSender()+"'";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                ReplyMessage(getReceiver(),m.getSender());              
                          
                }
                else if(Changestat.equals("Pending") || Changestat.equals("pending"))
                {
                   System.out.println(" Send Message fast before it vanishes ^^^ ?"); 
                }
                
            }
            
            if(MessNotifyList.isEmpty()){           
               System.out.println("-----You don't have any  message -------");
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
                ps.close();
                rs.close();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
    }
    }
 ///////////////////////////////////////////////Reply to Message/////////////////////////////////////////////////////   
    public void ReplyMessage(String receiver, String sender){
        
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Type here : ");
        setMessageDet(input.nextLine());
        
         final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelh0486";

        Connection conn = null;
        Statement st = null;
      

        try {

            conn = DriverManager.getConnection(DB_URL, "patelh0486", "1839363");
            st = conn.createStatement();    
          
            String sql = "Insert into messagetable values( '"+ receiver+"', '"+sender+"', '"+ getMessageDet()+"', CURRENT_TIMESTAMP,'pending') ";
            st.executeUpdate(sql);            
            System.out.println("-----Your message has been sent-------");
            System.out.println("");
           
            
            
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
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
    }
 }
   //////////////////////////////////////////////////////Getter setter method///////////////////////////////////////////////////////////////////////
       public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

     public String getMessageDet() {
        return messageDet;
    }

    public void setMessageDet(String messageDet) {
        this.messageDet = messageDet;
    }
       

    public DateAndTime getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(DateAndTime Date_Time) {
        this.Date_Time = Date_Time;
    }

    public String getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(String MessageStatus) {
        this.MessageStatus = MessageStatus;
    }
 
}
