/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.DTO.Request.FechasRequestDTO;
import com.tribboAdventure.demo.DTO.Request.DestinoRequestDTO;
import com.tribboAdventure.demo.DTO.Request.ViajeRequestDTO;
import com.tribboAdventure.demo.DTO.Response.ViajeResponseDTO;
import com.tribboAdventure.demo.Entity.Viaje;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Repository.ViajeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository viajeRepository;
    
    public ViajeResponseDTO crearViaje(ViajeRequestDTO viajeDTO) throws MiException {
        Viaje nuevoViaje = new Viaje(viajeDTO);;
        try {
            LocalDate fechaSalida = viajeDTO.validarFecha(viajeDTO.getSalida());
            LocalDate fechaLlegada = viajeDTO.validarFecha(viajeDTO.getLlegada());
            
            if (fechaSalida.isBefore(LocalDate.now())) {
                // Lógica para manejar la fecha de inicio anterior a la fecha actual
                throw new RuntimeException("La fecha de salida debe ser igual o posterior a la fecha actual");
            }
            if (fechaLlegada.isBefore(fechaSalida)) {
                // Lógica para manejar la fecha de finalización anterior a la fecha de inicio
                throw new RuntimeException("La fecha de llegada debe ser posterior a la fecha de salida");
            }
            
            nuevoViaje.setSalida(fechaSalida);
            nuevoViaje.setLlegada(fechaLlegada);
            
            viajeRepository.save(nuevoViaje);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
        ViajeResponseDTO respuestaViaje = new ViajeResponseDTO(nuevoViaje);
        return respuestaViaje;
    }

    public List<ViajeResponseDTO> listar() throws MiException {
        try {
            List<Viaje> viajes = viajeRepository.findAll();
            
            // Mapear la lista de Viaje a una lista de DatosRespuestaViaje
            List<ViajeResponseDTO> datosRespuestaList = viajes.stream()
                    .map(ViajeResponseDTO::new)
                    .collect(Collectors.toList());
            return datosRespuestaList;

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<ViajeResponseDTO> buscarPorDestino(DestinoRequestDTO destinoDTO) throws MiException {
        try {
            List<Viaje> viajes = viajeRepository.findByDestino(destinoDTO.getDestino());
            List<ViajeResponseDTO> datosRespuestaList = viajes.stream()
                    .map(ViajeResponseDTO::new)
                    .collect(Collectors.toList());
            return datosRespuestaList;
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public List<ViajeResponseDTO> buscarPorFechas(FechasRequestDTO fechasRequestDTO) throws MiException {
        // Validaciones de fechas (puedes agregar más validaciones según sea necesario)
        try {
            LocalDate fechaSalida = fechasRequestDTO.validarFecha(fechasRequestDTO.getFechaSalida());
            LocalDate fechaLlegada = fechasRequestDTO.validarFecha(fechasRequestDTO.getFechaLlegada());

            if (fechaSalida.isBefore(LocalDate.now())) {
                // Lógica para manejar la fecha de inicio anterior a la fecha actual
                throw new RuntimeException("La fecha de salida debe ser igual o posterior a la fecha actual");
            }
            if (fechaLlegada.isBefore(fechaSalida)) {
                // Lógica para manejar la fecha de finalización anterior a la fecha de inicio
                throw new RuntimeException("La fecha de llegada debe ser posterior a la fecha de salida");
            }

            List<Viaje> viajes = viajeRepository.findBySalidaBetween(fechaSalida, fechaLlegada);
            List<ViajeResponseDTO> datosRespuestaList = viajes.stream()
                    .map(ViajeResponseDTO::new)
                    .collect(Collectors.toList());
            return datosRespuestaList;
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

}
