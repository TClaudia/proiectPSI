package ro.uaic.feaa.psi.sgsm.model.repository;

import java.util.Date;
import java.util.List;

import ro.uaic.feaa.psi.metamodel.AbstractRepository;
import ro.uaic.feaa.psi.sgsm.model.entities.ActAditional;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.FormularReziliere;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;

public class ContractRepository extends AbstractRepository {

    /**
     * Salvează un contract în baza de date.
     * Efectuează o verificare pentru a determina dacă este un obiect nou (INSERT)
     * sau un obiect existent (UPDATE).
     *
     * @param contract obiectul de tip Contract care trebuie salvat
     * @return contractul salvat
     */
    public Contract saveContract(Contract contract) {
        if (contract.getId() == null) // obiect nou
            contract = (Contract) this.create(contract);
        else // obiect existent in BD
            contract = (Contract) this.update(contract);

        return contract;
    }

    /**
     * Salvează un act aferent în baza de date.
     *
     * @param act obiectul de tip ActeAferenteContract care trebuie salvat
     * @return actul aferent salvat
     */
    public ActeAferenteContract saveActAferent(ActeAferenteContract act) {
        if (act.getId() == null) // obiect nou
            act = (ActeAferenteContract) this.create(act);
        else // obiect existent in BD
            act = (ActeAferenteContract) this.update(act);

        return act;
    }

    /**
     * Salvează o modificare de contract în baza de date.
     *
     * @param modificare obiectul de tip ModificareContract care trebuie salvat
     * @return modificarea salvată
     */
    public ModificareContract saveModificareContract(ModificareContract modificare) {
        if (modificare.getId() == null) // obiect nou
            modificare = (ModificareContract) this.create(modificare);
        else // obiect existent in BD
            modificare = (ModificareContract) this.update(modificare);

        return modificare;
    }

    /**
     * Găsește toate contractele din baza de date.
     *
     * @return lista de contracte
     */
    public List<Contract> findContractAll() {
        return this.getEm().createQuery("SELECT c FROM Contract c").getResultList();
    }

    /**
     * Găsește contractele asociate unui furnizor specific.
     *
     * @param id ID-ul furnizorului
     * @return lista de contracte pentru furnizorul specificat
     */
    public List<Contract> findContractByFurnizorId(Long id) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.furnizor.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Găsește contractele cu un anumit status.
     *
     * @param status statusul căutat
     * @return lista de contracte cu statusul specificat
     */
    public List<Contract> findContractByStatus(String status) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c JOIN c.modificari m WHERE m.status = :status")
                .setParameter("status", status)
                .getResultList();
    }

    /**
     * Găsește un contract după ID.
     *
     * @param id ID-ul contractului
     * @return contractul găsit sau null dacă nu există
     */
    public Contract findContractById(Long id) {
        try {
            return (Contract) this.getEm()
                    .createQuery("SELECT c FROM Contract c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește contractele într-o anumită perioadă.
     *
     * @param startDate data de început
     * @param endDate data de sfârșit
     * @return lista de contracte din perioada specificată
     */
    public List<Contract> findContractByPeriod(Date startDate, Date endDate) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.dataDocument BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /**
     * Găsește actele aferente unui contract.
     *
     * @param contractId ID-ul contractului
     * @return lista de acte aferente contractului
     */
    public List<ActeAferenteContract> findActeAferenteByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT a FROM ActeAferenteContract a WHERE a.contract.id = :contractId")
                .setParameter("contractId", contractId)
                .getResultList();
    }

    /**
     * Găsește modificările unui contract.
     *
     * @param contractId ID-ul contractului
     * @return lista de modificări ale contractului
     */
    public List<ModificareContract> findModificariByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT m FROM ModificareContract m WHERE m.contract.id = :contractId")
                .setParameter("contractId", contractId)
                .getResultList();
    }

    /**
     * Șterge un contract din baza de date.
     *
     * @param contract contractul de șters
     */
    public void deleteContract(Contract contract) {
        this.delete(contract);
    }

    /**
     * Găsește un act adițional după ID.
     *
     * @param id ID-ul actului adițional
     * @return actul adițional găsit sau null dacă nu există
     */
    public ActAditional findActAditionalById(Long id) {
        try {
            return (ActAditional) this.getEm()
                    .createQuery("SELECT a FROM ActAditional a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește un formular de reziliere după ID.
     *
     * @param id ID-ul formularului de reziliere
     * @return formularul de reziliere găsit sau null dacă nu există
     */
    public FormularReziliere findFormularReziliereById(Long id) {
        try {
            return (FormularReziliere) this.getEm()
                    .createQuery("SELECT f FROM FormularReziliere f WHERE f.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Găsește o modificare de contract după ID.
     *
     * @param id ID-ul modificării
     * @return modificarea găsită sau null dacă nu există
     */
    public ModificareContract findModificareContractById(Long id) {
        try {
            return (ModificareContract) this.getEm()
                    .createQuery("SELECT m FROM ModificareContract m WHERE m.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Șterge un act aferent contractului.
     *
     * @param act actul de șters
     */
    public void deleteActAferent(ActeAferenteContract act) {
        this.delete(act);
    }

    /**
     * Șterge o modificare de contract.
     *
     * @param modificare modificarea de șters
     */
    public void deleteModificareContract(ModificareContract modificare) {
        this.delete(modificare);
    }

    /**
     * Găsește contractele active pentru un furnizor.
     * Un contract activ are cel puțin o modificare cu status "Activ".
     *
     * @param furnizorId ID-ul furnizorului
     * @return lista de contracte active pentru furnizorul specificat
     */
    public List<Contract> findActiveContractsByFurnizorId(Long furnizorId) {
        return this.getEm()
                .createQuery("SELECT DISTINCT c FROM Contract c JOIN c.modificari m WHERE c.furnizor.id = :furnizorId AND m.status = 'Activ'")
                .setParameter("furnizorId", furnizorId)
                .getResultList();
    }

    /**
     * Găsește contractele care expiră într-o anumită perioadă.
     * Folosește durata contractului pentru a calcula data de expirare.
     *
     * @param startDate data de început
     * @param endDate data de sfârșit
     * @return lista de contracte care expiră în perioada specificată
     */
    public List<Contract> findContractsDueToExpire(Date startDate, Date endDate) {
        // Această metodă este mai complexă și ar necesita logică suplimentară
        // pentru a calcula data de expirare bazată pe durata contractului
        // Aceasta este o implementare simplificată
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.dataDocument BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}