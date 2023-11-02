/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Entity;

import com.tribboAdventure.demo.Enum.TipoHabitacion;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Habitacion;
    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipoHabitacion; 
    private String descripcion;
    private Integer numeroCamas;
    private Integer numeroBaños;
    private Integer precioHabitacion;
    private Boolean disponible;
    
    @ManyToOne
    @JoinColumn(name="id_piso")
    private Piso piso;
    
    /*
    private Integer calcularPrecio(String tipoHab){
        if (this.tipoHabitacion.equalsIgnoreCase(tipoHab)) {
            this.precioHabitacion += 100;
        } else {
            this.precioHabitacion += 50;
        }
        return precioHabitacion;
    }
    
    */
}
