package chap08_inheritance.com.hw1.model.dto;

public class Student extends Person{

    private int grade;
    private String major;

    public Student() {
    }

    public Student(String name, int age, double height, double weight, int grade, String major) {
        super(age, height, weight);
        this.grade = grade;
        this.major = major;
    }

    @Override
    public String getInformation() {
        String info =  super.getInformation();
        String stuInfo = "학년: " + grade + ", 전공: " + major;

        return info + stuInfo;
    }
}
