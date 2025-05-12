package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

import ro.uaic.feaa.psi.sgsm.model.entities.Furnizor;
import ro.uaic.feaa.psi.sgsm.model.entities.Localitate;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;

/**
 * Un test prin care verificăm cele două metode de încărcare a datelor despre
 * furnizori:
 *
 * # {@link MasterRepository#findFurnizoriAll()} - încarcă complet graful de
 * obiecte, inclusiv obiecte Localitate corespunzătoare relațiilor ManyToMany
 *
 * # {@link MasterRepository#findFurnizoriAllLight()} - încarcă doar datele
 * specificate prin constructorul Furnizor care ignoră atributul
 * <code> localitate </code>
 */
public class TestFurnizori {

    static MasterRepository repo = new MasterRepository();

    public static void main(String[] args) {
        List<Furnizor> x = repo.findFurnizoriAll();
        if (x.size() == 0) { // dacă nu sunt date în tabelă, adăugăm date de test
            adaugaFurnizori();
            x = repo.findFurnizoriAll();
        }

        System.out.println("##################### Vezi SQL generat deasupra acestei linii și compară cu cel generat dedesubt. " +
                "Atenție la numărul de interogări pe tabela Localitate############");

        // acum ar trebui să avem date de test
        Assert.assertTrue(x.size() > 0);

        List<Furnizor> y = repo.findFurnizoriAllLight();
        // verificăm dacă cele două metode au returnat același număr de obiecte
        Assert.assertTrue(y.size() == x.size());

        // Afișăm furnizorii adăugați
        System.out.println("\nLista furnizorilor:");
        for (Furnizor f : x) {
            System.out.println("ID: " + f.getIdFurnizor() + ", Nume: " + f.getNumeFurnizor() +
                    ", Tip: " + f.getTipFurnizor() + ", Email: " + f.getEmail());
        }
    }

    public static void adaugaFurnizori() {
        repo.beginTransaction();

        for (int i = 1; i <= 5; i++) {
            Furnizor f = new Furnizor();
            f.setIdFurnizor("F10" + i);
            f.setNumeFurnizor("Furnizor Test " + i);
            f.setNumarTelefon("074010" + i);
            f.setEmail("contact@furnizor" + i + ".ro");
            f.setTipFurnizor("Tip " + (i % 2 == 0 ? "Permanent" : "Ocazional"));
            f.setGarantie((i * 6) + " luni");
            f.setDurataGarantie(String.valueOf(i * 6));
            f.setConditiiMontaj("Condiții de montaj pentru furnizorul " + i);

            // Data expirării la un an în viitor
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.add(java.util.Calendar.YEAR, 1);
            f.setTermenExpirare(calendar.getTime());

            repo.addFurnizor(f);
        }

        repo.commitTransaction();

        System.out.println("Au fost adăugați 5 furnizori pentru test!");
    }

    private static void adaugaLocalitati() {
        repo.beginTransaction();
        for (int i = 1; i < 10; i++) {
            Localitate loc = new Localitate();
            loc.setCod(100 + i);
            loc.setDenumire("Localitate " + (100 + i));

            repo.addLocalitate(loc);
        }
        repo.commitTransaction();

        System.out.println("Au fost adăugate localități pentru test!");
    }
}