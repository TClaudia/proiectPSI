package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ActAditional extends ActeAferenteContract {

    private String clauze;
    private String descriere;

    @OneToOne(mappedBy = "actAditional")
    private ModificareContract modificareContract;

    // Getters and Setters
    public String getClauze() {
        return clauze;
    }

    public void setClauze(String clauze) {
        this.clauze = clauze;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public ModificareContract getModificareContract() {
        return modificareContract;
    }

    public void setModificareContract(ModificareContract modificareContract) {
        this.modificareContract = modificareContract;
    }
}