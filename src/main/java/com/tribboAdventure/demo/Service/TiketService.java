/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tribboAdventure.demo.Service;

import com.tribboAdventure.demo.DTO.Request.TiketRequestDTO;
import com.tribboAdventure.demo.DTO.Response.TiketResponseDTO;
import com.tribboAdventure.demo.Entity.Habitacion;
import com.tribboAdventure.demo.Entity.Hotel;
import com.tribboAdventure.demo.Entity.Tiket;
import com.tribboAdventure.demo.Entity.Usuario;
import com.tribboAdventure.demo.Entity.Viaje;
import com.tribboAdventure.demo.Exception.MiException;
import com.tribboAdventure.demo.Repository.HabitacionRepository;
import com.tribboAdventure.demo.Repository.HotelRepository;
import com.tribboAdventure.demo.Repository.TiketRepository;
import com.tribboAdventure.demo.Repository.UsuarioRepository;
import com.tribboAdventure.demo.Repository.ViajeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class TiketService {

    private final TiketRepository tiketRepository;
    private final UsuarioRepository usuarioRepository;
    private final ViajeRepository viajeRepository;
    private final HotelRepository hotelRepository;
    private final HabitacionRepository habitacionRepository;

    public TiketResponseDTO crearTiket(TiketRequestDTO tiketDTO, String usernameUsuario, Long idViaje) throws MiException {
        Tiket nuevoTiket = new Tiket(tiketDTO);

        try {
            Usuario usuario = usuarioRepository.findByUsername(usernameUsuario).get();
            Viaje viaje = viajeRepository.findByDest(tiketDTO.getDestino());
            Hotel hotel = hotelRepository.findByNombre(tiketDTO.getNombreHotel());
            // Habitacion habitacion = habitacionRepository.findBytipoHabitacion(tiketDTO.getTipoHabitacion());

            //LocalDate fechaSalida = tiketDTO.validarFecha(tiketDTO.getSalida());
            //LocalDate fechaLlegada = tiketDTO.validarFecha(tiketDTO.getLlegada());
            
            LocalDate fecS = viaje.getSalida();
            LocalDate fecL = viaje.getLlegada();
            Integer diferenciaDias = calcularDiferenciaDias(fecS, fecL);
            
            Integer montoTotal = calcularMonto(tiketDTO);
            nuevoTiket.setFechaCreada(LocalDate.now());
            nuevoTiket.setUsuario(usuario);
            nuevoTiket.setMontoTotal(montoTotal);
            nuevoTiket.setViaje(viaje);
            nuevoTiket.setHotel(hotel);
            nuevoTiket.setCantidadDias(diferenciaDias);
            nuevoTiket.setSalida(fecS);
            nuevoTiket.setLlegada(fecL);
            
            tiketRepository.save(nuevoTiket);

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

        TiketResponseDTO responseTiket = new TiketResponseDTO(nuevoTiket);
        return responseTiket;
    }

    public Integer calcularMonto(TiketRequestDTO tiketDTO) {
        Integer montoTotal = 0;

        Hotel hotel = hotelRepository.findByNombre(tiketDTO.getNombreHotel());
        Habitacion habitacion = habitacionRepository.findById(tiketDTO.getNumeroHabitacion()).get();

        if (tiketDTO.getCategoria().equalsIgnoreCase("ECONOMICA")) {
            montoTotal += hotel.calcularPrecio() + habitacion.calcularPrecio() + 100;
        } else {
            montoTotal += hotel.calcularPrecio() + habitacion.calcularPrecio() + 200;
        }
        return montoTotal;
    }

    private static Integer calcularDiferenciaDias(LocalDate fechaSalida, LocalDate fechaLlegada) {
        return fechaSalida.until(fechaLlegada).getDays();
    }

    public List<TiketResponseDTO> listar() throws MiException {
        try {
            List<Tiket> tickets = tiketRepository.findAll();

            // Mapear la lista de Ticket a una lista de DatosRespuestaTicket
            List<TiketResponseDTO> datosRespuestaList = tickets.stream()
                    .map(TiketResponseDTO::new)
                    .collect(Collectors.toList());
            return datosRespuestaList;

        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }
    }

    public void pagar(Long id) {
        Tiket tiket = tiketRepository.findByID(id).get();
        tiket.setPagado(true);
        tiketRepository.save(tiket);
    }

}
