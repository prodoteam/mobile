package nogroupsprodoteam.ntnu.stud.httpwww.prodo;

/**
 * Created by Christian on 21.02.2017.
 */
import android.provider.UserDictionary;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static nogroupsprodoteam.ntnu.stud.httpwww.prodo.R.string.userID;

public class Database {
    private static String mysqlAddr = "jdbc:mysql://mysql.stud.ntnu.no:3306/prodoteam_testdb?allowMultiQueries=true";
    private static String mysqlUser = "jonaseth_tdt4140";
    private static String mysqlPass = "tdt4140";

    public Database (String adr, String user, String password){
        this.mysqlAddr = adr;
        this.mysqlUser = user;
        this.mysqlPass = password;
    }

    //returns ArrayList of courses from database
    public static ArrayList<Course> getCourses(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Course> courseList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> course = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    course.add(rs.getString(i));
                }courseList.add(new Course(course.get(0), course.get(1), course.get(2)));
            }
            conn.close();
            return courseList;
        }
        catch(SQLException e){
            return courseList;
        }
    }
    //returns ArrayList of lectures from database
    public static ArrayList<Lecture> getLectures(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Lecture> lectureList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lecture");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> lecture = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    lecture.add(rs.getString(i));
                }lectureList.add(new Lecture(lecture.get(0), lecture.get(1), lecture.get(2), lecture.get(3)));
            }
            conn.close();
            return lectureList;
        }
        catch(SQLException e){
            return lectureList;
        }
    }
    //returns lectureID from database
    public static Integer getLectureID(Integer courseID, Integer number){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer lectureID = null;
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT lectureID FROM lecture WHERE courseID = " + courseID.toString() +
                    " AND number = " + number.toString());

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lectureID = Integer.parseInt(rs.getString(1));
            }
            conn.close();
            return lectureID;
        }
        catch(SQLException e){
            return lectureID;
        }
    }

    //returns number of topics for selected lecture from database
    public static Integer countTopics(Integer lectureID){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer numberOfTopics = null;
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM topic WHERE lectureID = " + lectureID.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                numberOfTopics = Integer.parseInt(rs.getString(1));
            }
            conn.close();
            return numberOfTopics;
        }
        catch(SQLException e){
            return numberOfTopics;
        }
    }
    //returns arraylist of topics from database
    public static ArrayList<Topic> getTopics(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Topic> topicList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM topic");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> topic = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    topic.add(rs.getString(i));
                }topicList.add(new Topic(topic.get(0), topic.get(1), topic.get(2), topic.get(3)));
            }
            conn.close();
            return topicList;
        }
        catch(SQLException e){
            return topicList;
        }
    }
    //returns topicID from database
    public static Integer getTopicID(Integer number, Integer lectureID){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer topicID = null;

        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT topicID FROM topic WHERE number = " + number.toString() +
                    " AND lectureID = " + lectureID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                topicID = Integer.parseInt(rs.getString(1));
            }
            conn.close();
            return topicID;
        }
        catch(SQLException e){
            return topicID;
        }


    }

    //Uploading the rating to the database
    public static String setRating(Integer topicID, Integer stars, Integer userID){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer rating = null;
        String error ="Rating submitted";
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO rating(topicID,userID,stars) VALUES ('" + topicID.toString() + "','" + userID.toString() + "','" + stars.toString() + "')");
            stmt.execute();
            conn.close();
        }
        catch(SQLException e){
            error = "Database error:" + e;
            return error;
        }
        return error;
    }

    //Updating the rating in the Databse
    public static String updateRating(Integer topicID, Integer stars, Integer userID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer rating = null;
        String error ="Rating submitted";
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("UPDATE rating SET stars ="+stars.toString()+" WHERE userID = " +userID.toString()+ "AND topicID = " + topicID.toString());
            conn.close();
        }
        catch(SQLException e){
            error = "Database error:" + e;
            return error;
        }
        return error;
    }

    //Checks if nickname is already registered in database
    public static boolean checkNickname(String nickname){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean exists;
        Integer check = null;

        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE name = '" + nickname + "'") ;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                check = Integer.parseInt(rs.getString(1));
            }
            if(check > 0){
                exists = false;
            }
            else{
                exists = true;
            }
            conn.close();
            return exists;
        }
        catch(SQLException e){
            return false;
        }
    }
    //registers username to database
    public static Integer registerNickname(String nickname){
        Integer userID = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user(name) VALUES ('" + nickname + "')", Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                userID = rs.getInt(1);
            } else {
                System.out.println("rs empty");
            }
            conn.close();
        }
        catch(SQLException e){
        }
        return userID;
    }

    //gets userID from database
    public static Integer getUserID(String userName){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer userID = null;
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT userID FROM user WHERE name = '" + userName+"'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                userID = Integer.parseInt(rs.getString(1));
            }
            conn.close();
            return userID;
        }
        catch(SQLException e){
            userID = null;
            return userID;
        }


    }

    //send Questions to Database
    public static String sendQuestion(Integer topicID, String questionString, Integer userID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String error = null;

        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO question(topicID,userID,question) VALUES ('" + topicID.toString() + "','" + userID.toString() + "','" + questionString +"')");
            //INSERT INTO `question`(`questionID`, `topicID`, `userID`, `question`, `answer`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5])
            stmt.execute();
            conn.close();
        }
        catch(SQLException e){
            error = "Database error:" + e;
            return error;
        }
        return error;

    }

    //get Questions from Database
    public static ArrayList<Question> getQuestions(Integer topicID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Question> questionList = new ArrayList<Question>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question WHERE topicID = " + topicID.toString() +" ORDER BY rating DESC");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> question = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    question.add(rs.getString(i));
                }questionList.add(new Question(question.get(0), question.get(1), question.get(2),question.get(3), question.get(4), question.get(5)));
            }
            conn.close();
            return questionList;
        }
        catch(SQLException e){
            return questionList;
        }
    }

    //sets rating for questions
    public static String setQuestionRating(Integer questionID, Boolean up){
        Integer adderer = null;

        if(up==true){
              adderer = 1;
        }else{
            adderer = -1;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Integer rating=null;
        //Integer userID = 1;
        String error ="!";

        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT rating FROM question WHERE questionID =" + questionID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Integer Outrating = Integer.parseInt(rs.getString(1));
                rating = Outrating + adderer;
            }
            PreparedStatement stmt2 = conn.prepareStatement("UPDATE question SET rating = '" + rating.toString()+ "' WHERE questionID= " + questionID);
            stmt2.execute();
//"INSERT INTO question(topicID,userID,question,answer,rating) VALUES ('" + topicID.toString() + "','" + userID.toString() + "','" + questionString +"','" + answer +"','" + rating.toString() + "')");
            conn.close();
        }
        catch(SQLException e){
            error = "Database error:" + e;
            return error;
        }
        return error;
    }

    //returns arraylist of questions from the database
    public static ArrayList<Question> getAllQuestions() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Question> questionList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question ORDER BY rating DESC");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> question = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    question.add(rs.getString(i));
                }questionList.add(new Question(question.get(0), question.get(1), question.get(2), question.get(3), question.get(4), question.get(5)));
            }
            conn.close();
            return questionList;
        }
        catch(SQLException e){
            return questionList;
        }
    }

    //gets questions for the current user
    public static ArrayList<Question> getQuestionsWithUserID(int userID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Question> questionList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question WHERE userID =" + userID +" ORDER BY questionID DESC");
//SELECT *FROM  `question`ORDER BY  `question`.`questionID` DESC
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ArrayList<String> question = new ArrayList<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    question.add(rs.getString(i));
                }questionList.add(new Question(question.get(0), question.get(1), question.get(2), question.get(3), question.get(4), question.get(5)));
            }
            conn.close();
            return questionList;
        }
        catch(SQLException e){
            return questionList;

        }
    }

    //UNUSED
    //gets the origin of the question looked at. Coursename - Lecturename - Topicname
    public static String getQuestionOrigin(int topicID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String origin = "Cannot find origin of question";
        String topicname = null, lectureID = null, lecturename = null, courseID = null, coursename = null;
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT topic.name, topic.lectureID FROM question INNER JOIN topic ON topic.topicID = question.topicID WHERE question.topicID = " + topicID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                topicname = rs.getString(1);
                lectureID = rs.getString(2);
            }
            PreparedStatement stmt2 = conn.prepareStatement("SELECT lecture.name, lecture.courseID FROM topic INNER JOIN lecture ON lecture.lectureID = topic.lectureID WHERE topic.lectureID = " + lectureID);
            ResultSet rs2 = stmt2.executeQuery();
            while(rs2.next()){
                lecturename = rs2.getString(1);
                courseID = rs2.getString(2);
            }
            PreparedStatement stmt3 = conn.prepareStatement("SELECT course.name FROM lecture INNER JOIN course ON course.courseID = lecture.courseID WHERE lecture.courseID = " + courseID);
            ResultSet rs3 = stmt3.executeQuery();
            while(rs3.next()){
                coursename = rs3.getString(1);
            }
            conn.close();
            origin = coursename + " - " + lecturename + " - " + topicname;
            return origin;
        }
        catch(SQLException e){
            return origin;
        }
    }


}