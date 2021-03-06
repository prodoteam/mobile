package nogroupsprodoteam.ntnu.stud.httpwww.prodo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LectureActivity extends AppCompatActivity {
    ViewPager viewPager;
    private ArrayList<Course> courseArrayList;
    private ArrayList<Lecture> lectureArrayList;
    private ArrayList<Topic> topicArrayList;
    private ArrayList<Lecture> lecturesFromSelectedCourseArrayList;
    private static ArrayList<Question> questionArrayList;
    private int selectedCourseID;
    private Lecture selectedLecture;
    private static int selectedLectureID;
    private static List<Topic> topicsFromSelectedLecture = new ArrayList<>();
    static Integer numberOfTopics;
    private static int userID;
    private String nickname;

    public static Integer getNumberOfTopics() {
        return numberOfTopics;
    }

    public static Integer getLectureID(){
        return selectedLectureID;
    }

    public static ArrayList<Topic> getTopicsFromSelectedLecture(){
        return (ArrayList<Topic>) topicsFromSelectedLecture;
    }

    public static ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public static int getUserID(){ return userID;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_activity);

        //gets values from CourseActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        courseArrayList = (ArrayList<Course>) extras.getSerializable("CourseList");
        lectureArrayList = (ArrayList<Lecture>) extras.getSerializable("LectureList");
        topicArrayList = (ArrayList<Topic>) extras.getSerializable("TopicList");
        questionArrayList = (ArrayList<Question>) extras.getSerializable("QuestionList");
        lecturesFromSelectedCourseArrayList = (ArrayList<Lecture>) extras.getSerializable("lecturesFromSelectedCourseList");
        selectedLecture = (Lecture) extras.getSerializable("SelectedLecture");
        selectedCourseID = extras.getInt("SelectedCourseID");
        selectedLectureID = extras.getInt("SelectedLectureID");
        userID = extras.getInt("UserID");
        nickname = extras.getString("NickName");

        //sets your nickname and the prodo logo in the actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nickname);
        actionBar.setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //displays selected course and lecture
        TextView lbl_course = (TextView) findViewById(R.id.lbl_course);
        TextView lbl_lecture = (TextView) findViewById(R.id.lbl_lecture);
        lbl_course.setText(courseArrayList.get(selectedCourseID - 1).getCourseCode() + " - " +(courseArrayList.get(selectedCourseID - 1).getCourseName()));
        lbl_lecture.setText(selectedLecture.getLectureName());

        //creates new arraylist with the topics that belong to the selected lecture
        topicsFromSelectedLecture.clear();
        for( Topic topic : topicArrayList) {
            if (topic.getLectureID().equals(Integer.toString(selectedLectureID))) {
                topicsFromSelectedLecture.add(topic);
            }
        }
        numberOfTopics = topicsFromSelectedLecture.size();

        //sets the fragment to be swiped between and its adapter/controller
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
    }
    //switches fragment to the right
    public void swiperight(int positon){
        viewPager.setCurrentItem(positon + 1);
    }
    //switches fragment to the left
    public void swipeleft(int position){
        viewPager.setCurrentItem(position - 1);
    }

    //Creates account icon in actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Handles button/icon click activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            openUsersQuestions();
        }
        return super.onOptionsItemSelected(item);
    }

    //opens activity displaying the users asked questions
    public void openUsersQuestions(){
        Intent intent = new Intent(this, QuestionActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("UserID", userID);
        extras.putString("NickName", nickname);
        extras.putSerializable("CourseList", courseArrayList);
        extras.putSerializable("LectureList", lectureArrayList);
        extras.putSerializable("TopicList", topicArrayList);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
