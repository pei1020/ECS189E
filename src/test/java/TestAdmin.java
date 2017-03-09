import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/*
 * Created by pattyliu on 3/7/17.
 */

public class TestAdmin{
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup(){
        this.admin = new Admin();
        this.student = new Student();
    }

    /****************************************** Class Name ********************************************************/

    //vailid class name
    @Test
    public void testInvalidClass() {
        this.admin.createClass("ECS154A", 2017, "Sean Davis", 15);
        assertTrue(this.admin.classExists("ECS154A", 2017));
    }

    /****************************************** Year ********************************************************/

    //invalid year
    @Test
    public void testInvalidClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    //valid year
    @Test
    public void testInvalidClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    /****************************************** Instructor ********************************************************/


    //instructor exceeds maximum
    @Test
    public void testInvalidClass4() {
        this.admin.createClass("ECS160", 2017, "Nitta",  70);
        this.admin.createClass("ECS150", 2017, "Nitta",  100);
        this.admin.createClass("ECS140A", 2017, "Nitta",  110);
        assertFalse(this.admin.classExists("ECS140A", 2017));
    }

    //instructor meets the two classes maximum
    @Test
    public void testInvalidClass5() {
        this.admin.createClass("ECS160", 2017, "Nitta",  70);
        this.admin.createClass("ECS150", 2017, "Nitta",  100);
        assertTrue(this.admin.classExists("ECS160", 2017) && this.admin.classExists("ECS150", 2017));
    }

    //same class assign to same instructor, check for the first one exists or not
    @Test
    public void testInvalidClass6() {
        this.admin.createClass("ECS122A", 2017, "Rob",  70);
        this.admin.createClass("ECS122A", 2017, "Bai",  70);
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017).equals("Rob"));
    }

    /****************************************** Capacity ********************************************************/

    //check if the class exists before changing capacity; in this class does not exist
    @Test
    public void testInvalidClass7() {
        this.admin.changeCapacity("ECS122A", 2017, 40);
        assertTrue(this.admin.classExists("ECS122A", 2017));
    }
    //capacity invalid change to negative number
    @Test
    public void testInvalidClass8() {
        this.admin.createClass("ECS60", 2017, "Davis", 100);
        this.admin.changeCapacity("ECS60", 2017, -1);
        assertTrue(this.admin.getClassCapacity("ECS60", 2017)>0);
    }

    //capacity valid change
    @Test
    public void testInvalidClass9() {
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 50);
        this.student.registerForClass("Patty Liu", "ECS122B", 2017);
        this.student.registerForClass("Anny Hsu", "ECS122B", 2017);
        this.student.registerForClass("Judy Cheng", "ECS122B", 2017);
        this.admin.changeCapacity("ECS122B", 2017, 3);
        assertTrue(this.admin.getClassCapacity("ECS122B",2017)>=3);
    }

    //change capacity to # < enrollees
    @Test
    public void testInvalidClass10() {
        this.admin.createClass("ECS122B", 2017, "Rob Gysel", 5);
        this.student.registerForClass("Patty Liu", "ECS122B", 2017);
        this.student.registerForClass("Anny Hsu", "ECS122B", 2017);
        this.student.registerForClass("Raja", "ECS122B", 2017);
        this.admin.changeCapacity("ECS122B", 2017, 2);
        assertTrue(this.admin.getClassCapacity("ECS122B",2017) >= 3);
    }

    //capacity = 0 invalid because capacity has to be > 0
    @Test
    public void testInvalidClass11() {
        this.admin.createClass("ECS160", 2017, "Nitta",  0);
        assertFalse((this.admin.getClassCapacity("ECS160", 2017) ==0));
    }

    //change capacity from > 0 to zero is valid
    @Test
    public void testInvalidClass12(){
        this.admin.createClass("ECS160", 2017, "Nitta",  50);
        this.admin.changeCapacity("ECS160", 2017,0);
        assertTrue((this.admin.getClassCapacity("ECS160", 2017) ==0));
    }
    //change capacity to the same one
    @Test
    public void testInvalidClass13(){
        this.admin.createClass("ECS160", 2017, "Nitta",  50);
        this.admin.changeCapacity("ECS160", 2017,50);
        assertTrue((this.admin.getClassCapacity("ECS160", 2017) ==50));
    }

}
