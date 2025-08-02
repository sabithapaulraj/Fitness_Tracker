package com.searchapp.basicsearch.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchapp.basicsearch.model.User;
import com.searchapp.basicsearch.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader {
    private final UserRepository repo;

    public DataLoader(UserRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = getClass().getResourceAsStream("/users.json");
            List<User> users = mapper.readValue(input, new TypeReference<List<User>>() {});
            repo.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
