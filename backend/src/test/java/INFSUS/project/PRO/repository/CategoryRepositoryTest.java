package INFSUS.project.PRO.repository;

import static org.assertj.core.api.Assertions.assertThat;

import INFSUS.project.PRO.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindByName() {
        Category category = new Category();
        category.setName("Torbice");

        categoryRepository.save(category);

        Category foundCategory = categoryRepository.findByName("Torbice");

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Torbice");
    }

    @Test
    public void testFindByNameNotFound() {
        Category foundCategory = categoryRepository.findByName("NonExistentCategory");

        assertThat(foundCategory).isNull();
    }
}
