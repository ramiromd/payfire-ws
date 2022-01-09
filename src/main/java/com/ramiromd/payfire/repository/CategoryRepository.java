package com.ramiromd.payfire.repository;

import org.springframework.stereotype.Repository;

import com.ramiromd.payfire.entities.Category;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByNameContaining(String title);
}
