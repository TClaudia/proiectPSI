package ro.uaic.feaa.psi.sgsm.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entitate pentru gestionarea furnizorilor.
 * Conform diagramei din imaginile furnizate.
 */
@Entity
public class Furnizor {

    @Id
    @Column(name = "IdFurnizor", length = 10)
    private String idFurnizor;

    @Column(name = "NumeFurnizor", length = 255)
    private String numeFurnizor;

    @Column(name = "NumeTelefon", length = 10)
    private String numeTelefon;

    @Column(name = "Email", length = 255)
    private String email;

    @Column(name = "TipFurnizor", length = 30)
    private String tipFurnizor;

    @Column(name = "Garantie", length = 30)
    private String garantie;

    @Column(name = "CodFiscal", length = 30)
    private String codFiscal;

    @Column(name = "ConditiiMontaj", length = 255)
    private String conditiiMontaj;

    @Column(name = "termenExpirare", length = 30)
    private String termenExpirare;

    @OneToMany(mappedBy = "furnizor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contract> contracte = new HashSet<>();

    @OneToMany(mappedBy = "furnizor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comanda> comenzi = new HashSet<>();

    @OneToMany(mappedBy = "furnizor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DocumentInsotitor> documenteInsotitoare = new HashSet<>();

    @OneToMany(mappedBy = "furnizor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EvaluarePerformantaFurnizori> evaluari = new HashSet<>();

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public Furnizor() {
    }

    /**
     * Constructor pentru versiunea "light" a furnizorilor.
     */
    public Furnizor(String idFurnizor, String numeFurnizor, String numeTelefon,
                    String email, String tipFurnizor) {
        this.idFurnizor = idFurnizor;
        this.numeFurnizor = numeFurnizor;
        this.numeTelefon = numeTelefon;
        this.email = email;
        this.tipFurnizor = tipFurnizor;
    }

    // Getteri și setteri
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

    public String getNumeTelefon() {
        return numeTelefon;
    }

    public void setNumeTelefon(String numeTelefon) {
        this.numeTelefon = numeTelefon;
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

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public String getConditiiMontaj() {
        return conditiiMontaj;
    }

    public void setConditiiMontaj(String conditiiMontaj) {
        this.conditiiMontaj = conditiiMontaj;
    }

    public String getTermenExpirare() {
        return termenExpirare;
    }

    public void setTermenExpirare(String termenExpirare) {
        this.termenExpirare = termenExpirare;
    }

    public Set<Contract> getContracte() {
        return contracte;
    }

    public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public Set<DocumentInsotitor> getDocumenteInsotitoare() {
        return documenteInsotitoare;
    }

    public Set<EvaluarePerformantaFurnizori> getEvaluari() {
        return evaluari;
    }
}