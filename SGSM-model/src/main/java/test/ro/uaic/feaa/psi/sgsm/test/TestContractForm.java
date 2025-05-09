package test.ro.uaic.feaa.psi.sgsm.test.forms;

import java.util.Date;

import org.junit.Assert;

import ro.uaic.feaa.psi.sgsm.forms.ContractFormController;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;

/**
 * Test pentru formularul de gestionare a contractelor
 */
public class TestContractForm {

    public static void main(String[] args) {
        ContractFormController form = new ContractFormController();

        // Verificări inițiale: listele trebuie să aibă o sursă de date validă
        verificariInitiale(form);

        // 1. Adăugare contract nou
        form.contractNou();

        // 2. Verificăm tipul de document creat - trebuie să fie Contract
        Assert.assertTrue("Tipul de document nou creat nu este corect.",
                form.getFormData().getContractCurent().getTipDocument().equals("Contract"));

        // 3. Simulăm completarea datelor contractului
        form.getFormData().getContractCurent().setNumarContract("C-2025-001");
        form.getFormData().getContractCurent().setDataDocument(new Date());
        form.getFormData().getContractCurent().setDurataContract(12); // 12 luni
        form.getFormData().getContractCurent().setTipContract("Furnizare produse");
        form.getFormData().getContractCurent().setTermenePlata("30 zile");
        form.getFormData().getContractCurent().setTermeneLivrare("10 zile lucrătoare");
        form.getFormData().getContractCurent().setObservatii("Contract test");

        // 4. Simulăm selectarea furnizorului
        form.getFormData().setFurnizorSelectat(
                form.getFormData().getListaFurnizorilor().get(0));

        // 5. Verificăm dacă furnizorul a fost atașat corect
        Assert.assertTrue("Furnizor atașat contractului incorect",
                form.getFormData().getContractCurent().getFurnizor().equals(
                        form.getFormData().getFurnizorSelectat()));

        // 6. Simulăm selectarea responsabilului
        form.getFormData().setResponsabilSelectat(
                form.getFormData().getListaAngajati().get(0));

        // 7. Verificăm dacă responsabilul a fost atașat corect
        Assert.assertTrue("Responsabil atașat contractului incorect",
                form.getFormData().getContractCurent().getResponsabil().equals(
                        form.getFormData().getResponsabilSelectat()));

        // 8. Verificăm existența modificării inițiale a contractului
        Assert.assertNotNull("Modificarea inițială a contractului nu a fost creată",
                form.getFormData().getModificareCurenta());

        // 9. Salvăm contractul
        String rezultatSalvare = form.salveazaModificariContract();
        System.out.println("Rezultat salvare: " + rezultatSalvare);

        // 10. Verificăm dacă contractul a fost salvat cu succes
        Assert.assertTrue("Contractul nu a fost salvat cu succes",
                rezultatSalvare.contains("succes"));

        // 11. Verificăm dacă contractul are ID după salvare
        Assert.assertNotNull("Contractul nu are ID după salvare",
                form.getFormData().getContractCurent().getId());

        // 12. Simulăm adăugarea unui act adițional
        form.modificaContract("Actualizare");

        // 13. Simulăm completarea datelor actului adițional
        ActeAferenteContract actAditional = form.getFormData().getDocumentAferentCurent();
        actAditional.setNrDocument("AA-001");
        actAditional.setDataDocument(new Date());
        actAditional.setClauze("Prelungire durată contract cu 6 luni");

        // 14. Verificăm existența modificării pentru act adițional
        ModificareContract modificare = form.getFormData().getModificareCurenta();
        Assert.assertNotNull("Modificarea pentru act adițional nu a fost creată", modificare);
        Assert.assertEquals("Statusul modificării nu este corect",
                "Modificat", modificare.getStatus());

        // 15. Actualizăm datele contractului
        form.getFormData().getContractCurent().setDurataContract(18); // prelungire cu 6 luni

        // 16. Salvăm modificările
        rezultatSalvare = form.salveazaModificariContract();
        System.out.println("Rezultat salvare modificare: " + rezultatSalvare);

        // 17. Verificăm dacă modificarea a fost salvată cu succes
        Assert.assertTrue("Modificarea nu a fost salvată cu succes",
                rezultatSalvare.contains("succes"));

        // 18. Simulăm rezilierea contractului
        form.modificaContract("Reziliere");

        // 19. Simulăm completarea datelor formularului de reziliere
        ActeAferenteContract formularReziliere = form.getFormData().getDocumentAferentCurent();
        formularReziliere.setNrDocument("FR-001");
        formularReziliere.setDataDocument(new Date());
        formularReziliere.setMotivReziliere("Nerespectare termene de livrare");

        // 20. Verificăm existența modificării pentru reziliere
        modificare = form.getFormData().getModificareCurenta();
        Assert.assertNotNull("Modificarea pentru reziliere nu a fost creată", modificare);
        Assert.assertEquals("Statusul modificării nu este corect",
                "Reziliat", modificare.getStatus());

        // 21. Salvăm rezilierea
        rezultatSalvare = form.salveazaModificariContract();
        System.out.println("Rezultat salvare reziliere: " + rezultatSalvare);

        // 22. Verificăm dacă rezilierea a fost salvată cu succes
        Assert.assertTrue("Rezilierea nu a fost salvată cu succes",
                rezultatSalvare.contains("succes"));

        // 23. Verificăm dacă statusul contractului este "Reziliat"
        Assert.assertEquals("Statusul contractului nu este corect după reziliere",
                "Reziliat", form.getFormData().getContractCurent().getStatusCurent());
    }

    /**
     * Verificări privind inițializarea corectă a formularului
     */
    private static void verificariInitiale(ContractFormController form) {
        Assert.assertTrue(
                "BUG sau Nu există Furnizori pentru test. Rulați mai întâi testul TestFurnizori din proiectul SGSM-Model",
                form.getFormData().getListaFurnizorilor().size() > 0);

        Assert.assertTrue(
                "BUG sau Nu există Angajați pentru test. Rulați mai întâi testul TestAngajati din proiectul SGSM-Model",
                form.getFormData().getListaAngajati().size() > 0);
    }
}