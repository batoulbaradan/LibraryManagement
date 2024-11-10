package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response.APIResponse;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public APIResponse<Patron> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return APIResponse.successList(patrons);
    }

    @GetMapping("/{id}")
    public APIResponse<Patron> getPatronById(@PathVariable Long id) {
        Optional<Patron> patron = patronService.getPatronById(id);
        return patron.map(APIResponse::success)
                     .orElseGet(() -> APIResponse.error(HttpStatus.NOT_FOUND.value(), "Patron not found"));
    }

    @PostMapping
    public APIResponse<Patron> createPatron(@Valid @RequestBody Patron patron) {
        Patron savedPatron = patronService.savePatron(patron);
        return APIResponse.success(savedPatron);
    }

    @PutMapping("/{id}")
    public APIResponse<Patron> updatePatron(@Valid @PathVariable Long id, @RequestBody Patron patronDetails) {
        Optional<Patron> patron = patronService.getPatronById(id);
        if (patron.isPresent()) {
            patronDetails.setId(id);
            Patron updatedPatron = patronService.updatePatron(patronDetails);
            return APIResponse.success(updatedPatron);
        } else {
            return APIResponse.error(HttpStatus.NOT_FOUND.value(), "Patron not found");
        }
    }

    @DeleteMapping("/{id}")
    public APIResponse<Void> deletePatron(@PathVariable Long id) {
        boolean isDeleted = patronService.deletePatron(id);
        if (isDeleted) {
            return APIResponse.success(null); 
        } else {
            return APIResponse.error(HttpStatus.NOT_FOUND.value(), "Patron not found");
        }
    }
}
