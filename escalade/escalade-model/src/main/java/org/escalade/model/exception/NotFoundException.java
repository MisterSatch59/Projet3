package org.escalade.model.exception;

/**
 * Classe d'exception levée quand l'objet métier demandé n'a pas été trouvé
 *
 * @author Oltenos
 */
public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

    /**
     * Constructeur.
     *
     * @param pMessage message d'erreur
     */
    public NotFoundException(String pMessage) {
        super(pMessage);
    }
}
