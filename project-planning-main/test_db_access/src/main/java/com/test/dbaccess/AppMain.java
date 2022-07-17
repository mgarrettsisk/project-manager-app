package com.test.dbaccess;

import com.projman.dbaccess.*;

import java.util.Calendar;
import java.util.Date;

public class AppMain {
    private static String url = "jdbc:mysql://localhost:3306/project_manager?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static String user = "kim";
    private static String password = "test1234";

    public static void main(String[] args) {

        //-- Select examples.
        SelectData.url = url;
        SelectData.user = user;
        SelectData.password = password;

        SelectData select = new SelectData();
        //-- Get all phases.
        select.selectPhase();
        //-- Get all risk statuses.
        select.selectRiskStatus();
        //-- Get all projects.
        select.selectProject();
        //-- Get all members for a project.
        select.selectMember(1);
        //-- Get all risks for a project.
        select.selectRisk(14);
        //-- Get all requirements for a project.
        select.selectRequirement(1);
        //-- Get all time;
        select.selectProjectTime(1);

        //-- Insert examples.
        InsertData.url = url;
        InsertData.user = user;
        InsertData.password = password;

        InsertData insert = new InsertData();
        // examples: uncomment to run.
        //-- Insert new project.
        //insert.addProject(new Project("My Project", "Project about nothing", "Kim V."));
        //-- Insert new member.
        //insert.addMember(new Member("John Smith", 1, 1));
        //-- Insert new risk.
        //insert.addRisk(new Risk("Technology problems", 2, 14));
        //-- Insert project time.
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 31, 59, 59, 59);
        Date wkdate = calendar.getTime();
        //insert.addProjectTime(new ProjectTime(8, 1, 2, wkdate, 1));

        //-- Update examples.
        UpdateData update = new UpdateData();
        UpdateData.url = url;
        UpdateData.user = user;
        UpdateData.password = password;
        //-- Update a project.
        update.updateProject(new Project(14,"Java project",
                "Project about Talend", "Kim VanAsselberg"));
        //-- Update a member. (Active=0 will make member inactive)
        update.updateMember(new Member(2, 1, 1));
        //-- Update a risk.
        update.updateRisk(new Risk(1, 1,14));

    }
}
