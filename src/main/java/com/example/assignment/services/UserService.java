package com.example.assignment.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.assignment.entity.UserEntity;
import com.example.assignment.exception.UserNotFoundException;
import com.example.assignment.repository.UserRepository;

import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.mapper.orm.Search;
import jakarta.persistence.EntityManager;

@Service
@Slf4j

public class UserService {

	@Autowired
    private  UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private EntityManager entityManager;

    @Value("${external.api.url}")
    private String externalApiUrl;
    
    @Retryable(maxAttempts = 3)
    public void loadUsersFromExternalApi() {
    	//If needed I can handle paganization also for now it take all the limit
        Map response = restTemplate.getForObject(externalApiUrl+"?limit=208", Map.class);
        List<UserEntity> users = ((List<Map>) response.get("users")).stream()
            .map(this::mapToUser)
            .toList();
        userRepository.saveAll(users);
    }
    
    private UserEntity mapToUser(Map userData) {
        UserEntity user = new UserEntity();
        try {
        	user.setId(Long.valueOf(userData.get("id").toString()));
        	user.setFirstName((String) userData.get("firstName"));
        	user.setLastName((String) userData.get("lastName"));
        	user.setEmail((String) userData.get("email"));
        	user.setSsn((String) userData.get("ssn"));
        	user.setBirthDate((String) userData.get("birthDate"));
        	user.setUrl((String) userData.get("image"));
        	user.setWeight(((Number) userData.get("weight")).doubleValue());
            user.setHeight(((Number) userData.get("height")).doubleValue());
            user.setGender((String) userData.get("gender"));
        	return user;
        	
        }catch (Exception e) {
			throw e;
		}
    }
    
    @Cacheable(value = "users", key = "#id")
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    
    @Cacheable(value = "users", key = "#email")
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
    
    @Transactional(readOnly = true)
    public List<UserEntity> searchUsers(String searchTerm) {
        try {
            SearchSession searchSession = Search.session(entityManager);
            return searchSession.search(UserEntity.class)
                .where(f -> f.match()
                    .fields("firstName", "lastName", "ssn")
                    .matching(searchTerm))
                .fetchHits(208);
        } catch (Exception e) {
            throw new RuntimeException("Failed to perform search", e);
        }
    }
    
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }
}
