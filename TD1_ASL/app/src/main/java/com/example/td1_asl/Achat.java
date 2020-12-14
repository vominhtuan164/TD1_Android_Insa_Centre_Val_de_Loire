package com.example.td1_asl;


/**
 * ACHAT contient 2 informations:
 *
 * @name: nom d'acheteur
 * @item: item d'acheteur
 */

public class Achat {
    int _id;
    String _name;
    String item;

    public Achat() {
    }

    public Achat(int id, String name, String item) {
        this._id = id;
        this._name = name;
        this.item = item;
    }

    public Achat(String name, String item) {
        this._name = name;
        this.item = item;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}