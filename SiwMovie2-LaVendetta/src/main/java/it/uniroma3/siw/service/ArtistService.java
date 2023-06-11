package it.uniroma3.siw.service;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Rewiew;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RewiewRepository;

@Service
public class ArtistService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired 
    private CredentialsService credentialsService;
    @Autowired 
    private RewiewRepository rewiewRepository;

    @Transactional
    public Artist findById(Long id){ 
        Artist artist = this.artistRepository.findById(id).get();
        return artist;
    }

    @Transactional
    public void saveAndFlush(Artist toBeDeleted){
        this.artistRepository.saveAndFlush(toBeDeleted);
    }

    @Transactional
    public void save(Artist toBeDeleted){
        this.artistRepository.save(toBeDeleted);
    }

     @Transactional
    public void findAll(){
        this.artistRepository.findAll();
    }

    @Transactional
    public void deleteById(Long actorId){
        this.artistRepository.deleteById(actorId);
    }

    @Transactional
    public boolean existsByNameAndSurname(Artist artist){
        Boolean existsByNameAndSurname = artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname());
        return existsByNameAndSurname;
    }

    @Transactional
    public void removeArtist(Long actorId){
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
    }
}
