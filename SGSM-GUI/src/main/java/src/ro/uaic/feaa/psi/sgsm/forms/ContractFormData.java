package ro.uaic.feaa.psi.sgsm.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;
import ro.uaic.feaa.psi.sgsm.model.repository.AngajatRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.ContractRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;

/**
 * Această clasă constituie modelul pentru formularul de contracte. Este un adaptor
 * al modelului SGSM: are rolul de a pregăti (filtra/adapta/transforma) modelul SGSM
 * în forma necesară formularului.
 *
 * Structura clasei este organizată pe secțiuni pentru a fi mai clară
 * corespondența cu descrierea narativă a scenariilor.
 */
public class ContractFormData {

    // Constante pentru tipurile de acțiuni și statusuri
    public static final String STORNARE = "Stornare";
    public static final String ACTIUNE_ACTUALIZARE = "Actualizare";
    public static final String ACTIUNE_REZILIERE = "Reziliere";

    public static final String STATUS_ACTIV = "Activ";
    public static final String STATUS_MODIFICAT = "Modificat";
    public static final String STATUS_REZILIAT = "Reziliat";

    public static final String TIP_DOCUMENT_CONTRACT = "Contract";
    public static final String TIP_DOCUMENT_ACT_ADITIONAL = "Act Adițional";
    public static final String TIP_DOCUMENT_REZILIERE = "Formular Reziliere";
    public static final String TIP_DOCUMENT_FACTURA = "Factură";

    // --------------ZONA 0 - COMUNĂ TUTUROR FORMULARELOR -------------------//

    // Obiectul-țintă (sursa de date) al formularului - cel aflat curent în editare
    private Contract contractCurent;
    private ActeAferenteContract documentAferentCurent;
    private ModificareContract modificareCurenta;

    // Colecția de obiecte-țintă obținute în urma unei operații de căutare
    private List<Contract> listaContracte;

    // Obiecte de tip Repository necesare pentru interogarea modelului
    private MasterRepository masterRepo = new MasterRepository();
    private ContractRepository contractRepo = new ContractRepository();
    private AngajatRepository angajatRepo = new AngajatRepository();

    // ---------------ZONA 1 - FURNIZORI --------------//

    /**
     * Pentru încărcarea elementelor din lista de furnizori.
     */
    private List<Furnizor> listaFurnizorilor;

    /**
     * Implementare standard pentru o colecție-sursă de date a unui obiect de tip listă
     */
    public List<Furnizor> getListaFurnizorilor() {
        if (this.listaFurnizorilor == null) {
            // E primul apel (userul tocmai a deschis formul)
            this.listaFurnizorilor = this.masterRepo.findFurnizoriAllLight();
        }
        return listaFurnizorilor;
    }

    public void setListaFurnizorilor(List<Furnizor> listaFurnizorilor) {
        this.listaFurnizorilor = listaFurnizorilor;
    }

    // Getter și Setter pentru selecția curentă
    public Furnizor getFurnizorSelectat() {
        return this.contractCurent != null ? this.contractCurent.getFurnizor() : null;
    }

    /**
     * ATENȚIE - în majoritatea mediilor de dezvoltare, obiectele grafice de tip
     * listă returnează un id (o valoare atomică / un singur atribut) al
     * obiectului selectat din listă. Ca urmare, trebuie încărcat obiectul
     * corespunzător pentru relația ManyToOne.
     */
    public void setFurnizorSelectat(Furnizor furnizorSelectat) {
        if (furnizorSelectat == null || this.contractCurent == null) return;

        Furnizor furnizorComplet = this.masterRepo.findFurnizorById(furnizorSelectat.getId());
        this.contractCurent.setFurnizor(furnizorComplet);
    }

    // ---------------ZONA 2 - ANGAJAȚI (RESPONSABILI) --------------//

    private List<Angajat> listaAngajati;

    public List<Angajat> getListaAngajati() {
        if (this.listaAngajati == null) {
            this.listaAngajati = this.angajatRepo.findAllAngajatiLight();
        }
        return listaAngajati;
    }

    public void setListaAngajati(List<Angajat> listaAngajati) {
        this.listaAngajati = listaAngajati;
    }

    public Angajat getResponsabilSelectat() {
        return this.contractCurent != null ? this.contractCurent.getResponsabil() : null;
    }

    public void setResponsabilSelectat(Angajat responsabilSelectat) {
        if (responsabilSelectat == null || this.contractCurent == null) return;

        Angajat angajatComplet = this.angajatRepo.findAngajatById(responsabilSelectat.getId());
        this.contractCurent.setResponsabil(angajatComplet);
    }

    // ------------- ZONA 3 - TIP ACȚIUNE --------------//

    // Adăugăm lista de tipuri de acțiuni pentru contract
    private List<String> tipuriActiuni;

    public List<String> getTipuriActiuni() {
        if (tipuriActiuni == null) {
            tipuriActiuni = new ArrayList<>();
            tipuriActiuni.add(ACTIUNE_ACTUALIZARE);
            tipuriActiuni.add(ACTIUNE_REZILIERE);
        }
        return tipuriActiuni;
    }

    // Acțiunea selectată - folosită pentru determinarea tipului de modificare
    private String actiuneSelectata = ACTIUNE_ACTUALIZARE; // Implicit Actualizare

    public String getActiuneSelectata() {
        return this.actiuneSelectata;
    }

    public void setActiuneSelectata(String actiuneSelectata) {
        this.actiuneSelectata = actiuneSelectata;
    }

    // ------------- ZONA 4 - TIPURI DE CONTRACT --------------//

    private List<String> tipuriContract;

    public List<String> getTipuriContract() {
        if (tipuriContract == null) {
            tipuriContract = new LinkedList<>();
            tipuriContract.add("Furnizare produse");
            tipuriContract.add("Prestări servicii");
            tipuriContract.add("Lucrări");
            tipuriContract.add("Consultanță");
        }
        return tipuriContract;
    }

    // ------------- ZONA 5 - TIPURI DOCUMENTE AFERENTE --------------//

    private List<String> tipuriDocumentAferent;

    public List<String> getTipuriDocumentAferent() {
        if (tipuriDocumentAferent == null) {
            tipuriDocumentAferent = new LinkedList<>();
            tipuriDocumentAferent.add(TIP_DOCUMENT_ACT_ADITIONAL);
            tipuriDocumentAferent.add(TIP_DOCUMENT_REZILIERE);
            tipuriDocumentAferent.add(TIP_DOCUMENT_FACTURA);
            tipuriDocumentAferent.add("Altele");
        }
        return tipuriDocumentAferent;
    }

    // ------------- ZONA 6 - FILTRARE ȘI CĂUTARE --------------//

    private Date dataInceput;
    private Date dataSfarsit;
    private String numarContractCautare;
    private Furnizor furnizorCautare;
    private String statusCautare;

    // ------------- METODE DE OPERARE -------------//

    /**
     * Creează un contract nou
     */
    public void creeazaContractNou() {
        contractCurent = new Contract();
        contractCurent.setDataDocument(new Date());
        contractCurent.setDataOperare(new Date());
        contractCurent.setDataIncepere(new Date());
        contractCurent.setTipDocument(TIP_DOCUMENT_CONTRACT);

        // Inițializăm și modificarea inițială cu statusul ACTIV
        modificareCurenta = new ModificareContract();
        modificareCurenta.setStatus(STATUS_ACTIV);
        modificareCurenta.setDataModificare(new Date());
        modificareCurenta.setDescriere("Înregistrare inițială contract");
    }

    /**
     * Creează un document aferent nou
     */
    public void creeazaDocumentAferentNou(String tipDocument) {
        documentAferentCurent = new ActeAferenteContract();
        documentAferentCurent.setDataDocument(new Date());
        documentAferentCurent.setDataOperare(new Date());
        documentAferentCurent.setTipDocument(tipDocument);
        documentAferentCurent.setTipDocumentAferentContract(tipDocument);
    }

    /**
     * Realizează căutarea după criteriile specificate
     */
    public void cautaContracte() {
        if (dataInceput != null && dataSfarsit != null) {
            listaContracte = contractRepo.findContractsByPeriod(dataInceput, dataSfarsit);
        } else if (furnizorCautare != null) {
            listaContracte = contractRepo.findContractsByFurnizorId(furnizorCautare.getId());
        } else if (statusCautare != null && !statusCautare.isEmpty()) {
            listaContracte = contractRepo.findContractsByStatus(statusCautare);
        } else if (numarContractCautare != null && !numarContractCautare.isEmpty()) {
            Contract contractGasit = contractRepo.findContractByNumar(numarContractCautare);
            listaContracte = new ArrayList<>();
            if (contractGasit != null) {
                listaContracte.add(contractGasit);
            }
        } else {
            listaContracte = contractRepo.findAllContracts();
        }
    }

    /**
     * Finalizează legăturile între obiecte înaintea salvării
     */
    public void finalizeazaModificare() {
        if (contractCurent != null) {
            if (modificareCurenta != null) {
                modificareCurenta.setContract(contractCurent);
                contractCurent.addModificare(modificareCurenta);
            }

            if (documentAferentCurent != null) {
                documentAferentCurent.setContract(contractCurent);
                contractCurent.addActAferent(documentAferentCurent);

                if (modificareCurenta != null) {
                    modificareCurenta.setDocumentModificare(documentAferentCurent);
                }
            }
        }
    }

    // Getteri și setteri pentru proprietățile clasei

    public Contract getContractCurent() {
        return contractCurent;
    }

    public void setContractCurent(Contract contractCurent) {
        this.contractCurent = contractCurent;
    }

    public ActeAferenteContract getDocumentAferentCurent() {
        return documentAferentCurent;
    }

    public void setDocumentAferentCurent(ActeAferenteContract documentAferentCurent) {
        this.documentAferentCurent = documentAferentCurent;
    }

    public ModificareContract getModificareCurenta() {
        return modificareCurenta;
    }

    public void setModificareCurenta(ModificareContract modificareCurenta) {
        this.modificareCurenta = modificareCurenta;
    }

    public List<Contract> getListaContracte() {
        return listaContracte;
    }

    public void setListaContracte(List<Contract> listaContracte) {
        this.listaContracte = listaContracte;
    }

    public Date getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(Date dataInceput) {
        this.dataInceput = dataInceput;
    }

    public Date getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(Date dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public String getNumarContractCautare() {
        return numarContractCautare;
    }

    public void setNumarContractCautare(String numarContractCautare) {
        this.numarContractCautare = numarContractCautare;
    }

    public Furnizor getFurnizorCautare() {
        return furnizorCautare;
    }

    public void setFurnizorCautare(Furnizor furnizorCautare) {
        this.furnizorCautare = furnizorCautare;
    }

    public String getStatusCautare() {
        return statusCautare;
    }

    public void setStatusCautare(String statusCautare) {
        this.statusCautare = statusCautare;
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

    public AngajatRepository getAngajatRepo() {
        return angajatRepo;
    }

    public void setAngajatRepo(AngajatRepository angajatRepo) {
        this.angajatRepo = angajatRepo;
    }
}