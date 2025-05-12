package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

@Entity
public class Contract extends AbstractEntity {

    // proprietăți identice cu cele din baza de date
    private String idDocument;
    private String tipDocument;

    @Temporal(value = TemporalType.DATE)
    private Date dataDocument;

    @Temporal(value = TemporalType.DATE)
    private Date dataModificare;

    private String numarContract;
    private String durata;
    private String tipContract;

    @ManyToOne
    private Furnizor furnizor;

    @ManyToOne
    private Angajat responsabil;

    private String termenePlata;
    private String termeneLibrare;
    private String observatii;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ActeAferenteContract> acteAferente = new HashSet<ActeAferenteContract>();

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ModificareContract> modificari = new HashSet<ModificareContract>();

    // Metode pentru gestionarea colecției acteAferente
    public void addActAferent(ActeAferenteContract act) {
        this.acteAferente.add(act);
        act.setContract(this);
    }

    public void removeActAferent(ActeAferenteContract act) {
        this.acteAferente.remove(act);
        act.setContract(null);
    }

    public List<ActeAferenteContract> getActeAferente() {
        return Collections.unmodifiableList(new LinkedList<>(this.acteAferente));
    }

    // Metode pentru gestionarea colecției modificari
    public void addModificare(ModificareContract modificare) {
        this.modificari.add(modificare);
        modificare.setContract(this);
    }

    public void removeModificare(ModificareContract modificare) {
        this.modificari.remove(modificare);
        modificare.setContract(null);
    }

    public List<ModificareContract> getModificari() {
        return Collections.unmodifiableList(new LinkedList<>(this.modificari));
    }

    // Getters and Setters
    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getTipDocument() {
        return tipDocument;
    }

    public void setTipDocument(String tipDocument) {
        this.tipDocument = tipDocument;
    }

    public Date getDataDocument() {
        return dataDocument;
    }

    public void setDataDocument(Date dataDocument) {
        this.dataDocument = dataDocument;
    }

    public Date getDataModificare() {
        return dataModificare;
    }

    public void setDataModificare(Date dataModificare) {
        this.dataModificare = dataModificare;
    }

    public String getNumarContract() {
        return numarContract;
    }

    public void setNumarContract(String numarContract) {
        this.numarContract = numarContract;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getTipContract() {
        return tipContract;
    }

    public void setTipContract(String tipContract) {
        this.tipContract = tipContract;
    }

    public String getTermenePlata() {
        return termenePlata;
    }

    public void setTermenePlata(String termenePlata) {
        this.termenePlata = termenePlata;
    }

    public String getTermeneLibrare() {
        return termeneLibrare;
    }

    public void setTermeneLibrare(String termeneLibrare) {
        this.termeneLibrare = termeneLibrare;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
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
}