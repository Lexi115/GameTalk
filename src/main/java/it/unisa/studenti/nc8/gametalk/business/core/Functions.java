package it.unisa.studenti.nc8.gametalk.business.core;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Functions {

    public static Database getContextDatabase(HttpServlet servlet) {
        Object obj = servlet.getServletContext().getAttribute("db");
        return obj == null ? null : (Database) obj;
    }

    public static boolean matchesRegex(final String regex, final String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
