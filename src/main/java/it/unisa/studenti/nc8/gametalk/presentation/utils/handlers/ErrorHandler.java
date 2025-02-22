package it.unisa.studenti.nc8.gametalk.presentation.utils.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ErrorHandler {

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
    void handleError(
            HttpServletRequest req,
            HttpServletResponse resp,
            int status,
            String message
    ) throws ServletException, IOException;
}
