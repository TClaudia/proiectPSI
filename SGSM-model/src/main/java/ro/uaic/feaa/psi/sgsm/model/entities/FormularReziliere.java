package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class FormularReziliere extends ActeAferenteContract {

    private String motivReziliere;

    @OneToOne(mappedBy = "formularReziliere")
    private ModificareContract modificareContract;

    // Getters and Setters
    public String getMotivReziliere() {
        return motivReziliere;
    }

    public void setMotivReziliere(String motivReziliere) {
        this.motivReziliere = motivReziliere;
    }

    public ModificareContract getModificareContract() {
        return modificareContract;
    }

    public void setModificareContract(ModificareContract modificareContract) {
        this.modificareContract = modificareContract;
    }
}