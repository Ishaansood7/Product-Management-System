import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// ABSTRACTION: Define common behavior for users
abstract class User {

    ProductDB product = new ProductDB();

    public abstract void manageProducts();
}

// INHERITANCE: Admin extends the abstract User class
class Admin extends User {

    Scanner sc = new Scanner(System.in);

    @Override
    public void manageProducts() {
        int choice = 1;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear newline

                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        product.viewProducts();
                        break;
                    case 3:
                        updateProduct();
                        break;
                    case 4:
                        deleteProduct();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear invalid input
            }

        } while (choice != 5);
    }

    private void addProduct() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Category: ");
            String cat = sc.nextLine();
            System.out.print("Enter Description: ");
            String des = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();

            Product p = new Product(id, name, cat, des, price, quantity);
            product.addProduct(p);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine(); // clear buffer
        }
    }

    private void updateProduct() {
        try {
            System.out.println("Enter ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Category: ");
            String cat = sc.nextLine();
            System.out.print("Enter New Description: ");
            String des = sc.nextLine();
            System.out.print("Enter New Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter New Quantity: ");
            int quantity = sc.nextInt();

            Product p = new Product(id, name, cat, des, price, quantity);
            product.updateProduct(p);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Update cancelled.");
            sc.nextLine(); // clear buffer
        }
    }

    private void deleteProduct() {
        try {
            System.out.print("Enter ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();
            boolean removed = product.deleteProduct(id);
            if (removed) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Deletion cancelled.");
            sc.nextLine();
        }
    }
}

// DATA MODEL: Product class
class Product {

    private int id;
    private String name, cat, des;
    private double unitPrice;
    private double price;
    private int quantity;

    public Product(int id, String name, String cat, String des, double unitPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.cat = cat;
        this.des = des;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        this.price = this.unitPrice * this.quantity;
    }

    // setters for access the value.
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String cat) {
        this.cat = cat;
    }

    public void setDescription(String des) {
        this.des = des;
    }

    public void setPrice(double unitPrice, int quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        calculateTotalPrice();
    }

    // Getters for initializing values
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return cat;
    }

    public String getDescription() {
        return des;
    }

    public double getPrice() {
        return price;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Category: %s | Description: %s | Unit Price: %.2f | Quantity: %d | Total Price: %.2f", id, name, cat, des, unitPrice, quantity, price);
    }
}

// DATA MANAGEMENT: Product database
class ProductDB {

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
        System.out.println("Product added.");
    }

    public void viewProducts() {
        if (products == null) {
            System.out.println("No products available.");
        } else {
            for (int i = 0; i < products.size(); i++) {
                System.out.println(products.get(i));
            }
        }
    }

    public void updateProduct(Product p) {
        for (int i = 0; i < products.size(); i++) {
            Product prod = products.get(i);
            if (prod.getId() == p.getId()) {
                prod.setName(p.getName());
                prod.setCategory(p.getCategory());
                prod.setDescription(p.getDescription());
                prod.setPrice(p.getUnitPrice(), p.getQuantity());
                System.out.println("Product updated:");
                System.out.println(prod); // display updated product
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public boolean deleteProduct(int pid) {
        for (int i = 0; i < products.size(); i++) {
            Product prod = products.get(i);
            if (prod.getId() == pid) {
                products.remove(prod);
                return true;
            }
        }
        return false;
    }
}

// MAIN CLASS
public class Project {

    public static void main(String[] args) {
        User adminUser = new Admin();
        adminUser.manageProducts();
    }
}