import model.Car;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This application tries to read some data from a file name
 * carData and does some actions such as add a car , delete car ...
 */
public class Main {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Car> cars = new ArrayList<>();
    static final String DATA_FILE = "carData.txt";
    static final String NO_CAR_EXIST = "There is no car!" ;

    public static void main(String[] args) {
        try {


            readDataFromTheFile();
            while (true) {

                System.out.println("\nEnter the options: \n0.Exit \n1.Add a car \n2.List all the cars \n3.Modify the car \n4.Delete a car \n5.Statics Car");

                int userInput = input.nextInt();
                input.nextLine();

                switch (userInput) {
                    case 0:
                        saveToFile();
                        return;
                    case 1:
                        addCar();
                        break;
                    case 2:
                        listCars();
                        break;
                    case 3:
                        modifyCars();
                        break;
                    case 4:
                        deleteCar();
                        break;
                    case 5:
                        statisticsCar();
                        break;
                }
            }
        }
        catch (Exception exc){
            System.out.println("An exception happened "+exc.getMessage());
        }

    }

    private static void statisticsCar() {
        if(!cars.isEmpty()){
            ArrayList<Integer> indexOfOldestCars = new ArrayList<>();
            int oldestCar = cars.get(0).getYear();
            ArrayList<Integer> indexOfBiggestEngineSize = new ArrayList<>();
            double biggestEngineSize = cars.get(0).getEngineSize();
            int sumYearCars = 0;

            for (int i = 0; i < cars.size(); i++) {
                int currentCarYear = cars.get(i).getYear();
                double currentEngineSize = cars.get(i).getEngineSize();
                if(oldestCar > currentCarYear){
                    oldestCar = currentCarYear;
                    indexOfOldestCars.clear();
                    indexOfOldestCars.add(i);
                } else if (oldestCar == currentCarYear) {
                    indexOfOldestCars.add(i);
                }
                if(biggestEngineSize < currentEngineSize){
                    biggestEngineSize = currentEngineSize;
                    indexOfBiggestEngineSize.clear();
                    indexOfBiggestEngineSize.add(i);

                }else if(biggestEngineSize == currentEngineSize){
                    indexOfBiggestEngineSize.add(i);
                }
                sumYearCars += cars.get(i).getYear();
            }


            //print the oldest car
            System.out.println("\nThe oldest car(S) :");
            for (Integer indexOfOldestCar : indexOfOldestCars) {
                System.out.printf("#%d,%s %n",(indexOfOldestCar + 1),cars.get(indexOfOldestCar).toDataString());
            }

            //print the biggest engine size
            System.out.println("\nThe biggest Engine Size car(S) :");
            for (Integer indexOfBiggest : indexOfBiggestEngineSize) {
                System.out.printf("#%d,%s %n",(indexOfBiggest + 1),cars.get(indexOfBiggest).toDataString());
            }

            //avg of the production years
            System.out.printf("%nThe average year of the cars is : %.2f%n", (sumYearCars * 1.00/cars.size()));



        }else {
            System.out.println(NO_CAR_EXIST);
        }



    }

    private static void deleteCar() {
        int carNum = input.nextInt() - 1;
        input.nextLine();

        if (!cars.contains(cars.get(carNum))){
            System.out.println(NO_CAR_EXIST);
        }else{
            cars.remove(carNum);
        }


    }

    private static void readDataFromTheFile() {
        //try-with-resource
        try(Scanner reader = new Scanner(new File(DATA_FILE))) {
            while(reader.hasNextLine()){
                String[] dataLine = reader.nextLine().split(";");
                String make = dataLine[0];
                int productionYear = Integer.parseInt(dataLine[1]);
                double engineSize = Double.parseDouble(dataLine[2]);

                cars.add(new Car(make, productionYear,engineSize));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveToFile() {
        if(!cars.isEmpty()){
            try(PrintWriter writer = new PrintWriter(DATA_FILE)) {
                for (Car car: cars){
                    writer.println(car.toDataString());
                }

            }
            catch (Exception exc){

            }
        }
    }

    private static void modifyCars() {
        if (!cars.isEmpty()){
            int carNum = input.nextInt() - 1;
            input.nextLine();

            Car carToBeModified = cars.get(carNum);

            if(!cars.contains(cars.get(carNum))){
                System.out.println("The car does not exist!");
            }else {

                String[] carInformation = getCarInfo();

                carToBeModified.setMake(carInformation[0]);
                carToBeModified.setYear(Integer.parseInt(carInformation[1]));
                carToBeModified.setEngineSize(Double.parseDouble(carInformation[2]));



            }
        }else{
            System.out.println(NO_CAR_EXIST);
        }

    }

    private static String[] getCarInfo() {
        String[] carInfo = new String[3];
        System.out.println("Enter the make");
        carInfo[0] = input.nextLine();

        System.out.println("Enter the production year");
        carInfo[1] = input.nextLine();

        System.out.println("Enter the engine size");
        carInfo[2] = input.nextLine();

        return carInfo;
    }

    private static void listCars() {
        if (!cars.isEmpty()) {
            for (int i = 0; i < cars.size(); i++) {
                System.out.printf("#%d %s %n", i + 1, cars.get(i));
            }
        }else {
            System.out.println(NO_CAR_EXIST);
        }
    }

    private static void addCar() {
        String[] carInformation = getCarInfo();

        Car car = new Car(carInformation[0], Integer.parseInt(carInformation[1]),Double.parseDouble(carInformation[2]));
        cars.add(car);

    }
}
