package be.vdab.kamerplanten.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "kamerplanten")
public class Kamerplant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String naam;
    @Positive
    private long tijdinterval;
    @NotBlank
    private String hoeveelheid;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate watergekregen;
    @Transient
    private LocalDate deadline;

    public Kamerplant(String naam, long tijdinterval, String hoeveelheid, LocalDate watergekregen) {
        this.naam = naam;
        this.tijdinterval = tijdinterval;
        this.hoeveelheid = hoeveelheid;
        this.watergekregen = watergekregen;
    }

    protected Kamerplant() {
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getTijdinterval() {
        return tijdinterval;
    }

    public String getHoeveelheid() {
        return hoeveelheid;
    }

    public LocalDate getWatergekregen() {
        return watergekregen;
    }

    public LocalDate getDeadline() {
        return watergekregen.plusDays(tijdinterval);
    }


}
