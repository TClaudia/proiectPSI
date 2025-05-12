package ro.uaic.feaa.psi.sgsm.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import ro.uaic.feaa.psi.metamodel.AbstractRepository;
import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.entities.BunMaterial;
import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;
import ro.uaic.feaa.psi.sgsm.model.entities.Gestiune;
import ro.uaic.feaa.psi.sgsm.model.entities.Localitate;

public class MasterRepository extends AbstractRepository {

    public BunMaterial addBunMaterial(BunMaterial bunMaterial) {
        return (BunMaterial) this.create(bunMaterial);
    }

    public Furnizor addFurnizor(Furnizor furnizor) {
        return (Furnizor) this.create(furnizor);
    }

    public Angajat addAngajat(Angajat angajat) {
        return (Angajat) this.create(angajat);
    }

    public Gestiune addGestiune(Gestiune gestiune) {
        return (Gestiune) this.create(gestiune);
    }

    public Localitate addLocalitate(Localitate localitate) {
        return (Localitate) this.create(localitate);
    }

    public void deleteBunMaterial(BunMaterial bunMaterial) {
        this.delete(bunMaterial);
    }

    public void deleteFurnizor(Furnizor furnizor) {
        this.delete(furnizor);
    }

    public void deleteAngajat(Angajat angajat) {
        this.delete(angajat);
    }

    public void deleteGestiune(Gestiune gestiune) {
        this.delete(gestiune);
    }

    public void deleteLocalitate(Localitate localitate) {
        this.delete(localitate);
    }

    public BunMaterial findBunuriMaterialById(Long id) {
        return (BunMaterial) this.getEm().createQuery(
                        "Select b from BunMaterial b where id=:id").setParameter("id", id)
                .getSingleResult();
    }

    public List<BunMaterial> findBunuriMaterialeAll() {
        return this.getEm().createQuery("Select b from BunMaterial b")
                .getResultList();
    }

    public Furnizor findFurnizorById(Long id) {
        return (Furnizor) this.getEm().createQuery(
                        "Select f from Furnizor f where id=:id").setParameter("id", id)
                .getSingleResult();
    }

    public List<Furnizor> findFurnizoriAll() {
        return this.getEm().createQuery("Select f from Furnizor f")
                .getResultList();
    }

    public List<Furnizor> findFurnizoriAllLight() {
        // maxima atenție la ordinea parametrilor, trebuie să corespundă constructorului
        return this
                .getEm()
                .createQuery(
                        "Select new Furnizor(f.id, f.idFurnizor, f.numeFurnizor, f.numarTelefon, f.email, f.tipFurnizor, f.garantie) from Furnizor f")
                .getResultList();
    }

    public Angajat findAngajatById(Long id) {
        return (Angajat) this.getEm().createQuery(
                        "Select a from Angajat a where id=:id").setParameter("id", id)
                .getSingleResult();
    }

    public List<Angajat> findAngajatiAll() {
        return this.getEm().createQuery("Select a from Angajat a")
                .getResultList();
    }

    public Gestiune findGestiuneById(Long id) {
        return (Gestiune) this.getEm().createQuery(
                        "Select g from Gestiune g where id=:id").setParameter("id", id)
                .getSingleResult();
    }

    public List<Gestiune> findGestiuniAll() {
        return this.getEm().createQuery("Select g from Gestiune g")
                .getResultList();
    }

    public Localitate findLocalitateById(Long id) {
        return (Localitate) this.getEm().createQuery(
                        "Select l from Localitate l where id=:id").setParameter("id", id)
                .getSingleResult();
    }

    public List<Localitate> findLocalitatiAll() {
        return this.getEm().createQuery("Select l from Localitate l")
                .getResultList();
    }

    public BunMaterial updateBunMaterial(BunMaterial bunMaterial) {
        return (BunMaterial) this.update(bunMaterial);
    }

    public Furnizor updateFurnizor(Furnizor furnizor) {
        return (Furnizor) this.update(furnizor);
    }

    public Angajat updateAngajat(Angajat angajat) {
        return (Angajat) this.update(angajat);
    }

    public Gestiune updateGestiune(Gestiune gestiune) {
        return (Gestiune) this.update(gestiune);
    }

    public Localitate updateLocalitate(Localitate localitate) {
        return (Localitate) this.update(localitate);
    }
}