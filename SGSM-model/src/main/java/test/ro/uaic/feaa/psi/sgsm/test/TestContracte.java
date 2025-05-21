package test.ro.uaic.feaa.psi.sgsm.test;

import java.util.Date;
import java.util.List;

import org.junit.Assert;

import ro.uaic.feaa.psi.sgsm.model.entities.ActAditional;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.FormularReziliere;
import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;
import ro.uaic.feaa.psi.sgsm.model.repository.ContractRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;;

/**
 * Test pentru entitățile Contract și asociații.
 * Teste efectuate:
 * # extragere listă de contracte
 * # adăugare Contract
 * # adăugare ActeAferenteContract
 * # adăugare ModificareContract
 * # adăugare ActAditional
 * # adăugare FormularReziliere
 */
public class TestContracte {

    static MasterRepository masterRepo = new MasterRepository();
    static ContractRepository contractRepo = new ContractRepository();

    public static void main(String[] args) {
        // Asigurăm că avem date de test pentru furnizori și angajați
     //   asiguraDateTest();

        List<Contract> contracte = contractRepo.findContractAll();
        if (contracte.size() == 0) { // dacă nu sunt date în tabelă, adăugăm date de test
            adaugaContracte();
            contracte = contractRepo.findContractAll();
        }

        // acum ar trebui să avem date de test
        Assert.assertTrue("Nu avem contracte în baza de date", contracte.size() > 0);

        // Afișăm contractele
        System.out.println("\nLista contractelor:");
        for (Contract c : contracte) {
            System.out.println("ID Document: " + c.getIdDocument() +
                    ", Nr. Contract: " + c.getNumarContract() +
                    ", Tip: " + c.getTipContract() +
                    ", Furnizor: " + (c.getFurnizor() != null ? c.getFurnizor().getNumeFurnizor() : "N/A") +
                    ", Responsabil: " + (c.getResponsabil() != null ? c.getResponsabil().getNumeAngajat() : "N/A"));

            // Afișăm actele aferente
            System.out.println("  Acte aferente (" + c.getActeAferente().size() + "):");
            for (ActeAferenteContract act : c.getActeAferente()) {
                System.out.println("    - " + act.getTipDocument() +
                        ", Nr: " + act.getNumarActContract() +
                        ", Data: " + act.getDataDocument());
            }

            // Afișăm modificările
            System.out.println("  Modificări (" + c.getModificari().size() + "):");
            for (ModificareContract mod : c.getModificari()) {
                System.out.println("    - Status: " + mod.getStatus() +
                        ", Data: " + mod.getDataDocument() +
                        ", Descriere: " + mod.getModificariActe());
            }

            System.out.println(); // linie goală pentru separare
        }

        // Testăm căutarea contractelor după furnizor
        if (!masterRepo.findFurnizoriAll().isEmpty()) {
            Long furnizorId = masterRepo.findFurnizoriAll().get(0).getId();
            List<Contract> contracteFurnizor = contractRepo.findContractByFurnizorId(furnizorId);
            System.out.println("Contracte pentru furnizorul cu ID " + furnizorId + ": " + contracteFurnizor.size());
        }

        // Testăm căutarea contractelor după status
        List<Contract> contracteActive = contractRepo.findContractByStatus("Activ");
        System.out.println("Contracte active: " + contracteActive.size());
    }


    private static void adaugaContracte() {
        // Verificăm dacă avem furnizori și angajați
        List<Furnizor> furnizori = masterRepo.findFurnizoriAll();
        List<Angajat> angajati = masterRepo.findAngajatiAll();

        if (furnizori.isEmpty() || angajati.isEmpty()) {
            System.out.println("Nu există furnizori sau angajați! Nu se pot adăuga contracte.");
            return;
        }

        contractRepo.beginTransaction();

        try {
            // Adăugăm 3 contracte de test
            for (int i = 1; i <= 3; i++) {
                // Creăm contractul
                Contract contract = new Contract();
                contract.setIdDocument("C100" + i);
                contract.setTipDocument("Contract");
                contract.setNumarContract("C100" + i + "/" + (2024 + i));
                contract.setDataDocument(new Date());
                contract.setDataModificare(new Date());
                contract.setTipContract(i % 3 == 0 ? "Furnizare" : (i % 3 == 1 ? "Servicii" : "Colaborare"));
                contract.setDurata((i * 6) + " luni");
                contract.setTermenePlata((30 * i) + " zile");
                contract.setTermeneLibrare((i * 5) + " zile");
                contract.setObservatii("Contract de test " + i);

                // Atașăm furnizor și responsabil
                contract.setFurnizor(furnizori.get(i % furnizori.size()));
                contract.setResponsabil(angajati.get(i % angajati.size()));

                // Creăm modificarea inițială (status activ)
                ModificareContract modificareInitiala = new ModificareContract();
                modificareInitiala.setIdDocument("MI100" + i);
                modificareInitiala.setTipDocument("Modificare");
                modificareInitiala.setDataDocument(new Date());
                modificareInitiala.setModificariActe("Creare contract");
                modificareInitiala.setStatus("Activ");

                // Atașăm modificarea la contract
                contract.addModificare(modificareInitiala);

                // Creăm un act aferent (factură)
                ActeAferenteContract facturaAferenta = new ActeAferenteContract();
                facturaAferenta.setIdDocument("F100" + i);
                facturaAferenta.setTipDocument("Factura");
                facturaAferenta.setNumarActContract("F100" + i + "/" + (2024 + i));
                facturaAferenta.setDataDocument(new Date());
                facturaAferenta.setModificariActe("Factură inițială");

                // Atașăm factura la contract
                contract.addActAferent(facturaAferenta);

                // Pentru al doilea contract, adăugăm un act adițional
                if (i == 2) {
                    // Creăm actul adițional
                    ActAditional actAditional = new ActAditional();
                    actAditional.setIdDocument("AA100" + i);
                    actAditional.setTipDocument("Act Aditional");
                    actAditional.setNumarActContract("AA100" + i + "/" + (2024 + i));
                    actAditional.setDataDocument(new Date());
                    actAditional.setModificariActe("Modificare termeni contract");
                    actAditional.setClauze("Modificare termen de plată");
                    actAditional.setDescriere("Extindere termen de plată la 45 zile");

                    // Creăm modificarea asociată
                    ModificareContract modificareActualizare = new ModificareContract();
                    modificareActualizare.setIdDocument("MA100" + i);
                    modificareActualizare.setTipDocument("Modificare");
                    modificareActualizare.setDataDocument(new Date());
                    modificareActualizare.setModificariActe("Actualizare contract prin act adițional");
                    modificareActualizare.setStatus("Modificat");
                    modificareActualizare.setActAditional(actAditional);

                    // Stabilim relația bidirecțională
                    actAditional.setModificareContract(modificareActualizare);

                    // Atașăm actul adițional și modificarea la contract
                    contract.addActAferent(actAditional);
                    contract.addModificare(modificareActualizare);
                }

                // Pentru al treilea contract, adăugăm un formular de reziliere
                if (i == 3) {
                    // Creăm formularul de reziliere
                    FormularReziliere formularReziliere = new FormularReziliere();
                    formularReziliere.setIdDocument("FR100" + i);
                    formularReziliere.setTipDocument("Reziliere");
                    formularReziliere.setNumarActContract("FR100" + i + "/" + (2024 + i));
                    formularReziliere.setDataDocument(new Date());
                    formularReziliere.setModificariActe("Reziliere contract");
                    formularReziliere.setMotivReziliere("Nerespectare termene de livrare");

                    // Creăm modificarea asociată
                    ModificareContract modificareReziliere = new ModificareContract();
                    modificareReziliere.setIdDocument("MR100" + i);
                    modificareReziliere.setTipDocument("Reziliere");
                    modificareReziliere.setDataDocument(new Date());
                    modificareReziliere.setModificariActe("Detalii despre rezilierea contractului");
                    modificareReziliere.setStatus("Reziliat");
                    modificareReziliere.setFormularReziliere(formularReziliere);

                    // Stabilim relația bidirecțională
                    formularReziliere.setModificareContract(modificareReziliere);

                    // Atașăm formularul de reziliere și modificarea la contract
                    contract.addActAferent(formularReziliere);
                    contract.addModificare(modificareReziliere);
                }

                // Salvăm contractul în baza de date
                contractRepo.saveContract(contract);
            }

            // Comitem tranzacția
            contractRepo.commitTransaction();
            System.out.println("Au fost adăugate 3 contracte de test!");

        } catch (Exception e) {
            System.out.println("Eroare la adăugarea contractelor: " + e.getMessage());
            e.printStackTrace();
            // Anulăm tranzacția în caz de eroare
            try {
                contractRepo.getEm().getTransaction().rollback();
            } catch (Exception re) {
                System.out.println("Eroare la anularea tranzacției: " + re.getMessage());
            }
        }
    }
}