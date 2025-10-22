/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics_management_system;

import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

/**
 *
 * @author chanuki
 */
public class Logistics_Management_System {

    // City managemnet 
    static final int MAX_CITY_COUNT = 30;
    static int cityCount = 0;
    static String[] cities = new String[MAX_CITY_COUNT];

    // Distance Management 
    static int[][] interCityDistance = new int[MAX_CITY_COUNT][MAX_CITY_COUNT];

    //set 0 to self distance
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

    //Vehicle Management
    static String[] vehicleNames = {"Van", "Truck", "Lorry"};
    static int[] capacity = {1000, 5000, 10000};      // kg
    static int[] ratePerKm = {30, 40, 80};            // LKR per km
    static int[] avgSpeed = {60, 50, 45};             // km per h
    static int[] FuelEfficiency = {12, 6, 4};         // km per l

    static final double FUEL_PRICE = 310.0;

    //delivery management
    static final int MAX_DELIVERY_ITEMS = 50;
    static int deliveryCount = 0;

    //To store 50 deliveries maximum for (Cost, Time, and Fuel Calculations )
    static int[] fromCity = new int[MAX_DELIVERY_ITEMS];
    static int[] toCity = new int[MAX_DELIVERY_ITEMS];
    static int[] distance = new int[MAX_DELIVERY_ITEMS];
    static int[] vehicleType = new int[MAX_DELIVERY_ITEMS];
    static int[] weight = new int[MAX_DELIVERY_ITEMS];

    static double[] baseCost = new double[MAX_DELIVERY_ITEMS];
    static double[] fuelUsed = new double[MAX_DELIVERY_ITEMS];
    static double[] fuelCost = new double[MAX_DELIVERY_ITEMS];
    static double[] totalCost = new double[MAX_DELIVERY_ITEMS];
    static double[] profit = new double[MAX_DELIVERY_ITEMS];
    static double[] customerCharge = new double[MAX_DELIVERY_ITEMS];
    static double[] timeTaken = new double[MAX_DELIVERY_ITEMS];
    static String[] route = new String[MAX_DELIVERY_ITEMS];

    static Scanner scan = new Scanner(System.in);

    //main method
    public static void main(String[] args) {

        setSelfDistance(); // for set distance as 0 for  in 2D Array
        loadRoutes();       // load the saved routes
        loadDeliveries();  // load saved delivery history

        while (true) {
            try {
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
                        createDelivery();
                        break;
                    case 4:
                        showReports();
                        break;
                    case 5:
                        saveRoutes();
                        saveDeliveries();

                        System.out.println("Saved and Exit.");
                        return;
                    //System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1-5.");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

    //City Management 
    static void manageCities() {

        while (true) {
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1-5.");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
    }

    //function for add new city
    static void addCity() {

        try {
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
        System.out.println("");
    }

    //function for rename a city name
    static void renameCity() {
        listCities(); //show all cities

        try {
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //function for remove a city
    static void removeCity() {
        listCities();

        try {
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Distance Management 
    static void manageDistances() {

        while (true) {

            try {
                System.out.println("\n................................");
                System.out.println("    --- Distance Management ---    \n");
                System.out.println("  1. Add/Edit Distance              ");
                System.out.println("  2. Show Distance Table            ");
                System.out.println("  3. Back to Main Menu                 ");
                System.out.println("..................................");

                System.out.print("Enter your choice: ");
                int input = scan.nextInt();
                scan.nextLine(); // consume newline
                System.out.println("\n");

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

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1-3.");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
    }

    //function for edit distance
    static void editDistance() {

        try {
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
            int dist = scan.nextInt();
            scan.nextLine(); // consume newline

            interCityDistance[SourceCity][DesCity] = dist;
            interCityDistance[DesCity][SourceCity] = dist;

            System.out.println("Distance updated.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    //function for show intercity distance
    static void showDistanceTable() {

        
        if(cityCount<2){
            System.out.println("Empty.");
        }
        else{
                System.out.print(" \n  ......Intercity Distances......\n");
        System.out.print("      ");

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

    //function for create a Request Handling
    static void createDelivery() {

        while (true) {

            try {
                if (cityCount < 2) {
                    System.out.println("Add minimum 2 or more cities first!");
                    return;
                }

                System.out.println("\n.........Order details .........\n");

                listCities(); // show all cities 

                System.out.print("Enter the index of Source City: ");
                int SCity = scan.nextInt() - 1;
                scan.nextLine(); // consume newline

                System.out.print("Enter the index of Desination City: ");
                int DesCity = scan.nextInt() - 1;
                scan.nextLine(); // consume newline

                if (SCity == DesCity || SCity < 0 || DesCity < 0 || SCity >= cityCount || DesCity >= cityCount) {
                    System.out.println("Invalid selection !");
                    return;
                }

                System.out.print("Enter the Weight of the Package(kg): ");
                int Weight = scan.nextInt();
                scan.nextLine();

                //show vehicle capacities to before chose a vehicle
                System.out.println("\n ..............Vehicle capacities.............");
                for (int i = 0; i < 3; i++) {
                    System.out.printf(vehicleNames[i] + " - ");
                    System.out.printf(capacity[i] + " Kg    ");
                }
                System.out.println("\n");

                System.out.print("Select vehicle (1.Van 2.Truck 3.Lorry): ");
                int vehicle = scan.nextInt() - 1;
                scan.nextLine();

                if (vehicle < 0 || vehicle > 2) {
                    System.out.println("Invalid selection ! Please select valid Number.");
                    return;
                }
                if (Weight > capacity[vehicle]) {
                    System.out.println("Your package Weight exceeds the vehicle capacity ! \n Please choose another vehicle type !");
                    return;
                }

                // find least distance
                int dist = findShortestPath(SCity, DesCity);
                if (dist >= 999999) {
                    System.out.println("No route available.");
                    return;
                }

                // calculate costs
                double base = dist * ratePerKm[vehicle] * (1 + (double) Weight / 10000.0);
                double time = (double) dist / avgSpeed[vehicle];
                double fuel = (double) dist / FuelEfficiency[vehicle];
                double fCost = fuel * FUEL_PRICE;
                double total = base + fCost;
                double prof = base * 0.25;
                double charge = total + prof;

                System.out.println("\n");

                System.out.println("==================================================");
                System.out.println("\n DELIVERY COST  ESTIMATION ");
                System.out.println("--------------------------------------------------\n");
                System.out.println("From: " + cities[SCity]);
                System.out.println("To: " + cities[DesCity]);
                System.out.println("Minimum Distance: " + dist + " km");
                System.out.println("Vehicle: " + vehicleNames[vehicle]);
                System.out.println("Weight: " + Weight);
                System.out.println("\n------------------------------------------------\n");

                System.out.printf("Base Cost: %.2f LKR \n", base);
                System.out.printf("Fuel Cost: %.2f LKR \n", fCost);
                System.out.printf("Fuel Used: %.2f LKR \n", fuel);
                System.out.printf("Operational Cost: %.2f LKR \n", total);
                System.out.printf("Profit: %.2f LKR\n", prof);
                System.out.printf("Customer Charge: %.2f LKR \n", charge);
                System.out.printf("Estimated Time: %.2f hours \n", time);
                System.out.println("\n==================================================");

                System.out.print("Save delivery? (y/n): ");
                String ans = scan.nextLine();

                if (ans != null && ans.toLowerCase().equals("y") && deliveryCount < MAX_DELIVERY_ITEMS) {
                    fromCity[deliveryCount] = SCity;
                    toCity[deliveryCount] = DesCity;
                    distance[deliveryCount] = dist;
                    vehicleType[deliveryCount] = vehicle;
                    weight[deliveryCount] = Weight;
                    baseCost[deliveryCount] = base;
                    fuelUsed[deliveryCount] = fuel;
                    fuelCost[deliveryCount] = fCost;
                    totalCost[deliveryCount] = total;
                    profit[deliveryCount] = prof;
                    customerCharge[deliveryCount] = charge;
                    timeTaken[deliveryCount] = time;
                    route[deliveryCount] = cities[SCity] + " -> " + cities[DesCity];
                    deliveryCount++;
                    System.out.println("Delivery saved!");
                } else {
                    System.out.println("Order Cannot Delivary !");
                }
                break;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

    //Finding The Least-Cost Route (Least-Distance)
    static int findShortestPath(int src, int dest) {

        int n = cityCount;
        int[] d = new int[n];
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) {
            d[i] = 999999;
            used[i] = false;
        }
        d[src] = 0;

        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (v == -1 || d[j] < d[v])) {
                    v = j;
                }
            }
            if (d[v] == 999999) {
                break;
            }
            used[v] = true;
            for (int j = 0; j < n; j++) {
                if (interCityDistance[v][j] < 999999) {
                    if (d[v] + interCityDistance[v][j] < d[j]) {
                        d[j] = d[v] + interCityDistance[v][j];
                    }
                }
            }
        }
        return d[dest];
    }

    // Show Reports 
    static void showReports() {

        System.out.println("\n-------- Delivery Reports ---------\n");
        System.out.println("Total Deliveries: " + deliveryCount);

        int totalDist = 0;
        double totalTime = 0, totalProfit = 0, totalRevenue = 0;
        int longest = 0, shortest = 999999;
        String longRoute = "", shortRoute = "";

        for (int i = 0; i < deliveryCount; i++) {
            totalDist += distance[i];
            totalTime += timeTaken[i];
            totalProfit += profit[i];
            totalRevenue += customerCharge[i];

            if (distance[i] > longest) {
                longest = distance[i];
                longRoute = route[i];
            }
            if (distance[i] < shortest) {
                shortest = distance[i];
                shortRoute = route[i];
            }
        }

        System.out.println("Total Distance: " + totalDist + " km");

        if (deliveryCount > 0) {
            System.out.printf("Average Time: %.2f hours \n", totalTime / deliveryCount);
            System.out.printf("Total Profit: %.2f LKR  \n", totalProfit);
            System.out.printf("Total Revenue: %.2f LKR   \n", totalRevenue);
            System.out.println("Longest Route: " + longRoute + " (" + longest + " km)");
            System.out.println("Shortest Route: " + shortRoute + " (" + shortest + " km)");

        }
    }

    //File Handling 
    //function for save  City list and distance matrix
    static void saveRoutes() {
        try (FileWriter write = new FileWriter("routes.txt")) {

            // Save city count as text
            write.write(String.valueOf(cityCount) + "\n");

            // Save city names, comma-separated
            for (int i = 0; i < cityCount; i++) {
                write.write(cities[i]);
                if (i < cityCount - 1) {
                    write.write(",");
                }
            }
            write.write("\n");

            // Save distance matrix
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    write.write(String.valueOf(interCityDistance[i][j]));
                    if (j < cityCount - 1) {
                        write.write(",");
                    }
                }
                write.write("\n");
            }

            System.out.println("routes.txt Successfully saved.");

        } catch (IOException e) {
            System.out.println(e.getMessage() + " Error saving routes.");
        }
    }

    //function for load saved routed when starting the program
  static void loadRoutes() {
    File file = new File("routes.txt");

    if (!file.exists()) {
        return;
    }

    try (Scanner scanner = new Scanner(file)) {

        // Read city count
        cityCount = Integer.parseInt(scanner.nextLine().trim());

        // Read city names (comma-separated)
        String[] cityLine = scanner.nextLine().split(",");
        for (int i = 0; i < cityLine.length; i++) {
            cities[i] = cityLine[i].trim();
        }

        // Read distance matrix
        for (int i = 0; i < cityCount; i++) {
            String[] p = scanner.nextLine().split(",");
            for (int j = 0; j < cityCount; j++) {
                interCityDistance[i][j] = Integer.parseInt(p[j].trim());
            }
        }

        System.out.println("routes.txt loaded Successfully.");

    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error loading routes: " + e.getMessage());
    }
}


    //function for save delivery history
    static void saveDeliveries() {

        try (PrintWriter pw = new PrintWriter("deliveries.txt")) {

            for (int i = 0; i < deliveryCount; i++) {
                pw.printf("%d,%d,%d,%d,%d,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%s\n",
                        fromCity[i], toCity[i], distance[i], vehicleType[i], weight[i],
                        baseCost[i], fuelUsed[i], fuelCost[i], totalCost[i],
                        profit[i], customerCharge[i], route[i]);
            }
            pw.close();

            System.out.println("deliveries.txt saved.");

        } catch (IOException e) {
            System.out.println("Error saving deliveries.");
        }
    }

    //function for loading saved deliveries
    static void loadDeliveries() {

        File file = new File("deliveries.txt");

        if (!file.exists()) {
            return;
        }

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine() && deliveryCount < MAX_DELIVERY_ITEMS) {
                String[] p = sc.nextLine().split(",");
                fromCity[deliveryCount] = Integer.parseInt(p[0]);
                toCity[deliveryCount] = Integer.parseInt(p[1]);
                distance[deliveryCount] = Integer.parseInt(p[2]);
                vehicleType[deliveryCount] = Integer.parseInt(p[3]);
                weight[deliveryCount] = Integer.parseInt(p[4]);
                baseCost[deliveryCount] = Double.parseDouble(p[5]);
                fuelUsed[deliveryCount] = Double.parseDouble(p[6]);
                fuelCost[deliveryCount] = Double.parseDouble(p[7]);
                totalCost[deliveryCount] = Double.parseDouble(p[8]);
                profit[deliveryCount] = Double.parseDouble(p[9]);
                customerCharge[deliveryCount] = Double.parseDouble(p[10]);
                route[deliveryCount] = p[11];
                deliveryCount++;
            }
            sc.close();

            System.out.println("deliveries.txt loaded Successfully.");

        } catch (IOException e) {
            System.out.println("Error loading deliveries." + e.getMessage());
        }
    }

}
