package com.example.foodorderapp.model;

public class Cart {
    private String id;

    private Food food;
    private int amount;

    public Cart(String id, Food food, int amount) {
        this.id = id;
        this.food = food;
        this.amount = amount;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



    public Cart(Food food, int amount) {
        this.food = food;
        this.amount = amount;
    }

    public Cart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
