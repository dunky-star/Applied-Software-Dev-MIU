package com.dunky.cs489.dockerization;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    // Method to continuously show system time every second
    public static void startClock() {
        // Define the time format (HH:mm:ss)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        while (true) {
            //Get the current System time
            LocalTime now = LocalTime.now();
            // Format and print the time
            System.out.println("Current time: " + now.format(formatter));
            // Sleep for 1 second
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Handle the exception
                System.err.println("Thread interrupted: " + e.getMessage());
                break;
            }
        }


    }
    public static void main(String[] args) {
        System.out.println("Dockerizing a Vanilla Java App!");
        // Call the method to start showing time
        startClock();
    }
}