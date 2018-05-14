package org.escalade.model.recherche;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.escalade.model.bean.spot.Departement;

/**
 * Classe de critère de recherche de Spot
 * 
 * @author Oltenos
 *
 */
public class RechercheSpot {

	/**
	 * Critère de recherche sur le nom de la ville
	 */
	@Size(min = 2, max = 100)
	private String nomVille;

	/**
	 * Critère de recherche sur le code postal
	 */
	@Size(min = 5, max = 5)
	private String cpVille;

	/**
	 * Critère de recherche sur le département
	 */
	@Valid
	private Departement departement;

	/**
	 * Critére de recherche sur la difficultéMin
	 */
	@Size(min = 2, max = 2)
	private String difficulteMin;

	/**
	 * Critére de recherche sur la difficultéMin
	 */
	@Size(min = 2, max = 2)
	private String difficulteMax;

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	public String getCpVille() {
		return cpVille;
	}

	public void setCpVille(String cpVille) {
		this.cpVille = cpVille;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public String getDifficulteMin() {
		return difficulteMin;
	}

	public void setDifficulteMin(String difficulteMin) {
		this.difficulteMin = difficulteMin;
	}

	public String getDifficulteMax() {
		return difficulteMax;
	}

	public void setDifficulteMax(String difficulteMax) {
		this.difficulteMax = difficulteMax;
	}

	@Override
	public String toString() {
		return "RechercheSpot [nomVille=" + nomVille + ", cpVille=" + cpVille + ", departement=" + departement
				+ ", difficulteMin=" + difficulteMin + ", difficulteMax=" + difficulteMax + "]";
	}

}