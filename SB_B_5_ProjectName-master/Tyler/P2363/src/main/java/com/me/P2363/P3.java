package com.me.P2363;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class P3 {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (Exception E) {
			System.err.println("Unable to load driver");
			E.printStackTrace();
		}

		try {
			Connection conn1;
			String dbUrl = "jdbc:mysql://csdb.cs.iastate.edu:3306/db363tfritzy";
			String user = "dbu363tfritzy";
			String password = "5EZce#Fa";
			conn1 = DriverManager.getConnection(dbUrl, user, password);
			System.out.println("*** Connected to the database ***");

			// part a
			printProfessorSalaries(conn1);

			// part b
			topStudents(conn1);

			// part c
			printMeritList(conn1);

			// part d
			raisePayBasedOnMerit(conn1);

			// part e
			printProfessorSalaries(conn1);

			// part f
			dropMeritTable(conn1);

			// Close all statements and connections
			// note - each method closes its connections when done with them.
			conn1.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	/*
	 * Parts A and E
	 */
	private static void printProfessorSalaries(Connection conn) throws SQLException {
		Statement statement = (Statement) conn.createStatement();
		ResultSet rs;
		rs = statement.executeQuery("select * from Instructor i " + "inner join Person p on i.InstructorID = p.ID");

		int totalSalary = 0;
		int salary;

		while (rs.next()) {
			// get value of salary from each tuple
			salary = rs.getInt("Salary");
			totalSalary += salary;
			System.out.println(rs.getString("Name") + " : " + salary);
		}

		System.out.println("Total Salary of all faculty: " + totalSalary);
		statement.close();
		rs.close();
	}

	/*
	 * Part B
	 */
	private static void topStudents(Connection conn) throws SQLException {
		Statement statementQuery = (Statement) conn.createStatement();
		Statement statementInsert = (Statement) conn.createStatement();
		ResultSet rs2;
		int rs1;
		rs1 = statementInsert.executeUpdate("create table MeritList( " + "ID char(9) not null,"
				+ "Classification char(10)," + "MentorID char(9) not null," + "GPA double)");

		rs2 = statementQuery.executeQuery("Select * from Student s order by GPA desc");
		int studentsAdded = 0;
		double lastGPAAdded = 4.00;
		while (rs2.next()) {
			double GPA = rs2.getDouble("GPA");
			String MentorID = rs2.getString("MentorID");
			String Classification = rs2.getString("Classification");
			String ID = rs2.getString("StudentID");

			int rs3;
			if (studentsAdded <= 20 || GPA == lastGPAAdded) {
				rs3 = statementInsert.executeUpdate("Insert into MeritList " + "(GPA, MentorID, Classification, ID)"
						+ "VALUES (" + GPA + ", \"" + MentorID + "\"," + "\"" + Classification + "\",\"" + ID + "\")");
				lastGPAAdded = GPA;
				studentsAdded += 1;
			}

		}
		statementQuery.close();
		statementInsert.close();
		rs2.close();
	}

	/*
	 * Part C
	 */
	private static void printMeritList(Connection conn) throws SQLException {

		Statement statementQuery = (Statement) conn.createStatement();
		ResultSet rs;
		rs = statementQuery.executeQuery("select * from MeritList m order by GPA desc");
		while (rs.next()) {
			System.out.println(" GPA: " + rs.getDouble("GPA") + " MentorID: " + rs.getString("MentorID")
					+ " Classification: " + rs.getString("Classification") + " ID: " + rs.getString("ID"));
		}

		rs.close();
		statementQuery.close();

	}

	/*
	 * Part D
	 */
	private static void raisePayBasedOnMerit(Connection conn) throws SQLException {
		Statement statementQuery = (Statement) conn.createStatement();
		Statement statementUpdate = (Statement) conn.createStatement();
		ResultSet rs;
		rs = statementQuery.executeQuery("select distinct i.InstructorID, m.Classification, i.salary "
				+ "from MeritList m inner join Instructor i on m.MentorID=i.InstructorID" + " order by i.InstructorID");

		String currentInstructor = null;
		float highestRaise = .04f;
		while (rs.next()) {

			String newInstructor = rs.getString("InstructorID");
			String classification = rs.getString("Classification");
			if (currentInstructor == null) {
				currentInstructor = newInstructor;
			}

			if (classification.equals("Senior")) {
				if (.10f > highestRaise) {
					highestRaise = .10f;
				}
			} else if (classification.equals("Junior")) {
				if (.08f > highestRaise) {
					highestRaise = .08f;
				}
			} else if (classification.equals("Sophomore")) {
				if (.06f > highestRaise) {
					highestRaise = .06f;
				}
			}

			if (currentInstructor.equals(newInstructor) == false) {
				int newSalary = (int) (rs.getInt("Salary") * (1.0 + highestRaise));
				statementUpdate.executeUpdate("Update Instructor " + " set Salary = " + newSalary + " "
						+ "where InstructorID = '" + currentInstructor + "'");
				currentInstructor = newInstructor;
				highestRaise = .04f;

			}
		}

		rs.close();
		statementUpdate.close();
		statementQuery.close();

	}

	/*
	 * Part F
	 */
	private static void dropMeritTable(Connection conn) throws SQLException {
		Statement statementUpdate = (Statement) conn.createStatement();
		int rs;
		rs = statementUpdate.executeUpdate("drop table MeritList");
		statementUpdate.close();
	}
}
