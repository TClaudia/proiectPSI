package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

@Entity
public class ActeAferenteContract extends AbstractEntity {

    private String idDocument;
    private String tipDocument;

    @Temporal(value = TemporalType.DATE)
    private Date dataDocument;

    private String numarActContract;
    private String modificariActe;

    @ManyToOne
    private Contract contract;

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

    public String getNumarActContract() {
        return numarActContract;
    }

    public void setNumarActContract(String numarActContract) {
        this.numarActContract = numarActContract;
    }

    public String getModificariActe() {
        return modificariActe;
    }

    public void setModificariActe(String modificariActe) {
        this.modificariActe = modificariActe;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}