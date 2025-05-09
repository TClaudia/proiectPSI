package ro.uaic.feaa.psi.sgsm.forms;

import java.util.Date;

import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;

/**
 * Aceasta clasă are rol de Controller. Furnizează metodele
 * necesare pentru implementarea comportamentului formularului, comportament ce
 * se va manifesta ca răspuns la acțiunile utilizatorului (apăsarea butoanelor,
 * selecția unui element din liste etc). Pentru a accesa datele introduse de
 * utilizator pe forma grafică, clasa utilizează metoda compunerii prin
 * intermediul atributului formData.
 */
public class ContractFormController {
    // Câteva constante pentru tipuri de documente și acțiuni posibile
    public static final String CONTRACT_NOU = "Contract nou";
    public static final String MODIFICARE_CONTRACT = "Actualizare contract";
    public static final String REZILIERE_CONTRACT = "Reziliere contract";

    // Datele formularului sunt păstrate într-un obiect ContractFormData
    private ContractFormData formData = new ContractFormData();

    public ContractFormData getFormData() {
        return formData;
    }

    public void setFormData(ContractFormData formData) {
        this.formData = formData;
    }

    /**
     * Implementează comportamentul pentru butonul Adăugare.
     * Această operație nu presupune salvarea obiectului în baza de date ci doar
     * pregătirea unui obiect nou de tip Contract care va prelua
     * ulterior datele introduse de utilizator în formular.
     */
    public void contractNou() {
        // Se creează noul contract și se procedează la setarea lui ca contract
        // curent al formularului
        formData.creeazaContractNou();

        // Se adaugă prima modificare la contract (stare inițială - ACTIV)
        ModificareContract modificareInitiala = new ModificareContract();
        modificareInitiala.setStatus(ContractFormData.STATUS_ACTIV);
        modificareInitiala.setDataModificare(new Date());
        modificareInitiala.setDescriere("Creare contract");
        formData.setModificareCurenta(modificareInitiala);
    }

    /**
     * Implementează comportamentul pentru butonul "Adaugă document aferent".
     * Generează un nou document aferent pentru contractul curent.
     */
    public void adaugaDocumentAferent(String tipDocument) {
        // Verificăm existența unui contract curent și generăm o eroare dacă nu există
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Selectați mai întâi un contract!");

        // Creăm documentul aferent în funcție de tipul ales
        formData.creeazaDocumentAferentNou(tipDocument);
    }

    /**
     * Implementează comportamentul pentru modificarea contractului
     * (actualizare sau reziliere).
     */
    public void modificaContract(String tipModificare) {
        // Verificăm existența unui contract curent
        if (this.formData.getContractCurent() == null)
            throw new RuntimeException("Selectați mai întâi un contract!");

        // Verificăm dacă contractul este deja reziliat
        if (ContractFormData.STATUS_REZILIAT.equals(this.formData.getContractCurent().getStatusCurent()) &&
                !ContractFormData.ACTIUNE_REZILIERE.equals(tipModificare))
            throw new RuntimeException("Contractul este deja reziliat!");

        // Setăm acțiunea selectată
        formData.setActiuneSelectata(tipModificare);

        // Creăm obiectul de modificare
        ModificareContract modificare = new ModificareContract();
        modificare.setDataModificare(new Date());

        // În funcție de tipul de modificare, setăm statusul și creăm documentul aferent
        if (ContractFormData.ACTIUNE_ACTUALIZARE.equals(tipModificare)) {
            modificare.setStatus(ContractFormData.STATUS_MODIFICAT);
            formData.creeazaDocumentAferentNou(ContractFormData.TIP_DOCUMENT_ACT_ADITIONAL);
        } else if (ContractFormData.ACTIUNE_REZILIERE.equals(tipModificare)) {
            modificare.setStatus(ContractFormData.STATUS_REZILIAT);
            formData.creeazaDocumentAferentNou(ContractFormData.TIP_DOCUMENT_REZILIERE);
        }

        formData.setModificareCurenta(modificare);
    }

    /**
     * Implementează validarea contractului înainte de salvare.
     */
    private String valideazaContract() {
        Contract contract = formData.getContractCurent();

        if (contract.getNumarContract() == null || contract.getNumarContract().trim().isEmpty()) {
            return "Numărul contractului este obligatoriu!";
        }

        if (contract.getDataDocument() == null) {
            return "Data contractului este obligatorie!";
        }

        if (contract.getFurnizor() == null) {
            return "Furnizorul este obligatoriu!";
        }

        if (contract.getResponsabil() == null) {
            return "Responsabilul de contract este obligatoriu!";
        }

        if (contract.getDurataContract() == null || contract.getDurataContract() <= 0) {
            return "Durata contractului trebuie să fie un număr pozitiv!";
        }

        // Verificare existență contract cu același număr (pentru contracte noi)
        if (contract.getId() == null) {
            Contract contractExistent = formData.getContractRepo().findContractByNumar(contract.getNumarContract());
            if (contractExistent != null) {
                return "Există deja un contract cu numărul " + contract.getNumarContract();
            }
        }

        return null; // Nicio eroare
    }

    /**
     * Implementează validarea documentului aferent înainte de salvare.
     */
    private String valideazaDocumentAferent() {
        ActeAferenteContract document = formData.getDocumentAferentCurent();

        if (document == null) {
            return null; // Niciun document de validat
        }

        if (document.getNrDocument() == null || document.getNrDocument().trim().isEmpty()) {
            return "Numărul documentului aferent este obligatoriu!";
        }

        if (document.getDataDocument() == null) {
            return "Data documentului aferent este obligatorie!";
        }

        // Validări specifice în funcție de tipul documentului
        if (ContractFormData.TIP_DOCUMENT_ACT_ADITIONAL.equals(document.getTipDocument()) &&
                (document.getClauze() == null || document.getClauze().trim().isEmpty())) {
            return "Clauzele sunt obligatorii pentru un act adițional!";
        }

        if (ContractFormData.TIP_DOCUMENT_REZILIERE.equals(document.getTipDocument()) &&
                (document.getMotivReziliere() == null || document.getMotivReziliere().trim().isEmpty())) {
            return "Motivul rezilierii este obligatoriu!";
        }

        return null; // Nicio eroare
    }

    /**
     * Comite modificările din formular în baza de date
     */
    public String salveazaModificariContract() {
        // Validăm datele
        String eroareContract = valideazaContract();
        if (eroareContract != null) {
            return eroareContract;
        }

        String eroareDocument = valideazaDocumentAferent();
        if (eroareDocument != null) {
            return eroareDocument;
        }

        try {
            // Finalizăm legăturile între obiecte
            formData.finalizeazaModificare();

            // Începem tranzacția
            this.getFormData().getContractRepo().beginTransaction();

            // Salvăm contractul
            Contract contractSalvat = this.getFormData().getContractRepo().saveContract(this.getFormData().getContractCurent());

            // Actualizăm referința către contractul salvat
            this.getFormData().setContractCurent(contractSalvat);

            // Comitem tranzacția
            this.getFormData().getContractRepo().commitTransaction();

            return "Contract salvat cu succes!";
        } catch (Exception e) {
            return "Eroare la salvarea contractului: " + e.getMessage();
        }
    }

    /**
     * Implementează comportamentul pentru căutarea contractelor.
     */
    public void cautaContracte() {
        formData.cautaContracte();
    }

    /**
     * Încarcă un contract existent pentru editare
     */
    public void incarcaContract(Contract contract) {
        if (contract == null) return;

        formData.setContractCurent(contract);
        formData.setDocumentAferentCurent(null);
        formData.setModificareCurenta(null);
    }
}