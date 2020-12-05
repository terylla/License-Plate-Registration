import java.io.Serializable;
import java.text.DecimalFormat;

public class Car implements Serializable {

    private String model;
    private String color;
    private double mileage;
    private String plate;


    public Car(String model, String color, double mil){
        this.model = model;
        this.color = color;
        this.mileage = mil;
        this.plate = "Not Registered";
    }

    ////******** GETTERs & SETTERs *********////

    // Model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    // Mileage
    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    // Plate
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }


    ////***** OTHER METHODs *****/////

    // Method to register the plate if it's not registered
    public void getRegistered(String newPlate) {
        plate = newPlate;
    }

    //toString() to print
    public String toString() {
        DecimalFormat df = new DecimalFormat("#######.00");
        return "\tModel: " + getModel() +
                "\n\tColor: " + getColor() +
                "\n\tMileage: " +  df.format(getMileage()) +
                "\n\tPlate: " + getPlate();
    }

}
