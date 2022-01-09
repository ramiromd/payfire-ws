package com.ramiromd.payfire.unit.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramiromd.payfire.entities.Category;
import com.ramiromd.payfire.repository.CategoryRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void should_find_no_categories_if_repository_is_empty() {
        List<Category> categories = this.repository.findAll();
        assertTrue(categories.isEmpty());
    }

    @Test
    public void should_store_a_category() {

        Category aCategory = new Category("Test category", "test-category", "A test category ...");
        this.repository.save(aCategory);
        
        assertNotNull(aCategory.getId());
        assertEquals("Test category", aCategory.getName());
        assertEquals("test-category", aCategory.getSlug());
        assertEquals("A test category ...", aCategory.getDescription());
        assertNotNull(aCategory.getCreatedAt());
        assertNotNull(aCategory.getUpdatedAt());
        assertNull(aCategory.getDeletedAt());
    }
    
    @Test
    public void should_not_store_a_dupe_category() {

        Category aCategory = new Category("Test category", "test-category", "A test category ...");
        Category otherCategory = new Category("Test category", "test-category");
        this.repository.save(aCategory);
        assertThrows(DataIntegrityViolationException.class, () -> {
            this.repository.save(otherCategory);
        });
    }
    
    @Test
    public void should_find_all_tutorials() {
        
        List<Category> result;
        Category electric = new Category("Distribuidora de energía eléctrica", "energy-light");
        Category gas = new Category("Distribuidora de gas", "energy-gas");
        Category isp = new Category("Proveedores de servicios de intenet.", "isp");
        
        this.repository.save(electric);
        this.repository.save(gas);
        this.repository.save(isp);
        
        result = this.repository.findAll();
        
        assertEquals(3, result.size());
        assertTrue(result.contains(electric));
        assertTrue(result.contains(gas));
        assertTrue(result.contains(isp));
    }
    
    @Test
    public void should_find_tutorial_by_id() {
        
        Category aCategory;
        Category electric = new Category("Distribuidora de energía eléctrica", "energy-light");
        Category gas = new Category("Distribuidora de gas", "energy-gas");
        
        this.repository.save(electric);
        this.repository.save(gas);
        
        aCategory = this.repository.findById(electric.getId()).get();
        
        assertEquals(electric, aCategory);
    }
    
    @Test
    public void should_find_a_category_by_name_containing_string() {
        
        List<Category> result;
        Category electric = new Category("Distribuidora de energía eléctrica", "energy-light");
        Category gas = new Category("Distribuidora de gas", "energy-gas");
        Category isp = new Category("Proveedores de servicios de intenet.", "isp");
        
        this.repository.save(electric);
        this.repository.save(gas);
        this.repository.save(isp);
        
        result = this.repository.findByNameContaining("servicios");
        
        assertEquals(1, result.size());
        assertEquals(isp, result.get(0));
    }
    
    @Test
    public void should_update_a_category() {

        Category electric = new Category("Distribuidora de energía eléctrica", "energy-light", "Some description ...");
        this.entityManager.persist(electric);
        
        Category gas = new Category("Distribuidora de gas", "energy-gas");
        this.entityManager.persist(gas);
        
        
        long originalUpdatedAt = electric.getUpdatedAt().getTime();
        String updatedName = "Updated";
        String updatedSlug = "updated";
        String updatedDescription = "Updated ...";
        
        
        electric.setName(updatedName);
        electric.setSlug(updatedSlug);
        electric.setDescription(updatedDescription);
        
        this.repository.save(electric);
        
        Category checkCategory = this.repository.findById(electric.getId()).get();
        
        assertEquals(electric.getId(), checkCategory.getId());
        assertEquals(updatedName, checkCategory.getName());
        assertEquals(updatedSlug, checkCategory.getSlug());
        assertEquals(updatedDescription, checkCategory.getDescription());
        assertEquals(electric.getCreatedAt(), checkCategory.getCreatedAt());
        
        // TODO: Check with a fake time library ...
        //assertTrue(originalUpdatedAt < checkCategory.getUpdatedAt().getTime());   
    }
}
