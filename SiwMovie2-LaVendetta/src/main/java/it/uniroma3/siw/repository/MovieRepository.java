package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	public List<Movie> findByYear(int year);

	public List<Movie> findByTitle(String title);

	public boolean existsByTitleAndYear(String title, int year);	

	public List<Movie> findByTitleAndYear(String title, int year);

}