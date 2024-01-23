package com.statisticandreport.StatisticAndReport.Repository;

import com.statisticandreport.StatisticAndReport.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
