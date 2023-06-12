package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.StorageService;

@Controller
public class ArtistController {
	
	@Autowired
	private ArtistService artistService;

	@Autowired 
	private ArtistRepository artistRepository;

	@Autowired
	private StorageService storageService;

	@GetMapping(value="/admin/formUpdateArtist/{id}")
	public String formUpdateArtist(@PathVariable Long id, Model model) {
		Artist artist = this.artistService.findById(id);
		model.addAttribute("artist", artist);
		return "admin/formUpdateArtist.html";
	}

	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist.html";
	}
	
	@GetMapping(value="/admin/indexArtist")
	public String indexArtist() {
		return "admin/indexArtist.html";
	}

	@GetMapping(value = "/admin/removeArtist/{actorId}")
	public String removeArtist(@PathVariable Long actorId) {
		artistService.removeArtist(actorId);		
		return "redirect:/admin/artists";
	}
	
	@PostMapping("/admin/artist")
	public String newArtist(@ModelAttribute Artist artist, @RequestParam ("foto") MultipartFile file , Model model) throws IOException {
		if (!this.artistService.existsByNameAndSurname(artist)) {
			
			if(!file.isEmpty()){
				Image fileName = storageService.uploadImage(file);                
				artist.setImage(fileName);             
			}
			
			this.artistService.save(artist);
			model.addAttribute("artist", artist);
			model.addAttribute("artists", this.artistRepository.findAll());
			return "redirect:/admin/artists";
		} else {
			model.addAttribute("messaggioErrore", "Questo artista esiste già");
			return "admin/formNewArtist.html"; 
		}
	}

	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable Long id, Model model) {
		Artist artist = this.artistService.findById(id);
		model.addAttribute("artist", artist);
		return "artist.html";
	}

	@GetMapping("/admin/artists")
	public String getArtistsAdmin(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "admin/artists.html";
	}

	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artists.html";
	}

	@GetMapping("/formSearchArtists")
	public String formSearchArtists() {
		return "formSearchArtists.html";
	}

	@PostMapping("/foundArtistsByName")
	public String searchArtistsByName(Model model, @RequestParam String name) {
		model.addAttribute("artists", this.artistService.findByName(name));
		return "foundArtists.html";
	}

	@PostMapping("/foundArtistsBySurname")
	public String searchArtistsBySurname(Model model, @RequestParam String surname) {
		model.addAttribute("artists", this.artistService.findBySurname(surname));
		return "foundArtists.html";
	}
}
