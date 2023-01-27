package it.uniroma3.siw.esame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.esame.model.Citta;
import it.uniroma3.siw.esame.model.Hotel;
import it.uniroma3.siw.esame.model.Servizio;
import it.uniroma3.siw.esame.model.Stanza;
import it.uniroma3.siw.esame.service.CittaService;
import it.uniroma3.siw.esame.service.HotelService;

@Controller
public class FacadeController {
	
	@Autowired 
	private HotelService hotelService;
	
	@Autowired 
	private CittaService cittaService;
	
	@Autowired 
	private AuthenticationController authenticationController;

	@GetMapping("/admin/creaOggettiTest")
	public String creaOggettiTest(Model model) {
		Hotel h1 = new Hotel();
		Hotel h2 = new Hotel();
		Hotel h3 = new Hotel();
		h1.setNome("Hotel Roma");
		h2.setNome("Principe");
		h3.setNome("Milano Residence");
		h1.setDescrizione("Hotel nel centro di Roma");
		h2.setDescrizione("Hotel di lusso");
		h3.setDescrizione("Hotel a 4 stelle nei quartieri di Milano");
		h1.setStelle(4);
		h2.setStelle(5);
		h3.setStelle(4);
		
		Stanza s1 = new Stanza();
		Stanza s2 = new Stanza();
		Stanza s3 = new Stanza();
		Stanza s4 = new Stanza();
		Stanza s5 = new Stanza();
		Stanza s6 = new Stanza();

		s1.setNome("Doppia");
		s1.setDescrizione("Stanza doppia vista Colosseo");
		s1.setNumero(32);
		s1.setPrezzoNotte(100f);
		s2.setNome("Singola");
		s2.setDescrizione("Stanza singola");
		s2.setNumero(12);
		s2.setPrezzoNotte(60f);
		s3.setNome("Attico");
		s3.setDescrizione("Stanza doppia di lusso situata nell'attico");
		s3.setNumero(125);
		s3.setPrezzoNotte(350f);
		s4.setNome("Doppia");
		s4.setDescrizione("Stanza doppia dall'arredamento vintage");
		s4.setNumero(35);
		s4.setPrezzoNotte(200f);
		s5.setNome("Singola");
		s5.setDescrizione("Stanza singola");
		s5.setNumero(7);
		s5.setPrezzoNotte(70f);
		s6.setNome("Tripla");
		s6.setDescrizione("Stanza con tre letti singoli");
		s6.setNumero(15);
		s6.setPrezzoNotte(150f);
		

		Servizio sv1 = new Servizio();
		Servizio sv2 = new Servizio();
		Servizio sv3 = new Servizio();
		Servizio sv4 = new Servizio();
		Servizio sv5 = new Servizio();
		Servizio sv6 = new Servizio();
		Servizio sv7 = new Servizio();
		Servizio sv8 = new Servizio();
		Servizio sv9 = new Servizio();
		Servizio sv10 = new Servizio();

		sv1.setNome("Aria Condizionata");
		sv1.setDescrizione("Condizionatore in camera");
		sv2.setNome("Televisione");
		sv2.setDescrizione("Televisione in camera");
		sv3.setNome("WiFi");
		sv3.setDescrizione("Rete WiFi aperta");
		sv4.setNome("Servizio in Camera");
		sv4.setDescrizione("Servizio in camera h24");
		sv5.setNome("Doccia idromassaggio");
		sv5.setDescrizione("Doccia con getti idromassaggio");
		sv6.setNome("Parcheggio riservato");
		sv6.setDescrizione("Parcheggio sorvegliato e riservato al cliente");
		sv7.setNome("Frigo bar");
		sv7.setDescrizione("Frigo bar a disposizione del cliente");
		sv8.setNome("Aria Condizionata");
		sv8.setDescrizione("Condizionatore in camera");
		sv9.setNome("Televisione");
		sv9.setDescrizione("Televisione in camera");
		sv10.setNome("WiFi");
		sv10.setDescrizione("Rete WiFi aperta");

		s1.addServizio(sv1);
		s2.addServizio(sv2);
		s2.addServizio(sv3);
		s3.addServizio(sv4);
		s4.addServizio(sv5);
		s4.addServizio(sv6);
		s5.addServizio(sv7);
		s6.addServizio(sv8);
		s6.addServizio(sv9);
		s6.addServizio(sv10);

		h1.addStanza(s1);
		h1.addStanza(s2);
		h2.addStanza(s3);
		h2.addStanza(s4);
		h3.addStanza(s5);
		h3.addStanza(s6);
		
		System.out.println(h1.getStanze());
		
		Citta c1 = new Citta();
		c1.setNome("Roma");
		c1.setRegione("Lazio");
		Citta c2 = new Citta();
		c2.setNome("Genova");
		c2.setRegione("Liguria");
		Citta c3 = new Citta();
		c3.setNome("Milano");
		c3.setRegione("Lombardia");
		
		h1.setCitta(c1);
		h2.setCitta(c2);
		h3.setCitta(c3);
		
		cittaService.save(c1);
		cittaService.save(c2);
		cittaService.save(c3);
		
		hotelService.save(h1);
		hotelService.save(h2);
		hotelService.save(h3);

		return authenticationController.defaultAfterLogin(model);
	}
}
