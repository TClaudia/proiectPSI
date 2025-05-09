package ro.uaic.feaa.psi.sgsm.model.entities;

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

/**
 * Entitate pentru gestionarea angajaților.
 * Conform diagramei din imaginile furnizate.
 */
@Entity
public class Angajat {

    @Id
    @Column(name = "IdAngajat", length = 10)
    private String idAngajat;

    @Column(name = "Responsabilitate", length = 255)
    private String responsabilitate;

    @ManyToOne
    @JoinColumn(name = "Receptie_IdDocument")
    private Receptie receptie;

    @OneToMany(mappedBy = "angajat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comanda> comenzi = new HashSet<>();

    @OneToMany(mappedBy = "angajat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RaportDeNeconformitate> rapoarteNeconformitate = new HashSet<>();

    /**
     * Constructor implicit necesar pentru JPA.
     */
    public Angajat() {
    }

    // Getteri și setteri
    public String getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(String idAngajat) {
        this.idAngajat = idAngajat;
    }

    public String getResponsabilitate() {
        return responsabilitate;
    }

    public void setResponsabilitate(String responsabilitate) {
        this.responsabilitate = responsabilitate;
    }

    public Receptie getReceptie() {
        return receptie;
    }

    public void setReceptie(Receptie receptie) {
        this.receptie = receptie;
    }

    public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public Set<RaportDeNeconformitate> getRapoarteNeconformitate() {
        return rapoarteNeconformitate;
    }

    public void addComanda(Comanda comanda) {
        this.comenzi.add(comanda);
        comanda.setAngajat(this);
    }

    public void removeComanda(Comanda comanda) {
        this.comenzi.remove(comanda);
        comanda.setAngajat(null);
    }

    public void addRaportNeconformitate(RaportDeNeconformitate raport) {
        this.rapoarteNeconformitate.add(raport);
        raport.setAngajat(this);
    }

    public void removeRaportNeconformitate(RaportDeNeconformitate raport) {
        this.rapoarteNeconformitate.remove(raport);
        raport.setAngajat(null);
    }
}