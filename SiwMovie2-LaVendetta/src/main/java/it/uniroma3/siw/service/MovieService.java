package it.uniroma3.siw.service;

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
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired 
    private RewiewRepository rewiewRepository;

    @Transactional
    public Movie setDirectorToMovie(Long directorId, Long movieId){
        Artist director = this.artistRepository.findById(directorId).get();
		Movie movie = this.movieRepository.findById(movieId).get();
		movie.setDirector(director);
		this.movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public Movie findById(Long movieId){
        Movie movie = movieRepository.findById(movieId).get();
        return movie;
    }

    @Transactional
    public void removeMovie(Long movieId){
        Movie toBeDeleted = this.movieRepository.findById(movieId).get();
            
            for(Artist artist : toBeDeleted.getActors()){
                artist.getActorOf().remove(toBeDeleted);
                artistRepository.saveAndFlush(artist);
            }

            toBeDeleted.setDirector(null);
            this.movieRepository.saveAndFlush(toBeDeleted);
            this.movieRepository.deleteById(movieId);
    }

    @Transactional
    public void removeReview(Long reviewId){
        Rewiew toBeDeleted = this.rewiewRepository.findById(reviewId).get();
            toBeDeleted.setMovie(null);
            toBeDeleted.setUser(null);
            this.rewiewRepository.saveAndFlush(toBeDeleted);
            this.rewiewRepository.deleteById(reviewId);
    }

    @Transactional
    public Movie addActorToMovie(Long movieId,Long actorId){
        Movie movie = this.movieRepository.findById(movieId).get();
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.add(actor);
		this.movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public Movie removeActorFromMovie(Long movieId, Long actorId){
        Movie movie = this.movieRepository.findById(movieId).get();
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.remove(actor);
		this.movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public Movie getMovie(Long id){
        Movie movie = movieRepository.findById(id).get();
        return movie;
    }

    @Transactional
    public Movie newReview(Long id, Rewiew rewiew, User currentUser){
        Movie movie = movieRepository.findById(id).get();
		rewiew.setMovie(movie);
		rewiew.setUser(currentUser);
		this.rewiewRepository.save(rewiew); 
        return movie;
    }

    @Transactional
    public void saveAndFlush(Movie movie){
        this.movieRepository.saveAndFlush(movie);
    }

    @Transactional
    public void deleteById(Long movieId){
        this.movieRepository.deleteById(movieId);
    }

    
}
