package ro.uaic.feaa.psi.sgsm.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

/**
 * Entitate pentru gestionarea modificărilor contractelor.
 */
@Entity
public class ModificareContract extends AbstractEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "DataModificare", nullable = false)
    private Date dataModificare;

    @Column(name = "Status", length = 30, nullable = false)
    private String status;

    @Column(name = "Descriere", length = 255)
    private String descriere;

    @ManyToOne
    @JoinColumn(name = "Contract_id", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "DocumentModificare_id")
    private ActeAferenteContract documentModificare;

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public ModificareContract() {
        this.dataModificare = new Date();
    }

    // Getteri și setteri
    public Date getDataModificare() {
        return dataModificare;
    }

    public void setDataModificare(Date dataModificare) {
        this.dataModificare = dataModificare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ActeAferenteContract getDocumentModificare() {
        return documentModificare;
    }

    public void setDocumentModificare(ActeAferenteContract documentModificare) {
        this.documentModificare = documentModificare;
    }
}