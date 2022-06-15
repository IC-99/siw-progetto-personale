package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Hotel;
import it.uniroma3.siw.esame.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Transactional //in scrittura
	public void save(Hotel hotel) {
		hotelRepository.save(hotel);
	}
	
	@Transactional
	public void deleteById(Long id) {
		hotelRepository.deleteById(id);
	}
	
	public Hotel findById(Long id) {
		return hotelRepository.findById(id).get();
	}
	
	public List<Hotel> findAll(){
		List<Hotel> hotels = new ArrayList<Hotel>();
		for(Hotel b : hotelRepository.findAll()) {
			hotels.add(b);
		}
		return hotels;
	}
	
	public boolean alreadyExists(Hotel hotel) {
		return this.hotelRepository.existsByNomeAndDescrizione(hotel.getNome(), hotel.getDescrizione());
	}
}