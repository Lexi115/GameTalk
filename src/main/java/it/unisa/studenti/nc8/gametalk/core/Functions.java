package it.unisa.studenti.nc8.gametalk.core;

import it.unisa.studenti.nc8.gametalk.persistence.Database;
import jakarta.servlet.http.HttpServlet;

public abstract class Functions {

    public static Database getContextDatabase(HttpServlet servlet) {
        Object obj = servlet.getServletContext().getAttribute("db");
        return obj == null ? null : (Database) obj;
    }

}
