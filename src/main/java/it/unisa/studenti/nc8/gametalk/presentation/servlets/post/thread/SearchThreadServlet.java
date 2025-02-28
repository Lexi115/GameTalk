package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;
import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/searchThread")
public class SearchThreadServlet extends ThreadServlet {

    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** Pagina default. */
    private static final int DEFAULT_PAGE = 1;

    /** Numero default di thread per pagina. */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Gestisce la richiesta GET per visualizzare la pagina di ricerca Thread.
     *
     * @param req  l'oggetto HttpServletRequest contenente i
     *             parametri della richiesta
     * @param resp l'oggetto HttpServletResponse per inviare
     *             la risposta al client
     * @throws IOException se si verifica un errore.
     */
    @Override
    protected void doGet(
            final HttpServletRequest req,
            final HttpServletResponse resp
    ) throws ServletException, IOException {
        ErrorHandler errorHandler = getErrorHandler();
        ThreadService threadService = getThreadService();
        // Parametri di ricerca.
        String queryParam = req.getParameter("query");
        String categoryParam = req.getParameter("category");
        String dateFromParam = req.getParameter("dateFrom");
        String dateToParam = req.getParameter("dateTo");
        String orderParam = req.getParameter("order");

        // Parametri di paginazione.
        String pageParam = req.getParameter("page");
        int page = DEFAULT_PAGE;
        int pageSize = DEFAULT_PAGE_SIZE;

        try {
            // Pagina.
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (page <= 0) {
                throw new IllegalArgumentException(
                        "Numero pagina deve essere maggiore di 0");
            }

            // Categoria.
            Category category = (categoryParam == null
                    || categoryParam.isBlank())
                    ? null : Category.valueOf(categoryParam);

            // Range date.
            LocalDate dateFrom = (dateFromParam == null
                    || dateFromParam.isBlank())
                    ? null : LocalDate.parse(dateFromParam);
            LocalDate dateTo = (dateToParam == null
                    || dateToParam.isBlank())
                    ? null : LocalDate.parse(dateToParam);

            // Ordine.
            Order order = (orderParam == null
                    || orderParam.isBlank())
                    ? null : Order.valueOf(orderParam);

            List<Thread> threads = threadService.findThreads(
                    queryParam, category, page, pageSize,
                    order, dateFrom, dateTo);

            //Calcolo numero massimo di pagine
            int maxPages = (int) Math.ceil(
                    (double) threadService.getThreadCount(
                            queryParam, category, dateFrom, dateTo) / pageSize);

            req.setAttribute("threads", threads);
            req.setAttribute("category", category);
            req.setAttribute("dateFrom", dateFrom);
            req.setAttribute("dateTo", dateTo);
            req.setAttribute("order", order);
            req.setAttribute("page", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("maxPages", maxPages);
            RequestDispatcher rd = req.getRequestDispatcher("searchThread.jsp");
            rd.forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di ricerca thread", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());

        } catch (IllegalArgumentException | NullPointerException
                 | DateTimeException e) {
            LOGGER.error("Parametri non validi", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
        }
    }
}
