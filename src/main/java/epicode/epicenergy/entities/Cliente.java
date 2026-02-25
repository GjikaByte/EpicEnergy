package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="clienti")
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_cliente;
    @Column(name = "ragione_sociale", nullable = false)
    private String ragioneSociale;
    @Column(name = "parita_iva", nullable = false)
    private String partitaIva;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate dataInserimento;
    @Column(nullable = false)
    private LocalDate dataUltimoContatto;
    @Column(nullable = false)
    private Double fatturatoAnnuale;
    @Column(nullable = false)
    private String pec;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String emailContatto;
    @Column(nullable = false)
    private String nomeContatto;
    @Column(nullable = false)
    private String cognomeContatto;
    @Column(nullable = false)
    private String telefonoContatto;
    @Column(nullable = false)
    private String logoAziendale;
    @Column (nullable = false)
    private TipoCliente tipoCliente;
    @Column(nullable = false)
    private StatoCliente statoCliente;

    @ManyToOne
    @JoinColumn(name = "id_indirizzo_sede_operativa", nullable = false)
    private IndirizzoSedeOperativa indirizzoSedeOperativa;

    @ManyToOne
    @JoinColumn(name = "id_indirizzo_sede_legale", nullable = false)
    private IndirizzoSedeLegale indirizzoSedeLegale;

    public Cliente(String ragioneSociale, String partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, Double fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, TipoCliente tipoCliente, IndirizzoSedeOperativa indirizzoSedeOperativa, IndirizzoSedeLegale indirizzoSedeLegale) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.tipoCliente = tipoCliente;
        this.statoCliente = StatoCliente.ATTIVO;
        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
    }
}