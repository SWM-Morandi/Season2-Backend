package kr.co.morandi.backend.domain.contenttype.customdefense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomDefenseRepository extends JpaRepository<CustomDefense, Long> {
    List<CustomDefense> findAllByVisibility(Visibility visibility);
}
