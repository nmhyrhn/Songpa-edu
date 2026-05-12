package chap08_inheritance.com.hw1.model.dto;

public class Employee extends Person{

    private int salary;
    private String dept;

    public Employee() {
    }

    public Employee(String name, int age, double height, double weight, int salary, String dept) {
        super(age, height, weight);
        this.salary = salary;
        this.dept = dept;
    }

    @Override
    public String getInformation() {
        String info = super.getInformation();
        String emplInfo = "급여: " + salary + ", 부서: " + dept;

        return info + emplInfo;
    }
}
