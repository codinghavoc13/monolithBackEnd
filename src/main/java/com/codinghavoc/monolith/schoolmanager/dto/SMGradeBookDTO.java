package com.codinghavoc.monolith.schoolmanager.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class SMGradeBookDTO {
    public List<String> assignmentTypes;
    public List<String> courseNames;
    public ArrayList<HashMap<String,LocalDate>> weeks;
    public List<SMIndividualGradeDTO> gradeDtos;
    public List<Integer> periods;

    public SMGradeBookDTO(){
        assignmentTypes = new ArrayList<>();
        courseNames = new ArrayList<>();
        gradeDtos = new ArrayList<>();
        periods = new ArrayList<>();
        weeks = new ArrayList<>();
    }

    public void addAssignmentType(String a){
        if(!assignmentTypes.contains(a)) assignmentTypes.add(a);
    }

    public void addCourseName(String c){
        if(!courseNames.contains(c)) courseNames.add(c);
    }

    public void addPeriod(Integer p){
        if(!periods.contains(p)) periods.add(p);
    }

    public void addWeeksListEntry(LocalDate ld){
        HashMap<String,LocalDate> temp;
        temp = buildStartEndPair(ld);
        if(!weeks.contains(temp))
            weeks.add(temp);
    }

    private static HashMap<String, LocalDate> buildStartEndPair(LocalDate ld){
        HashMap<String,LocalDate> result = new HashMap<>();
        result.put("start", findStartOfWeek(ld));
        result.put("end", findEndOfWeek(ld));
        return result;
    }

    private static LocalDate findStartOfWeek(LocalDate ld){
        LocalDate working = ld;
        DayOfWeek day = ld.getDayOfWeek();
        while(day != DayOfWeek.MONDAY){
            working = working.minusDays(1);
            day = working.getDayOfWeek();
        }
        return working;
    }

    private static LocalDate findEndOfWeek(LocalDate ld){
        LocalDate working = ld;
        DayOfWeek day = ld.getDayOfWeek();
        while(day != DayOfWeek.FRIDAY){
            working = working.plusDays(1);
            day = working.getDayOfWeek();
        }
        return working;
    }
}
