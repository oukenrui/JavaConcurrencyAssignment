/******************************************************************************************************************
* File: PrintThread
* Course: 17630
* Author: Kentrell Owens
* Project: Assignment A6
*
* Description: This thread prints information at a fixed frequency
*
* Parameters:
*   AtomicReference<String> prodVar: the name of the current product being processed
*   AtomicInteger arrRate: the average arrival rate of requests (ms)
*   AtomicReference<String> prodVar1: the name of the current product being processed in Tool 1
*   AtomicReference<String> prodVar2: the name of the current product being processed in Tool 2
*   AtomicReference<String> prodVar3: the name of the current product being processed in Tool 3
*   AtomicInteger numA: the number of Product A's produced
*   AtomicInteger numB: the number of Product B's produced
*   AtomicInteger numC: the number of Product C's produced
*   AtomicInteger numD: the number of Product D's produced
*   AtomicInteger totNum: the total number of requests received
*   ConcurrentLinkedQueue<String> q1: the Tool1 queue
*   ConcurrentLinkedQueue<String> q2: the Tool2 queue
*   ConcurrentLinkedQueue<String> q3: the Tool3 queue
*
* Internal Methods:
*   none
*
* External Dependencies:
*   Server.java
******************************************************************************************************************/


import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class PrintThread implements Runnable
{
    // Queues for Tools
    public ConcurrentLinkedQueue<String> Q1;
    public ConcurrentLinkedQueue<String> Q2;
    public ConcurrentLinkedQueue<String> Q3;

    // ProductName
    public String ProductName;

    // Current Products in Production
    public AtomicReference<String> currentProd1;
    public AtomicReference<String> currentProd2;
    public AtomicReference<String> currentProd3;

    // Num of each product produced
    public AtomicInteger aProd;
    public AtomicInteger bProd;
    public AtomicInteger cProd;
    public AtomicInteger dProd;

    // total number of requests received
    public AtomicInteger total;

    // average arrival rate
    public AtomicInteger rate;

    // Constructor
    public PrintThread (AtomicInteger arrRate, AtomicReference<String> prodVar1, AtomicReference<String> prodVar2,
            AtomicReference<String> prodVar3, AtomicInteger numA, AtomicInteger numB, AtomicInteger numC,
            AtomicInteger numD, AtomicInteger totNum, ConcurrentLinkedQueue<String> q1, ConcurrentLinkedQueue<String> q2, ConcurrentLinkedQueue<String> q3)
    {
        this.rate = arrRate;
        this.currentProd1 = prodVar1;
        this.currentProd2 = prodVar2;
        this.currentProd3 = prodVar3;
        this.Q1 = q1;
        this.Q2 = q2;
        this.Q3 = q3;
        this.aProd = numA;
        this.bProd = numB;
        this.cProd = numC;
        this.dProd = numD;
        this.total = totNum;
    }

    public void run ()
    /*
     * This thread prints out information
     * every five seconds.
     */
    {
        while (true) {
            if (total.get() > 0)
            {
                try {
                    Thread.sleep(5000); // sleep 5s
                    System.out.println("");
                    System.out.println("Status update:");
                    System.out.println("");
                    int count1a = 0;
                    int count1b = 0;
                    int count1c = 0;
                    int count1d = 0;
                    System.out.println("Tool 1 is producing "+ currentProd1);

                    synchronized(Q1) {
                        System.out.println("Tool 1 Queue (length = "+Q1.size()+")");
                        for (String s: Q1) {
                            if (s.equals("ProductA")) {
                                count1a++;
                            } else if (s.equals("ProductB")) {
                                count1b++;
                            } else if (s.equals("ProductC")) {
                                count1c++;
                            } else if (s.equals("ProductD")) {
                                count1d++;
                            } // if - else if
                        } // for
                    } // synchronized

                    System.out.println("  Product A: "+ count1a);
                    System.out.println("  Product B: "+ count1b);
                    System.out.println("  Product C: "+ count1c);
                    System.out.println("  Product D: "+ count1d);
                    System.out.println("");

                    int count2a = 0;
                    int count2b = 0;
                    int count2c = 0;
                    int count2d = 0;
                    System.out.println("Tool 2 is producing "+ currentProd2);

                    synchronized(Q2) {
                        System.out.println("Tool 2 Queue (length = "+Q2.size()+")");
                        for (String s: Q2) {
                            if (s.equals("ProductA")) {
                                count2a++;
                            } else if (s.equals("ProductB")) {
                                count2b++;
                            } else if (s.equals("ProductC")) {
                                count2c++;
                            } else if (s.equals("ProductD")) {
                                count2d++;
                            } // if - else if
                        } // for
                    } // synchronized

                    System.out.println("  Product A: "+ count2a);
                    System.out.println("  Product B: "+ count2b);
                    System.out.println("  Product C: "+ count2c);
                    System.out.println("  Product D: "+ count2d);
                    System.out.println("");

                    int count3a = 0;
                    int count3b = 0;
                    int count3c = 0;
                    int count3d = 0;
                    System.out.println("Tool 3 is producing "+ currentProd3);

                    synchronized(Q3) {
                        System.out.println("Tool 3 Queue (length = "+Q3.size()+")");
                        for (String s: Q3) {
                            if (s.equals("ProductA")) {
                                count3a++;
                            } else if (s.equals("ProductB")) {
                                count3b++;
                            } else if (s.equals("ProductC")) {
                                count3c++;
                            } else if (s.equals("ProductD")) {
                                count3d++;
                            } // if - else if
                        } // for
                    } // synchronized

                    System.out.println("  Product A: "+ count3a);
                    System.out.println("  Product B: "+ count3b);
                    System.out.println("  Product C: "+ count3c);
                    System.out.println("  Product D: "+ count3d);
                    System.out.println("");

                    System.out.println("Product A's produced: " + aProd);
                    System.out.println("Product B's produced: " + bProd);
                    System.out.println("Product C's produced: " + cProd);
                    System.out.println("Product D's produced: " + dProd);
                    System.out.println("");
                    System.out.println("Total number of requests: " + total.get());
                    System.out.format("Average rate: %.1f s \n", (((double) rate.get())/1000));
                    System.out.println("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // try catch
            } // if
        } // while
    } // run
}
