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
public class ConsoleDatingApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         //declare varaibles
        Scanner input = new Scanner(System.in);
        String selection = "";
        
        while(!selection.equals("x"))
        {
            //display the 
            System.out.println("*******************************");
            System.out.println("Welcome to Finder Dating app");
            System.out.println("*******************************");
            System.out.println("Please choose any from below");
            System.out.println("1: Sign up for new account");
            System.out.println("2: Login to existing account");
            System.out.println("x: Finish the simulation");
            System.out.println("");
            System.out.println("");
            
            //get the selection from the user
            selection = input.nextLine();
            System.out.println();
            
            if(selection.equals("1"))
            {
                //open a new account
                User_Account UA = new User_Account();
                UA.createNewUser_Account();
            }
            else if(selection.equals("2"))
            {
                //go to the online system
                User_Account log = new User_Account();
                log.showMainPage();
            }
            else if(selection.equals("x")){
                break;
            }
        }
    }
    }
    

