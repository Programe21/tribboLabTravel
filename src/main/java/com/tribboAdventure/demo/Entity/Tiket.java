
package com.tribboAdventure.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name="tikets")
public class Tiket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Tiket;
    private Integer cantidadDias;
    private Integer montoTotal;
    private LocalDate fechaTiket;
    private Boolean pagado;
    private String tipoPago;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    
    @ManyToOne
    @JoinColumn(name="id_viaje")
    private Viaje viaje;
    
  
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
}
