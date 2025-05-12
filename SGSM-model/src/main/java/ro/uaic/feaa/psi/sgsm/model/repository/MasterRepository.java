package ro.uaic.feaa.psi.sgsm.model.repository;

import java.util.List;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import ro.uaic.feaa.psi.metamodel.AbstractRepository;
import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;

/**
 * Repository centralizat pentru entitățile de tip nomenclator specifice
 * sistemului de contracte.
 * Extinde AbstractRepository pentru a beneficia de metodele generice CRUD
 * și de gestionare a tranzacțiilor.
 */
public class MasterRepository extends AbstractRepository {

    //========== METODE PENTRU FURNIZORI ==========//

    /**
     * Adaugă un nou furnizor în baza de date.
     *
     * @param furnizor obiectul care urmează să fie adăugat
     * @return furnizorul adăugat, cu ID generat
     */
    public Furnizor addFurnizor(Furnizor furnizor) {
        return (Furnizor) this.create(furnizor);
    }

    /**
     * Șterge un furnizor din baza de date.
     *
     * @param furnizor obiectul care urmează să fie șters
     */
    public void deleteFurnizor(Furnizor furnizor) {
        this.delete(furnizor);
    }

    /**
     * Găsește un furnizor după ID.
     *
     * @param id ID-ul furnizorului căutat
     * @return furnizorul găsit sau null dacă nu există
     */
    public Furnizor findFurnizorById(Long id) {
        try {
            return (Furnizor) this.getEm().createQuery(
                            "SELECT f FROM Furnizor f WHERE f.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește un furnizor după codul unic.
     *
     * @param idFurnizor codul unic al furnizorului
     * @return furnizorul găsit sau null dacă nu există
     */
    public Furnizor findFurnizorByCode(String idFurnizor) {
        try {
            return (Furnizor) this.getEm().createQuery(
                            "SELECT f FROM Furnizor f WHERE f.idFurnizor = :code")
                    .setParameter("code", idFurnizor)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește toți furnizorii din baza de date, cu toate atributele.
     *
     * @return lista completă de furnizori
     */
    public List<Furnizor> findFurnizoriAll() {
        return this.getEm().createQuery("SELECT f FROM Furnizor f")
                .getResultList();
    }

    /**
     * Găsește toți furnizorii din baza de date, dar doar cu atributele de bază
     * (optimizat pentru listarea în interfața utilizator).
     *
     * @return lista de furnizori cu informații de bază
     */
    public List<Furnizor> findFurnizoriAllLight() {
        // Atenție la ordinea parametrilor, trebuie să corespundă constructorului
        return this.getEm()
                .createQuery(
                        "SELECT new Furnizor(f.id, f.idFurnizor, f.numeFurnizor, f.numarTelefon, f.email, f.tipFurnizor, f.garantie) FROM Furnizor f")
                .getResultList();
    }

    /**
     * Găsește furnizori după numele lor (căutare parțială).
     *
     * @param numeFurnizor partea din numele furnizorului
     * @return lista de furnizori care conțin șirul specificat în nume
     */
    public List<Furnizor> findFurnizoriByName(String numeFurnizor) {
        return this.getEm()
                .createQuery("SELECT f FROM Furnizor f WHERE LOWER(f.numeFurnizor) LIKE LOWER(:name)")
                .setParameter("name", "%" + numeFurnizor + "%")
                .getResultList();
    }

    /**
     * Găsește furnizori după tipul lor.
     *
     * @param tipFurnizor tipul furnizorului
     * @return lista de furnizori de tipul specificat
     */
    public List<Furnizor> findFurnizoriByType(String tipFurnizor) {
        return this.getEm()
                .createQuery("SELECT f FROM Furnizor f WHERE f.tipFurnizor = :type")
                .setParameter("type", tipFurnizor)
                .getResultList();
    }

    /**
     * Găsește furnizori care au termen de expirare în perioada specificată.
     *
     * @param startDate data de început a perioadei
     * @param endDate data de sfârșit a perioadei
     * @return lista de furnizori cu termen de expirare în perioada specificată
     */
    public List<Furnizor> findFurnizoriByExpirationPeriod(Date startDate, Date endDate) {
        return this.getEm()
                .createQuery("SELECT f FROM Furnizor f WHERE f.termenExpirare BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /**
     * Actualizează un furnizor în baza de date.
     *
     * @param furnizor obiectul care urmează să fie actualizat
     * @return furnizorul actualizat
     */
    public Furnizor updateFurnizor(Furnizor furnizor) {
        return (Furnizor) this.update(furnizor);
    }

    //========== METODE PENTRU ANGAJAȚI ==========//

    /**
     * Adaugă un nou angajat în baza de date.
     *
     * @param angajat obiectul care urmează să fie adăugat
     * @return angajatul adăugat, cu ID generat
     */
    public Angajat addAngajat(Angajat angajat) {
        return (Angajat) this.create(angajat);
    }

    /**
     * Șterge un angajat din baza de date.
     *
     * @param angajat obiectul care urmează să fie șters
     */
    public void deleteAngajat(Angajat angajat) {
        this.delete(angajat);
    }

    /**
     * Găsește un angajat după ID.
     *
     * @param id ID-ul angajatului căutat
     * @return angajatul găsit sau null dacă nu există
     */
    public Angajat findAngajatById(Long id) {
        try {
            return (Angajat) this.getEm().createQuery(
                            "SELECT a FROM Angajat a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește un angajat după codul său unic.
     *
     * @param idAngajat codul unic al angajatului
     * @return angajatul găsit sau null dacă nu există
     */
    public Angajat findAngajatByCode(String idAngajat) {
        try {
            return (Angajat) this.getEm().createQuery(
                            "SELECT a FROM Angajat a WHERE a.idAngajat = :code")
                    .setParameter("code", idAngajat)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește toți angajații din baza de date.
     *
     * @return lista de angajați
     */
    public List<Angajat> findAngajatiAll() {
        return this.getEm().createQuery("SELECT a FROM Angajat a")
                .getResultList();
    }

    /**
     * Găsește angajați după numele lor (căutare parțială).
     *
     * @param numeAngajat partea din numele angajatului
     * @return lista de angajați care conțin șirul specificat în nume
     */
    public List<Angajat> findAngajatiByName(String numeAngajat) {
        return this.getEm()
                .createQuery("SELECT a FROM Angajat a WHERE LOWER(a.numeAngajat) LIKE LOWER(:name)")
                .setParameter("name", "%" + numeAngajat + "%")
                .getResultList();
    }

    /**
     * Găsește angajați după departament.
     *
     * @param departament departamentul angajatului
     * @return lista de angajați din departamentul specificat
     */
    public List<Angajat> findAngajatiByDepartment(String departament) {
        return this.getEm()
                .createQuery("SELECT a FROM Angajat a WHERE a.departament = :dept")
                .setParameter("dept", departament)
                .getResultList();
    }

    /**
     * Găsește angajați după responsabilitate.
     *
     * @param responsabilitate responsabilitatea angajatului
     * @return lista de angajați cu responsabilitatea specificată
     */
    public List<Angajat> findAngajatiByResponsibility(String responsabilitate) {
        return this.getEm()
                .createQuery("SELECT a FROM Angajat a WHERE a.responsabilitate = :resp")
                .setParameter("resp", responsabilitate)
                .getResultList();
    }

    /**
     * Găsește angajați care sunt responsabili pentru un număr minim de contracte.
     *
     * @param minContracte numărul minim de contracte
     * @return lista de angajați care sunt responsabili pentru cel puțin minContracte
     */
    public List<Angajat> findAngajatiByContractCount(int minContracte) {
        return this.getEm()
                .createQuery("SELECT a FROM Angajat a WHERE SIZE(a.contracte) >= :minCount")
                .setParameter("minCount", minContracte)
                .getResultList();
    }

    /**
     * Găsește conturile contractelor de care este responsabil un angajat.
     *
     * @param angajatId ID-ul angajatului
     * @return lista de contracte pentru care angajatul este responsabil
     */
    public List<Contract> findContracteByAngajatId(Long angajatId) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.responsabil.id = :id")
                .setParameter("id", angajatId)
                .getResultList();
    }

    /**
     * Actualizează un angajat în baza de date.
     *
     * @param angajat obiectul care urmează să fie actualizat
     * @return angajatul actualizat
     */
    public Angajat updateAngajat(Angajat angajat) {
        return (Angajat) this.update(angajat);
    }

    //========== METODE STATISTICE ==========//

    /**
     * Obține numărul total de contracte pentru fiecare furnizor.
     *
     * @return lista de obiecte Object[] conținând [furnizor, număr de contracte]
     */
    public List<Object[]> getContractCountByFurnizor() {
        return this.getEm()
                .createQuery("SELECT f, COUNT(c) FROM Furnizor f LEFT JOIN Contract c ON c.furnizor = f GROUP BY f")
                .getResultList();
    }

    /**
     * Obține numărul total de contracte pentru fiecare angajat responsabil.
     *
     * @return lista de obiecte Object[] conținând [angajat, număr de contracte]
     */
    public List<Object[]> getContractCountByAngajat() {
        return this.getEm()
                .createQuery("SELECT a, COUNT(c) FROM Angajat a LEFT JOIN Contract c ON c.responsabil = a GROUP BY a")
                .getResultList();
    }

    /**
     * Obține numărul de contracte după status.
     *
     * @return lista de obiecte Object[] conținând [status, număr de contracte]
     */
    public List<Object[]> getContractCountByStatus() {
        return this.getEm()
                .createQuery("SELECT m.status, COUNT(DISTINCT c) FROM Contract c JOIN c.modificari m GROUP BY m.status")
                .getResultList();
    }

    /**
     * Obține numărul de contracte după tip.
     *
     * @return lista de obiecte Object[] conținând [tip contract, număr de contracte]
     */
    public List<Object[]> getContractCountByType() {
        return this.getEm()
                .createQuery("SELECT c.tipContract, COUNT(c) FROM Contract c GROUP BY c.tipContract")
                .getResultList();
    }
}