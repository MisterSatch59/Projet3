package org.escalade.business;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.model.bean.spot.Departement;
import org.escalade.model.bean.spot.Spot;
import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.topo.Emprunt;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.escalade.model.bean.topo.Topo;
import org.escalade.model.bean.utilisateur.Utilisateur;

@Named
public class Test {
	
	@Inject
	private DaoFactory daoFactory;
	
	public void test() {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Departement d = new Departement(null,"Nord");
		
		Set<ConstraintViolation<Departement>> param = validator.validate(d);
		for (ConstraintViolation<Departement> constraintViolation : param) {
			System.out.println("constraintViolation : " + constraintViolation.getConstraintDescriptor().getMessageTemplate());
		}
		System.out.println(d.toString());
		
		
		//--
		
		Utilisateur max = daoFactory.getUtilisateurDao().getUtilisateur("Max");
		System.out.println(max.getPseudo());
		System.out.println(max.getMail());
		System.out.println(max.getAvatar());
		System.out.println(max.isAdmin());
		
		System.out.println("---");
		List<Commentaire> comm = daoFactory.getCommentaireDao().getListCommentaire(1);
		for (Commentaire commentaire : comm) {
			System.out.println(commentaire.getId());
			System.out.println(commentaire.getAuteur().getPseudo());
			System.out.println(commentaire.getTitre());
			System.out.println(commentaire.getDate());
			List<String> para = commentaire.getListParagraphes();
			for (String s : para) {
				System.out.println(s);
			}
		}
		System.out.println("---");
		Spot spot = daoFactory.getSpotDao().getSpot(1);
		System.out.println(spot.getId());
		System.out.println(spot.getNom());
		System.out.println(spot.getDifficulteMax());
		System.out.println(spot.getDifficulteMin());
		System.out.println(spot.getHauteurMax());
		System.out.println(spot.getHauteurMin());
		System.out.println(spot.getLatitude());
		System.out.println(spot.getLongitude());
		System.out.println(spot.getNbSecteur());
		System.out.println(spot.getNbVoie());
		System.out.println(spot.getVille().getNom());
		System.out.println(spot.getVille().getId());
		System.out.println(spot.getVille().getCP());
		System.out.println(spot.getVille().getDepartement().toString());
		System.out.println(spot.isAdapteEnfants());
		System.out.println(spot.isOuvert());
		System.out.println(spot.getAuteur().getAvatar());
		if(spot.getPresentation()!=null) {
			System.out.println(spot.getPresentation().getTitre());
			System.out.println(spot.getPresentation().getListParagraphes().get(0));
		}
		System.out.println(spot.getTypes().get(0));
		System.out.println(spot.getProfils().get(0));
		System.out.println(spot.getOrientations().get(0));
		if(spot.getListPhotos()!=null && !spot.getListPhotos().isEmpty()) {
			System.out.println(spot.getListPhotos().get(0));
		}
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		Topo topo = daoFactory.getTopoDao().getTopo("Grimper autour de Toulon");
		System.out.println(topo.getTitre());
		System.out.println(topo.getDescription());
		List<Spot> listspot = topo.getListSpot();
		for (Spot spot2 : listspot) {
			System.out.println(spot2.getNom());
		}
		System.out.println(topo.getListPhotos());
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		List<ExemplaireTopo> exemplTopos = daoFactory.getExemplaireTopoDao().getListExemplaireTopo("SingEscalade");
		for (ExemplaireTopo exemplaireTopo : exemplTopos) {
			System.out.println(exemplaireTopo.getId());
			System.out.println(exemplaireTopo.getTopo());
			System.out.println(exemplaireTopo.getProprietaire());
			System.out.println(exemplaireTopo.getCondition().getTitre());
		}
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		Emprunt emprunt = daoFactory.getEmpruntDao().getListEmprunt("Max").get(0);
		System.out.println(emprunt.getDateDebut());
		System.out.println(emprunt.getDateFin());
		System.out.println(emprunt.getEmprunteur().getPseudo());
		System.out.println(emprunt.getExemplaire().getId());
		
	}
}
