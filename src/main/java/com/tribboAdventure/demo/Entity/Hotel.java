/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
@Table(name="hoteles")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String localidad;
    private String direccion;
    private String CBU;
    private Integer precioHotel;
    private Boolean restaurante;
    private Boolean piscina;
    private Boolean gimnasio;
    private boolean alta;
    
    @OneToMany(mappedBy = "hotel")
    private List<Tiket> tikets;
    
    @OneToMany(mappedBy = "hotel")
    private List<Piso> pisos;
    
    
    public Integer calcularPrecio(){
        
        if (this.restaurante) {
            this.precioHotel += 50; 
        } else if(this.gimnasio) {
            this.precioHotel += 100;
        } else if(this.piscina){
            this.precioHotel += 150;
        }
        
        return precioHotel ;
    }
    
}

