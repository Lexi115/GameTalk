package it.unisa.studenti.nc8.gametalk.presentation.utils.handlers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorHandlerImpl implements ErrorHandler {

    /**
     * Gestisce gli errori mostrando una pagina di errore personalizzata.
     * Imposta il codice di stato e passa il messaggio di errore alla vista.
     *
     * @param req     L'oggetto HTTP request.
     * @param resp    L'oggetto HTTP response.
     * @param status  Il codice di stato HTTP da impostare nella risposta.
     * @param message Il messaggio di errore da mostrare all'utente.
     * @throws ServletException Se si verifica un errore generico.
     * @throws IOException      Se si verifica un errore I/O.
     */
    public void handleError(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final int status,
            final String message
    ) throws ServletException, IOException {
        req.setAttribute("errorMessage", message);
        req.setAttribute("errorCode", status);
        resp.setStatus(status);

        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/errors/error.jsp");
        dispatcher.forward(req, resp);
    }
}
