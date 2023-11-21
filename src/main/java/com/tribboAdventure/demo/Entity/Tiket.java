package com.tribboAdventure.demo.Entity;

import com.tribboAdventure.demo.DTO.Request.TiketRequestDTO;
import com.tribboAdventure.demo.Exception.MiException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Admin
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tikets")
public class Tiket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadDias;
    private Integer montoTotal;
    private LocalDate fechaCreada;
    private Boolean pagado;
    private String tipoPago;
    private LocalDate salida;
    private LocalDate llegada;

    @ManyToOne
    @JoinColumn(name = "id_viaje")
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Tiket(TiketRequestDTO tiketDTO) throws MiException {
        this.pagado = false;
        this.tipoPago = tiketDTO.getTipoPago();
    }

    public Integer calcularMonto(String categoria, Integer numeroPiso, Integer numeroHabitacion) {
       
        Integer precioHotel = hotel.calcularPrecio();
        Integer precioHabitacion = hotel.getPisos().get(numeroPiso).getHabitaciones().get(numeroHabitacion).calcularPrecio();

        if (categoria.equalsIgnoreCase("ECONOMICA")) {
            montoTotal += precioHotel + precioHabitacion + 100;
        } else {
            montoTotal += precioHotel + precioHabitacion + 200;
        }
        return montoTotal * cantidadDias;
    }

    public LocalDate validarFecha(String fecha) throws MiException {
        try {
            //si la fecha que recibe es en formato corrrecto devuelve la fecha como localdate
            LocalDate fechaFormateada = LocalDate.parse(fecha);
/*
            if (salida.isAfter(llegada)) {
                throw new MiException("La fecha de salida debe ser antes que la de llegada", HttpStatus.BAD_REQUEST);
            }

            if (llegada.isBefore(salida)) {
                throw new MiException("La fecha de llegada debe ser despues que la de salida", HttpStatus.BAD_REQUEST);
            }

            LocalDateTime fechaActual = LocalDateTime.now();
            if (salida.isBefore(fechaActual) || llegada.isBefore(fechaActual)) {
                throw new MiException("La fecha de salida o llegada debe ser actual", HttpStatus.BAD_REQUEST);
            }*/
            return fechaFormateada;

        } catch (DateTimeParseException e) {
            
            throw new MiException("El formato de fecha debe ser yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
    }
}
