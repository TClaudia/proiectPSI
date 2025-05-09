package ro.uaic.feaa.psi.sgsm.model.repository;

import java.util.List;

import ro.uaic.feaa.psi.metamodel.AbstractRepository;

/**
 * Repository pentru operațiile specifice angajaților.
 * Oferă metode pentru persistența și interogarea datelor despre angajați.
 */
public class AngajatRepository extends AbstractRepository {

    /**
     * Adaugă un nou angajat în baza de date.
     *
     * @param angajat Angajatul de adăugat
     * @return Angajatul adăugat, cu ID generat
     */
    public Angajat addAngajat(Angajat angajat) {
        return (Angajat) this.create(angajat);
    }

    /**
     * Actualizează datele unui angajat existent.
     *
     * @param angajat Angajatul de actualizat
     * @return Angajatul actualizat
     */
    public Angajat updateAngajat(Angajat angajat) {
        return (Angajat) this.update(angajat);
    }

    /**
     * Șterge un angajat din baza de date.
     *
     * @param angajat Angajatul de șters
     */
    public void deleteAngajat(Angajat angajat) {
        this.delete(angajat);
    }

    /**
     * Găsește toți angajații.
     *
     * @return Lista tuturor angajaților
     */
    @SuppressWarnings("unchecked")
    public List<Angajat> findAllAngajati() {
        return this.getEm()
                .createQuery("SELECT a FROM Angajat a")
                .getResultList();
    }

    /**
     * Versiune optimizată care încarcă doar datele de bază pentru angajați.
     * Similar cu findFurnizoriAllLight() din MasterRepository.
     *
     * @return Lista angajaților cu date de bază (id, marcă, nume, funcție)
     */
    @SuppressWarnings("unchecked")
    public List<Angajat> findAllAngajatiLight() {
        return this.getEm()
                .createQuery("SELECT NEW Angajat(a.id, a.marcaAngajat, a.numeAngajat, a.functieAngajat) FROM Angajat a")
                .getResultList();
    }

    /**
     * Găsește un angajat după ID.
     *
     * @param id ID-ul angajatului
     * @return Angajatul găsit sau null dacă nu există
     */
    public Angajat findAngajatById(Long id) {
        try {
            return (Angajat) this.getEm()
                    .createQuery("SELECT a FROM Angajat a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Nu există angajat cu ID-ul specificat
        }
    }

    /**
     * Găsește un angajat după marca sa unică.
     *
     * @param marca Marca angajatului
     * @return Angajatul găsit sau null dacă nu există
     */
    public Angajat findAngajatByMarca(Integer marca) {
        try {
            return (Angajat) this.getEm()
                    .createQuery("SELECT a FROM Angajat a WHERE a.marcaAngajat = :marca")
                    .setParameter("marca", marca)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Nu există angajat cu marca specificată
        }
    }
}