package com.example.samuraitravel.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }
    
    public Page<House> findAllHouses(Pageable pageable) {
		return houseRepository.findAll(pageable);
	}
    
    public Page<House> findByNameLike(String keyword, Pageable pageable) {
    	return houseRepository.findByNameLink("%" + keyword + "%", pageable);
    }
    
    public Optional<House> findHouseById(Integer id) {
		return houseRepository.findById(id);
	}
    
    @Transactional
    public void createHouse(HouseRegisterForm houseRegisterForm) {
    	House house = new House();
    	MultipartFile imageFile = houseRegisterForm.getImageFile();
    	
    	if(!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/images/" + hashedImageName);
			house.setImageName(hashedImageName);
			}
    	
    	house.setName(houseRegisterForm.getName());
		house.setDescription(houseRegisterForm.getDescription());
		house.setPrice(houseRegisterForm.getPrice());
		house.setCapacity(houseRegisterForm.getCapacity());
		house.setPostalCode(houseRegisterForm.getPostalCode());
		house.setAddress(houseRegisterForm.getAddress());
		house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
		
		houseRepository.save(house);
	}
    
    public String generateNewFileName(String fileName) {
    	String[] fileNameParts = fileName.split("\\.");
    	
    	for (int i = 0; i < fileNameParts.length - 1; i++) {
			fileNameParts[i] = UUID.randomUUID().toString();
		}
    	
    	String hashedFileName = String.join(".", fileNameParts);
    	
    	return hashedFileName;
    }
    
    public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}