package com.statisticandreport.StatisticAndReport.Service;

import com.statisticandreport.StatisticAndReport.Entity.Student;
import com.statisticandreport.StatisticAndReport.Repository.StudentRepository;
import com.statisticandreport.StatisticAndReport.RequestDto.StudentRequest;
import com.statisticandreport.StatisticAndReport.ResponseDto.MessageResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class StudentService {

    public final StudentRepository studentRepository;
    public final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public MessageResponse addStudent(StudentRequest studentRequest){
        Student newStudent = modelMapper.map(studentRequest, Student.class);

        // Save the new student using the repository
        studentRepository.save(newStudent);

        return new MessageResponse("Student create successfully!");
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    //Generate All Students
    public String exportReportAllStudent() throws FileNotFoundException, JRException {
        // Obtain the user's home directory dynamically
        String userHome = System.getProperty("user.home");

        // Set the folder name to "StudentReport"
        String folderName = "StudentReport";
        String path = userHome + File.separator + folderName;
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }

        List<Student> student = studentRepository.findAll();

        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:StudentReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(student);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Beejay");

        // Generate a unique filename
        String filename = getUniqueFilename(path, "all_students_report", "pdf");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Always export to PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, filename);
        return "Report generated in path: " + path;
    }

    //Generate Student by Id
    public String exportReportById(long studentId) throws FileNotFoundException, JRException {
        // Obtain the user's home directory dynamically
        String userHome = System.getProperty("user.home");

        // Set the folder name to "StudentReport"
        String folderName = "StudentReport";
        String path = userHome + File.separator + folderName;
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Load file and compile it
            File file = ResourceUtils.getFile("classpath:StudentReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(student));
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Beejay");

            // Generate a unique filename
            String filename = getUniqueFilename(path, "students_report_by_Id", "pdf");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Always export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, filename);

            return "Report generated for student with ID " + studentId + " in path: " + path;
        } else {
            return "Student with ID " + studentId + " not found.";
        }
    }

    //Generate the specific students by Ids
    public String exportReportByIds(List<Long> studentIds) throws FileNotFoundException, JRException {
        // Obtain the user's home directory dynamically
        String userHome = System.getProperty("user.home");

        // Set the folder name to "StudentReport"
        String folderName = "StudentReport";
        String path = userHome + File.separator + folderName;
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }

        List<Student> students = studentRepository.findAllById(studentIds);

        if (!students.isEmpty()) {
            // Load file and compile it
            File file = ResourceUtils.getFile("classpath:StudentReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Beejay");

            // Generate a unique filename
            String filename = getUniqueFilename(path, "students_report_By_Ids", "pdf");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Always export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, filename);

            return "Report generated for students in path: " + filename;
        } else {
            return "No students found with the given IDs.";
        }
    }

    //Avoid Overwrite the file
    //Auto increment for the file name
    private String getUniqueFilename(String path, String baseName, String extension) {
        String filename = baseName + "." + extension;
        String uniqueFilename = filename;
        int counter = 1;

        while (new File(path + File.separator + uniqueFilename).exists()) {
            uniqueFilename = baseName + "_" + counter + "." + extension;
            counter++;
        }

        return path + File.separator + uniqueFilename;
    }



}
