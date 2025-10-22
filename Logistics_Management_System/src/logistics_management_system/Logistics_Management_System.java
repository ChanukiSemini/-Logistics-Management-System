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

    // Distance Management 
    static int[][] interCityDistance = new int[MAX_CITY_COUNT][MAX_CITY_COUNT];

    static void setSelfDistance() {
        for (int i = 0; i < MAX_CITY_COUNT; i++) {
            for (int j = 0; j < MAX_CITY_COUNT; j++) {
                if (i == j) {
                    interCityDistance[i][j] = 0; //Prevent distance from city → itself (set as 0)
                } else {
                    interCityDistance[i][j] = 999999;
                }
            }
        }
    }

    //main method
    public static void main(String[] args) {

        setSelfDistance();

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

            switch (choice) {
                case 1:
                    manageCities();
                    break;
                case 2:
                    manageDistances();
                    break;
                case 3:
                    //delivary request
                    break;
                case 4:
                    //show reports
                    break;
                case 5:
                    return;
                //System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }

        }

    }

    //City Management 
    static void manageCities() {

        while (true) {

            System.out.println("\n................................");
            System.out.println("        --- City Management ---       \n");
            System.out.println(" 1. Add City                          ");
            System.out.println(" 2. Rename City                      ");
            System.out.println(" 3. Remove City                       ");
            System.out.println(" 4. Show all Cities                   ");
            System.out.println(" 5. Back to Main Menu                 ");
            System.out.println("..................................");
            System.out.print("Enter your choice: ");

            int input = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (input) {
                case 1:
                    addCity();
                    break;
                case 2:
                    renameCity();
                    break;
                case 3:
                    removeCity();
                    break;
                case 4:
                    listCities();
                    break;
                case 5:
                    return; //  back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
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

    //function for rename a city name
    static void renameCity() {
        listCities(); //show all cities

        System.out.print("Enter city index to rename: ");
        int i = scan.nextInt();
        scan.nextLine();

        if (i < 0 || i > cityCount) {
            System.out.println("Invalid index !");
            return;
        }
        System.out.print("Enter new name: ");
        cities[(i - 1)] = scan.nextLine();
        System.out.println("Renamed successfully.");
    }

    //function for remove a city
    static void removeCity() {
        listCities();

        System.out.print("Enter index to remove: ");
        int i = scan.nextInt();
        scan.nextLine();

        int index = i - 1;

        if (i < 0 || i >= cityCount) {
            System.out.println("Invalid index !");
            return;
        }

        // Shift elements left starting from the removed index
        for (int j = index; j < cityCount - 1; j++) {
            cities[(j)] = cities[j + 1];
        }
        cities[cityCount - 1] = null;   // Clear the last element

        cityCount--;
        System.out.println("city Removed.");
    }

    // Distance Management 
    static void manageDistances() {

        while (true) {
            System.out.println("\n................................");
            System.out.println("    --- Distance Management ---    \n");
            System.out.println("  1. Add/Edit Distance              ");
            System.out.println("  2. Show Distance Table            ");
            System.out.println("  3. Back to Main Menu                 ");
            System.out.println("..................................");

            System.out.print("Enter your choice: ");
            int input = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (input) {
                case 1:
                    editDistance();
                    break;
                case 2:
                    showDistanceTable();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid.");
                    break;
            }
        }
    }

    //function for edit distance
    static void editDistance() {

        if (cityCount < 2) {
            System.out.println("Need  atleast 2 cities to Add distance !");
            return;
        }
        listCities();

        System.out.print("Source City index: ");
        int SourceCity = scan.nextInt() - 1;
        scan.nextLine(); // consume newline

        System.out.print("Destination City index: ");
        int DesCity = scan.nextInt() - 1;
        scan.nextLine(); // consume newline

        if (SourceCity < 0 || SourceCity >= cityCount || DesCity < 0 || DesCity >= cityCount || SourceCity == DesCity) {
            System.out.println("Invalid Input !.");
            return;
        }
        System.out.print("Enter distance (km): ");
        int distance = scan.nextInt();
        scan.nextLine(); // consume newline

        interCityDistance[SourceCity][DesCity] = distance;
        interCityDistance[DesCity][SourceCity] = distance;

        System.out.println("Distance updated.");
    }

    //function for show distance
    static void showDistanceTable() {

        System.out.print(" \n  ......intercity distances......");

        for (int j = 0; j < cityCount; j++) {
            System.out.printf("%10s", cities[j]);
        }
        System.out.println();

        for (int i = 0; i < cityCount; i++) {

            System.out.printf("%-6s", cities[i]);

            for (int j = 0; j < cityCount; j++) {
                if (interCityDistance[i][j] >= 999999) {
                    System.out.printf("%10s", "-");
                } else {
                    System.out.printf("%10d", interCityDistance[i][j]);
                }
            }

            System.out.println();
        }
    }

}
