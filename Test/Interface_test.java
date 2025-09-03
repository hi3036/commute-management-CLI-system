import java.util.*;

interface Movable {
    void move();
}

interface Chargeable {
    void charge();
}

abstract class Vehicle implements Movable {
    private String type = null;
    private int maxSpeed = 0;
    Vehicle(String type, int maxSpeed) {
        this.type = type;
        this.maxSpeed = maxSpeed;
    }

    public String getType() { return type; }
    public int getMaxSpeed() { return maxSpeed; }

    abstract void drive();
}

class Car extends Vehicle {
    Car(String type, int maxSpeed) {
        super(type, maxSpeed);
    }
    @Override
    public void drive() {
        System.out.println("The Car is "+super.getType()+". Now, this maxspeed is "+super.getMaxSpeed()+".");
        System.out.println("Booooon~~!");
    }

    @Override
    public void move() {
        System.out.println(super.getType() + " is moving quickly on the road.");
    }
}

class Bike extends Vehicle {
    Bike(String type, int maxSpeed) {
        super(type, maxSpeed);
    }
    @Override
    public void drive() {
        System.out.println("The Bike is "+super.getType()+". Now, this maxspeed is "+super.getMaxSpeed()+".");
        System.out.println("Bulololo~~!");
    }

    @Override
    public void move() {
        System.out.println(super.getType() + " is moving easy on the mountain.");
    }
}

class ElectricCar extends Vehicle implements Chargeable{
    private int batteryCapacity = 0;

    ElectricCar(String type, int maxSpeed, int batteryCapacity) {
        super(type, maxSpeed);
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void drive() {
        System.out.println("The ElectricCar is " + getType() + ". Max speed: " + getMaxSpeed() + ".");
        System.out.println("Battery capacity: " + batteryCapacity + " kWh.");
        System.out.println("Zuuuuuuuuuuuuuuun~~!");
    }

    @Override
    public void move() {
        System.out.println(super.getType() + " is moving without noize on the road.");
    }

    @Override
    public void charge() {
        System.out.println(getType() + " is charging...");
    }
}

class Driver {
    private List<Vehicle> myVehicles = new ArrayList<>();
    
    public void AddVehicle(Vehicle vehicle) {
        myVehicles.add(vehicle);
    }
    
    public void testDriveAll() {
        for(Vehicle vehicle : myVehicles) {
            vehicle.drive();
            vehicle.move();
        }
    }

    public void testChargeAll() {
        for(Vehicle vehicle : myVehicles) {
            if(vehicle instanceof Chargeable) {
                ((Chargeable) vehicle).charge();
            }
        }
    }

}

public class Interface_test {
    public static void main(String[] args) {
        
        Driver machine = new Driver();

        machine.AddVehicle(new Car("Nissan", 150));
        machine.AddVehicle(new Bike("Estima", 80));
        machine.AddVehicle(new Car("Toyota", 170));
        machine.AddVehicle(new ElectricCar("Honada", 110, 100));
        machine.testDriveAll();
        machine.testChargeAll();
    }
}
