package com.lab;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Named
@ViewScoped
public class MainPageBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ResultsService resultsService;

    private Double x;
    
    @NotNull(message = "Y is required")
    @DecimalMin(value = "-3.0", message = "Y must be >= -3")
    @DecimalMax(value = "3.0", message = "Y must be <= 3")
    private Double y;
    
    @NotNull(message = "R is required")
    @DecimalMin(value = "1.0", message = "R must be >= 1")
    @DecimalMax(value = "3.0", message = "R must be <= 3")
    private Double r = 1.0;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public List<PointResult> getResults() {
        return resultsService.getAllResults();
    }

    public String checkPoint() {
        if (x == null || y == null || r == null) {
            return null;
        }

        long before = System.nanoTime();
        Instant currentInstant = Instant.now();

        PointResult result = PointChecker.handlePointCheck(x, y, r);
        PointResult timestampedResult = new PointResult(
            result.isSuccess(), 
            result.getR(), 
            result.getX(), 
            result.getY(), 
            currentInstant.toString(), 
            (System.nanoTime() - before) / 1000000 + "ms"
        );

        resultsService.addResult(timestampedResult);
        
        x = null;
        y = null;
        
        return null;
    }

    public String checkPointFromGraph(double graphX, double graphY) {
        if (r == null) {
            return null;
        }

        this.x = graphX;
        this.y = graphY;
        return checkPoint();
    }

    public void clearResults() {
        resultsService.clearResults();
    }

    public void setXFromButton(Double value) {
        this.x = value;
    }

    public String getHistoryJson() {
        try {
            return objectMapper.writeValueAsString(getResults());
        } catch (Exception e) {
            return "[]";
        }
    }

    public void setHistoryJson(String historyJson) {
    }
}

