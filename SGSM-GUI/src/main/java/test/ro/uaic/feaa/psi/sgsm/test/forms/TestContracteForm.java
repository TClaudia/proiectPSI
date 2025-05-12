package test.ro.uaic.feaa.psi.sgsm.test.forms;

import java.util.Date;
import java.util.List;

import org.junit.Assert;

import ro.uaic.feaa.psi.sgsm.model.entities.ActAditional;
import ro.uaic.feaa.psi.sgsm.model.entities.ActeAferenteContract;
import ro.uaic.feaa.psi.sgsm.model.entities.Contract;
import ro.uaic.feaa.psi.sgsm.model.entities.FormularReziliere;
import ro.uaic.feaa.psi.sgsm.model.entities.ModificareContract;
import src.ro.uaic.feaa.psi.sgsm.forms.ContracteFormCtrl;
import src.ro.uaic.feaa.psi.sgsm.forms.ContracteFormData;

public class TestContracteForm {

	public static void main(String[] args) {
		// Instantiere controller formular
		ContracteFormCtrl form = new ContracteFormCtrl();

		// Asigurăm existența datelor de test
		asiguraDateTest();

		// Verificări inițiale: listele trebuie să aibă o sursă de date validă
		verificariInitiale(form);

		// 1. Adaugă contract nou
		form.contractNou();

		// Verifică tipul de contract creat
		Assert.assertTrue("Contractul nou creat nu există",
				form.getFormData().getContractCurent() != null);

		// 1.4 simulează furnizor selectat (primul din listă)
		form.getFormData().setFurnizorSelectat(
				form.getFormData().getListaFurnizorilor().get(0));

		// Verifică furnizor
		Assert.assertTrue("Furnizor atașat contractului incorect",
				form.getFormData().getContractCurent().getFurnizor().equals(
						form.getFormData().getListaFurnizorilor().get(0)));

		// Afișăm detalii despre furnizorul selectat
		Furnizor furnizorSelectat = form.getFormData().getFurnizorSelectat();
		System.out.println("Furnizor selectat: " + furnizorSelectat.getNumeFurnizor() +
				", Telefon: " + furnizorSelectat.getNumarTelefon() +
				", Email: " + furnizorSelectat.getEmail());

		// Completează date contract
		form.getFormData().getContractCurent().setIdDocument("C001");
		form.getFormData().getContractCurent().setNumarContract("C001/2025");
		form.getFormData().getContractCurent().setDataDocument(new Date());
		form.getFormData().getContractCurent().setDataModificare(new Date());
		form.getFormData().getContractCurent().setTipDocument("Contract");
		form.getFormData().getContractCurent().setTipContract(ContracteFormData.TIP_CONTRACT_FURNIZARE);
		form.getFormData().getContractCurent().setDurata("12 luni");
		form.getFormData().getContractCurent().setTermenePlata("30 zile");
		form.getFormData().getContractCurent().setTermeneLibrare("10 zile");
		form.getFormData().getContractCurent().setObservatii("Contract test");

		// Adaugă document aferent (factură)
		form.adaugaDocumentAferent();

		// Verifică lista de documente aferente
		Assert.assertTrue(
				"La acest moment contractul trebuie să aibă exact un document aferent.",
				form.getFormData().getContractCurent().getActeAferente().size() == 1);

		// Completează date document aferent
		ActeAferenteContract documentAferent = form.getFormData().getDocumentAferentSelectat();
		documentAferent.setIdDocument("F001");
		documentAferent.setTipDocument(ContracteFormData.TIP_DOCUMENT_FACTURA);
		documentAferent.setNumarActContract("F001/2025");
		documentAferent.setDataDocument(new Date());
		documentAferent.setModificariActe("Prima factură pentru contract");

		// Verifică modificări (ar trebui să existe una inițială cu status "Activ")
		Assert.assertTrue(
				"Contractul trebuie să aibă exact o modificare inițială.",
				form.getFormData().getContractCurent().getModificari().size() == 1);

		ModificareContract modificareInitiala = form.getFormData().getContractCurent().getModificari().get(0);
		Assert.assertTrue(
				"Modificarea inițială trebuie să aibă statusul 'Activ'.",
				modificareInitiala.getStatus().equals(ContracteFormData.STATUS_ACTIV));

		// Adaugă act adițional
		form.adaugaActAditional();

		// Verifică modificări (ar trebui să fie 2 acum)
		Assert.assertTrue(
				"Contractul trebuie să aibă exact două modificări.",
				form.getFormData().getContractCurent().getModificari().size() == 2);

		// Completează date act adițional
		ActAditional actAditional = (ActAditional) form.getFormData().getDocumentAferentSelectat();
		actAditional.setIdDocument("AA001");
		actAditional.setNumarActContract("AA001/2025");
		actAditional.setTipDocument(ContracteFormData.TIP_DOCUMENT_ACT_ADITIONAL);
		actAditional.setDataDocument(new Date());
		actAditional.setModificariActe("Modificare termeni contract");
		actAditional.setClauze("Modificare termen de plată");
		actAditional.setDescriere("Extindere termen de plată la 45 zile");

		// Verifică status modificare
		ModificareContract modificareActualizare = form.getFormData().getModificareContractSelectata();
		Assert.assertTrue(
				"Modificarea de actualizare trebuie să aibă statusul 'Modificat'.",
				modificareActualizare.getStatus().equals(ContracteFormData.STATUS_MODIFICAT));

		// Salvează contractul
		try {
			form.salveazaContract();
			System.out.println("Contract salvat cu succes!");
		} catch (Exception e) {
			System.out.println("Eroare la salvarea contractului: " + e.getMessage());
		}

		// Adaugă formular de reziliere
		form.adaugaFormularReziliere();

		// Completează date formular reziliere
		FormularReziliere formularReziliere = (FormularReziliere) form.getFormData().getDocumentAferentSelectat();
		formularReziliere.setIdDocument("R001");
		formularReziliere.setTipDocument(ContracteFormData.TIP_DOCUMENT_REZILIERE);
		formularReziliere.setNumarActContract("R001/2025");
		formularReziliere.setDataDocument(new Date());
		formularReziliere.setModificariActe("Reziliere contract");
		formularReziliere.setMotivReziliere("Nerespectare termene de livrare");

		// Verifică status modificare
		ModificareContract modificareReziliere = form.getFormData().getModificareContractSelectata();
		Assert.assertTrue(
				"Modificarea de reziliere trebuie să aibă statusul 'Reziliat'.",
				modificareReziliere.getStatus().equals(ContracteFormData.STATUS_REZILIAT));

		// Completează datele pentru ModificareContract
		modificareReziliere.setIdDocument("MC001");
		modificareReziliere.setTipDocument("Modificare");
		modificareReziliere.setDataDocument(new Date());
		modificareReziliere.setModificariActe("Detalii despre rezilierea contractului");

		// Salvează contractul
		try {
			form.salveazaContract();
			System.out.println("Contract reziliat cu succes!");
		} catch (Exception e) {
			System.out.println("Eroare la rezilierea contractului: " + e.getMessage());
		}
	}

	/**
	 * Asigură existența datelor de test necesare
	 */
	private static void asiguraDateTest() {
		// Rulăm testele pentru a asigura existența datelor de bază în BD
		TestFurnizori.main(null);
		TestAngajati.main(null);
	}

	/**
	 * Verificări privind inițializarea corectă a formularului
	 */
	private static void verificariInitiale(ContracteFormCtrl form) {
		Assert.assertTrue(
				"BUG sau Nu există Furnizori pentru test. Rulați mai întâi testul TestFurnizori din proiectul SGSM-Model",
				form.getFormData().getListaFurnizorilor().size() > 0);

		// Verificăm lista de angajați
		Assert.assertTrue(
				"BUG sau Nu există Angajați pentru test. Rulați mai întâi testul TestAngajati din proiectul SGSM-Model",
				form.getFormData().getListaAngajati().size() > 0);

		// Verificăm tipurile de contracte
		Assert.assertTrue(
				"Lista de tipuri de contracte nu este inițializată",
				form.getFormData().getTipuriContract().size() > 0);

		// Verificăm tipurile de documente aferente
		Assert.assertTrue(
				"Lista de tipuri de documente aferente nu este inițializată",
				form.getFormData().getTipuriDocumenteAferente().size() > 0);

		// Verificăm statusurile contractelor
		Assert.assertTrue(
				"Lista de statusuri ale contractelor nu este inițializată",
				form.getFormData().getStatusuriContract().size() > 0);

		System.out.println("Verificări inițiale completate cu succes!");
	}
}