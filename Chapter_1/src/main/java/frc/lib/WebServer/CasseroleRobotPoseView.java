package frc.lib.WebServer;

import java.awt.geom.Point2D;

public class CasseroleRobotPoseView {
	
	public static double x = 0;
	public static double y = 0;
	public static double theta = 0;
	
	public static double bot_w = 0;
	public static double bot_h = 0;
	
	public static Point2D.Double[] fieldPolygon = null;
	
	
	/**
	 * Defines the size of the robot to display. All units are feet
 	 * @param robot_x_len_ft
	 * @param robot_y_len_ft
	 */
	public static void setRobotSize(double robot_x_len_ft, double robot_y_len_ft) {
		bot_w = robot_x_len_ft;
		bot_h = robot_y_len_ft;
	}
	
	/**
	 * Defines the set of points outlining the field
	 * @param points
	 */
	public static void setFieldPolygon(Point2D.Double[] points) {
		fieldPolygon = points;
	}
	
	/**
	 * Sets the current position to display the robot at, relative to the field origin
	 * @param x_position_ft
	 * @param y_position_ft
	 * @param rotation_deg
	 */
	public static void setRobotPose(double x_position_ft, double y_position_ft, double rotation_deg) {
		x = x_position_ft;
		y = y_position_ft;
		theta = rotation_deg;
	}

}
