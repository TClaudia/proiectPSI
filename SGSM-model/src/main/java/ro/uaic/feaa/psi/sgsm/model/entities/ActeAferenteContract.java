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
 * Entitate pentru gestionarea actelor aferente contractelor.
 * Conform diagramei din imaginile furnizate.
 */
@Entity
public class ActeAferenteContract {

    @Id
    @Column(name = "IdDocument", length = 10)
    private String idDocument;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataDocument")
    private Date dataDocument;

    @Column(name = "TipDocument", length = 30)
    private String tipDocument;

    @Column(name = "NrDocument", length = 10)
    private String nrDocument;

    @Column(name = "Clauze", length = 255)
    private String clauze;

    @Column(name = "MotivReziliere", length = 255)
    private String motivReziliere;

    @Column(name = "Descriere", length = 255)
    private String descriere;

    @Column(name = "TipDocumentAferentContract")
    private String tipDocumentAferentContract;

    @ManyToOne
    @JoinColumn(name = "Contract_IdDocument")
    private Contract contract;

    @OneToMany(mappedBy = "acteAferenteContract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ModificareContract> modificariContract = new HashSet<>();

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public ActeAferenteContract() {
    }

    // Getteri și setteri
    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
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

    public String getNrDocument() {
        return nrDocument;
    }

    public void setNrDocument(String nrDocument) {
        this.nrDocument = nrDocument;
    }

    public String getClauze() {
        return clauze;
    }

    public void setClauze(String clauze) {
        this.clauze = clauze;
    }

    public String getMotivReziliere() {
        return motivReziliere;
    }

    public void setMotivReziliere(String motivReziliere) {
        this.motivReziliere = motivReziliere;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getTipDocumentAferentContract() {
        return tipDocumentAferentContract;
    }

    public void setTipDocumentAferentContract(String tipDocumentAferentContract) {
        this.tipDocumentAferentContract = tipDocumentAferentContract;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Set<ModificareContract> getModificariContract() {
        return modificariContract;
    }
}