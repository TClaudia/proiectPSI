package test.ro.uaic.feaa.psi.sgsm.test;

import java.util.List;

import org.junit.Assert;

import ro.uaic.feaa.psi.sgsm.model.entities.Angajat;
import ro.uaic.feaa.psi.sgsm.model.repository.MasterRepository;

/**
 * Test pentru Angajati. Teste efectuate:
 * # extragere lista de angajati
 * # adaugare Angajat
 */
public class TestAngajati {

    static MasterRepository repo = new MasterRepository();

    public static void main(String[] args) {
        List<Angajat> angajati = repo.findAngajatiAll();

        if (angajati.size() == 0) { // dacă nu sunt date în tabelă, adăugăm date de test
            adaugaAngajati();
            angajati = repo.findAngajatiAll();
        }

        // acum ar trebui să avem date de test
        Assert.assertTrue("Nu avem angajați în baza de date", angajati.size() > 0);

        // Afișăm angajații
        System.out.println("\nLista angajaților:");
        for (Angajat a : angajati) {
            System.out.println("ID: " + a.getIdAngajat() + ", Nume: " + a.getNumeAngajat() +
                    ", Responsabilitate: " + a.getResponsabilitate() +
                    ", Departament: " + a.getDepartament());
        }
    }

    public static void adaugaAngajati() {
        repo.beginTransaction();

        // Adăugăm câțiva angajați cu responsabilități diferite
        String[] nume = {"Popescu Ion", "Ionescu Ana", "Georgescu Mihai", "Vasilescu Elena", "Dumitrescu Dan"};
        String[] responsabilitati = {"Manager Contracte", "Responsabil Achiziții", "Agent Vânzări",
                "Responsabil Juridic", "Director Comercial"};
        String[] departamente = {"Vânzări", "Achiziții", "Vânzări", "Juridic", "Management"};

        for (int i = 0; i < nume.length; i++) {
            Angajat angajat = new Angajat();
            angajat.setIdAngajat("A" + (i + 100));
            angajat.setNumeAngajat(nume[i]);
            angajat.setResponsabilitate(responsabilitati[i]);
            angajat.setDepartament(departamente[i]);

            repo.addAngajat(angajat);
        }

        repo.commitTransaction();

        System.out.println("Au fost adăugați " + nume.length + " angajați!");
    }
}