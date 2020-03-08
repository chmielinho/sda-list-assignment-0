package pl.sda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Student {
    private String name;
    private String surname;
    List<Integer> grades;

    public Student(String name, String surname, List<Integer> grades) {
        this.name = name;
        this.surname = surname;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Integer> getGrades() {
        return grades;
    }
}
