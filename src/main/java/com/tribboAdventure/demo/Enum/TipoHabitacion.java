/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.tribboAdventure.demo.Enum;

/**
 *
 * @author Admin
 */
public enum TipoHabitacion {
    SIMPLE,
    DOBLE,
    SUITE;

    public boolean equalsIgnoreCase(String name) {
        if (name.equalsIgnoreCase(SIMPLE.name()) || name.equalsIgnoreCase(DOBLE.name()) || name.equalsIgnoreCase(SUITE.name()) ) {
            return true;
        }
        return false;
    }
}
