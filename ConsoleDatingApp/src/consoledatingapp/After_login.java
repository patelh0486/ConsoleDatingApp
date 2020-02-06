 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoledatingapp;

import java.util.Scanner;

/**
 *
 * @author hinal
 */
public class After_login {
    
    //User_Account curr = new User_Account();
    public After_login(User_Account user){
        //this.curr = user;
    }
    User_Account user;
     public After_login(){
        this.user = User_Account.curr;
    }
     
    public void test(){
        
        Scanner input = new Scanner(System.in);
        String selection = "";
        
       
        
         while (!selection.equals("9")) {

                    System.out.println("+++++++++++++++++++++++++++++++++");
                    System.out.println(" Choose what you want to do :");
                    System.out.println("+++++++++++++++++++++++++++++++++");
                    System.out.println("1: Wanna Reset password ?");
                    System.out.println("2: View your own Profile ?");
                    System.out.println("3: Let's Try Dating ;) [search]");
                    System.out.println("4: Send request  to friends");
                    System.out.println("5: View frienlist");
                    System.out.println("6: View notification");
                    System.out.println("7: Check request status :( ");
                    System.out.println("8: Message |_| ");
                    System.out.println("9: Logout :( ");
                    
                    selection = input.nextLine();
                    System.out.println();

                    if (selection.equals("1")) {
                        Profile_page lp = new Profile_page();
                        lp.ResetPassword(user.getPassword(),user.getFirst_name());
                    } 
                    else if (selection.equals("2")) {
                        Profile_page lp = new Profile_page();
                        lp.ViewPofile(user.getUser_id(),user.getFirst_name(), user.getPassword());
                    } 
                    else if (selection.equals("3")) {
                        Profile_page lp = new Profile_page();
                        lp.Search(user.getUser_id(),user.getFirst_name(), user.getPassword());                     
                    } 
                    else if (selection.equals("4")) {
                        FriendList fl = new FriendList();
                        fl.RequestFriends(user.getUser_id(),user.getUser_id());
                    }
                    else if (selection.equals("5")) {
                        FriendList fl = new FriendList();
                        fl.ViewFriends(user.getUser_id(),user.getUser_id());
                    }
                    else if (selection.equals("6")) {
                        FriendList fl = new FriendList();
                        fl.ViewNotification(user.getUser_id());
                    }
                     else if (selection.equals("7")) {                         
                        FriendList fl = new FriendList();
                        fl.CheckRequestStatus(user.getUser_id());                        
                     }
                     else if (selection.equals("8")) {                         
                        Message m = new Message();
                       m.messageMenu(user.getUser_id());
                        
                     }
                    else if (selection.equals("9")) {
                        Profile_page lp = new Profile_page();                        
                        lp.logout(user.getFirst_name(),user.getPassword());
                        break;
                    }
               }
         }  
 
   }


