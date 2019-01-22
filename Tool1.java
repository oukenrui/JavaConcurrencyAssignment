/******************************************************************************************************************
* File: Tool1
* Course: 17630
* Author: Kentrell Owens
* Project: Assignment A6
*
* Description: This thread removes a product from Tool1's queue, waits
* a predetermined amount of time, then add the product to its next Tool queue.
*
* Parameters:
*   AtomicReference<String> prodVar: the name of the current product being processed
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

public class Tool1 implements Runnable
{
    // Queues for Tools
    public ConcurrentLinkedQueue<String> Q1;
    public ConcurrentLinkedQueue<String> Q2;
    public ConcurrentLinkedQueue<String> Q3;

    // ProductName
    public String ProductName;
    public AtomicReference<String> currentProd;

    // Constructor
    public Tool1 (AtomicReference<String> prodVar, ConcurrentLinkedQueue<String> q1, ConcurrentLinkedQueue<String> q2,
            ConcurrentLinkedQueue<String> q3)
    {
        this.currentProd = prodVar;
        this.Q1 = q1;
        this.Q2 = q2;
        this.Q3 = q3;
    }

    // Runnable
    public void run ()
    /*
     * This function should have three scenarios:
     * Product A, C or D. It sleeps and then adds
     * the product to the next appropriate list.
     */
    {
        while (true) {
            if (!Q1.isEmpty()) // while the queue is not empty
            {
                // Dequeue
                ProductName = Q1.poll();

                currentProd.set(ProductName);

                if (ProductName.equals("ProductA"))
                {
                    // Product A
                    try {
                        Thread.sleep(10000); // sleep 10s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Add to Tool2 Queue
                    Q2.add(ProductName);

                }
                else if (ProductName.equals("ProductC"))
                {
                    // Product C
                    try {
                        Thread.sleep(11000); // sleep 11s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Add to Tool3 Queue
                    Q3.add(ProductName);

                }
                else if (ProductName.equals("ProductD"))
                {
                    // Product D
                    try {
                        Thread.sleep(7000); // sleep 7s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Add to Tool2 Queue
                    Q2.add(ProductName);

                }
            } // if
        } // while
    } // run

}
