package org.example.miniprojet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.miniprojet.DTO.ActivityDTO;
import org.example.miniprojet.entities.Activity;
import org.example.miniprojet.services.ActivityService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@Slf4j
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/getAllActivities")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @GetMapping("/getActivity/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(activityService.getActivityById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/addActivity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addActivity(@RequestParam String activityJson, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ActivityDTO activity = objectMapper.readValue(activityJson, ActivityDTO.class);
            activityService.createActivity(activity, file);
            return ResponseEntity.ok("OK");
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/modifyActivity/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Activity> modifyActivity(@PathVariable Long id, @RequestParam String activityJson,
                                                   @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ActivityDTO activity = objectMapper.readValue(activityJson, ActivityDTO.class);
            return ResponseEntity.ok(activityService.updateActivity(id, activity, photo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deleteActivity/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok("Activity deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addContactToActivity/{idActivity}")
    public ResponseEntity<Activity> addContactToActivity(@RequestParam String username, @PathVariable Long idActivity) {
        try {
            return ResponseEntity.ok(activityService.addContactToActivity(username, idActivity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/removeContactFromActivity/{idActivity}")
    public ResponseEntity<Activity> removeContactFromActivity(@RequestParam String username, @PathVariable Long idActivity) {
        try {
            return ResponseEntity.ok(activityService.removeContactFromActivity(username, idActivity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
