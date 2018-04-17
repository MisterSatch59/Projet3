package org.escalade.model.exception;

/**
 * Classe d'exception levée quand une erreur fonctionnelle survient
 *
 * @author Oltenos
 */
public class FunctionalException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
     * Constructeur.
     *
     * @param pMessage message d'erreur
     */
    public FunctionalException(String pMessage) {
        super(pMessage);
    }
}
