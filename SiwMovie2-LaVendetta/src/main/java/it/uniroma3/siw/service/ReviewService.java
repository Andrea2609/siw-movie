package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RewiewRepository;

@Service
public class ReviewService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired RewiewRepository rewiewRepository;


    @Transactional
    public boolean HasReviewed(User currentUser,Movie movie){
        Boolean hasReviewed =  this.rewiewRepository.existsByUserAndMovie(currentUser, movie);
        return hasReviewed;
    }
    
}
