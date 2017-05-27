/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * Written and Compiled first in NetBeans
 */

package diningphilosophers;

/**
 * Team Members involved in this project
 * 
 * @author anupvasudevan
 * @author praneetdaniels
 * @author nadia
 */

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Philosopher extends Thread {
    private static Random rnd = new Random();
    private static int cnt = 0;
    private final int num = cnt++;
    private Spoon leftSpoon;
    private Spoon rightSpoon;
    static int waiting = 0;

    public Philosopher(Spoon left, Spoon right) {
        leftSpoon = left;
        rightSpoon = right;
        start();
    }

    public void thinking() {
        System.out.println(this + " is thinking");

        if (waiting > 0) {
            try {
                sleep(rnd.nextInt(waiting));
            } 
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void eating() {
        synchronized (leftSpoon) {
        System.out.println(this + " has " + this.leftSpoon + " Waiting for " + this.rightSpoon);

            synchronized (rightSpoon) {
            System.out.println(this + " eating");
            }
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + num;
    }

    @Override
    public void run() {
        while (true) {
          thinking();
          eating();
        }
    }
}


class Spoon {
    private static int cnt = 0;
    private final int num = cnt++;
    
    @Override
    public String toString() {
        return "Spoon " + num;
    }

}


public class DiningPhilosophers {
    
    /**
    * Change the below variable "deadLockOn" to false 
    * if you want the program to run indefinitely and not get deadlocked
    * 
    * If you want to showcase deadlock, change the value of "deadLockOn" to true
    */
    private static boolean deadLockOn = false;
    
    public static void main(String[] args) {
        Philosopher[] phil = new Philosopher[10];
        Philosopher.waiting = 8;
        Spoon left = new Spoon(), right = new Spoon(), first = left;

        int i = 0;
        while (i < phil.length - 1) {
            phil[i++] = new Philosopher(left, right);

            left = right;
            right = new Spoon();
        }


        if (deadLockOn) {
          phil[i] = new Philosopher(left, first);
        } 
        else {
          phil[i] = new Philosopher(first, left);
        }

        if (args.length >= 4) {
          int delay = 3;

          if (delay != 0) {
            Timeout timeout = new Timeout(delay * 4000, "Timed out");
          }
        }
    }
}

class Timeout extends Timer {
    public Timeout(int delay, final String msg) {

        super(true); 

        schedule(new TimerTask() {

        @Override
        public void run() {
            System.out.println(msg);
            System.exit(0);
        }

       }, delay);
   }
}
