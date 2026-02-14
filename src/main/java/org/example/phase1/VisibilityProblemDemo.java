package org.example.phase1;

public class VisibilityProblemDemo {

    private static volatile boolean flag = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (!flag) {
                // Busy wait
            }
            System.out.println("Thread noticed flag change!");
        });

        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println("Flag changed to true");
    }
}
