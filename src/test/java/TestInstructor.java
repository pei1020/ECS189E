import api.IInstructor;
import api.IStudent;
import api.core.impl.Student;
import api.core.impl.Admin;
import api.IAdmin;
import api.core.impl.Instructor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Created by pattyliu on 3/7/17.
 */

public class TestInstructor {
    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup(){
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 60);
        this.admin.createClass("ECS30", 2017, "Rob Gysel", 60);
        this.student.registerForClass("Patty Liu","ECS122A", 2017);
        this.student.registerForClass("Anny Hsu","ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW1", "Greedy Algorithm");
        this.student.submitHomework("Patty Liu", "HW1", "Answer.", "ECS122A", 2017);

    }

    /****************************************** Add Homework ********************************************************/
    //created class and hw exists, valid
    @Test
    public void testValidHomework(){
        this.instructor.addHomework("Rob Gysel", "ECS30", 2017, "HW1","HW" );
        assertTrue(this.instructor.homeworkExists("ECS30", 2017, "HW1"));
    }

    //class exists
    @Test
    public void testValidHomework2(){
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "Midterm Project","midterm" );
        assertTrue(this.admin.classExists("ECS122A", 2017));
    }

    //add homework to non-created class
    @Test
    public void testValidHomework3(){
        this.instructor.addHomework("Rob Gysel", "ECS152", 2017, "HW1","HW" );
        assertTrue(this.instructor.homeworkExists("ECS152", 2017, "HW1"));
    }

    //the one adds Homework == instructor
    @Test
    public void testValidHomework4(){
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW2","Divide and Conquer");
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017) .equals("Rob Gysel"));
    }

    //the one adds Homework != instructor
    @Test
    public void testValidHomework5(){
        this.instructor.addHomework("Sean Davis", "ECS122A", 2017, "HW2","Divide and Conquer");
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017) .equals("Sean Davis"));
    }

    //class not exists
    @Test
    public void testValidHomework6(){
        this.instructor.addHomework("Rob Gysel", "ECS120", 2017, "HW1","Turing Machine" );
        assertTrue(this.admin.classExists("ECS120", 2017));
    }

    /****************************************** Sumbit Homework ********************************************************/
    //valid submit
    @Test
    public void testValidHomework7(){
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW3","HW" );
        this.student.submitHomework("Patty Liu", "HW3", "Correct Answer", "ECS122A", 2017);
        assertTrue(this.student.hasSubmitted("Patty Liu", "HW3",  "ECS122A", 2017));
    }

    //answer blank, valid
    @Test
    public void testValidHomework8(){
        this.student.submitHomework("Patty Liu", "HW1", "", "ECS122A", 2017);
        assertTrue(this.student.hasSubmitted("Patty Liu", "HW1",  "ECS122A", 2017));
    }


    //submit homework that not added
    @Test
    public void testValidHomework9(){
        this.student.submitHomework("Patty Liu", "HW3", "Correct Answer", "ECS122A", 2017);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW3"));
    }

    //Submit without registration
    @Test
    public void testValidHomework10(){
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW3","HW" );
        this.student.submitHomework("David Hsu", "HW3", "Correct Answer", "ECS122A", 2017);
        assertTrue(this.student.isRegisteredFor("David Hsu", "ECS122A", 2017));
    }
    @Test
    public void testValidHomework11(){
        this.student.submitHomework("Patty Liu", "HW3", "Correct Answer", "ECS122A", 2016);
        assertTrue(this.admin.classExists("ECS122A", 2016));
    }

    @Test
    public void testValidHomework12(){
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "Hwk1","HW" );
        this.student.submitHomework("Patty Liu", "Hwk1", "Correct Answer", "ECS122A", 2017);
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017).equals("Rob Gysel"));
    }

    /****************************************** Grades ********************************************************/
    //valid homework grade retrieve
    @Test
    public void testValidHomework13(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Patty Liu", 100);
        assertTrue(this.instructor.getGrade("ECS122A", 2017, "HW1", "Patty Liu") ==100);
    }

    //negative grades
    @Test
    public void testValidHomework14(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Patty Liu", -1);
        assertTrue(this.instructor.getGrade("ECS122A", 2017, "HW1", "Patty Liu") >=0);
    }


    //assign homework to students are not registered for class
    @Test
    public void testValidHomework15(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Anny Hsu", 100);
        assertFalse(this.student.hasSubmitted("Anny Hsu", "HW1", "ECS122A", 2017));
    }

    //only assign grades to students who submit the hw
    @Test
    public void testValidHomework16(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Anny Hsu", 100);
        assertTrue(this.student.hasSubmitted("Anny Hsu", "HW1", "ECS122A", 2017));
    }

    //only assign grades to students who register for the class
    @Test
    public void testValidHomework17(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Judy Cheng", 80);
        assertTrue(this.student.hasSubmitted("Judy Cheng", "HW1", "ECS122A", 2017));
    }


    //grades exceeds 100, valid
    @Test
    public void testValidHomework18(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW1", "Patty Liu", 120);
        assertTrue(this.instructor.getGrade("ECS122A",2017, "HW1", "Patty Liu") >= 100);
    }


    //The one who assigns the grade should equal to the instructor
    @Test
    public void testValidHomework19(){
        this.instructor.assignGrade("Sean Davis", "ECS122A", 2017, "HW1", "Patty Liu", 100);
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017).equals("Sean Davis"));
    }

    //assign grades to existing hw
    @Test
    public void testValidHomework20(){
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW6", "Patty Liu", 100);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW6"));
    }





}
