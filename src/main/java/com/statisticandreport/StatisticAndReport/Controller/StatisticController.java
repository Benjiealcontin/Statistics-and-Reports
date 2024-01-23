package com.statisticandreport.StatisticAndReport.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {

    //Bar Graph
    @GetMapping("/displayBarGraph")
    public ModelAndView barGraph(Model model){
        Map<String, Integer> surveyMap = new LinkedHashMap<>();
        surveyMap.put("Java", 75);
        surveyMap.put("Dev oops", 25);
        surveyMap.put("Python", 20);
        surveyMap.put(".Net", 15);
        model.addAttribute("surveyMap", surveyMap);
        return new ModelAndView("barGraph");
    }

    //Pie Chart
    @GetMapping("/displayPieChart")
    public ModelAndView pieChart(Model model) {
        model.addAttribute("IT", 30);
        model.addAttribute("IS", 50);
        model.addAttribute("BSA", 20);
        return new ModelAndView("pieChart");
    }
}
