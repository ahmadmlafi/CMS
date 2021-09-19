package com.Lafi.CMS.Controller;

import com.Lafi.CMS.DAO.AdminDAO;
import com.Lafi.CMS.DataBaseAccess.StudentAccess;
import com.Lafi.CMS.Models.Instructors.InstructorBasic;
import com.Lafi.CMS.Models.Students.StudentBasic;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class AdminController {

    private AdminDAO adminDAO;

    public AdminController(AdminDAO adminDAO) {
        super();
        this.adminDAO = adminDAO;
    }

    // handler method to handle list students and return mode and view
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", adminDAO.getAllStudents());
        return "admin";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {

        // create student object to hold student form data
        StudentBasic student = new StudentBasic();
        model.addAttribute("student", student);
        return "create_student";

    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") StudentBasic student) {
        adminDAO.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", new StudentAccess().readByID(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") StudentBasic student,
                                Model model) {

        // get student from database by id
        StudentBasic existingStudent = new StudentAccess().readByID(id);
        existingStudent.setID(id);
        existingStudent.setEmail(student.getEmail());
        existingStudent.setEmail(student.getDOB());
        existingStudent.setMajor(student.getMajor());

        // save updated student object
        adminDAO.editStudent(existingStudent);
        return "redirect:/students";
    }

    // handler method to handle delete student request

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        adminDAO.deleteStudent(id);
        return "redirect:/students";
    }

    // handler method to handle list students and return mode and view
    @GetMapping("/students")
    public String listInstructors(Model model) {
        model.addAttribute("instructors", adminDAO.getAllInstructor());
        return "instructors";
    }

    @GetMapping("/instructors/new")
    public String createinstructorForm(Model model) {

        // create student object to hold student form data
        InstructorBasic instructor = new InstructorBasic();
        model.addAttribute("instructor", instructor);
        return "addInstructor";

    }

    @PostMapping("/instructor")
    public String saveStudent(@ModelAttribute("instructor") InstructorBasic instructor) {
        adminDAO.addInstructor(instructor);
        return "redirect:/instructor";
    }

    @GetMapping("/instructor/edit/{id}")
    public String editInstructorForm(@PathVariable Long id, Model model) {
        model.addAttribute("instructor", new StudentAccess().readByID(id));
        return "editInstructor";

    }

}

