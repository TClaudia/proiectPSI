package ro.uaic.feaa.psi.sgsm.model.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entitate pentru gestionarea contractelor cu furnizorii.
 * Conform diagramei din imaginile furnizate.
 */
@Entity
public class Contract {

    @Id
    @Column(name = "IdDocument", length = 10)
    private String idDocument;

    @Column(name = "NumarContract", length = 255)
    private String numarContract;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataDocument")
    private Date dataDocument;

    @Column(name = "TipDocument", length = 255)
    private String tipDocument;

    @Column(name = "Observatii", length = 255)
    private String observatii;

    @Column(name = "DurataContract", length = 10)
    private Integer durataContract;

    @ManyToOne
    @JoinColumn(name = "Furnizor_IdFurnizor")
    private Furnizor furnizor;

    @Column(name = "TermeneLivrare", length = 255)
    private String termeneLivrare;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataIncepere")
    private Date dataIncepere;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ModificareContract> modificariContract = new HashSet<>();

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActeAferenteContract> acteAferenteContract = new HashSet<>();

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public Contract() {
    }

    /**
     * Adaugă o modificare la contract.
     */
    public void addModificareContract(ModificareContract modificare) {
        modificariContract.add(modificare);
        modificare.setContract(this);
    }

    /**
     * Elimină o modificare din contract.
     */
    public void removeModificareContract(ModificareContract modificare) {
        modificariContract.remove(modificare);
        modificare.setContract(null);
    }

    /**
     * Adaugă un act aferent la contract.
     */
    public void addActAferentContract(ActeAferenteContract act) {
        acteAferenteContract.add(act);
        act.setContract(this);
    }

    /**
     * Elimină un act aferent din contract.
     */
    public void removeActAferentContract(ActeAferenteContract act) {
        acteAferenteContract.remove(act);
        act.setContract(null);
    }

    // Getteri și setteri
    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getNumarContract() {
        return numarContract;
    }

    public void setNumarContract(String numarContract) {
        this.numarContract = numarContract;
    }

    public Date getDataDocument() {
        return dataDocument;
    }

    public void setDataDocument(Date dataDocument) {
        this.dataDocument = dataDocument;
    }

    public String getTipDocument() {
        return tipDocument;
    }

    public void setTipDocument(String tipDocument) {
        this.tipDocument = tipDocument;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public Integer getDurataContract() {
        return durataContract;
    }

    public void setDurataContract(Integer durataContract) {
        this.durataContract = durataContract;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    public String getTermeneLivrare() {
        return termeneLivrare;
    }

    public void setTermeneLivrare(String termeneLivrare) {
        this.termeneLivrare = termeneLivrare;
    }

    public Date getDataIncepere() {
        return dataIncepere;
    }

    public void setDataIncepere(Date dataIncepere) {
        this.dataIncepere = dataIncepere;
    }

    public Set<ModificareContract> getModificariContract() {
        return modificariContract;
    }

    public Set<ActeAferenteContract> getActeAferenteContract() {
        return acteAferenteContract;
    }
}