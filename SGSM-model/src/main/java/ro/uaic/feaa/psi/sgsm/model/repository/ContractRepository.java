package ro.uaic.feaa.psi.sgsm.model.repository;

import java.util.Date;
import java.util.List;

import ro.uaic.feaa.psi.metamodel.AbstractRepository;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;

/**
 * Repository pentru operațiile specifice contractelor.
 * Oferă metode pentru persistența și interogarea contractelor și documentelor asociate.
 */
public class ContractRepository extends AbstractRepository {

    /**
     * Salvează un contract în baza de date.
     * Decide automat dacă e vorba de o creare sau actualizare.
     *
     * @param contract Contractul de salvat
     * @return Contractul salvat, cu ID actualizat pentru instanțele noi
     */
    public Contract saveContract(Contract contract) {
        if (contract.getId() == null) {
            // Obiect nou
            contract = (Contract) this.create(contract);
        } else {
            // Obiect existent în BD
            contract = (Contract) this.update(contract);
        }
        return contract;
    }

    /**
     * Salvează un act aferent contractului.
     *
     * @param act Actul aferent de salvat
     * @return Actul salvat
     */
    public ActeAferenteContract saveActAferent(ActeAferenteContract act) {
        if (act.getId() == null) {
            act = (ActeAferenteContract) this.create(act);
        } else {
            act = (ActeAferenteContract) this.update(act);
        }
        return act;
    }

    /**
     * Salvează o modificare de contract.
     *
     * @param modificare Modificarea de salvat
     * @return Modificarea salvată
     */
    public ModificareContract saveModificareContract(ModificareContract modificare) {
        if (modificare.getId() == null) {
            modificare = (ModificareContract) this.create(modificare);
        } else {
            modificare = (ModificareContract) this.update(modificare);
        }
        return modificare;
    }

    /**
     * Găsește toate contractele.
     *
     * @return Lista tuturor contractelor
     */
    @SuppressWarnings("unchecked")
    public List<Contract> findAllContracts() {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c")
                .getResultList();
    }

    /**
     * Găsește contractele filtrate după furnizor.
     *
     * @param furnizorId ID-ul furnizorului
     * @return Lista contractelor pentru furnizorul specificat
     */
    @SuppressWarnings("unchecked")
    public List<Contract> findContractsByFurnizorId(Long furnizorId) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.furnizor.id = :furnizorId")
                .setParameter("furnizorId", furnizorId)
                .getResultList();
    }

    /**
     * Găsește contractele filtrate după status.
     *
     * @param status Statusul contractului (Activ, Modificat, Reziliat)
     * @return Lista contractelor cu statusul specificat
     */
    @SuppressWarnings("unchecked")
    public List<Contract> findContractsByStatus(String status) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c JOIN c.modificari m WHERE m.status = :status " +
                        "GROUP BY c HAVING MAX(m.dataModificare) = " +
                        "(SELECT MAX(m2.dataModificare) FROM ModificareContract m2 WHERE m2.contract.id = c.id)")
                .setParameter("status", status)
                .getResultList();
    }

    /**
     * Găsește contractele din perioada specificată.
     *
     * @param startDate Data de început
     * @param endDate Data de sfârșit
     * @return Lista contractelor din perioada specificată
     */
    @SuppressWarnings("unchecked")
    public List<Contract> findContractsByPeriod(Date startDate, Date endDate) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.dataDocument BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /**
     * Găsește un contract după număr.
     *
     * @param numarContract Numărul contractului
     * @return Contractul găsit sau null dacă nu există
     */
    public Contract findContractByNumar(String numarContract) {
        try {
            return (Contract) this.getEm()
                    .createQuery("SELECT c FROM Contract c WHERE c.numarContract = :numarContract")
                    .setParameter("numarContract", numarContract)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Nu există contract cu numărul specificat
        }
    }

    /**
     * Găsește actele aferente unui contract.
     *
     * @param contractId ID-ul contractului
     * @return Lista actelor aferente contractului
     */
    @SuppressWarnings("unchecked")
    public List<ActeAferenteContract> findActeAferenteByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT a FROM ActeAferenteContract a WHERE a.contract.id = :contractId")
                .setParameter("contractId", contractId)
                .getResultList();
    }

    /**
     * Găsește istoria modificărilor unui contract.
     *
     * @param contractId ID-ul contractului
     * @return Lista modificărilor contractului, în ordine cronologică
     */
    @SuppressWarnings("unchecked")
    public List<ModificareContract> findModificariByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT m FROM ModificareContract m WHERE m.contract.id = :contractId ORDER BY m.dataModificare")
                .setParameter("contractId", contractId)
                .getResultList();
    }
}