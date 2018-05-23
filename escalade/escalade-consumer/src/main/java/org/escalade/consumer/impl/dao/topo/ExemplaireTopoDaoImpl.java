package org.escalade.consumer.impl.dao.topo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.escalade.consumer.contract.dao.DaoFactory;
import org.escalade.consumer.contract.dao.topo.ExemplaireTopoDao;
import org.escalade.consumer.impl.dao.AbstractDaoImpl;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.topo.ExemplaireTopo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Implementation de ExemplaireTopoDao
 * 
 * @author Oltenos
 *
 */
@Named("exemplaireTopoDao")
public class ExemplaireTopoDaoImpl extends AbstractDaoImpl implements ExemplaireTopoDao {
	private static final Logger LOGGER = LogManager.getLogger(ExemplaireTopoDaoImpl.class);

	@Inject
	private RowMapper<ExemplaireTopo> exemplaireTopoRM;
	@Inject
	private DaoFactory daoFactory;

	@Override
	public List<ExemplaireTopo> getListExemplaireTopo(String pseudoProprietaire) {
		LOGGER.traceEntry("pseudoProprietaire = " + pseudoProprietaire);

		if (pseudoProprietaire != null) {
			String vSQL = "SELECT * FROM public.exemplaire_topo WHERE pseudo_proprietaire = ?";

			JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

			List<ExemplaireTopo> listExemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, pseudoProprietaire);

			LOGGER.traceExit(listExemplaireTopo);
			return listExemplaireTopo;
		}

		LOGGER.traceExit(null);
		return null;

	}

	@Override
	public List<ExemplaireTopo> getListExemplaireTitreTopo(String titreTopo, Date debut, Date fin) {
		LOGGER.traceEntry("titreTopo = " + titreTopo + " debut = " + debut + " fin = " + fin);
		
		//Création des object java.sql.Date du début et fin de période
		String format = "yyyy-MM-dd";
		SimpleDateFormat formater = new java.text.SimpleDateFormat(format);

		java.sql.Date datedebut = java.sql.Date.valueOf(formater.format(debut));
		java.sql.Date datefin = java.sql.Date.valueOf(formater.format(fin));

		if (titreTopo != null) {
			
			//Chargement de tout les exemplaire de topo correspondant
			String vSQL = "SELECT * FROM public.exemplaire_topo WHERE exemplaire_topo.titre_topo = :titreTopo";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titreTopo", titreTopo);

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			List<ExemplaireTopo> listExemplaireTopo = vJdbcTemplate.query(vSQL, vParams, exemplaireTopoRM);
			
			// Suppression de la liste les topos non disponibles au dates souhaités
			List<ExemplaireTopo> exemplairesASuppr=new ArrayList<ExemplaireTopo>();
			for (ExemplaireTopo exemplaireTopo : listExemplaireTopo) {
				String vSQL2 = "SELECT COUNT(*) FROM public.emprunt WHERE exemplaire_topo_id = :exemplaireId AND (debut <=  :dateFin AND fin >= :dateDebut)";

				NamedParameterJdbcTemplate vJdbcTemplate2 = new NamedParameterJdbcTemplate(getDataSource());

				MapSqlParameterSource vParams2 = new MapSqlParameterSource();
				vParams2.addValue("exemplaireId", exemplaireTopo.getId());
				vParams2.addValue("dateFin", datefin);
				vParams2.addValue("dateDebut", datedebut);

				int nbIntersection = vJdbcTemplate2.queryForObject(vSQL2, vParams2, Integer.class);

				if (nbIntersection > 0) {
					exemplairesASuppr.add(exemplaireTopo);
				}
			}
			listExemplaireTopo.removeAll(exemplairesASuppr);

			LOGGER.traceExit(listExemplaireTopo);
			return listExemplaireTopo;
		}

		LOGGER.traceExit(null);
		return null;
	}

	@Override
	public ExemplaireTopo getExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);

		String vSQL = "SELECT * FROM public.exemplaire_topo WHERE id = ?";

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource());

		List<ExemplaireTopo> exemplaireTopo = vJdbcTemplate.query(vSQL, exemplaireTopoRM, id);

		if (exemplaireTopo.isEmpty()) {
			LOGGER.traceExit("null");
			return null;
		} else {
			LOGGER.traceExit(exemplaireTopo.get(0));
			return exemplaireTopo.get(0);
		}
	}

	@Override
	public ExemplaireTopo createExemplaireTopo(ExemplaireTopo exemplaireTopo) {
		LOGGER.traceEntry("exTopo = " + exemplaireTopo);

		if (exemplaireTopo != null) {
			ZoneTexte condition = daoFactory.getZoneTexteDao().createZoneTexte(exemplaireTopo.getCondition());

			String vSQL = "INSERT INTO public.exemplaire_topo (titre_topo, pseudo_proprietaire, condition_id) VALUES (:titreTopo, :pseudoProprietaire, :conditionId)";

			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("titreTopo", exemplaireTopo.getTopo().getTitre());
			vParams.addValue("pseudoProprietaire", exemplaireTopo.getProprietaire().getPseudo());
			vParams.addValue("conditionId", condition.getId());

			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

			vJdbcTemplate.update(vSQL, vParams);
		}

		LOGGER.traceExit(exemplaireTopo);
		return exemplaireTopo;
	}

	@Override
	public void deleteExemplaireTopo(int id) {
		LOGGER.traceEntry("id = " + id);

		ZoneTexte condition = this.getExemplaireTopo(id).getCondition();

		String vSQL = "DELETE FROM public.exemplaire_topo WHERE id = :id";

		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());

		vJdbcTemplate.update(vSQL, vParams);

		if (condition != null) {
			daoFactory.getZoneTexteDao().deleteZoneTexte(condition.getId());
		}

		LOGGER.traceExit();
	}

}
