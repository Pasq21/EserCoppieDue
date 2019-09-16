package it.lefosse.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lefosse.connection.GestioneDb;
import it.lefosse.object.DistrictPop;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String main(Model model) {

		return "intro";
	}

	@PostMapping("/primapagina")
	public String hello(@RequestParam(name = "citta") String citta, Model model)
			throws ClassNotFoundException, SQLException {

		boolean flag = GestioneDb.checkCity(citta);
		if (flag) {
			List<String> listaCitta = new ArrayList<String>();
			listaCitta.addAll(GestioneDb.getCittaFromCode(citta));
			model.addAttribute("lista", listaCitta);
			model.addAttribute("citta", citta);
			return "scelta";
		} else {
			return "cittaNonTrovata";
		}
	}

	@PostMapping("/secondapagina")
	public String hellodue(@RequestParam(name = "scelta") String scelta, @RequestParam(name = "citta") String citta,
			Model model) throws ClassNotFoundException, SQLException {

		if ("1".equals(scelta)) {
			DistrictPop oggetto = GestioneDb.getDistrictAndPopulation(citta);
			List<String> codes = new ArrayList<String>();
			codes.addAll(GestioneDb.getAllCodes());
			String district = oggetto.getDistrict();
			int population = oggetto.getPopulation();
			model.addAttribute("listacodici", codes);
			model.addAttribute("district", district);
			model.addAttribute("population", population);
			model.addAttribute("citta", citta);
			model.addAttribute("cittavecchia", citta);
			return "modifica";
		} else {
			GestioneDb.deleteCity(citta);
			return "elimina";
		}
	}

	@PostMapping("/terzapagina")
	public String hellotre(@RequestParam(name = "citta") String citta, @RequestParam(name = "district") String district,
			@RequestParam(name = "codice") String codice, @RequestParam(name = "population") int population,
			@RequestParam(name = "cittavecchia") String cittavecchia, Model model) throws ClassNotFoundException, SQLException {

		GestioneDb.modificaNelDb(citta, district, codice, cittavecchia, population);
		return "success";

	}

}