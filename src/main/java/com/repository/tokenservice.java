package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Token;

@Repository
public interface tokenservice extends JpaRepository<Token,Integer> {
	Token findBytoken(String t);
}
