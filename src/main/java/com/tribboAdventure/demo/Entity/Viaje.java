/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Entity;

import com.tribboAdventure.demo.DTO.Request.ViajeRequestDTO;
import com.tribboAdventure.demo.Enum.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name="viajes")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private LocalDate salida;
    private LocalDate llegada;
    private String aerolinea;
    private Double precio;
    private String horaLlegada;
    private String horaSalida;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @OneToMany(mappedBy = "viaje")
    private List<Tiket> tikets;
    
    @OneToMany(mappedBy = "viaje")
    private List<Esparcimiento> esparcimientos;
    
    public Viaje (ViajeRequestDTO viajeDTO){
        this.destino = viajeDTO.getDestino();
        this.categoria = viajeDTO.getCategoria();
        
    }

}
