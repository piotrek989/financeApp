package Model;

public class User {
    public User(int id, String email, String password, String login) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.login = login;
    }
    public User(){

    }
    public int id;
    public String email;
    public String password;
    public String login;

//    public ArrayList<Income> incomes;
//    public ArrayList<Expense> expenses;

}
