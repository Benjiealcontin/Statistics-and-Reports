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

    //Report for All Students
    @GetMapping("/report")
    public String generateReportForAllStudent() throws FileNotFoundException, JRException {
        return studentService.exportReportAllStudent();
    }

    // Report for Student by Id
    @GetMapping("/report/{id}")
    public String generateReportForSpecificStudent(@PathVariable long id) throws FileNotFoundException, JRException {
        return studentService.exportReportById(id);
    }

    //Report for Students by Ids
    @GetMapping("/report/student")
    public String generateReportForSelectiveStudent(@RequestParam List<Long> Ids) {
        try {
            return studentService.exportReportByIds(Ids);
        } catch (FileNotFoundException | JRException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return "Error generating report.";
        }
    }
}
