package com.lab;

public class PointChecker {
    
    public static PointResult handlePointCheck(double x, double y, double r) {
        boolean inRectangle = -r <= x && x <= 0 && 0 >= y && y >= -r;
        
        boolean inQuarterCircle = (-r/2 <= x && x <= 0) &&
                                  (r/2 >= y && y >= 0) &&
                                  (Math.sqrt(x * x + y * y) <= r/2);
        
        boolean inTriangle = 0 <= x && x <= r &&
                             0 <= y && y <= r/2 &&
                             y <= (r - x) / 2;
                        
        boolean success = inRectangle || inQuarterCircle || inTriangle;
        
        return new PointResult(success, r, x, y);
    }
}
