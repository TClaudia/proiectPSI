package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

@Entity
public class Furnizor extends AbstractEntity {

    @Column(unique = true)
    private String idFurnizor;

    private String numeFurnizor;
    private String numarTelefon;
    private String email;
    private String tipFurnizor;
    private String garantie;
    private String durataGarantie;
    private String conditiiMontaj;

    @Temporal(value = TemporalType.DATE)
    private Date termenExpirare;

    /**
     * Constructor necesar pentru JPA
     */
    public Furnizor() {
    }

    /**
     * Constructor necesar pentru a încărca doar anumite câmpuri din baza de
     * date. Scop: optimizare acces la date și timp de execuție interogări.
     *
     * @param id - id-ul trebuie OBLIGATORIU încărcat din BD
     * @param idFurnizor - codul furnizorului
     * @param numeFurnizor - numele furnizorului
     * @param numarTelefon - telefonul furnizorului
     * @param email - emailul furnizorului
     * @param tipFurnizor - tipul furnizorului
     * @param garantie - garanția oferită
     */
    public Furnizor(Long id, String idFurnizor, String numeFurnizor, String numarTelefon,
                    String email, String tipFurnizor, String garantie) {
        super();
        this.id = id; //din superclasă
        this.idFurnizor = idFurnizor;
        this.numeFurnizor = numeFurnizor;
        this.numarTelefon = numarTelefon;
        this.email = email;
        this.tipFurnizor = tipFurnizor;
        this.garantie = garantie;
    }

    // Getters și Setters
    public String getIdFurnizor() {
        return idFurnizor;
    }

    public void setIdFurnizor(String idFurnizor) {
        this.idFurnizor = idFurnizor;
    }

    public String getNumeFurnizor() {
        return numeFurnizor;
    }

    public void setNumeFurnizor(String numeFurnizor) {
        this.numeFurnizor = numeFurnizor;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipFurnizor() {
        return tipFurnizor;
    }

    public void setTipFurnizor(String tipFurnizor) {
        this.tipFurnizor = tipFurnizor;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }

    public String getDurataGarantie() {
        return durataGarantie;
    }

    public void setDurataGarantie(String durataGarantie) {
        this.durataGarantie = durataGarantie;
    }

    public String getConditiiMontaj() {
        return conditiiMontaj;
    }

    public void setConditiiMontaj(String conditiiMontaj) {
        this.conditiiMontaj = conditiiMontaj;
    }

    public Date getTermenExpirare() {
        return termenExpirare;
    }

    public void setTermenExpirare(Date termenExpirare) {
        this.termenExpirare = termenExpirare;
    }
}