package com.p.parent.child.relation.topics.ParentChildRelationsTopics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.p.parent.child.relation.topics.ParentChildRelationsTopics.dto.TopicNode;

//@Repository
@Service
public class TopicNodeRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public int save(TopicNode customer) {
		
		System.out.println(customer);
		if(customer.getParentId()<=0) {
			return jdbcTemplate.update(
	                "insert into t_views (title) values(?)",
	                customer.getTitle());
		}else {
			return jdbcTemplate.update(
	                "insert into t_views (title, parent_id) values(?,?)",
	                customer.getTitle(), customer.getParentId());
		}
        
    }
	
	public int update(TopicNode customer) {
        return jdbcTemplate.update(
                "update t_views set title=?, parent_id=? where id=?",
                customer.getTitle(), customer.getParentId(),customer.getId());
    }
	
	public TopicNode findById(int id) {

        String sql = "SELECT * FROM t_views WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new TopicNode(
                        rs.getInt("id"),                        
                        rs.getInt("parent_id"),
                        rs.getString("title")
                ));

    }
	
	public List<TopicNode> findTopElements() {

        String sql = "SELECT * FROM t_views WHERE PARENT_ID IS NULL";

        List<TopicNode> customers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
        	TopicNode obj = new TopicNode();
        	System.out.println(row);
            obj.setId(((Integer) row.get("ID")).intValue());
            obj.setTitle((String) row.get("TITLE"));
            int parentId=(row.get("PARENT_ID"))!=null?((Integer)(row.get("PARENT_ID"))):0;
            obj.setParentId(parentId); // Spring returns BigDecimal, need convert
            customers.add(obj);
        }

        return customers;

    }
	
	public List<TopicNode> findChildrenByParentId(int pId) {

        String sql = "SELECT * FROM t_views WHERE PARENT_ID = ?";

        List<TopicNode> customers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{pId});

        for (Map<String, Object> row : rows) {
        	TopicNode obj = new TopicNode();
        	System.out.println(row);
            obj.setId(((Integer) row.get("ID")).intValue());
            obj.setTitle((String) row.get("TITLE"));
            int parentId=(row.get("PARENT_ID"))!=null?((Integer)(row.get("PARENT_ID"))):0;
            obj.setParentId(parentId); // Spring returns BigDecimal, need convert
            customers.add(obj);
        }

        return customers;

    }
	
	public List<TopicNode> findAll() {

        String sql = "SELECT * FROM t_views";

        List<TopicNode> customers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
        	TopicNode obj = new TopicNode();
        	System.out.println(row);
            obj.setId(((Integer) row.get("ID")).intValue());
            obj.setTitle((String) row.get("TITLE"));
            int parentId=(row.get("PARENT_ID"))!=null?((Integer)(row.get("PARENT_ID"))):0;
            obj.setParentId(parentId); // Spring returns BigDecimal, need convert
            customers.add(obj);
        }

        return customers;
    }
	
	public List<TopicNode> findAllWithPath() {

        String sql = "WITH RECURSIVE category_path (id, title,parent_id, PATH) AS\r\n" + 
        		"(\r\n" + 
        		"  SELECT id, title,parent_id, title AS PATH\r\n" + 
        		"    FROM t_views\r\n" + 
        		"    WHERE parent_id IS NULL\r\n" + 
        		"  UNION ALL\r\n" + 
        		"  SELECT c.id, c.title,c.parent_id, CONCAT(cp.path, ' > ', c.title)\r\n" + 
        		"    FROM category_path AS cp JOIN t_views AS c\r\n" + 
        		"      ON cp.id = c.parent_id\r\n" + 
        		")\r\n" + 
        		"SELECT * FROM category_path\r\n" + 
        		"ORDER BY PATH;";

        List<TopicNode> customers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
        	TopicNode obj = new TopicNode();

            obj.setId(((Integer) row.get("ID")).intValue());
            obj.setTitle((String) row.get("TITLE"));
            obj.setPath((String) row.get("PATH"));
            int parentId=(row.get("PARENT_ID"))!=null?((Integer)(row.get("PARENT_ID"))):0;
            obj.setParentId(parentId); // Spring returns BigDecimal, need convert
            customers.add(obj);
        }

        return customers;
    }
	
	

}
