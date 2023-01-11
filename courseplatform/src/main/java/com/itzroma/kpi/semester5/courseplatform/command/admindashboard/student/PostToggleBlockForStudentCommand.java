package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.student;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostToggleBlockForStudentCommand extends Command {
    public PostToggleBlockForStudentCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        DispatchType dt;
        StudentService service = new StudentServiceImpl();
        try {
            Student student = service.findByEmail(String.valueOf(request.getAttribute("email")));
            service.toggleBlock(student);
            dt = DispatchType.REDIRECT;
        } catch (ServiceException ex) {
            request.setAttribute("students", service.findAll());
            request.setAttribute("error", ex.getMessage());
            dt = DispatchType.FORWARD;
        }
        return new View(JspPage.AD_STUDENTS, dt);
    }
}