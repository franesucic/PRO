package INFSUS.project.PRO.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import INFSUS.project.PRO.models.Advert;
import INFSUS.project.PRO.models.AdvertRequest;
import INFSUS.project.PRO.models.Category;
import INFSUS.project.PRO.models.User;
import INFSUS.project.PRO.repository.AdvertRepository;
import INFSUS.project.PRO.repository.CategoryRepository;
import INFSUS.project.PRO.repository.UserRepository;
import INFSUS.project.PRO.services.RefactorString;

public class AdvertControllerIntegrationTest {

    public class RefactorStringWrapper {
        public String refactor(String str) {
            return RefactorString.refactor(str);
        }
    }

    @Captor
    private ArgumentCaptor<Advert> advertCaptor;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefactorStringWrapper refactorStringWrapper;

    @InjectMocks
    private AdvertController advertController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        refactorStringWrapper = mock(RefactorStringWrapper.class);
    }

    @Test
    public void testAddAdvert() {
        AdvertRequest advertRequest = new AdvertRequest();
        advertRequest.setTitle("Test Advert");
        advertRequest.setDescription("Test Description");
        advertRequest.setPicture(new byte[] {});
        advertRequest.setPrice(10.0);
        advertRequest.setUser("testuser");
        advertRequest.setCategoryName("testCategory");

        User user = new User();
        user.setId(1);
        when(userRepository.findByUsername(advertRequest.getUser())).thenReturn(Optional.of(user));

        Category category = new Category();
        category.setId(1);
        when(categoryRepository.findByName("Testcategory")).thenReturn(category);

        when(refactorStringWrapper.refactor("testCategory")).thenReturn("Testcategory");

        advertController.addAdvert(advertRequest);
        verify(advertRepository).save(advertCaptor.capture());
        Advert savedAdvert = advertCaptor.getValue();

        assertEquals(new Advert("Test Advert", "Test Description", new byte[] {}, 10.0, 1, 1), savedAdvert);
    }

}
