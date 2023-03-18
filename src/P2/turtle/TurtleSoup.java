/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.*;

public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if (sides <= 2) {
            throw new IllegalArgumentException("参数错误");
        }
        double angle = (double) 180.0 - (double) 360.0 / (double) sides;
        return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int i;
        for (i = 3; angle * i > (i - 2) * 180.0; i++) ;
        if (angle * i - (i - 2) * 180 > 0.000001) {
            throw new IllegalArgumentException("参数不合法");
        }
        return i;

    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        if (sides > 2) {
            double angle = calculateRegularPolygonAngle(sides);
            for (int i = 0; i < sides; i++) {
                turtle.forward(sideLength);
                turtle.turn(180.0 - angle);
            }
        } else {
            throw new IllegalArgumentException("参数错误");
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double angle = Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;
        angle = angle < 0 ? -angle : 360.0 - angle;
        return (90.0 - currentBearing + angle) % 360.0;
    }
    //拓展函数功能，让浮点数坐标点也可以参与运算
    public static double calculateBearingToPoint(double currentBearing, double currentX, double currentY,
                                                 double targetX, double targetY) {
        double angle = Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;
        angle = angle < 0 ? -angle : 360.0 - angle;
        return (90.0 - currentBearing + angle) % 360.0;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        //检测两列表提供的点个数是否相等
        if (xCoords.size() != yCoords.size()) {
            throw new IllegalArgumentException("参数错误");
        } else {
            List<Double> list_angle = new ArrayList<>();
            double angle = 0, temp = 0;
            //由数学关系得
            for (int i = 0; i < xCoords.size() - 1; i++) {
                temp = calculateBearingToPoint(angle, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1));
                list_angle.add(temp);
                angle += temp;
                temp = 0;
            }
            return list_angle;
        }
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size() <= 3) {
            return points;
        }
        Set<Point> tag = new HashSet<>();
        Iterator<Point> ie = points.iterator();
        Point temp = ie.next();
        //找到最左下角的点
        while (ie.hasNext()) {
            Point a = ie.next();
            if ((Double.compare(temp.y(), a.y()) == 1) || ((Double.compare(temp.x(), a.x()) == 0) && (Double.compare(temp.y(), a.y()) == 1))) {
                temp = a;
            }
        }
        tag.add(temp);
        Point first = temp, point_min = temp;
        ArrayList<Point> listp = new ArrayList<>(points);
        double tempx, tempy;
        double angle = 0.0, angle_temp = 0.0;
//        遍历
        do {
            double angle_min = Double.MAX_VALUE;
            for (int i = 0; i < listp.size(); i++) {
                if (listp.get(i) != temp) {
                    tempx = listp.get(i).x();
                    tempy = listp.get(i).y();
                    angle_temp = calculateBearingToPoint(angle, temp.x(), temp.y(), tempx, tempy);
                    if (Double.compare(angle_temp, angle_min) == -1) {
                        point_min = listp.get(i);
                        angle_min = angle_temp;
//                        比较距离
                    } else if (Double.compare(angle_temp, angle_min) == 0) {
                        double dis1 = Math.pow(tempx - temp.x(), 2) + Math.pow(tempy - temp.y(), 2);
                        double dis2 = Math.pow(temp.x() - point_min.x(), 2) + Math.pow(temp.y() - point_min.y(), 2);
                        if (dis1 > dis2) {
                            point_min = listp.get(i);
                            angle_min = angle_temp;
                        }
                    }
                }
            }
            angle += angle_min;
            if (angle - 360.0 > 0.0000001) angle -= 360.0;
            temp = point_min;
            tag.add(temp);
            listp.remove(temp);
        } while (temp != first);
        return tag;
    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        for (int i = 50; i < 150; i = i + 10) {
            double angle = 36;
            for(PenColor color:PenColor.values()){
                turtle.color(color);
                drawRegularPolygon(turtle,6,i);
                turtle.turn(angle);
            }
        }
    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        //创建三个变量，以便用三个窗口分别绘制图形
        DrawableTurtle turtle = new DrawableTurtle();
        DrawableTurtle turtle2 = new DrawableTurtle();
        DrawableTurtle turtle3 = new DrawableTurtle();
        //分别打开三个窗口
        drawSquare(turtle, 200);
        turtle.draw();
        drawRegularPolygon(turtle2, 6, 50);
        turtle2.draw();
        drawPersonalArt(turtle3);
        // draw the window
        turtle3.draw();
    }

}
