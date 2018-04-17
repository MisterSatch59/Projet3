package org.escalade.model.exception;

/**
 * Classe d'exception levée quand une erreur technique est survenue
 *
 * @author Oltenos
 */
public class TechnicalException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
     * Constructeur.
     *
     * @param pMessage message d'erreur
     */
    public TechnicalException(String pMessage) {
        super(pMessage);
    }
}
