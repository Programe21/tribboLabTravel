/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tribboAdventure.demo.Repository;

import com.tribboAdventure.demo.Entity.Tiket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface TiketRepository extends JpaRepository<Tiket, Long>{
    @Query("SELECT t FROM Tiket t WHERE t.id = :id")
    Optional<Tiket> findByID(@Param("id")Long id); 
}
