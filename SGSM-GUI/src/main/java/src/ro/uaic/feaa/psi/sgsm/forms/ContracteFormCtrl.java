package src.ro.uaic.feaa.psi.sgsm.forms;

import java.util.Date;

import ro.uaic.feaa.psi.sgsm.model.entities.*;

/**
 * Aceasta clasă are rol de Controller. Asta înseamnă că furnizează metodele
 * necesare pentru implementarea comportamentului formularului, comportament ce
 * se va manifesta ca răspuns la acțiunile utilizatorului (apăsarea butoanelor,
 * selecția unui element din liste etc). Pentru a accesa datele introduse de
 * utilizator pe forma grafică, clasa utilizează metoda compunerii prin
 * intermediul atributului formData.
 */
public class ContracteFormCtrl {




    private ContracteFormData formData = new ContracteFormData();

    public ContracteFormData getFormData() {
        return formData;
    }

    public void setFormData(ContracteFormData formData) {
        this.formData = formData;
    }

    /**
     * Implementează comportamentul pentru butonul Adăugare. Această
     * operație nu presupune salvarea obiectului în baza de date ci doar
     * pregătirea unui obiect nou de tip Contract care va prelua
     * ulterior datele introduse de utilizator în formular.
     */
    public void contractNou() {
        Contract contract = new Contract();
        this.formData.setContractCurent(contract);

        // se adaugă eventuale date predefinite
        contract.setIdDocument("C" + System.currentTimeMillis()); // ID unic bazat pe timestamp
        contract.setTipDocument("Contract");
        contract.setDataDocument(new java.util.Date()); // data curentă
        contract.setDataModificare(new java.util.Date()); // data curentă

        if (!this.formData.getListaFurnizorilor().isEmpty()) {
            Furnizor furnizorDefault = this.formData.getListaFurnizorilor().get(0);
            contract.setFurnizor(furnizorDefault);
            System.out.println("Furnizor default setat: " + furnizorDefault.getNumeFurnizor());
        }

        if (!this.formData.getListaAngajati().isEmpty()) {
            Angajat angajat = this.formData.getListaAngajati().get(0);
            contract.setResponsabil(angajat);
            System.out.println("Responsabil default setat: " + angajat.getNumeAngajat());
        }

        // setăm tipul de contract implicit
        if (!this.formData.getTipuriContract().isEmpty()) {
            contract.setTipContract(this.formData.getTipuriContract().get(0));
        }
        adaugaModificareInitiala();
    }

    /**
     * Adaugă o modificare inițială cu statusul "Activ" pentru contractul nou creat
     */
    private void adaugaModificareInitiala() {
        ModificareContract modificare = new ModificareContract();
        modificare.setIdDocument("MC" + System.currentTimeMillis()); // ID unic bazat pe timestamp
        modificare.setTipDocument("Modificare");
        modificare.setStatus(ContracteFormData.STATUS_ACTIV);
        modificare.setDataDocument(new Date());
        modificare.setModificariActe("Creare contract");
        this.formData.getContractCurent().addModificare(modificare);
    }

    /**
     * Implementează comportamentul pentru butonul Adăugare din secțiunea Documente Aferente.
     * Generează un nou document aferent pentru a fi prezentat în tabelul de documente.
     */
    public void adaugaDocumentAferent() {
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Selectați mai întâi un contract!");

        ActeAferenteContract act = new ActeAferenteContract();
        act.setIdDocument("DA" + System.currentTimeMillis()); // ID unic bazat pe timestamp
        act.setDataDocument(new java.util.Date()); // data curentă

        if (!this.formData.getTipuriDocumenteAferente().isEmpty()) {
            act.setTipDocument(this.formData.getTipuriDocumenteAferente().get(0));
        }

        act.setModificariActe("Document nou adăugat");

        this.formData.setDocumentAferentSelectat(act);
        this.formData.getContractCurent().addActAferent(act);
    }

    /**
     * Implementează comportamentul pentru butonul Actualizare din secțiunea Modificări.
     * Generează un nou Act Adițional pentru a fi asociat contractului.
     */
    public void adaugaActAditional() {
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Selectați mai întâi un contract!");

        ActAditional actAditional = new ActAditional();
        actAditional.setIdDocument("AA" + System.currentTimeMillis()); // ID unic bazat pe timestamp
        actAditional.setDataDocument(new java.util.Date()); // data curentă
        actAditional.setTipDocument(ContracteFormData.TIP_DOCUMENT_ACT_ADITIONAL);
        actAditional.setModificariActe("Modificare contract prin act adițional");

        ModificareContract modificare = new ModificareContract();
        modificare.setIdDocument("MC" + System.currentTimeMillis()); // ID unic bazat pe timestamp
        modificare.setTipDocument("Modificare");
        modificare.setStatus(ContracteFormData.STATUS_MODIFICAT);
        modificare.setDataDocument(new java.util.Date());
        modificare.setModificariActe("Actualizare contract prin act adițional");
        modificare.setActAditional(actAditional);

        actAditional.setModificareContract(modificare);

        this.formData.getContractCurent().addActAferent(actAditional);
        this.formData.getContractCurent().addModificare(modificare);

        this.formData.setDocumentAferentSelectat(actAditional);
        this.formData.setModificareContractSelectata(modificare);
    }

    /**
     * Implementează comportamentul pentru butonul Reziliere din secțiunea Modificări.
     * Generează un nou Formular de Reziliere pentru a fi asociat contractului.
     */
    public void adaugaFormularReziliere() {
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Selectați mai întâi un contract!");

        FormularReziliere formularReziliere = new FormularReziliere();
        formularReziliere.setIdDocument("FR" + System.currentTimeMillis());
        formularReziliere.setDataDocument(new java.util.Date());
        formularReziliere.setTipDocument(ContracteFormData.TIP_DOCUMENT_REZILIERE);
        formularReziliere.setModificariActe("Reziliere contract");

        ModificareContract modificare = new ModificareContract();
        modificare.setIdDocument("MCR" + System.currentTimeMillis());
        modificare.setTipDocument("Reziliere");
        modificare.setStatus(ContracteFormData.STATUS_REZILIAT);
        modificare.setDataDocument(new java.util.Date());
        modificare.setModificariActe("Detalii despre rezilierea contractului");
        modificare.setFormularReziliere(formularReziliere);

        formularReziliere.setModificareContract(modificare);

        this.formData.getContractCurent().addActAferent(formularReziliere);
        this.formData.getContractCurent().addModificare(modificare);

        this.formData.setDocumentAferentSelectat(formularReziliere);
        this.formData.setModificareContractSelectata(modificare);
    }

    /**
     * Implementează comportamentul pentru butonul Salvare din formular.
     * Salvează contractul curent și toate entitățile asociate în baza de date.
     */

    //DIAGRAMA
    public void salveazaContract() {
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Nu există un contract de salvat!");

        this.formData.getContractRepo().beginTransaction();

        try {
            Contract contractSalvat = this.formData.getContractRepo().saveContract(this.formData.getContractCurent());
            this.formData.setContractCurent(contractSalvat);
            this.formData.getContractRepo().commitTransaction();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la salvarea contractului: " + e.getMessage());
        }
    }

    /**
     * Implementează comportamentul pentru căutarea contractelor după criterii specifice.
     * Actualizează lista de contracte din formData.
     */
    public void cautaContracte(Long furnizorId, String status, Date dataInceput, Date dataSfarsit) {
        this.formData.setListaContracte(this.formData.getContractRepo().findContractAll());
        if (furnizorId != null) {
            this.formData.setListaContracte(this.formData.getContractRepo().findContractByFurnizorId(furnizorId));
        }

        if (status != null && !status.isEmpty()) {
            this.formData.setListaContracte(this.formData.getContractRepo().findContractByStatus(status));
        }

        if (dataInceput != null && dataSfarsit != null) {
            this.formData.setListaContracte(this.formData.getContractRepo().findContractByPeriod(dataInceput, dataSfarsit));
        }
    }

    /**
     * Încarcă un contract existent din baza de date după ID și îl setează ca contract curent.
     */
    public void incarcaContract(Long contractId) {
        Contract contract = this.formData.getContractRepo().findContractById(contractId);
        if (contract != null) {
            this.formData.setContractCurent(contract);
        } else {
            throw new RuntimeException("Contractul cu ID-ul " + contractId + " nu a fost găsit!");
        }
    }

    /**
     * Șterge contractul curent din baza de date.
     */
    public void stergeContract() {
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Nu există un contract de șters!");
        this.formData.getContractRepo().beginTransaction();

        try {
            this.formData.getContractRepo().deleteContract(this.formData.getContractCurent());
            this.formData.setContractCurent(null);
            this.formData.getContractRepo().commitTransaction();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la ștergerea contractului: " + e.getMessage());
        }
    }
}