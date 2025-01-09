package it.unisa.studenti.nc8.gametalk.auth;

import it.unisa.studenti.nc8.gametalk.user.User;

public interface Authenticator {
    User authenticate(String email, String password);
}
