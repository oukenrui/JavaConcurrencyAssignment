/******************************************************************************************************************
* File: Tool3
* Course: 17630
* Author: Kentrell Owens
* Project: Assignment A6
*
* Description: This thread removes a product from Tool3's queue, waits
* a predetermined amount of time, then increments the number of products produced
*
* Parameters:
*   AtomicReference<String> prodVar: the name of the current product being processed
*   AtomicInteger numB: the number of Product B's produced
*   AtomicInteger numC: the number of Product C's produced
*   AtomicInteger numD: the number of Product D's produced
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

public class Tool3 implements Runnable
{
    // Queue for Tool 3
    public ConcurrentLinkedQueue<String> Q3;

    // ProductName
    public String ProductName;
    public AtomicReference<String> currentProd;

    // num Produced
    public AtomicInteger numBProd;
    public AtomicInteger numCProd;
    public AtomicInteger numDProd;

    // Constructor
    public Tool3 (AtomicReference<String> prodVar, AtomicInteger numB, AtomicInteger numC,
            AtomicInteger numD,ConcurrentLinkedQueue<String> q3)
    {
        this.currentProd = prodVar;
        this.numBProd = numB;
        this.numCProd = numC;
        this.numDProd = numD;
        this.Q3 = q3;
    }

    public void run ()
    /*
     * This function should have three scenarios:
     * Product B, C or D. It sleeps and then adds
     * the product to the next appropriate list.
     */
    {
        while (true) {
            if (!Q3.isEmpty())
            {
                // Dequeue Tool 3's queue
                ProductName = Q3.poll();

                currentProd.set(ProductName);

                if (ProductName.equals("ProductB"))
                {
                    // Product B
                    try {
                        Thread.sleep(6000); // sleep 6s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Increment the number of Product B's
                    numBProd.getAndIncrement();
                }
                else if (ProductName.equals("ProductC"))
                {
                    // Product C
                    try {
                        Thread.sleep(9000); // sleep 9s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Increment the number of Product C's
                    numCProd.getAndIncrement();
                }
                else if (ProductName.equals("ProductD"))
                {
                    // Product D
                    try {
                        Thread.sleep(5000); // sleep 5s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Increment the number of Product D's
                    numDProd.getAndIncrement();
                }
            } // if
        } // while
    } // run

}
