package ro.uaic.feaa.psi.sgsm.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import ro.uaic.feaa.psi.metamodel.AbstractEntity;

@Entity
public class Angajat extends AbstractEntity {

    @Column(unique = true)
    private String idAngajat;
    private String numeAngajat;
    private String responsabilitate;
    private String departament;

    @OneToMany(mappedBy = "responsabil")
    private Set<Contract> contracte = new HashSet<>();

    // Getters and Setters
    public String getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(String idAngajat) {
        this.idAngajat = idAngajat;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getResponsabilitate() {
        return responsabilitate;
    }

    public void setResponsabilitate(String responsabilitate) {
        this.responsabilitate = responsabilitate;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public Set<Contract> getContracte() {
        return contracte;
    }

    public void setContracte(Set<Contract> contracte) {
        this.contracte = contracte;
    }
}