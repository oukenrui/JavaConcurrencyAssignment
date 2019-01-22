/******************************************************************************************************************
* File: JobSim.java
* Course: 17630
* Project: Assignment A6
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*   1.0 June 2018 - Initial write of Job Subission Simulator (ajl).
*
* Description: This class submits jobs to a job server. It is a client that uses sockets to submit product requests
* to a server that satisfies the order. Orders are in the form of one of four strings: ProductA, ProductB, ProductC
* ProductD for each of the possible products. The type of product is decided by random number. Jobs will be sent 
* continously in 1 to 6 second intervals.
*
* Parameters: None
*
* Internal Methods: None
*
* External Dependencies: None
*
******************************************************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.Random;

public class JobSim 
{
    public static void main(String[] args) throws IOException 
    {

        int     i;                      // counter
        int     rannum;                 // random number
        int     randomitem = 0;         // Some random item for males
        int     orderdelay = 0;         // Inter-order delay time
        String  product = null;         // customer name    

        
        // Random number generator
        
        Random rand = new Random(); // Random object

        // Socket to the server... assumed to be on the local host

        Socket s = new Socket("localhost", 9090);

        // Main submission loop

        while(true)
        {
            
            // We get a random number between 1 and 4. Then we assign a
            // product string. 1=A, 2=B, 3=C, 4=D.

            rannum = rand.nextInt(4) + 1;

            switch (rannum) 
            {
                case 1:
                {
                    product = "ProductA";
                    break;
                }
                case 2:
                {
                    product = "ProductB";
                    break;
                }
                case 3:
                {
                    product = "ProductC";
                    break;
                }
                case 4:
                {
                    product = "ProductD";
                    break;
                }
            }


            System.out.println("Product String:: " + product);

            // Here we write the product string to the sever socket

            try 
            {
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                out.println(product);

            } catch(Exception e) {

                System.out.println("Error writing to socket::" + e);
                s.close();
                System.exit(0);

            }

            // Here we get a radom number between 1 and 6 that will be the 
            // inter-order delay time.

            orderdelay = rand.nextInt(6) + 1;
            System.out.println("Inter-order delay: " + orderdelay + " seconds.");
            orderdelay *= 1000;

            try 
            {

                Thread.sleep(orderdelay);
            
            } catch (InterruptedException e) {
            
                System.out.println("Error in sleep::" + e);
                s.close();
                System.exit(0);
            
            }

        } // while
    } // main
} // class