package com.example.demo.repository;

import com.example.demo.model.TutorialDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialDetailRepository extends JpaRepository<TutorialDetail, Long> {
}
