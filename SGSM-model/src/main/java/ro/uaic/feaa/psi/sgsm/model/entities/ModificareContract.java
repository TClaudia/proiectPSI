package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

@Entity
public class ModificareContract extends AbstractEntity {

    private String idDocument;
    private String tipDocument;

    @Temporal(value = TemporalType.DATE)
    private Date dataDocument;

    private String modificariActe;
    private String status; // "Activ", "Modificat", "Reziliat"

    @ManyToOne
    private Contract contract;

    @OneToOne
    private ActAditional actAditional;

    @OneToOne
    private FormularReziliere formularReziliere;

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

    public String getModificariActe() {
        return modificariActe;
    }

    public void setModificariActe(String modificariActe) {
        this.modificariActe = modificariActe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ActAditional getActAditional() {
        return actAditional;
    }

    public void setActAditional(ActAditional actAditional) {
        this.actAditional = actAditional;
    }

    public FormularReziliere getFormularReziliere() {
        return formularReziliere;
    }

    public void setFormularReziliere(FormularReziliere formularReziliere) {
        this.formularReziliere = formularReziliere;
    }

    public void setDataModificare(Date date) {
    }
}