package com.java.springBoot.app.Service;

import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Patron patron) {
        return patronRepository.save(patron);
    }


    public boolean deletePatron(Long id) {
        Optional<Patron>  patron=  patronRepository.findById(id);
        if (patron.isPresent()) {
        	patronRepository.delete(patron.get());
            return true;  
        } else {
            return false; 
        }
    }
}
