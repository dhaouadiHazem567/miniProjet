package org.example.miniprojet.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.miniprojet.DTO.ActivityDTO;
import org.example.miniprojet.entities.Activity;
import org.example.miniprojet.entities.Contact;
import org.example.miniprojet.repositories.ActivityRepository;
import org.example.miniprojet.repositories.ContactRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity createActivity(ActivityDTO activity, MultipartFile file) throws IOException {

        Activity activityToSave = new Activity();
        activityToSave.setDate(activity.date());
        activityToSave.setTypeActivity(activity.typeActivity());
        activityToSave.setSubject(activity.subject());
        activityToSave.setNotes(activity.notes());

        if(file != null && !file.isEmpty())
            activityToSave.setDocumentPath(FileManager.saveFile(file, activity.date(), activity.typeActivity()));

        return activityRepository.save(activityToSave);
    }

    @Override
    public Activity updateActivity(Long id, ActivityDTO activity, MultipartFile file) throws IOException {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if(optionalActivity.isEmpty())
            return null;

        Activity activityToUpdate = optionalActivity.get();
        activityToUpdate.setDate(activity.date());
        activityToUpdate.setTypeActivity(activity.typeActivity());
        activityToUpdate.setSubject(activity.subject());
        activityToUpdate.setNotes(activity.notes());

        if(file != null && !file.isEmpty())
            activityToUpdate.setDocumentPath(FileManager.saveFile(file, activity.date(), activity.typeActivity()));

        return activityRepository.save(activityToUpdate);
    }

    @Override
    public void deleteActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if(optionalActivity.isPresent())
            activityRepository.deleteById(id);
    }

    @Override
    public Activity addContactToActivity(String username, Long idActivity) {

        Optional<Activity> optionalActivity = activityRepository.findById(idActivity);
        if(optionalActivity.isEmpty())
            return null;

        Optional<Contact> optionalContact = contactRepository.findContactByUsername(username);
        if(optionalContact.isEmpty())
            return null;

        Activity activity = optionalActivity.get();
        Contact contact = optionalContact.get();

        activity.getContacts().add(contact);
        return activityRepository.save(activity);
    }

    @Override
    public Activity removeContactFromActivity(String username, Long idActivity) {
        Optional<Activity> optionalActivity = activityRepository.findById(idActivity);
        if(optionalActivity.isEmpty())
            return null;

        Optional<Contact> optionalContact = contactRepository.findContactByUsername(username);
        if(optionalContact.isEmpty())
            return null;

        if(!activityRepository.existsByIdAndContactsContains(idActivity, optionalContact.get()))
            return null;

        Activity activity = optionalActivity.get();
        Contact contact = optionalContact.get();

        activity.getContacts().remove(contact);
        return activityRepository.save(activity);
    }
}
