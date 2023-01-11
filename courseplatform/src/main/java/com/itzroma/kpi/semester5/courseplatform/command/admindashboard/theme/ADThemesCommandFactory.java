package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.theme;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.NotFoundCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADThemesCommandFactory extends CommandFactory {
    private static final String AD_THEMES_COMMAND_REGEX =
            "(?<=/admin-dashboard/themes).*(?=\\?)|(?<=/admin-dashboard/themes).*(?=$)";

    private static final String THEME_NAME_EXTRACTION_REGEX = "/([\\w%]+)";

    private static final String THEME_UPDATE_REGEX = "^/([\\w%]+)/update$";
    private static final String THEME_DELETE_REGEX = "^/([\\w%]+)/delete$";

    public ADThemesCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(AD_THEMES_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        if (action.equals("")) {
            return defineCommand(false, GetAllThemesCommand.class, InternalServerErrorCommand.class);
        }
        if (action.equals("/new")) {
            return defineCommand(false, GetThemeCreateCommand.class, PostThemeCreateCommand.class);
        }
        if (action.matches(THEME_UPDATE_REGEX)) {
            return defineCommand(true, GetThemeUpdateCommand.class, PostThemeUpdateCommand.class);
        }
        if (action.matches(THEME_DELETE_REGEX)) {
            return defineCommand(true, NotFoundCommand.class, PostThemeDeleteCommand.class);
        }

        return invalidCommand();
    }

    private Command defineCommand(boolean extractName, Class<? extends Command> get, Class<? extends Command> post) {
        if (extractName) {
            Matcher matcher = Pattern.compile(THEME_NAME_EXTRACTION_REGEX)
                    .matcher(Objects.requireNonNull(action));
            if (matcher.find()) {
                request.setAttribute("name", matcher.group(1));
            } else {
                return new NotFoundCommand(request, response);
            }
        }
        return request.getMethod().equals("GET")
                ? instantiateCommand(get)
                : instantiateCommand(post);
    }
}
