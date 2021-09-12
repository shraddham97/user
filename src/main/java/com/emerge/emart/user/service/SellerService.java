package com.emerge.emart.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerge.emart.user.dto.LoginDTO;
import com.emerge.emart.user.dto.SellerDTO;
import com.emerge.emart.user.entity.Seller;
import com.emerge.emart.user.exception.EmergeEmartException;
import com.emerge.emart.user.repository.SellerRepository;

@Service
@Transactional
public class SellerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SellerRepository sellerrepo;

	// Seller register
	public void saveSeller(SellerDTO sellerDTO) throws EmergeEmartException {
		logger.info("Registration request for seller with data {}", sellerDTO);
		Seller seller = sellerDTO.createSeller();
		sellerrepo.save(seller);
	}

	// Get all sellers
	public List<SellerDTO> getAllSeller() throws EmergeEmartException {

		Iterable<Seller> sellers = sellerrepo.findAll();
		List<SellerDTO> sellerDTOs = new ArrayList<>();

		sellers.forEach(seller -> {
			SellerDTO sellerDTO = SellerDTO.valueOf(seller);
			sellerDTOs.add(sellerDTO);
		});
		if (sellerDTOs.isEmpty())
			throw new EmergeEmartException("Service.SELLERS_NOT_FOUND");
		logger.info("Seller Details : {}", sellerDTOs);
		return sellerDTOs;
	}

	// Seller Login
	public boolean login(LoginDTO loginDTO) throws EmergeEmartException {
		logger.info("Login request for selelr {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
		Seller sell = sellerrepo.findByEmail(loginDTO.getEmail());
		if (sell != null && sell.getPassword().equals(loginDTO.getPassword())) {
			return true;
		} else {
			throw new EmergeEmartException("Service.DETAILS_NOT_FOUND");
		}
	}

	// Delete Seller
	public void deleteSeller(String sellerid) throws EmergeEmartException {
		Optional<Seller> seller = sellerrepo.findById(sellerid);
		seller.orElseThrow(() -> new EmergeEmartException("Service.SELLERS_NOT_FOUND"));
		sellerrepo.deleteById(sellerid);
	}
}
