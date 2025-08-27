package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {
	// ここに必要なメソッドを追加できます
	// 例えば、特定の条件でハウスを検索するメソッドなど

}
