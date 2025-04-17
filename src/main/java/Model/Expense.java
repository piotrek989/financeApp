package Model;

public class Expense {
    public int id;
    public String date;
    public float price;
    public String type;
    public int userId;
    public Expense(){}

    public Expense(String date, float price, String type, int userid) {
        this.date = date;
        this.price = price;
        this.type = type;
        this.userId = userid;
    }
    public Expense(int id, String date, float price, String type, int userid) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.type = type;
        this.userId = userid;
    }

}