package ro.uaic.feaa.psi.sgsm.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entitate pentru gestionarea modificărilor contractelor.
 * Conform diagramei din imaginile furnizate.
 */
@Entity
public class ModificariContract {

    @Id
    @Column(name = "IdDocument", length = 10)
    private String idDocument;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataModificare")
    private Date dataModificare;

    @ManyToOne
    @JoinColumn(name = "Contract_IdDocument", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "ActeAferenteContract_IdDocument")
    private ActeAferenteContract acteAferenteContract;

    @Column(name = "ModificareAprobata")
    private String modificareAprobata;

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public ModificareContract() {
    }

    // Getteri și setteri
    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public Date getDataModificare() {
        return dataModificare;
    }

    public void setDataModificare(Date dataModificare) {
        this.dataModificare = dataModificare;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ActeAferenteContract getActeAferenteContract() {
        return acteAferenteContract;
    }

    public void setActeAferenteContract(ActeAferenteContract acteAferenteContract) {
        this.acteAferenteContract = acteAferenteContract;
    }

    public String getModificareAprobata() {
        return modificareAprobata;
    }

    public void setModificareAprobata(String modificareAprobata) {
        this.modificareAprobata = modificareAprobata;
    }
}