package org.example.miniprojet.services;

import org.example.miniprojet.DTO.ActivityDTO;
import org.example.miniprojet.DTO.AdminDTO;
import org.example.miniprojet.entities.Activity;
import org.example.miniprojet.entities.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ActivityService {

    List<Activity> getAllActivities();
    Activity getActivityById(Long id);
    Activity createActivity(ActivityDTO activity, MultipartFile file) throws IOException;
    Activity updateActivity(Long id, ActivityDTO activity, MultipartFile file) throws IOException;
    void deleteActivity(Long id);
    Activity addContactToActivity(String username, Long idActivity);
    Activity removeContactFromActivity(String username, Long idActivity);

}
