package ro.uaic.feaa.psi.sgsm.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ro.uaic.feaa.psi.sgsm.model.repository.ContractRepository;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;
import ro.uaic.feaa.psi.sgsm.reports.ContracteReport;

/**
 * Test pentru rapoartele privind contractele.
 * Generează și afișează rapoartele implementate.
 */
public class TestRapoarteContracte {

    // Repository-uri necesare pentru teste
    static ContractRepository contractRepo = new ContractRepository();
    static MasterRepository masterRepo = new MasterRepository();

    public static void main(String[] args) {
        // Verificăm existența contractelor în baza de date
        List<Contract> contracte = contractRepo.findAllContracts();
        if (contracte.isEmpty()) {
            System.err.println("Nu există contracte în baza de date. Rulați mai întâi TestContracte.");
            return;
        }

        // Obținem perioada ultimelor 6 luni pentru rapoarte
        Calendar cal = Calendar.getInstance();
        Date dataSfarsit = cal.getTime();
        cal.add(Calendar.MONTH, -6);
        Date dataInceput = cal.getTime();

        // Inițializăm generatorul de rapoarte
        ContracteReport raportGenerator = new ContracteReport();

        // Generăm și afișăm raportul de contracte grupate pe furnizori
        System.out.println("\n===== RAPORT: CONTRACTE GRUPATE PE FURNIZORI =====\n");
        String raportFurnizori = raportGenerator.generateRaportContractePeFurnizori(
                dataInceput, dataSfarsit, null, null);
        System.out.println(raportFurnizori);

        // Generăm și afișăm raportul de contracte active
        System.out.println("\n===== RAPORT: CONTRACTE CU STATUS ACTIV =====\n");
        String raportContracteActive = raportGenerator.generateRaportContracteStatus(
                dataInceput, dataSfarsit, "Activ");
        System.out.println(raportContracteActive);

        // Generăm și afișăm raportul de contracte reziliate
        System.out.println("\n===== RAPORT: CONTRACTE CU STATUS REZILIAT =====\n");
        String raportContracteReziliate = raportGenerator.generateRaportContracteStatus(
                dataInceput, dataSfarsit, "Reziliat");
        System.out.println(raportContracteReziliate);

        // Dacă există contracte și furnizori, generăm un raport filtrat după primul furnizor
        if (!contracte.isEmpty() && contracte.get(0).getFurnizor() != null) {
            Furnizor furnizor = contracte.get(0).getFurnizor();
            System.out.println("\n===== RAPORT: CONTRACTE PENTRU FURNIZORUL " + furnizor.getNume() + " =====\n");

            String raportFurnizorSpecific = raportGenerator.generateRaportContractePeFurnizori(
                    dataInceput, dataSfarsit, furnizor.getId(), null);
            System.out.println(raportFurnizorSpecific);
        }
    }
}