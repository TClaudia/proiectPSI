package ro.uaic.feaa.psi.sgsm.model.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

/**
 * Entitate pentru gestionarea contractelor cu furnizorii.
 */
@Entity
public class Contract extends AbstractEntity {

    @Column(name = "NumarContract", length = 50)
    private String numarContract;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataDocument")
    private Date dataDocument;

    @Column(name = "TipDocument", length = 30)
    private String tipDocument;

    @Column(name = "TipContract", length = 30)
    private String tipContract;

    @Column(name = "Observatii", length = 255)
    private String observatii;

    @Column(name = "DurataContract")
    private Integer durataContract;

    @Column(name = "TermenePlata", length = 30)
    private String termenePlata;

    @Column(name = "TermeneLivrare", length = 50)
    private String termeneLivrare;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataIncepere")
    private Date dataIncepere;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataOperare")
    private Date dataOperare;

    @ManyToOne
    @JoinColumn(name = "Furnizor_id")
    private Furnizor furnizor;

    @ManyToOne
    @JoinColumn(name = "Responsabil_id")
    private Angajat responsabil;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ModificareContract> modificari = new HashSet<>();

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ActeAferenteContract> acteAferente = new HashSet<>();

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public Contract() {
        this.tipDocument = "Contract";
        this.dataOperare = new Date();
    }

    /**
     * Adaugă o modificare la contract.
     */
    public void addModificare(ModificareContract modificare) {
        modificari.add(modificare);
        modificare.setContract(this);
    }

    /**
     * Elimină o modificare din contract.
     */
    public void removeModificare(ModificareContract modificare) {
        modificari.remove(modificare);
        modificare.setContract(null);
    }

    /**
     * Adaugă un act aferent la contract.
     */
    public void addActAferent(ActeAferenteContract act) {
        acteAferente.add(act);
        act.setContract(this);
    }

    /**
     * Elimină un act aferent din contract.
     */
    public void removeActAferent(ActeAferenteContract act) {
        acteAferente.remove(act);
        act.setContract(null);
    }

    /**
     * Returnează starea curentă a contractului bazată pe cea mai recentă modificare.
     */
    public String getStatusCurent() {
        if (modificari == null || modificari.isEmpty()) {
            return "Necunoscut";
        }

        ModificareContract ultimaModificare = null;
        Date ultimaData = null;

        for (ModificareContract mc : modificari) {
            if (ultimaData == null || mc.getDataModificare().after(ultimaData)) {
                ultimaData = mc.getDataModificare();
                ultimaModificare = mc;
            }
        }

        return ultimaModificare != null ? ultimaModificare.getStatus() : "Necunoscut";
    }

    // Getteri și setteri

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

    public String getTipContract() {
        return tipContract;
    }

    public void setTipContract(String tipContract) {
        this.tipContract = tipContract;
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

    public String getTermenePlata() {
        return termenePlata;
    }

    public void setTermenePlata(String termenePlata) {
        this.termenePlata = termenePlata;
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

    public Date getDataOperare() {
        return dataOperare;
    }

    public void setDataOperare(Date dataOperare) {
        this.dataOperare = dataOperare;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    public Angajat getResponsabil() {
        return responsabil;
    }

    public void setResponsabil(Angajat responsabil) {
        this.responsabil = responsabil;
    }

    public Set<ModificareContract> getModificari() {
        return modificari;
    }

    public Set<ActeAferenteContract> getActeAferente() {
        return acteAferente;
    }
}