/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tribboAdventure.demo.Repository;

import com.tribboAdventure.demo.Entity.Viaje;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("SELECT v FROM Viaje v WHERE LOWER(v.destino) LIKE LOWER(CONCAT('%', :destino, '%'))")
    List<Viaje> findByDestino(@Param("destino") String destino);

    @Query("SELECT v FROM Viaje v WHERE LOWER(v.destino) LIKE LOWER(CONCAT('%', :destino, '%'))")
    Viaje findByDest(@Param("destino") String destino);
    
    List<Viaje> findBySalidaBetween(LocalDate fechaSalida, LocalDate fechaLlegada);
}
