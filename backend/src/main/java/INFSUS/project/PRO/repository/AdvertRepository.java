package INFSUS.project.PRO.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import INFSUS.project.PRO.models.Advert;

public interface AdvertRepository extends JpaRepository<Advert, Integer> {
    public List<Advert> findByCategoryId(int categoryId);

    public Optional<Advert> findById(int id);
}
