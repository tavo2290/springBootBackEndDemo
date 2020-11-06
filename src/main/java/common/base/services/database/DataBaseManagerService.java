package common.base.services.database;

import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

@Service
public class DataBaseManagerService {

    @PersistenceContext
    private EntityManager entityManager;

    public Object executeNativeSingleObjectQuery(String query, Map parameterMap){
        Query executedQuery = entityManager.createNativeQuery(query, Tuple.class);
        if(!parameterMap.isEmpty()) appendParametersToQuery(executedQuery,parameterMap);
        return executedQuery.getSingleResult();
    }

    public List<Tuple> executeQueryWithTupleList(String query, Map parameterMap){
        Query executedQuery = entityManager.createNativeQuery(query, Tuple.class);
        if(!parameterMap.isEmpty()) appendParametersToQuery(executedQuery,parameterMap);
        return executedQuery.getResultList();
    }

    public void appendParametersToQuery(Query query, Map<String, Object> parameters){
        parameters.forEach((key, value) -> query.setParameter(key,value));
    }

    public String composeSql(String baseTable, Map selectMap, Map joinMap, Map whereMap, Map orderMap, Map groupMap){
        String sql = "";
        sql += selectMap.isEmpty() ? "SELECT *" : "SELECT "+iterateMapForQuery(selectMap,",");
        sql += "\n FROM "+baseTable;
        sql += joinMap.isEmpty() ? "" : "\n"+iterateMapForQuery(joinMap,"\n");
        sql += whereMap.isEmpty() ? "" : "\n WHERE "+iterateMapForQuery(whereMap," AND ");
        sql += orderMap.isEmpty() ? "" : "\n ORDER BY "+iterateMapForQuery(orderMap,",");
        sql += groupMap.isEmpty() ? "" : "\n GROUP BY "+iterateMapForQuery(groupMap,",");
        return sql;
    }

    public String iterateMapForQuery(Map<String,String> queryMap, String separator){
        String querySegment = "";
        int mapSize = queryMap.size()-1;
        int currentMapPosition = 0;
        for (String mapValue : queryMap.values()) {
            querySegment += mapValue + ((currentMapPosition<mapSize) ? separator : "");
            currentMapPosition++;
        }
        return querySegment;
    }
}
