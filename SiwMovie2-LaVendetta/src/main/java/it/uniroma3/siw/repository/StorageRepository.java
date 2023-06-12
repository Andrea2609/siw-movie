package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Image;



public interface StorageRepository extends JpaRepository<Image, Long>{

    Optional<Image> findByName(String fileName);

}
