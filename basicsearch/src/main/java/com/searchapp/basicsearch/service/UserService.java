package com.searchapp.basicsearch.service;

import com.searchapp.basicsearch.dto.SearchRequest;
import com.searchapp.basicsearch.dto.SearchResponse;
import com.searchapp.basicsearch.model.User;
import com.searchapp.basicsearch.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public SearchResponse searchUsers(SearchRequest request) {
        List<User> all = userRepo.findAll();

        List<User> filtered = all.stream()
                .filter(u -> u.getSalary() >= request.getMinSalary())
                .filter(u -> u.getInterests().contains(request.getInterest()))
                .limit(10)
                .collect(Collectors.toList());

        long total = all.stream()
                .filter(u -> u.getSalary() >= request.getMinSalary())
                .filter(u -> u.getInterests().contains(request.getInterest()))
                .count();

        return new SearchResponse(total, filtered);
    }
}
