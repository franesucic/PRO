package INFSUS.project.PRO.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import INFSUS.project.PRO.models.Advert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdvertRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;

    @Test
    public void testFindByCategoryId() {
        Advert advert1 = new Advert();
        advert1.setTitle("Advert 1");
        advert1.setDescription("Description 1");
        advert1.setPicture(new byte[] { 1, 2, 3 });
        advert1.setPrice(10.0);
        advert1.setUserId(1);
        advert1.setCategoryId(8);

        Advert advert2 = new Advert();
        advert2.setTitle("Advert 2");
        advert2.setDescription("Description 2");
        advert2.setPicture(new byte[] { 4, 5, 6 });
        advert2.setPrice(20.0);
        advert2.setUserId(2);
        advert2.setCategoryId(8);

        Advert advert3 = new Advert();
        advert3.setTitle("Advert 3");
        advert3.setDescription("Description 3");
        advert3.setPicture(new byte[] { 7, 8, 9 });
        advert3.setPrice(30.0);
        advert3.setUserId(3);
        advert3.setCategoryId(9);

        advertRepository.save(advert1);
        advertRepository.save(advert2);
        advertRepository.save(advert3);

        List<Advert> adverts = advertRepository.findByCategoryId(8);

        assertThat(adverts).hasSize(2).contains(advert1, advert2).doesNotContain(advert3);
    }

    @Test
    public void testFindById() {
        Advert advert = new Advert();
        advert.setTitle("Advert");
        advert.setDescription("Description");
        advert.setPicture(new byte[] { 1, 2, 3 });
        advert.setPrice(10.0);
        advert.setUserId(1);
        advert.setCategoryId(8);

        Advert savedAdvert = advertRepository.save(advert);

        Advert foundAdvert = advertRepository.findById(savedAdvert.getId()).orElse(null);

        assertThat(foundAdvert).isNotNull();
        assertThat(foundAdvert.getTitle()).isEqualTo(advert.getTitle());
        assertThat(foundAdvert.getDescription()).isEqualTo(advert.getDescription());
        assertThat(foundAdvert.getPicture()).isEqualTo(advert.getPicture());
        assertThat(foundAdvert.getPrice()).isEqualTo(advert.getPrice());
        assertThat(foundAdvert.getUserId()).isEqualTo(advert.getUserId());
        assertThat(foundAdvert.getCategoryId()).isEqualTo(advert.getCategoryId());
    }
}
