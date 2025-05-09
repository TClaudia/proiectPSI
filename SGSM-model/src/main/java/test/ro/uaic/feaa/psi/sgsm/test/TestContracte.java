package ro.uaic.feaa.psi.sgsm.test;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import ro.uaic.feaa.psi.sgsm.model.repository.AngajatRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.ContractRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;

/**
 * Test pentru entitatea Contract. Teste efectuate:
 * # adăugare contract nou
 * # adăugare act adițional
 * # reziliere contract
 * # căutare contracte după diverse criterii
 */
public class TestContracte {

    // Repository-uri necesare pentru teste
    static ContractRepository contractRepo = new ContractRepository();
    static MasterRepository masterRepo = new MasterRepository();
    static AngajatRepository angajatRepo = new AngajatRepository();

    // Constante utilizate în teste
    private static final String STATUS_ACTIV = "Activ";
    private static final String STATUS_MODIFICAT = "Modificat";
    private static final String STATUS_REZILIAT = "Reziliat";

    private static final String ACT_ADITIONAL = "Act Adițional";
    private static final String FORMULAR_REZILIERE = "Formular Reziliere";

    public static void main(String[] args) {
        // Verificăm disponibilitatea datelor pentru teste
        verificariInitiale();

        // Testăm adăugarea unui contract
        Contract contract = adaugaContractTest();

        if (contract != null) {
            // Testăm adăugarea unui act adițional
            adaugaActAditionalTest(contract);

            // Testăm rezilierea contractului
            rezilieazaContractTest(contract);

            // Testăm căutarea contractelor
            testeazaCautareContracte();
        }
    }

    /**
     * Verifică disponibilitatea datelor necesare pentru teste.
     */
    private static void verificariInitiale() {
        // Verificăm existența furnizorilor
        List<Furnizor> furnizori = masterRepo.findFurnizoriAll();
        if (furnizori.isEmpty()) {
            System.err.println("Nu există furnizori în baza de date. Rulați mai întâi TestFurnizori.");
            return;
        }

        // Verificăm existența angajaților
        List<Angajat> angajati = angajatRepo.findAllAngajati();
        if (angajati.isEmpty()) {
            System.err.println("Nu există angajați în baza de date. Rulați mai întâi TestAngajati.");
            return;
        }

        System.out.println("Verificări inițiale trecute cu succes.");
    }

    /**
     * Testează adăugarea unui contract.
     *
     * @return Contractul adăugat sau null în caz de eroare
     */
    private static Contract adaugaContractTest() {
        System.out.println("TESTĂM ADĂUGAREA UNUI CONTRACT NOU");

        contractRepo.beginTransaction();

        try {
            // Creăm un contract nou
            Contract contract = new Contract();

            // Setăm proprietățile de bază
            contract.setTipDocument("Contract");
            contract.setNumarDocument("C-" + System.currentTimeMillis());
            contract.setDataDocument(new Date());
            contract.setDataOperare(new Date());

            // Setăm proprietățile specifice
            contract.setTipContract("Furnizare produse");
            contract.setDurataContract(12); // 12 luni
            contract.setTermenePlata("30 zile");
            contract.setTermeneLivrare("10 zile lucrătoare");
            contract.setObservatii("Contract test adăugat prin TestContracte");

            // Setăm furnizorul și responsabilul
            List<Furnizor> furnizori = masterRepo.findFurnizoriAll();
            List<Angajat> angajati = angajatRepo.findAllAngajati();

            contract.setFurnizor(furnizori.get(0));
            contract.setResponsabil(angajati.get(0));

            // Adăugăm înregistrarea inițială în istoricul modificărilor
            ModificariContract modificareInitiala = new ModificariContract();
            modificareInitiala.setContract(contract);
            modificareInitiala.setStatus(STATUS_ACTIV);
            modificareInitiala.setDataModificare(new Date());

            // Adăugăm modificarea la contract
            contract.addModificare(modificareInitiala);

            // Salvăm contractul
            contract = contractRepo.saveContract(contract);

            contractRepo.commitTransaction();

            System.out.println("Contract adăugat cu succes: " + contract.getNumarDocument());
            return contract;
        } catch (Exception e) {
            System.err.println("Eroare la adăugarea contractului: " + e.getMessage());
            e.printStackTrace();
            return null;
            // Tranzacția va fi anulată automat de EntityManager
        }
    }

    /**
     * Testează adăugarea unui act adițional la contractul specificat.
     *
     * @param contract Contractul la care se adaugă actul adițional
     */
    private static void adaugaActAditionalTest(Contract contract) {
        System.out.println("TESTĂM ADĂUGAREA UNUI ACT ADIȚIONAL");

        contractRepo.beginTransaction();

        try {
            // Creăm un act adițional
            ActeAferente actAditional = new ActeAferente();
            actAditional.setTipDocument(ACT_ADITIONAL);
            actAditional.setTipActAferent(ACT_ADITIONAL);
            actAditional.setNumarDocument("AA-" + System.currentTimeMillis());
            actAditional.setDataDocument(new Date());
            actAditional.setDataOperare(new Date());
            actAditional.setClauze("Prelungire durată contract cu 6 luni");
            actAditional.setDescriere("Act adițional pentru prelungirea duratei contractului");

            // Îl asociem contractului
            contract.addActAferent(actAditional);

            // Actualizăm durata contractului
            contract.setDurataContract(contract.getDurataContract() + 6);

            // Creăm o nouă modificare
            ModificariContract modificare = new ModificariContract();
            modificare.setContract(contract);
            modificare.setStatus(STATUS_MODIFICAT);
            modificare.setDataModificare(new Date());
            modificare.setDocumentModificare(actAditional);

            // Adăugăm modificarea la contract
            contract.addModificare(modificare);

            // Salvăm contractul actualizat
            contractRepo.saveContract(contract);

            contractRepo.commitTransaction();

            System.out.println("Act adițional adăugat cu succes: " + actAditional.getNumarDocument());
        } catch (Exception e) {
            System.err.println("Eroare la adăugarea actului adițional: " + e.getMessage());
            e.printStackTrace();
            // Tranzacția va fi anulată automat de EntityManager
        }
    }

    /**
     * Testează rezilierea contractului specificat.
     *
     * @param contract Contractul care va fi reziliat
     */
    private static void rezilieazaContractTest(Contract contract) {
        System.out.println("TESTĂM REZILIEREA CONTRACTULUI");

        contractRepo.beginTransaction();

        try {
            // Creăm un formular de reziliere
            ActeAferente formularReziliere = new ActeAferente();
            formularReziliere.setTipDocument(FORMULAR_REZILIERE);
            formularReziliere.setTipActAferent(FORMULAR_REZILIERE);
            formularReziliere.setNumarDocument("FR-" + System.currentTimeMillis());
            formularReziliere.setDataDocument(new Date());
            formularReziliere.setDataOperare(new Date());
            formularReziliere.setMotiv("Nerespectarea termenelor de livrare");

            // Îl asociem contractului
            contract.addActAferent(formularReziliere);

            // Creăm o nouă modificare
            ModificariContract modificare = new ModificariContract();
            modificare.setContract(contract);
            modificare.setStatus(STATUS_REZILIAT);
            modificare.setDataModificare(new Date());
            modificare.setDocumentModificare(formularReziliere);

            // Adăugăm modificarea la contract
            contract.addModificare(modificare);

            // Salvăm contractul actualizat
            contractRepo.saveContract(contract);

            contractRepo.commitTransaction();

            System.out.println("Contract reziliat cu succes");
        } catch (Exception e) {
            System.err.println("Eroare la rezilierea contractului: " + e.getMessage());
            e.printStackTrace();
            // Tranzacția va fi anulată automat de EntityManager
        }
    }

    /**
     * Testează căutarea contractelor după diverse criterii.
     */
    private static void testeazaCautareContracte() {
        System.out.println("TESTĂM CĂUTAREA CONTRACTELOR");

        try {
            // Testăm căutarea tuturor contractelor
            List<Contract> toateContractele = contractRepo.findAllContracts();
            System.out.println("Total contracte în baza de date: " + toateContractele.size());

            // Testăm căutarea după perioada curentă (ultima lună)
            Calendar cal = Calendar.getInstance();
            Date dataSfarsit = cal.getTime();
            cal.add(Calendar.MONTH, -1);
            Date dataInceput = cal.getTime();

            List<Contract> contractePerioadaCurenta = contractRepo.findContractsByPeriod(dataInceput, dataSfarsit);
            System.out.println("Contracte din ultima lună: " + contractePerioadaCurenta.size());

            // Testăm căutarea după status reziliat
            List<Contract> contracteReziliate = contractRepo.findContractsByStatus(STATUS_REZILIAT);
            System.out.println("Contracte reziliate: " + contracteReziliate.size());

            // Testăm căutarea după furnizor
            if (!toateContractele.isEmpty() && toateContractele.get(0).getFurnizor() != null) {
                Long furnizorId = toateContractele.get(0).getFurnizor().getId();
                List<Contract> contracteFurnizor = contractRepo.findContractsByFurnizorId(furnizorId);
                System.out.println("Contracte pentru furnizorul cu ID " + furnizorId + ": " + contracteFurnizor.size());
            }

            System.out.println("Teste de căutare finalizate cu succes");
        } catch (Exception e) {
            System.err.println("Eroare la căutarea contractelor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}