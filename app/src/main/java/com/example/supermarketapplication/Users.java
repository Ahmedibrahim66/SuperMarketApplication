package com.example.supermarketapplication;

import java.util.ArrayList;

public class Users {

    private String Email,Password,City,Gender,Phone,FirstName,LastName;
    private ArrayList<String> Cart,Favorite;
    private String IsAdmin;


    public Users(String email, String password, String city, String gender,
                 String phone, String firstName, String lastName,
                 ArrayList<String> cart, ArrayList<String> favorite,
                 String isAdmin) {

        Email = email;
        Password = password;
        City = city;
        Gender = gender;
        Phone = phone;
        FirstName = firstName;
        LastName = lastName;
        Cart = cart;
        Favorite = favorite;
        IsAdmin = isAdmin;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public ArrayList<String> getCart() {
        return Cart;
    }

    public void setCart(ArrayList<String> cart) {
        Cart = cart;
    }

    public ArrayList<String> getFavorite() {
        return Favorite;
    }

    public void setFavorite(ArrayList<String> favorite) {
        Favorite = favorite;
    }

    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }
}
