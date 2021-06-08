package ru.mirea.populationcensus.Model;

public class City {
    public String name,sex,about;


    public City(){

    }

    public City(String name, String sex, String about) {
        this.name = name;
        this.sex = sex;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
