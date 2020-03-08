package pl.sda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         1. Utworzyc klase Student zawierajaca pola:
           - name
           - surname
           - grades (lista Integerow)
         2. w mainie utworz 10 studentow i dodaj ich do listy students
         3. w klasie main utworz funkcje pozwalajaca wyliczac srednia oceny studentow
         4. w klasie main utworz funkcje wyszukujaca studentow wedlug czesci podanego nazwiska
         5. w klasie main utworz funkcje wyszukujaca wedlug imienia i nazwiska (dokladnie wprowadzone imie i nazwisko)
         6. W konsoli stworz "interfejs" pozwalajacy na dodawanie i usuwanie studentow
         7. zamiast tworzyc 10 studentow "recznie" 10 razy wywolujac "new" odczytaj ich z pliku o strukturze:
            imie;nazwisko;ocena1,ocena2,ocena3
            np.
            Jakub;Plonka;1,2,3,4,5,6,2,2,4
            Jakub;Test;1,4,3,4,5,3,2,2,4
         */
        ArrayList<Student> studentsList = readFile();

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            printMainMenu();
            switch (scanner.nextInt()) {
                case 1: {
                    addStudent(studentsList);
                    break;
                }
                case 2: {
                    removeStudent(studentsList);
                    break;
                }
                case 3: {
                    findStudent(studentsList);
                    break;
                }
                case 4: {
                    findExaStudent(studentsList);
                    break;
                }
                case 5: {
                    sredniaOcen(studentsList);
                    break;
                }
                case 9: {
                    saveData(studentsList);
                    exit = true;
                }
                default:
                    break;
            }
        }
    }

    public static void saveData(ArrayList<Student> studentsList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
        for (Student student : studentsList) {
            StringBuilder sb = new StringBuilder();
            List<Integer> grades = student.getGrades();
            for (Integer grade : grades) {
                sb.append(grade).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            writer.write(student.getName() + ";" + student.getSurname() + ";" + sb.toString());
            writer.newLine();
        }
        writer.close();
    }

    public static ArrayList<Student> readFile() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        List<String> stringLines = Files.readAllLines(Path.of("data.txt"));
        stringLines.forEach(String -> {
            String[] valueTab = String.split(";");
            String[] grades = valueTab[2].split(",");

            ArrayList<Integer> gradesArray = new ArrayList<>();
            for (String grade : grades) {
                gradesArray.add(Integer.parseInt(grade));
            }
            students.add(new Student(valueTab[0], valueTab[1], gradesArray));
        });
        return students;
    }

    public static void addStudent(ArrayList<Student> students) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> grades = new ArrayList<>();

        System.out.print("Imie: ");
        String name = scanner.nextLine();
        System.out.print("Nazwisko: ");
        String surname = scanner.nextLine();
        System.out.print("Wprowadz ocenę:");
        String grade = scanner.nextLine();

        do {
            grades.add(Integer.parseInt(grade));
            System.out.print("Wprowadz następna ocene (jesli nie to kliknij enter): ");
            grade = scanner.nextLine();
        } while (grade.matches("[1-6]"));
        students.add(new Student(name, surname, grades));
        scanner.close();
    }

    public static void removeStudent(ArrayList<Student> students) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj szukanego studenta: ");
        String name = scanner.nextLine();
        students.removeIf(student -> (student.getName() + " " + student.getSurname()).toLowerCase().equals(name.toLowerCase()));
        scanner.close();
    }

    public static void printMainMenu() {
        String sb = "".concat("1. Dodaj studenta\n")
                .concat("2. Usun studenta\n")
                .concat("3. Znajdz studenta po nazwisku\n")
                .concat("4. Znajdz studenta po imieniu i nazwisku\n")
                .concat("5. Wypisz srednia ocen studentow\n")
                .concat("9. Zakoncz");
        System.out.println(sb);
    }

    public static void findExaStudent(ArrayList<Student> studentsList) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.print("Podaj szukanego studenta: ");
        String name = scanner.nextLine();

        for (Student student : studentsList) {
            if ((student.getName() + " " + student.getSurname()).matches(name)) {
                stringBuilder.append(student.getName())
                        .append(" ")
                        .append(student.getSurname())
                        .append(" oceny: ")
                        .append(student.grades.toString().replace("[", "").replace(".", ""))
                        .append("\n");
            }
        }

        if (stringBuilder.length() == 0) {
            stringBuilder.append("Nie znaleziono studenta");
        }

        scanner.close();
        System.out.println(stringBuilder.toString());
    }

    public static void findStudent(ArrayList<Student> studentsList) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.print("Wyszukaj po nazwisku: ");
        String surname = scanner.nextLine();
        studentsList.forEach(student -> {
            if (student.getSurname().matches(".*" + surname + ".*")) {
                stringBuilder.append(student.getName())
                        .append(" ")
                        .append(student.getSurname())
                        .append(" oceny: ")
                        .append(student.grades.toString().replace("[", "").replace(".", ""))
                        .append("\n");
            }
        });

        if (stringBuilder.length() == 0) {
            stringBuilder.append("Nie znaleziono studenta");
        }
        scanner.close();
        System.out.println(stringBuilder.toString());
    }

    public static void sredniaOcen(ArrayList<Student> studentsList) {
        StringBuilder stringBuilder = new StringBuilder();
        studentsList.forEach(student -> {
            int suma = 0;
            for (Integer grade : student.grades) {
                suma += grade;
            }
            double srednia = (double) suma / student.grades.size();
            stringBuilder.append(student.getName())
                    .append(" ")
                    .append(student.getSurname())
                    .append(" ma srednia: ")
                    .append(srednia)
                    .append("\n");
        });
        System.out.println(stringBuilder.toString());
    }


}
