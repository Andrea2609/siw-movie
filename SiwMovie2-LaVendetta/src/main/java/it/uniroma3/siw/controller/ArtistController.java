package it.uniroma3.siw.controller;

import java.util.Collection;
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

@Controller
public class ArtistController {
	
	@Autowired 
	private ArtistRepository artistRepository;

	@Autowired
	private MovieRepository movieRepository;

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
		Artist toBeDeleted = this.artistRepository.findById(actorId).get();
		
		for(Movie movie : toBeDeleted.getActorOf()){
			movie.getActors().remove(toBeDeleted);
			movieRepository.saveAndFlush(movie);
		}

		for(Movie movie : toBeDeleted.getDirectorOf()){
			if(movie.getDirector() == toBeDeleted){
				movie.setDirector(null);
				movieRepository.saveAndFlush(movie);
			}
		}
		toBeDeleted.setDirectorOf(Collections.emptyList());
		toBeDeleted.setActorOf(Collections.emptySet());
		this.artistRepository.saveAndFlush(toBeDeleted);
		this.artistRepository.deleteById(actorId);
		
		return "redirect:/admin/artist";
	}
	
	@PostMapping("/admin/artist")
	public String newArtist(@ModelAttribute Artist artist, Model model) {
		if (!artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname())) {
			this.artistRepository.save(artist); 
			model.addAttribute("artist", artist);
			return "artist.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo artista esiste già");
			return "admin/formNewArtist.html"; 
		}
	}

	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable Long id, Model model) {
		model.addAttribute("artist", this.artistRepository.findById(id).get());
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
