package it.uniroma3.siw.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;

@Controller
public class ArtistController {
	
	@Autowired
	private ArtistService artistService;
	@Autowired 
	private MovieService movieService;
	@Autowired 
	private ArtistRepository artistRepository;

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
	public String newArtist(@ModelAttribute Artist artist, Model model) {
		if (!this.artistService.existsByNameAndSurname(artist)) {
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
}
