package INFSUS.project.PRO.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import INFSUS.project.PRO.models.Advert;
import INFSUS.project.PRO.models.AdvertRequest;
import INFSUS.project.PRO.models.Category;
import INFSUS.project.PRO.models.User;
import INFSUS.project.PRO.repository.AdvertRepository;
import INFSUS.project.PRO.repository.CategoryRepository;
import INFSUS.project.PRO.repository.UserRepository;
import INFSUS.project.PRO.services.RefactorString;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllAdverts() {
        return ResponseEntity.ok(advertRepository.findAll());
    }

    @GetMapping("/findByCategory")
    public ResponseEntity<?> findByCategory(@RequestParam String categoryName) {
        if (categoryName.isEmpty()) {
            return ResponseEntity.badRequest().body("Category name is empty.");
        }

        String categoryNameModified = RefactorString.refactor(categoryName);

        return ResponseEntity
                .ok(advertRepository.findByCategoryId(categoryRepository.findByName(categoryNameModified).getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdvert(@RequestBody AdvertRequest advertRequest) {
        User user = userRepository.findByUsername(advertRequest.getUser()).orElse(null);
        Category category = categoryRepository.findByName(RefactorString.refactor(advertRequest.getCategoryName()));

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        } else if (category == null) {
            return ResponseEntity.badRequest().body("Category not found.");
        }

        Advert newAdvert = new Advert(advertRequest.getTitle(), advertRequest.getDescription(),
                advertRequest.getPicture(),
                advertRequest.getPrice(), user.getId(),
                category.getId());

        return ResponseEntity.ok(advertRepository.save(newAdvert));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editAdvert(@RequestParam int id, @RequestBody AdvertRequest advertRequest) {
        Optional<Advert> optionalAdvert = advertRepository.findById(id);

        if (!optionalAdvert.isPresent()) {
            return ResponseEntity.badRequest().body("Advert not found.");
        }

        Advert advert = optionalAdvert.get();
        User user = userRepository.findByUsername(advertRequest.getUser()).orElse(null);
        Category category = categoryRepository.findByName(RefactorString.refactor(advertRequest.getCategoryName()));

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        } else if (category == null) {
            return ResponseEntity.badRequest().body("Category not found.");
        }

        advert.setTitle(advertRequest.getTitle());
        advert.setDescription(advertRequest.getDescription());
        advert.setPicture(advertRequest.getPicture());
        advert.setPrice(advertRequest.getPrice());
        advert.setUserId(user.getId());
        advert.setCategoryId(category.getId());

        return ResponseEntity.ok(advertRepository.save(advert));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAdvert(@RequestParam int id) {
        advertRepository.deleteById(id);

        return ResponseEntity.ok("Advert deleted.");
    }

}
