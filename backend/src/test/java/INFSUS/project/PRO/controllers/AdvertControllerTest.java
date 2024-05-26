package INFSUS.project.PRO.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import INFSUS.project.PRO.models.Advert;
import INFSUS.project.PRO.models.AdvertRequest;
import INFSUS.project.PRO.models.Category;
import INFSUS.project.PRO.models.User;
import INFSUS.project.PRO.repository.AdvertRepository;
import INFSUS.project.PRO.repository.CategoryRepository;
import INFSUS.project.PRO.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdvertController.class)
public class AdvertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertRepository advertRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindAllAdverts() throws Exception {
        mockMvc.perform(get("/advert/findAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Shoes");

        when(categoryRepository.findByName(any(String.class))).thenReturn(category);

        mockMvc.perform(get("/advert/findByCategory")
                .param("categoryName", "Shoes"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAdvert() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("korisnik1");

        Category category = new Category();
        category.setId(1);
        category.setName("Cipele");

        when(userRepository.findByUsername(any(String.class))).thenReturn(java.util.Optional.of(user));
        when(categoryRepository.findByName(any(String.class))).thenReturn(category);

        AdvertRequest advertRequest = new AdvertRequest();
        advertRequest.setTitle("Prodajem cipele");
        advertRequest.setDescription("Nove, nikad nošene");
        advertRequest.setPicture("nopic".getBytes());
        advertRequest.setPrice(3.05);
        advertRequest.setUser("korisnik1");
        advertRequest.setCategoryName("Cipele");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(advertRequest);

        mockMvc.perform(post("/advert/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditAdvert() throws Exception {
        Advert advert = new Advert();
        advert.setId(1);
        advert.setTitle("Old Title");

        User user = new User();
        user.setId(1);
        user.setUsername("korisnik1");

        Category category = new Category();
        category.setId(1);
        category.setName("Cipele");

        when(advertRepository.findById(1)).thenReturn(Optional.of(advert));
        when(userRepository.findByUsername("korisnik1")).thenReturn(Optional.of(user));
        when(categoryRepository.findByName("Cipele")).thenReturn(category);

        AdvertRequest advertRequest = new AdvertRequest();
        advertRequest.setTitle("New Title");
        advertRequest.setDescription("Nove, nikad nošene");
        advertRequest.setPicture("nopic".getBytes());
        advertRequest.setPrice(3.05);
        advertRequest.setUser("korisnik1");
        advertRequest.setCategoryName("Cipele");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(advertRequest);

        mockMvc.perform(put("/advert/edit")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAdvert() throws Exception {
        mockMvc.perform(delete("/advert/delete")
                .param("id", "1"))
                .andExpect(status().isOk());
    }
}
