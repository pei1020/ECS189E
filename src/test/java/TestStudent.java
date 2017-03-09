import api.IAdmin;
import api.IStudent;
import api.IInstructor;
import api.core.impl.Admin;
import api.core.impl.Student;
import api.core.impl.Instructor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Created by pattyliu on 3/7/17.
 */
public class TestStudent {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup(){
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
        this.admin.createClass("", 2017, "Norm Matloff", 80);
        this.admin.createClass("ECS132", 2017, "Norm Matloff", 80);
        this.admin.createClass("ECS163", 2017, "Ma", 120);
        this.instructor.addHomework("Norm Matloff", "ECS132", 2017, "HW6", "Final Project");
        this.instructor.addHomework("Ma", "ECS163", 2017, "Final", "Group Project");
        this.student.registerForClass("Patty Liu","ECS132", 2017);
        this.student.submitHomework("Patty Liu", "HW6", "R code.", "ECS132", 2017);

    }

    /****************************************** Registered ********************************************************/
    //valid registration
    @Test
    public void testValidStudent(){
        this.student.registerForClass("Name", "ECS163", 2017);
        assertTrue(this.student.isRegisteredFor("Name", "ECS163", 2017));
    }

    //register for the class in the past
    @Test
    public void testValidStudent2(){
        this.admin.createClass("ECS145", 2016, "Norm Matloff", 80);
        this.student.registerForClass("Name", "ECS145", 2016);
        assertFalse(this.student.isRegisteredFor("Name", "ECS145", 2016));
    }

    //check for existing class
    @Test
    public void testValidStudent3(){
        this.student.registerForClass("Anny Hsu", "ECS132", 2017);
        assertTrue(this.admin.classExists("ECS132", 2017));
    }

    //if exceeds the capacity the last person should not register the class
    @Test
    public void testValidStudent4(){
        this.admin.createClass("ECS160", 2017, "Nitta", 5);
        this.student.registerForClass("Anny Hsu", "ECS160", 2017);
        this.student.registerForClass("Katherine", "ECS160", 2017);
        this.student.registerForClass("Justine", "ECS160", 2017);
        this.student.registerForClass("Bryce", "ECS160", 2017);
        this.student.registerForClass("Brian", "ECS160", 2017);
        this.student.registerForClass("Jeff", "ECS160", 2017);
        assertFalse(this.student.isRegisteredFor("Jeff", "ECS160", 2017));
    }

    //capacitiy should be equal or larger than enrollees
    @Test
    public void testValidStudent5(){
        this.admin.createClass("ECS160", 2017, "Nitta", 5);
        this.student.registerForClass("Anny Hsu", "ECS160", 2017);
        this.student.registerForClass("Katherine", "ECS160", 2017);
        this.student.registerForClass("Justine", "ECS160", 2017);
        this.student.registerForClass("Bryce", "ECS160", 2017);
        this.student.registerForClass("Brian", "ECS160", 2017);
        this.student.registerForClass("Jeff", "ECS160", 2017);
        assertTrue(this.admin.getClassCapacity("ECS160", 2017) >= 6);
    }

    //valid register that capacity must be larger that enrollees
    @Test
    public void testValidStudent6(){
        this.admin.createClass("ECS160", 2017, "Nitta", 10);
        this.student.registerForClass("Anny Hsu", "ECS160", 2017);
        this.student.registerForClass("Katherine", "ECS160", 2017);
        this.student.registerForClass("Jeff", "ECS160", 2017);
        assertTrue(this.admin.getClassCapacity("ECS160", 2017) >= 3);
    }
    /****************************************** valid drop scenario ********************************************************/

    //successful drop
    @Test
    public void testValidStudent7(){
        this.student.dropClass("Patty Liu", "ECS132", 2017);
        assertFalse(this.student.isRegisteredFor("Patty Liu", "ECS132", 2017));
    }


    //dropping a class needs to make sure the class exists
    @Test
    public void testValidStudent8(){
        this.student.dropClass("Patty Liu", "ECS171", 2017);
        assertTrue(this.admin.classExists("ECS132", 2017));
    }

    //drop a class that is in the past
    @Test
    public void testValidStudent9(){
        this.student.dropClass("Patty Liu", "ECS132", 2016);
        assertTrue(this.admin.classExists("ECS132", 2016));
    }

    /****************************************** homeworksubmitted ********************************************************/

    //valid homework subission
    @Test
    public void testValidStudent10(){
        assertTrue(this.student.hasSubmitted("Patty Liu", "HW6", "ECS132", 2017));
    }

    //check if submit hw without registration
    @Test
    public void testValidStudent11(){
        this.student.submitHomework("Patty Liu", "Final", "Final Project", "ECS163", 2017);
        assertTrue(this.student.isRegisteredFor("Patty Liu", "ECS163", 2017));
    }

    //check if hw exists
    @Test
    public void testValidStudent12(){
        this.student.submitHomework("Patty Liu", "Extra Credit", "EC", "ECS132", 2017);
        assertTrue(this.instructor.homeworkExists("ECS132", 2017, "Extra Credit"));
    }


    //homework answer string blank is okay
    @Test
    public void testValidStudent13(){
        this.student.submitHomework("Patty Liu", "HW6", "", "ECS132", 2017);
        assertTrue(this.student.hasSubmitted("Patty Liu", "HW6", "ECS132", 2017));
    }


    //submit hw needs to check if the class exists
    @Test
    public void testValidStudent14(){
        this.student.submitHomework("Patty Liu", "P1", "c++", "ECS60", 2017);
        assertTrue(this.admin.classExists("ECS60", 2017));
    }


}
