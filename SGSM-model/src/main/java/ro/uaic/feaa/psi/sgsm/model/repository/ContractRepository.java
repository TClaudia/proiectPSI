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

    public Contract saveContract(Contract contract) {
        if (contract.getId() == null) // obiect nou
            contract = (Contract) this.create(contract);
        else // obiect existent in BD
            contract = (Contract) this.update(contract);

        return contract;
    }

    public ActeAferenteContract saveActAferent(ActeAferenteContract act) {
        if (act.getId() == null) // obiect nou
            act = (ActeAferenteContract) this.create(act);
        else // obiect existent in BD
            act = (ActeAferenteContract) this.update(act);

        return act;
    }

    public ModificareContract saveModificareContract(ModificareContract modificare) {
        if (modificare.getId() == null) // obiect nou
            modificare = (ModificareContract) this.create(modificare);
        else // obiect existent in BD
            modificare = (ModificareContract) this.update(modificare);

        return modificare;
    }

    public List<Contract> findContractAll() {
        return this.getEm().createQuery("SELECT c FROM Contract c").getResultList();
    }

    public List<Contract> findContractByFurnizorId(Long id) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.furnizor.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Contract> findContractByStatus(String status) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c JOIN c.modificari m WHERE m.status = :status")
                .setParameter("status", status)
                .getResultList();
    }

    public Contract findContractById(Long id) {
        return (Contract) this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Contract> findContractByPeriod(Date startDate, Date endDate) {
        return this.getEm()
                .createQuery("SELECT c FROM Contract c WHERE c.dataDocument BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<ActeAferenteContract> findActeAferenteByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT a FROM ActeAferenteContract a WHERE a.contract.id = :contractId")
                .setParameter("contractId", contractId)
                .getResultList();
    }

    public List<ModificareContract> findModificariByContractId(Long contractId) {
        return this.getEm()
                .createQuery("SELECT m FROM ModificareContract m WHERE m.contract.id = :contractId")
                .setParameter("contractId", contractId)
                .getResultList();
    }

    public void deleteContract(Contract contract) {
        this.delete(contract);
    }
}