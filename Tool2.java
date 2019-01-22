/******************************************************************************************************************
* File: Tool2
* Course: 17630
* Author: Kentrell Owens
* Project: Assignment A6
*
* Description: This thread removes a product from Tool2's queue, waits
* a predetermined amount of time, then add the product to its next Tool queue
* or increments the number of products produced.
*
* Parameters:
*   AtomicReference<String> prodVar: the name of the current product being processed
*   AtomicInteger numA: the number of Product A's produced
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

public class Tool2 implements Runnable
{
    // Queues for Tools
    public ConcurrentLinkedQueue<String> Q2;
    public ConcurrentLinkedQueue<String> Q3;

    // ProductName
    public String ProductName;
    public AtomicReference<String> currentProd;

    // numAProduced
    public AtomicInteger numAProd;

    // Constructor
    public Tool2 (AtomicReference<String> prodVar, AtomicInteger numA, ConcurrentLinkedQueue<String> q2,
            ConcurrentLinkedQueue<String> q3)
    {
        this.currentProd = prodVar;
        this.numAProd = numA;
        this.Q2 = q2;
        this.Q3 = q3;
    }

    public void run ()
    /*
     * This function should have three scenarios:
     * Product A, B or D. It sleeps and then adds
     * the product to the next appropriate list.
     */
    {
        while (true) {
            if (!Q2.isEmpty())
            {
                // Dequeue Tool 2's Queue
                ProductName = Q2.poll();

                currentProd.set(ProductName);

                if (ProductName.equals("ProductA"))
                {
                    // Product A
                    try {
                        Thread.sleep(8000); // sleep 8s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Increment the number of Product A's produced
                    numAProd.getAndIncrement();

                }
                else if (ProductName.equals("ProductB"))
                {
                    // Product B
                    try {
                        Thread.sleep(12000); // sleep 12s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Add to Tool 3's Queue
                    Q3.add(ProductName);

                }
                else if (ProductName.equals("ProductD"))
                {
                    // Product D
                    try {
                        Thread.sleep(6000); // sleep 6s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Add to Tool 3's queue
                    Q3.add(ProductName);

                } // else - if
            } // if
        } // while
    } // run

}
