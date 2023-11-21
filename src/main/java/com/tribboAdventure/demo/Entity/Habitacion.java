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
    private Long id;
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
    
    
    public Integer calcularPrecio(){
        if (this.tipoHabitacion.equalsIgnoreCase("SUITE")) {
            this.precioHabitacion += 500;
        } else if (this.tipoHabitacion.equalsIgnoreCase("DOBLE")){
            this.precioHabitacion += 300;
        } else if(this.tipoHabitacion.equalsIgnoreCase("SIMPLE")){
            this.precioHabitacion += 175;
        }
        return precioHabitacion;
    }
    
    
}
