package epicode.epicenergy.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private Double fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    public Cliente(){}

    public Long getId(){return id;}
    public String getRagioneSociale(){return ragioneSociale;}
    public String getPartitaIva(){return partitaIva;}
    public String getEmail(){return email;}
    public LocalDate getDataInserimento(){return dataInserimento;}
    public LocalDate getDataUltimoContatto(){return dataUltimoContatto;}
    public Double getFatturatoAnnuale(){return fatturatoAnnuale;}
    public String getPec(){return pec;}
    public String getTelefono(){return telefono;}
    public String getEmailContatto(){return emailContatto;}
    public String getNomeContatto(){return nomeContatto;}
    public String getCognomeContatto(){return cognomeContatto;}
    public String getTelefonoContatto(){return telefonoContatto;}
    public String getLogoAziendale(){return logoAziendale;}

    public void setRagioneSociale(String ragioneSociale){this.ragioneSociale = ragioneSociale;}
    public void setPartitaIva(String partitaIva){this.partitaIva = partitaIva;}
    public void setEmail(String email){this.email = email;}
    public void setDataInserimento(LocalDate dataInserimento){this.dataInserimento = dataInserimento;}
    public void setDataUltimoContatto(LocalDate dataUltimoContatto){this.dataUltimoContatto = dataUltimoContatto;}
    public void setFatturatoAnnuale(Double fatturatoAnnuale){this.fatturatoAnnuale = fatturatoAnnuale;}
    public void setPec(String pec){this.pec = pec;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setEmailContatto(String emailContatto){this.emailContatto = emailContatto;}
    public void setNomeContatto(String nomeContatto){this.nomeContatto = nomeContatto;}
    public void setCognomeContatto(String cognomeContatto){this.cognomeContatto = cognomeContatto;}
    public void setTelefonoContatto(String telefonoContatto){this.telefonoContatto = telefonoContatto;}
    public void setLogoAziendale(String logoAziendale){this.logoAziendale = logoAziendale;}
}