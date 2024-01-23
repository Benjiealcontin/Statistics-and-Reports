package com.statisticandreport.StatisticAndReport.Controller;

import com.statisticandreport.StatisticAndReport.Entity.Student;
import com.statisticandreport.StatisticAndReport.RequestDto.StudentRequest;
import com.statisticandreport.StatisticAndReport.Service.StudentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //Create Student
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest){
        return ResponseEntity.ok(studentService.addStudent(studentRequest));
    }

    //GetAllStudent
    @GetMapping("/all")
    public ResponseEntity<?> getAllStudent(){
        List<Student> studentList = studentService.getAllStudents();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/report")
    public String generateReport() throws FileNotFoundException, JRException {
        return studentService.exportReportAllStudent();
    }

    @GetMapping("/report/{id}")
    public String generateReport2(@PathVariable long id) throws FileNotFoundException, JRException {
        return studentService.exportReportById(id);
    }

    @GetMapping("/report")
    public String generateReport(@RequestParam List<Long> studentIds) {
        try {
            return studentService.exportReportByIds(studentIds);
        } catch (FileNotFoundException | JRException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return "Error generating report.";
        }
    }
}
