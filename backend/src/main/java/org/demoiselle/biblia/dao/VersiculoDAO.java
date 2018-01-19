package org.demoiselle.biblia.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.inject.Inject;
import org.demoiselle.biblia.entity.Versiculo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.lucene.search.Sort;
import org.demoiselle.biblia.constants.ItemFTS;
import org.demoiselle.biblia.constants.ResponseFTS;
import org.demoiselle.biblia.ia.nlp.NLPtools;
import org.demoiselle.jee.crud.AbstractDAO;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class VersiculoDAO extends AbstractDAO< Versiculo, Integer> {

    private static final Logger LOG = getLogger(VersiculoDAO.class.getName());

    @Inject
    private NLPtools nlp;

    @PersistenceContext(unitName = "bibliaPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param nome
     * @return
     */
    public ResponseFTS listarVersiculosFTS(String nome) {
        ResponseFTS response = new ResponseFTS();

        FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(getEntityManager());
        QueryBuilder qb = fullTextEm.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Versiculo.class)
                .get();

        org.apache.lucene.search.Query luceneQuery;

        if (nome.split(" ").length > 1) {
            luceneQuery = qb.phrase()
                    .onField("livro")
                    .andField("texto").boostedTo(2)
                    .andField("versao")
                    .andField("testamento")
                    .sentence(nome).createQuery();
        } else {
            luceneQuery = qb.keyword()
                    .onField("livro")
                    .andField("texto").boostedTo(2)
                    .andField("versao")
                    .andField("testamento")
                    .matching(nome).createQuery();
        }

        FullTextQuery fullTextQuery = fullTextEm.createFullTextQuery(luceneQuery, Versiculo.class);
        fullTextQuery.setProjection(FullTextQuery.SCORE, FullTextQuery.THIS);

        StringBuilder sb = new StringBuilder();

        fullTextQuery.setSort(Sort.RELEVANCE).getResultList().forEach((object) -> {
            try {

                Float score = (Float) ((Object[]) object)[0];
                Versiculo versiculo = (Versiculo) ((Object[]) object)[1];
                ItemFTS fts = new ItemFTS();
                fts.setVersiculo(versiculo);
                fts.setOcorrencias(score);
                sb.append(versiculo.getTexto()).append(" ");

                response.getItens().add(fts);
            } catch (Exception ex) {
                Logger.getLogger(VersiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        response.getPersons().putAll(nlp.persons(sb.toString()));
        return response;

    }

    public void reindex() {
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                .getFullTextEntityManager(getEntityManager());
        try {
            fullTextEntityManager.createIndexer().optimizeOnFinish(Boolean.TRUE).startAndWait();
        } catch (InterruptedException e) {
            LOG.severe(e.getMessage());
        }
    }

    public List<Versiculo> listAll() {
        return getEntityManager().createQuery("SELECT v FROM Versiculo v Order by v.id", Versiculo.class).getResultList();
    }
}
