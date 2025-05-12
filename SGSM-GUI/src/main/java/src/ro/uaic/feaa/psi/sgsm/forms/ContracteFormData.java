package src.ro.uaic.feaa.psi.sgsm.forms;

import java.util.LinkedList;
import java.util.List;

import ro.uaic.feaa.psi.sgsm.model.entities.ActAditional;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.FormularReziliere;
import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;
import ro.uaic.feaa.psi.sgsm.model.repository.ContractRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;

/**
 * Această clasă constituie modelul pentru formularul de contracte. Este un
 * adaptor al modelului SGSM: are rolul de a pregăti (filtra/adapta/transforma)
 * modelul SGSM în forma necesară formularului.
 */
public class ContracteFormData {

    // Constante pentru statusurile contractelor
    public static final String STATUS_ACTIV = "Activ";
    public static final String STATUS_MODIFICAT = "Modificat";
    public static final String STATUS_REZILIAT = "Reziliat";

    // Constante pentru tipurile de contracte
    public static final String TIP_CONTRACT_FURNIZARE = "Furnizare";
    public static final String TIP_CONTRACT_SERVICII = "Servicii";
    public static final String TIP_CONTRACT_COLABORARE = "Colaborare";

    // Constante pentru tipuri de acte aferente
    public static final String TIP_DOCUMENT_FACTURA = "Factura";
    public static final String TIP_DOCUMENT_ACT_ADITIONAL = "Act Aditional";
    public static final String TIP_DOCUMENT_REZILIERE = "Reziliere";

    // --------------ZONA 0 - COMUNA TUTUROR FORMULARELOR -------------------//

    // Obiectul-țintă (sursa de date) al formularului - cel aflat curent în editare
    private Contract contractCurent;

    // Colecția de obiecte-țintă obținute în urma unei operații de căutare
    private List<Contract> listaContracte;

    // Obiecte de tip Repository necesare pentru interogarea modelului
    private MasterRepository masterRepo = new MasterRepository();
    private ContractRepository contractRepo = new ContractRepository();

    // ---------------ZONA 1 - FURNIZORI --------------//

    private List<Furnizor> listaFurnizorilor;

    /**
     * Implementare standard pentru o colecție-sursă de date a unui obiect de
     * tip listă
     */
    public List<Furnizor> getListaFurnizorilor() {
        if (this.listaFurnizorilor == null) { // e primul apel (userul tocmai a deschis formul)
            this.listaFurnizorilor = this.masterRepo.findFurnizoriAllLight();
        }
        return listaFurnizorilor;
    }

    public void setListaFurnizorilor(List<Furnizor> listaFurnizorilor) {
        this.listaFurnizorilor = listaFurnizorilor;
    }

    // Getteri și Setteri pentru manipularea selecției furnizorului
    public Furnizor getFurnizorSelectat() {
        return this.contractCurent.getFurnizor();
    }

    /**
     * ATENȚIE - în majoritatea mediilor de dezvoltare, obiectele grafice de tip
     * listă returnează un id (o valoare atomică / un singur atribut) al
     * obiectului selectat din listă. Ca urmare, trebuie încărcat obiectul
     * corespunzător pentru relația ManyToOne.
     */
    public void setFurnizorSelectat(Furnizor furnizorSelectat) {
        // Încarcă furnizorul complet din baza de date
        Furnizor furnizorComplet = this.masterRepo.findFurnizorById(furnizorSelectat.getId());
        // Atașează furnizorul la contract
        this.contractCurent.setFurnizor(furnizorComplet);
    }

    // ------------- ZONA 2 -- RESPONSABILI --------------//

    private List<Angajat> listaAngajati;

    public List<Angajat> getListaAngajati() {
        if (this.listaAngajati == null) {
            // Obținem lista angajaților folosind metoda din MasterRepository
            this.listaAngajati = this.masterRepo.findAngajatiAll();
        }
        return listaAngajati;
    }

    public void setListaAngajati(List<Angajat> listaAngajati) {
        this.listaAngajati = listaAngajati;
    }

    public Angajat getResponsabilSelectat() {
        return this.contractCurent.getResponsabil();
    }

    public void setResponsabilSelectat(Angajat responsabilSelectat) {
        // Obținem angajatul complet din baza de date
        Angajat angajatComplet = this.masterRepo.findAngajatById(responsabilSelectat.getId());
        this.contractCurent.setResponsabil(angajatComplet);
    }

    // ------------- ZONA 3 -- TIPURI DE CONTRACTE --------------//

    private List<String> tipuriContract;

    public List<String> getTipuriContract() {
        if (tipuriContract == null) {
            tipuriContract = new LinkedList<String>();
            tipuriContract.add(TIP_CONTRACT_FURNIZARE);
            tipuriContract.add(TIP_CONTRACT_SERVICII);
            tipuriContract.add(TIP_CONTRACT_COLABORARE);
        }
        return tipuriContract;
    }

    public String getTipContractSelectat() {
        return this.contractCurent.getTipContract();
    }

    public void setTipContractSelectat(String tipContract) {
        this.contractCurent.setTipContract(tipContract);
    }

    // ------------- ZONA 4 -- STATUSURI CONTRACT --------------//

    private List<String> statusuriContract;

    public List<String> getStatusuriContract() {
        if (statusuriContract == null) {
            statusuriContract = new LinkedList<String>();
            statusuriContract.add(STATUS_ACTIV);
            statusuriContract.add(STATUS_MODIFICAT);
            statusuriContract.add(STATUS_REZILIAT);
        }
        return statusuriContract;
    }

    // ------------- ZONA 5 -- TIPURI DOCUMENTE AFERENTE --------------//

    private List<String> tipuriDocumenteAferente;

    public List<String> getTipuriDocumenteAferente() {
        if (tipuriDocumenteAferente == null) {
            tipuriDocumenteAferente = new LinkedList<String>();
            tipuriDocumenteAferente.add(TIP_DOCUMENT_FACTURA);
            tipuriDocumenteAferente.add(TIP_DOCUMENT_ACT_ADITIONAL);
            tipuriDocumenteAferente.add(TIP_DOCUMENT_REZILIERE);
        }
        return tipuriDocumenteAferente;
    }

    // ------------- ZONA 6 -- DOCUMENTE AFERENTE --------------//

    private ActeAferenteContract documentAferentSelectat;

    public List<ActeAferenteContract> getDocumenteAferente() {
        return this.contractCurent.getActeAferente();
    }

    public ActeAferenteContract getDocumentAferentSelectat() {
        return documentAferentSelectat;
    }

    public void setDocumentAferentSelectat(ActeAferenteContract documentAferentSelectat) {
        this.documentAferentSelectat = documentAferentSelectat;
    }

    // ------------- ZONA 7 -- MODIFICARI CONTRACT --------------//

    private ModificareContract modificareContractSelectata;

    public List<ModificareContract> getModificariContract() {
        return this.contractCurent.getModificari();
    }

    public ModificareContract getModificareContractSelectata() {
        return modificareContractSelectata;
    }

    public void setModificareContractSelectata(ModificareContract modificareContractSelectata) {
        this.modificareContractSelectata = modificareContractSelectata;
    }

    // GETTERI SI SETTERI pentru proprietățile zonei 0

    public Contract getContractCurent() {
        return contractCurent;
    }

    public void setContractCurent(Contract contractCurent) {
        this.contractCurent = contractCurent;
    }

    public List<Contract> getListaContracte() {
        return listaContracte;
    }

    public void setListaContracte(List<Contract> listaContracte) {
        this.listaContracte = listaContracte;
    }

    public MasterRepository getMasterRepo() {
        return masterRepo;
    }

    public void setMasterRepo(MasterRepository masterRepo) {
        this.masterRepo = masterRepo;
    }

    public ContractRepository getContractRepo() {
        return contractRepo;
    }

    public void setContractRepo(ContractRepository contractRepo) {
        this.contractRepo = contractRepo;
    }
}