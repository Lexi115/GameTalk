package it.unisa.studenti.nc8.gametalk.business.core;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;

public abstract class Functions {

    public static Database getContextDatabase(HttpServlet servlet) {
        Object obj = servlet.getServletContext().getAttribute("db");
        return obj == null ? null : (Database) obj;
    }

}
