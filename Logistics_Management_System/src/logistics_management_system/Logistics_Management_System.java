/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics_management_system;

import java.util.Scanner;

/**
 *
 * @author chanuki
 */
public class Logistics_Management_System {

    // City managemnet
    static final int MAX_CITY_COUNT = 30;
    static int cityCount = 0;
    static String[] cities = new String[MAX_CITY_COUNT];

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n ==== LOGISTICS MANAGEMENT SYSTEM ====");
            System.out.println("|                                     |");
            System.out.println("|   1. City Management                |");
            System.out.println("|   2. Distance Management            |");
            System.out.println("|   3. Create Delivery Request        |");
            System.out.println("|   4. Reports                        |");
            System.out.println("|   5. Save and Exit                  |");
            System.out.println(" -------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();

            if (choice == 1) {
                manageCities();
            } else {
                //return;
            }
        }

    }

    //City Management 
    static void manageCities() {

        while (true) {
            System.out.println("\n................................");
            System.out.println("        --- City Management ---       ");
            System.out.println(" 1. Add City                          ");
            System.out.println(" 2. Rename City                      ");
            System.out.println(" 3. Remove City                       ");
            System.out.println(" 4. Show all Cities                   ");
            System.out.println(" 5. Back to Main Menu                 ");
            System.out.println("..................................");
            System.out.print("Enter your choice: ");

            int c = scan.nextInt();
            scan.nextLine(); // consume newline

            if (c == 1) {
                addCity();
            } else if (c == 2) {
                //renameCity();
            } else if (c == 3) {
                //removeCity();
            } else if (c == 4) {
                listCities();
            } else if (c == 5) {
                //  back to main menu
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //function for add new city
    static void addCity() {

        //check whether the maximun city limit has reached or not
        if (cityCount >= MAX_CITY_COUNT) {
            System.out.println("Cannot added more cities. City limit reached ! ");
            return;
        }

        System.out.print("Enter city name: ");
        String cityName = scan.nextLine();

        System.out.println("\n");

        //checking the entered name exits or not
        for (int i = 0; i < cityCount; i++) {
            if (cities[i].toLowerCase().equals(cityName.toLowerCase())) {
                System.out.println("City " + cityName + " is already exists !");
                return;
            }
        }
        //otherwise, add the name to the list
        cities[cityCount++] = cityName;
        System.out.println(cityName + " Added to the List ! ");
    }

    //function for show all cities
    static void listCities() {
        System.out.println("All the Cities: \n");
        if (cityCount < 1) {
            System.out.println("No Cities to display..");
        }
        for (int i = 0; i < cityCount; i++) {
            System.out.println((i + 1) + " - " + cities[i]);
        }
    }

 

}
