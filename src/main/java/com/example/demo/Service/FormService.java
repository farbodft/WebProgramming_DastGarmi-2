package com.example.demo.Service;

import com.example.demo.Repository.FormRepository;
import com.example.demo.model.Field;
import com.example.demo.model.Form;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public Form addForm(Form form) {
        return formRepository.save(form);
    }

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Form getFormById(Long id) {
        return formRepository.findById(id).orElse(null);
    }

    public Form updateForm(Long id, Form form) {
        Optional<Form> existingForm = formRepository.findById(id);
        if (existingForm.isPresent()) {
            Form updatedForm = existingForm.get();
            updatedForm.setName(form.getName());
            updatedForm.setPublished(form.isPublished());
            updatedForm.setFields(form.getFields());
            return formRepository.save(form);
        } else {
            return null;
        }
    }

    public Boolean deleteForm(Long id) {
        if (formRepository.existsById(id)){
            formRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Field> getFormFields(Long id) {
        Form form = getFormById(id);
        if (form != null) {
            return form.getFields();
        } else {
            return null;
        }
    }

    public Form updateFormFields(Long id, List<Field> newFields) {
        Optional<Form> existingForm = formRepository.findById(id);
        if (existingForm.isPresent()) {
            Form updatedForm = existingForm.get();
            updatedForm.setFields(newFields);
            return formRepository.save(updatedForm);
        } else {
            return null;
        }
    }

    public Form changePublicationStatus(Long id) {
        Optional<Form> existingForm = formRepository.findById(id);
        if (existingForm.isPresent()) {
            Form form = existingForm.get();
            form.setPublished(!form.isPublished());
            return formRepository.save(form);
        } else {
            return null;
        }
    }

    public List<Form> getPublishedForms() {
        return formRepository.findByPublished(true);
    }
}
