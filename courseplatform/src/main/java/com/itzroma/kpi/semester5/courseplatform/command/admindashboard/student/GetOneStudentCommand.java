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

public class GetOneStudentCommand extends Command {
    public GetOneStudentCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        try {
            StudentService service = new StudentServiceImpl();
            Student student = service.findByEmail(String.valueOf(request.getAttribute("email")));
            request.setAttribute("user", student);
            return new View(JspPage.PROFILE, DispatchType.FORWARD);
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            return View.notFound(request.getRequestURI(), DispatchType.FORWARD);
        }
    }
}
