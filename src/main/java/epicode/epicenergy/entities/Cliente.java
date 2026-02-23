package epicode.epicenergy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_cliente;
    @Column(name="ragione_sociale", nullable = false)
    private String ragioneSociale;
    @Column(name="parita_iva", nullable = false)
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

    public Cliente(){}

public Cliente(String ragioneSociale, String partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, Double fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, String logoAziendale) {
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
    this.logoAziendale = logoAziendale;
}

    public UUID getIdCliente(){return id_cliente;}

    public String getRagioneSociale(){return ragioneSociale;}

    public void setRagioneSociale(String ragioneSociale){this.ragioneSociale = ragioneSociale;}

    public String getPartitaIva(){return partitaIva;}

    public void setPartitaIva(String partitaIva){this.partitaIva = partitaIva;}

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public LocalDate getDataInserimento(){return dataInserimento;}

    public void setDataInserimento(LocalDate dataInserimento){this.dataInserimento = dataInserimento;}

    public LocalDate getDataUltimoContatto(){return dataUltimoContatto;}

    public void setDataUltimoContatto(LocalDate dataUltimoContatto){this.dataUltimoContatto = dataUltimoContatto;}

    public Double getFatturatoAnnuale(){return fatturatoAnnuale;}

    public void setFatturatoAnnuale(Double fatturatoAnnuale){this.fatturatoAnnuale = fatturatoAnnuale;}

    public String getPec(){return pec;}

    public void setPec(String pec){this.pec = pec;}

    public String getTelefono(){return telefono;}

    public void setTelefono(String telefono){this.telefono = telefono;}

    public String getEmailContatto(){return emailContatto;}

    public void setEmailContatto(String emailContatto){this.emailContatto = emailContatto;}

    public String getNomeContatto(){return nomeContatto;}

    public void setNomeContatto(String nomeContatto){this.nomeContatto = nomeContatto;}

    public String getCognomeContatto(){return cognomeContatto;}

    public void setCognomeContatto(String cognomeContatto){this.cognomeContatto = cognomeContatto;}

    public String getTelefonoContatto(){return telefonoContatto;}

    public void setTelefonoContatto(String telefonoContatto){this.telefonoContatto = telefonoContatto;}

    public String getLogoAziendale(){return logoAziendale;}

        public void setLogoAziendale(String logoAziendale){this.logoAziendale = logoAziendale;}}