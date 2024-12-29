package com.example.demo.Repository;

import com.example.demo.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends MongoRepository<Form, Long> {
    List<Form> findByPublished(boolean published);
}
