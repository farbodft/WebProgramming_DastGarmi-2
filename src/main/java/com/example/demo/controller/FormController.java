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

    // CRUD operations:
    // Post request to add a new form
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
        if (!formService.validateFieldIds(form.getFields())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The given field ids already exist.");
        }
        Form createdForm = formService.addForm(form);
        if (createdForm != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New form added successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }

    // Get request to get all forms
    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    // Get request to get a form by its id
    @GetMapping("/{id}")
    public ResponseEntity<?> getFormById(@PathVariable Long id) {
        Form existingForm = formService.getFormById(id);
        return existingForm != null ? ResponseEntity.ok(existingForm) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
    }

    // Put request to update a from by its id
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

    // Delete request to delete a form
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteForm(@PathVariable Long id) {
        if (!formService.deleteForm(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Form with given id deleted successfully.");
    }

    // Get request to get fields of a specific form
    @GetMapping("/{id}/fields")
    public ResponseEntity<?> getFormFields(@PathVariable Long id) {
        if (formService.getFormById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(formService.getFormFields(id));
    }

    // Put request to update a form's fields
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

    // Post request to change a form's publication status
    @PostMapping("/{id}/publish")
    public ResponseEntity<?> changePublicationStatus(@PathVariable Long id) {
        if (formService.getFormById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Form with given id does not exist!");
        }
        formService.changePublicationStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body("form publication status updated");
    }

    // Get request to get published forms
    @GetMapping("/published")
    public ResponseEntity<?> getPublishedForms() {
        List<Form> publishedForms = formService.getPublishedForms();
        if (publishedForms != null) {
            return ResponseEntity.status(HttpStatus.OK).body(publishedForms);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred please try again!");
    }
}
