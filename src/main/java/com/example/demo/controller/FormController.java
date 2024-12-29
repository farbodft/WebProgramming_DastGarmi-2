package com.example.demo.controller;


import com.example.demo.Service.FormService;
import com.example.demo.model.Field;
import com.example.demo.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping
    public ResponseEntity<String> addForm(@RequestBody Form form) {
        if (form.getId() == null || form.getName() == null || form.getFields() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Form must have an id, a name, and some fields.");
        }
        for (Field field : form.getFields()) {
            if (!List.of("Text", "Number", "Boolean", "Date").contains(field.getType())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields can only be Text/Number/Boolean/Date");
            }
        }
        if (formService.getFormById(form.getId()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Form with this id already exists!");
        }
        Form createdForm = formService.addForm(form);
        if (createdForm != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New form added successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }

    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFormById(@PathVariable Long id) {
        Form existingForm = formService.getFormById(id);
        return existingForm != null ? ResponseEntity.ok(existingForm) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateForm(@PathVariable Long id, @RequestBody Form form) {
        if (form.getId() == null || form.getName() == null || form.getFields() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Form must have an id, a name, and some fields.");
        }
        if (!id.equals(form.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can not change the form's id!");
        }
        Form createdForm = formService.updateForm(id, form);
        if (createdForm != null) {
            return ResponseEntity.status(HttpStatus.OK).body("The form updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteForm(@PathVariable Long id) {
        if (!formService.deleteForm(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Form with given id deleted successfully.");
    }

    @GetMapping("/{id}/fields")
    public ResponseEntity<?> getFormFields(@PathVariable Long id) {
        if (formService.getFormById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(formService.getFormFields(id));
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<String> updateFormFields(@PathVariable Long id, @RequestBody List<Field> fields) {
        if (formService.getFormById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        if (formService.updateFormFields(id, fields) != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Form's fields updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<?> changePublicationStatus(@PathVariable Long id) {
        if (formService.getFormById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("form publication status updated");
    }

    @GetMapping("/published")
    public ResponseEntity<?> getPublishedForms() {
        List<Form> publishedForms = formService.getPublishedForms();
        if (publishedForms != null) {
            return ResponseEntity.status(HttpStatus.OK).body(publishedForms);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }
}
