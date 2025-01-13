package it.unisa.studenti.nc8.gametalk.business.enums;

/**
 * Enum che rappresenta i criteri di ordinamento per un post.
 */
public enum Order {
    /** Ordinamento per i più votati (votes DESC). */
    Best,

    /** Ordinamento per i più recenti (creation_date DESC). */
    Newest,

    /** Ordinamento per i più vecchi (creation_date ASC). */
    Oldest
}
