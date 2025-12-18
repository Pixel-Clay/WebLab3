package com.lab;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ResultsService {

    @Inject
    private PointResultRepository repository;

    private List<PointResult> results;

    @PostConstruct
    public void init() {
        results = new ArrayList<>();
        loadResults();
    }

    public void addResult(PointResult result) {
        repository.save(result);
        loadResults();
    }

    public List<PointResult> getAllResults() {
        if (results == null) {
            loadResults();
        }
        return results;
    }

    public void clearResults() {
        repository.deleteAll();
        results.clear();
    }

    private void loadResults() {
        try {
            results = repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            results = new ArrayList<>();
        }
    }
}

