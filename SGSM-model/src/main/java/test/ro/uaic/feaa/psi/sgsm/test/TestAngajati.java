package ro.uaic.feaa.psi.sgsm.test;

import java.util.List;

import junit.framework.Assert;
import ro.uaic.feaa.psi.sgsm.model.repository.AngajatRepository;

/**
 * Test pentru entitatea Angajat. Teste efectuate:
 * # extragere lista de angajați
 * # adăugare Angajat
 */
public class TestAngajati {

    static AngajatRepository repo = new AngajatRepository();

    public static void main(String[] args) {
        List<Angajat> angajati = repo.findAllAngajati();

        // Dacă nu sunt angajați, adăugăm câțiva pentru teste
        if (angajati.size() == 0) {
            adaugaAngajati();
            angajati = repo.findAllAngajati();
        }

        /**
         * Adaugă angajați de test în baza de date.
         */
        private static void adaugaAngajati() {
            repo.beginTransaction();

            try {
                // Adăugăm câțiva angajați de test
                for (int i = 1; i <= 5; i++) {
                    Angajat angajat = new Angajat();
                    angajat.setMarcaAngajat(1000 + i);
                    angajat.setNumeAngajat("Angajat Test " + i);
                    angajat.setFunctieAngajat("Functie Test " + i);
                    angajat.setEmail("angajat" + i + "@test.com");
                    angajat.setTelefon("07" + String.format("%08d", i));

                    repo.addAngajat(angajat);
                }

                repo.commitTransaction();
                System.out.println("Angajați de test adăugați cu succes.");
            } catch (Exception e) {
                System.err.println("Eroare la adăugarea angajaților: " + e.getMessage());
                e.printStackTrace();
                // Tranzacția va fi anulată automat de EntityManager
            }
        }
    }

    // Verificăm că avem cel puțin un angajat
        Assert.assertTrue("Nu există angajați în baza de date", angajati.size() > 0);

    // Testăm varianta optimizată de încărcare (light)
    List<Angajat> angajatiLight = repo.findAllAngajatiLight();
        Assert.assertEquals("Numărului de angajați din lista light diferă",
                angajati.size(), angajatiLight.size());

    // Afișăm angajații pentru verificare manuală
        for (Angajat a : angajati) {
        System.out.println(a.getMarcaAngajat() + " - " + a.getNumeAngajat() + " (" + a.getFunctieAngajat() + ")");
    }
}