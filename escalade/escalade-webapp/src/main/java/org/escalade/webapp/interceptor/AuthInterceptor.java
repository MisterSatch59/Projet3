package org.escalade.webapp.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Interceptor permettant de vérifier que l'utilisateur est bien connecté
 */
public class AuthInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	/**
	 * Vérifie que le parametre de session "utilisateur" n'est pas null et donc que
	 * l'utilisateur est connecté.
	 */
	@Override
	public String intercept(ActionInvocation pInvocation) throws Exception {
		String vResult;
		if (pInvocation.getInvocationContext().getSession().get("utilisateur") != null) {
			vResult = pInvocation.invoke();
		} else {
			vResult = "error-forbidden";
		}
		return vResult;
	}
}
