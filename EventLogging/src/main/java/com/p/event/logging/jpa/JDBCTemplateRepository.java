package com.p.event.logging.jpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.p.event.logging.pojo.Event;
import com.p.event.logging.pojo.EventLabelRelation;
import com.p.event.logging.pojo.Label;

@Service
public class JDBCTemplateRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<String, Object> addEvent(Event event) {
		List<Event> lw = new ArrayList<Event>();
		lw.add(event);
		int[][] updateCounts = jdbcTemplate.batchUpdate(
				"INSERT INTO t_event(title, detail, event_date) VALUES (?,?,?) ",
				lw, 1, new ParameterizedPreparedStatementSetter<Event>() {
					public void setValues(PreparedStatement ps, Event argument) throws SQLException {
						int i = 1;
						
						ps.setString(i++, argument.getTitle());
						ps.setString(i++, argument.getDetail());
						ps.setObject(i++, argument.getEventDate());

					}
				});
		Map<String, Object> map=new HashMap<>();
		map.put("totalUpdatedRows", 1);
		return map;
	}

	public List<Event> allEvent() {
		
		String sql = "SELECT id, title, detail, event_date, created_on, updated_on, enabled FROM t_event";

		System.out.println(sql);

		List<Event> reportData = new ArrayList<>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

//		Format f = new SimpleDateFormat("dd-MMM-yyyy");

		for (Map<String, Object> row : rows) {
			Event event=new Event();
			event.setId((String) row.get("id"));
			event.setTitle((String) row.get("title"));
			event.setDetail((String) row.get("detail"));
			event.setEventDate((java.sql.Date) row.get("event_date"));
			event.setCreatedOn(Date.from(((java.sql.Timestamp) row.get("created_on")).toInstant()));
			event.setUpdatedOn(Date.from(((java.sql.Timestamp) row.get("updated_on")).toInstant()));
			event.setId((String) row.get("id"));
			
			reportData.add(event);
		}
		return reportData;
	}

	public Event getEventById(String id) {
		String sql = "SELECT id, title, detail, event_date, created_on, updated_on, enabled FROM t_event where id = ?";
		jdbcTemplate.query(sql, new Object[] {id}, new ResultSetExtractor<Event>(){

			@Override
			public Event extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					Event event=new Event();
					event.setId( rs.getString("id"));
					event.setTitle( rs.getString("title"));
					event.setDetail( rs.getString("detail"));
					event.setEventDate( rs.getDate("event_date"));
					event.setCreatedOn(Date.from(( rs.getTimestamp("created_on")).toInstant()));
					event.setUpdatedOn(Date.from(( rs.getTimestamp("updated_on")).toInstant()));
					event.setId( rs.getString("id"));
				}else {
					throw new SQLException("No Event data found for given id : "+id);
				}
				return null;
			}
			
		});
		return null;
	}

	public Event updateEventById(String id, Event event) {
//		StringBuilder sbUserRegQuery = new StringBuilder();
//		sbUserRegQuery.append("INSERT INTO users (username, password , email, enabled, datetime_condo_changed, datetime_last_login");
//		if(user.getCondo_id()!=null) sbUserRegQuery.append(", condo_id");
//		if(user.getProvider()!=null) sbUserRegQuery.append(", provider");
//		sbUserRegQuery.append(")");
//		sbUserRegQuery.append(" VALUES ( ?, ?, ? , ? , ?, ?");
//		if(user.getCondo_id()!=null) sbUserRegQuery.append(", ?");
//		if(user.getProvider()!=null) sbUserRegQuery.append(", ?");
//		sbUserRegQuery.append(");");
//
//		ArrayList<Object> params = new ArrayList<Object>();
//		params.add(user.getUsername());
//		params.add(PasswordEncoderGenerator.main(user.getPassword()));
//		params.add(user.getEmail());
//		params.add(enabled);
//		params.add(user.getDatetime_condo_changed());
//		params.add(currentTimeStamp);
//		if(user.getCondo_id()!=null) params.add(user.getCondo_id());
//		if(user.getProvider()!=null) params.add(user.getProvider());
//
//		int row = getJdbcTemplate().update(sbUserRegQuery.toString(), params.toArray());
//		jdbcTemplate.update(sql, args);
		return null;
	}

	public Map<String, Object> addLabel(Label label) {
		
		return null;
	}

	public List<Label> allLabel() {
		
		return null;
	}

	public Label getLabelById(String id) {
		
		return null;
	}

	public Label updateLabelById(String id, Label label) {
		
		return null;
	}

	public Map<String, Object> addEventLabelRelation(EventLabelRelation relation) {
		
		return null;
	}

	public EventLabelRelation getEventLabelRelationById(String eventId, String labelId) {
		
		return null;
	}

	public List<EventLabelRelation> allEventLabelRelation() {
		
		return null;
	}

	public List<EventLabelRelation> getEventLabelRelationByEventId(String eventId) {
		
		return null;
	}

	public List<EventLabelRelation> getEventLabelRelationByLabelId(String labelId) {
		
		return null;
	}

	public EventLabelRelation updateEventLabelRelationById(String eventId, String labelId,
			EventLabelRelation relation) {
		
		return null;
	}

}
