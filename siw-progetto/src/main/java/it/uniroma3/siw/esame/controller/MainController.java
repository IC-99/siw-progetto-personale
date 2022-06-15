package it.uniroma3.siw.esame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.esame.model.Credentials;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.service.CredentialsService;

@Controller
public class MainController {

	@Autowired
	private CredentialsService credentialsService;

	private boolean haAdmin = false;

	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {

		if(!haAdmin) {
			this.creaAdmin("Ivan", "Carlini", "ivan_admin", "admin");
		}

		return "index";
	}
	
	private void creaAdmin(String nome, String cognome, String username, String password) {
		
		User admin = new User();
		admin.setNome(nome);
		admin.setCognome(cognome);

		Credentials adminCredentials = new Credentials();
		adminCredentials.setPassword(password);
		adminCredentials.setRole(Credentials.ADMIN_ROLE);
		adminCredentials.setUsername(username);
		adminCredentials.setUser(admin);

		credentialsService.saveCredentials(adminCredentials);
		haAdmin = true;
	}

}