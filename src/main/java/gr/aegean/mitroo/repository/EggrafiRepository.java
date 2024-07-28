package gr.aegean.mitroo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.aegean.mitroo.model.Eggrafi;

public interface EggrafiRepository extends JpaRepository<Eggrafi, String>{
	List<Eggrafi> findByAt(String at);
	List<Eggrafi> findByAfm(String afm);
	List<Eggrafi> findByLastname(String lastname);
	List<Eggrafi> findByAtAndLastname(String at, String lastname);
	List<Eggrafi> findByAtAndAfm(String at, String afm);
	List<Eggrafi> findByAfmAndLastname(String afm, String lastname);
	List<Eggrafi> findBySex(String sex);

}
