import java.util.*;
//This is an example of the strategy pattern
//For cs3250

interface SearchBehaviour<T,S>{
    //T is the object, S is the value
    boolean search(T obj, S v);
}
class StudentSearch implements SearchBehaviour<Student, String>{
    @Override
    public boolean search(Student obj, String v) {
        return obj.getID().equals(v);
    }
}
class CourseSearch implements SearchBehaviour<Course, String>{
    @Override
    public boolean search(Course obj, String v) {
        return obj.getNumber().equals(v);
    }
}
class CnumSearch implements SearchBehaviour<String, String>{
    @Override
    public boolean search(String obj, String v){return obj.equals(v);}
}

class AllItems<T>{
    private ArrayList<T> _items;

    public AllItems(){_items = new ArrayList<T>();}
    public AllItems(int size){_items = new ArrayList<T>(size);}
    public void addItem(T t){_items.add(t);}
    public void removeItem(int i){
        if(i>= 0 && i <_items.size())
            _items.remove(i);
    }
    public <S> int findItem(S v, SearchBehaviour<T, S> sb){
        for(int i=0;i< _items.size();i++){
            if(sb.search(_items.get(i), v)){
                return i;
            }
        }
        return -1;
    }
    public int size(){return _items.size();}
    public T getItem(int i){return _items.get(i);}

}
class Course{
    private String _cnum;
    private int _credits;
    public Course(){}
    public Course(String num, int cred){
        _cnum = num;  _credits = cred;
    }
    public void setNumber(String num){_cnum = num;}
    public void setCredits(int cred){_credits = cred;}
    public String getNumber(){return _cnum;}
    public int getCredits(){return _credits;}
    public String toString(){return _cnum + " " + _credits;}
}
class Student{
    private String _sid;
    public Student(){}
    public Student(String d){_sid = d;}
    public void setID(String id){_sid = id;}
    public String getID(){return _sid;}
    public String toString(){return _sid;}
}
class AllStudents{
    private AllItems<Student> _students;
    public AllStudents(){
        _students = new AllItems<Student>();
    }
    public AllStudents(int size){
        _students = new AllItems<>(size);
    }
    public void addStudent(String id){
        _students.addItem(new Student(id));
    }
    public boolean isStudent(String id){
        if(_students.findItem(id, new StudentSearch()) == -1)
            return false;
        return true;
    }
    public int findStudent(String id){
        return _students.findItem(id, new StudentSearch());
    }
    public boolean modifyStudentID(String id, String newID){
        int idx = findStudent(id);
        if(idx<0)
            return false;
        else {
            _students.getItem(idx).setID(newID);
            return true;
        }
    }
    public void removeStudent(String id){
        int i = _students.findItem(id, new StudentSearch());

        _students.removeItem(i);

    }
    public String toString(){
        String s = "Students:\n";
        for (int i=0; i<_students.size(); i++)
            s += (_students.getItem(i).toString() + "\n");
        return s;
    }
    public int size(){
        return _students.size();
    }
}
class AllCourses{
    private AllItems<Course> _courses;
    public AllCourses(){
        _courses = new AllItems<Course>();
    }
    public AllCourses(int size) {_courses = new AllItems<>(size);}
    public void addCourse(String cnum, int c){
        _courses.addItem(new Course(cnum, c));
    }
    public boolean isCourse(String cnum){
        if(_courses.findItem(cnum, new CourseSearch()) != -1)
            return true;
        return false;
    }
    public int findCourse(String cnum){
        return _courses.findItem(cnum, new CourseSearch());
    }
    public boolean modifyCourse(String cnum, String newCnum){
        int idx = findCourse(cnum);
        if(idx <0) {
            return false;
        }
        else{
            _courses.getItem(idx).setNumber(newCnum);
            return true;
        }
    }
    public void removeCourse(String cnum){
        int i = _courses.findItem(cnum, new CourseSearch());
        _courses.removeItem(i);
    }
    public String toString(){
        String s = "Courses:\n";
        for (int i=0; i<_courses.size(); i++)
            s += (_courses.getItem(i).toString() + "\n");
        return s;
    }
    public int size(){
        return _courses.size();
    }
}
class Enrollment{
    private HashMap<String, AllItems<String>> _enroll;
    public Enrollment(){_enroll = new HashMap<String, AllItems<String>>();}
    public boolean dropStudentFromCourse(String id, String cnum){
        // drops course from the students set of courses
        // if no other courses exist for student drop student from hashmap
        AllItems<String> t = _enroll.get(id);
        int i = t.findItem(cnum, new CnumSearch());
        if(i == -1)
            return false;
        t.removeItem(i);
        if(t.size() == 0)
            _enroll.remove(id);
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        AllStudents as = new AllStudents();
        AllCourses ac = new AllCourses();
        as.addStudent("100");
        as.addStudent("200");
        as.addStudent("300");
        ac.addCourse("CSC3250", 4);
        ac.addCourse("CSC1700", 4);
        ac.addCourse("MTH3270", 4);
        System.out.println(as);
        System.out.println(ac);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));
        as.removeStudent("300");
        System.out.println("\nAfter removing student 300");
        as.removeStudent("300");
        System.out.println(as);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("\nAfter removing course 3250");
        ac.removeCourse("CSC3250");
        System.out.println(ac);
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));
    }
}
