package INFSUS.project.PRO.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import INFSUS.project.PRO.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
