/******************************************************************************************************************
* File: Server
* Course: 17630
* Author: Kentrell Owens
* Project: Assignment A6
*
* Description: This class uses sockets to receive product requests from the client job submission
* simulator (JobSim.java). After receiving the product request, it adds it to the appropriate
* tool queue. This application has four threads: Tool 1-3 and a Print Thread that prints
* information about the production every five seconds.
*
* Parameters: None
*
* Internal Methods:
*   none
*
* External Dependencies:
*   JobSim.java, Tool1.java, Tool2.java, Tool3.java, PrintThread.java
******************************************************************************************************************/


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import static java.lang.Math.toIntExact;

public class Server
{
    // store the number of received requests
    public static AtomicInteger totNumRequests = new AtomicInteger(0);

    // Create atomic counter variables
    public static AtomicInteger numAProduced = new AtomicInteger(0);
    public static AtomicInteger numBProduced = new AtomicInteger(0);
    public static AtomicInteger numCProduced = new AtomicInteger(0);
    public static AtomicInteger numDProduced = new AtomicInteger(0);

    // Create queues for Tool 1/2/3
    public static ConcurrentLinkedQueue<String> Q1 = new ConcurrentLinkedQueue<String>();
    public static ConcurrentLinkedQueue<String> Q2 = new ConcurrentLinkedQueue<String>();
    public static ConcurrentLinkedQueue<String> Q3 = new ConcurrentLinkedQueue<String>();

    // Create variables to save current products in production
    public static AtomicReference<String> prodT1 = new AtomicReference<>("nothing");
    public static AtomicReference<String> prodT2 = new AtomicReference<>("nothing");
    public static AtomicReference<String> prodT3 = new AtomicReference<>("nothing");

    // Variables to keep track of average arrival rate
    public static long arrRate;
    public static AtomicInteger myRate = new AtomicInteger(0);
    public static long newTime;
    public static Date prevTime = new Date();

    public static void main(String[] args) throws IOException
    {
        // We create a listener socket and wait for the client.
        ServerSocket listener = new ServerSocket(9090);

        System.out.println("Waiting for client...");

        try
        {
            // Here we accept a client and the print a message that we have connected.
            Socket s = listener.accept();
            System.out.println("Client connected...");

            // Instantiate threads
            Thread tool1 = new Thread(new Tool1(prodT1, Q1, Q2, Q3),"Tool 1");
            Thread tool2 = new Thread(new Tool2(prodT2, numAProduced, Q2, Q3),"Tool 2");
            Thread tool3 = new Thread(new Tool3(prodT3, numBProduced, numCProduced, numDProduced, Q3),"Tool 3");
            Thread print = new Thread(new PrintThread(myRate, prodT1, prodT2, prodT3, numAProduced, numBProduced,
                        numCProduced, numDProduced, totNumRequests, Q1, Q2, Q3),"Print Thread");

            // start threads
            tool1.start();
            tool2.start();
            tool3.start();
            print.start();

            while (true)
            {

                try
                {
                    // Here we read the job. You will get one of four strings from the client job
                    // simulator: ProductA, ProductB, ProductC, ProductD for each of the possible products.
                    // Once received, I print out the strings.
                    String acceptableInputs = "ProductAProductBProductCProductD";
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String prod = input.readLine();

                    // reset current Time when new product req arrives
                    Date currentTime = new Date();

                    if (acceptableInputs.contains(prod)) // if the product request is valid
                    {

                        if (prod.equals("ProductA"))
                        {
                            // Product A
                            Q1.add(prod);
                        }
                        else if (prod.equals("ProductB"))
                        {
                            // Product B
                            Q2.add(prod);
                        }
                        else if (prod.equals("ProductC"))
                        {
                            // Product C
                            Q1.add(prod);
                        }
                        else if (prod.equals("ProductD"))
                        {
                            // Product D
                            Q1.add(prod);
                        }

                        // Update the total number of requests and
                        // the average arrival rate
                        synchronized (totNumRequests)
                        {
                            totNumRequests.getAndIncrement(); // increment num of requests

                            // if there are more than two requests
                            if (totNumRequests.get() > 2) {

                                newTime = currentTime.getTime() - prevTime.getTime();

                                // calculate average arrival rate
                                arrRate = (arrRate * (totNumRequests.get() - 1) + newTime)/totNumRequests.get();
                                prevTime.setTime(currentTime.getTime());

                            // if there have only been two requests
                            } else if (totNumRequests.get() == 2) {

                                // calculate new arrival rate
                                newTime = currentTime.getTime() - prevTime.getTime();
                                arrRate = newTime;
                                prevTime.setTime(currentTime.getTime());

                            // if this is the first request
                            } else {

                                // set prevTime = currentTime
                                prevTime.setTime(currentTime.getTime());
                            }

                            // set the Atomic Interger that we pass into Print Thread
                            // equal to the average rate in milliseconds
                            myRate.set( toIntExact(arrRate));
                        } // synchronized
                    }
                    else
                    {
                        System.out.println("Invalid Product Input: "+prod);
                    }

                } catch(Exception e) {

                    System.out.println("Error reading socket:: " + e);

                }

            } // while

        } catch(Exception e) {

            System.out.println("Error connecting to client:: " + e);

        } // try
    } // main
} // class
