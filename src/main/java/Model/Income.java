package Model;

public class Income {
    public int id;
    public String date;
    public float price;
    public String type;
    public int userId;

    public Income(int id, String date, float price, String type, int userid) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.type = type;
        this.userId = userid;
    }
    public Income(){

    }
    public Income(String date, float price, String type, int userid) {
        this.date = date;
        this.price = price;
        this.type = type;
        this.userId = userid;
    }
}
