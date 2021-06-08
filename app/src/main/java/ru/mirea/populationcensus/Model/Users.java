package ru.mirea.populationcensus.Model;

public class Users {
    public static boolean isUserAuthorized;
    public static String login_name,root;
    public String login,pass;

    public Users(){

    }

    public Users(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
