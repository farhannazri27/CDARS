package com.onsemi.cdars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.model.HardwareQueue;
import com.onsemi.cdars.tools.QueryResult;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwareQueueDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareQueueDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public HardwareQueueDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertHardwareQueue(HardwareQueue hardwareQueue) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_hardware_queue (code, name, start_date, created_by, created_time) VALUES (?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, hardwareQueue.getCode());
            ps.setString(2, hardwareQueue.getName());
            ps.setString(3, hardwareQueue.getStartDate());
            ps.setString(4, hardwareQueue.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult updateHardwareQueue(HardwareQueue hardwareQueue) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_hardware_queue SET code = ?, name = ?, start_date = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, hardwareQueue.getCode());
            ps.setString(2, hardwareQueue.getName());
            ps.setString(3, hardwareQueue.getStartDate());
            ps.setString(4, hardwareQueue.getModifiedBy());
            ps.setString(5, hardwareQueue.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult deleteHardwareQueue(String hardwareQueueId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_hardware_queue WHERE id = '" + hardwareQueueId + "'"
            );
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public HardwareQueue getHardwareQueue(String hardwareQueueId) {
        String sql = "SELECT *, DATE_FORMAT(start_date,'%Y-%m-%d') AS new_start_date, DATE_FORMAT(start_date,'%d %M %Y') AS start_date_view FROM cdars_hardware_queue WHERE id = '" + hardwareQueueId + "'";
        HardwareQueue hardwareQueue = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwareQueue = new HardwareQueue();
                hardwareQueue.setId(rs.getString("id"));
                hardwareQueue.setCode(rs.getString("code"));
                hardwareQueue.setName(rs.getString("name"));
                hardwareQueue.setStartDate(rs.getString("new_start_date"));
                hardwareQueue.setCreatedBy(rs.getString("created_by"));
                hardwareQueue.setCreatedTime(rs.getString("created_time"));
                hardwareQueue.setModifiedBy(rs.getString("modified_by"));
                hardwareQueue.setModifiedTime(rs.getString("modified_time"));
                hardwareQueue.setStartDateView(rs.getString("start_date_view"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return hardwareQueue;
    }

    public List<HardwareQueue> getHardwareQueueList() {
        String sql = "SELECT *, DATE_FORMAT(start_date,'%Y-%m-%d') AS new_start_date, DATE_FORMAT(start_date,'%d %M %Y') AS start_date_view FROM cdars_hardware_queue ORDER BY code ASC";
        List<HardwareQueue> hardwareQueueList = new ArrayList<HardwareQueue>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            HardwareQueue hardwareQueue;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwareQueue = new HardwareQueue();
                hardwareQueue.setId(rs.getString("id"));
                hardwareQueue.setCode(rs.getString("code"));
                hardwareQueue.setName(rs.getString("name"));
                hardwareQueue.setStartDate(rs.getString("new_start_date"));
                hardwareQueue.setCreatedBy(rs.getString("created_by"));
                hardwareQueue.setCreatedTime(rs.getString("created_time"));
                hardwareQueue.setModifiedBy(rs.getString("modified_by"));
                hardwareQueue.setModifiedTime(rs.getString("modified_time"));
                hardwareQueue.setStartDateView(rs.getString("start_date_view"));
                hardwareQueueList.add(hardwareQueue);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return hardwareQueueList;
    }

    public List<HardwareQueue> getHardwareQueueList(String hardwareQueueId) {
        String sql = "SELECT *, DATE_FORMAT(start_date,'%Y-%m-%d') AS new_start_date, DATE_FORMAT(start_date,'%d %M %Y') AS start_date_view, IF(id=\"" + hardwareQueueId + "\",\"selected=''\",\"\") AS selected FROM cdars_hardware_queue ORDER BY code ASC";
        List<HardwareQueue> hardwareQueueList = new ArrayList<HardwareQueue>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            HardwareQueue hardwareQueue;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwareQueue = new HardwareQueue();
                hardwareQueue.setId(rs.getString("id"));
                hardwareQueue.setCode(rs.getString("code"));
                hardwareQueue.setName(rs.getString("name"));
                hardwareQueue.setStartDate(rs.getString("new_start_date"));
                hardwareQueue.setCreatedBy(rs.getString("created_by"));
                hardwareQueue.setCreatedTime(rs.getString("created_time"));
                hardwareQueue.setModifiedBy(rs.getString("modified_by"));
                hardwareQueue.setModifiedTime(rs.getString("modified_time"));
                hardwareQueue.setSelected(rs.getString("selected"));
                hardwareQueue.setStartDateView(rs.getString("start_date_view"));
                hardwareQueueList.add(hardwareQueue);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return hardwareQueueList;
    }

    /* DBUtils - START */
    //All DAO should be using DBUtils in future and should format the table and column name according to default ResultSetHandler so it will be less coding
    
    //CRUD
    public QueryResult insert(HardwareQueue hardwareQueue) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "INSERT INTO cdars_hardware_queue (code, name, start_date, created_by, created_time) VALUES (?, ?, ?, ?, NOW())";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Long result = queryRunner.insert(
                    sql, 
                    new ScalarHandler<Long>(), 
                    hardwareQueue.getCode(), 
                    hardwareQueue.getName(), 
                    hardwareQueue.getStartDate(), 
                    hardwareQueue.getCreatedBy()
            );
            queryResult.setResult(result.intValue());
            queryResult.setGeneratedKey(result.toString());
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }
    
    public QueryResult update(HardwareQueue hardwareQueue) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE cdars_hardware_queue SET code = ?, name = ?, start_date = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql, 
                    hardwareQueue.getCode(), 
                    hardwareQueue.getName(), 
                    hardwareQueue.getStartDate(), 
                    hardwareQueue.getModifiedBy(),
                    hardwareQueue.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }
    
    public QueryResult delete(String hardwareQueueId) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "DELETE FROM cdars_hardware_queue WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql, 
                    hardwareQueueId
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }
    
    public HardwareQueue get(String hardwareQueueId) {
        HardwareQueue hardwareQueue = new HardwareQueue();
        String sql = "SELECT *, DATE_FORMAT(start_date, '%d %b %Y') AS new_start_date FROM cdars_hardware_queue WHERE id = ?";
        try {
            ResultSetHandler<HardwareQueue> h = new ResultSetHandler<HardwareQueue>() {
                @Override
                public HardwareQueue handle(ResultSet rs) throws SQLException {
                    HardwareQueue hardwareQueue = new HardwareQueue();
                    while (rs.next()) {
                        hardwareQueue.setId(rs.getString("id"));
                        hardwareQueue.setCode(rs.getString("code"));
                        hardwareQueue.setName(rs.getString("name"));
                        hardwareQueue.setStartDate(rs.getString("new_start_date"));
                        hardwareQueue.setCreatedBy(rs.getString("created_by"));
                        hardwareQueue.setCreatedTime(rs.getString("created_time"));
                        hardwareQueue.setModifiedBy(rs.getString("modified_by"));
                        hardwareQueue.setModifiedTime(rs.getString("modified_time"));
                    }
                    return hardwareQueue;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            hardwareQueue = run.query(sql, h, hardwareQueueId);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return hardwareQueue;
    }
    
    //LISTING
    public List<HardwareQueue> list() {
        return list("");
    }

    public List<HardwareQueue> list(String hardwareQueueId) {
        List<HardwareQueue> list = new ArrayList<HardwareQueue>();
        String sql = "SELECT *, DATE_FORMAT(start_date, '%d %b %Y') AS new_start_date, IF(id = ?, \"selected=''\", '') AS selected FROM cdars_hardware_queue ORDER BY code ASC";
        try {
            //Default ResultSetHandler
            //Database table name and column names need to be in following format:
            //HardwareQueue
            //  - Id
            //  - Code
            //  - Name
            //  - StartDate
            //  - CreatedBy
            //  - CreatedTime
            //  - ModifiedBy
            //  - ModifiedTime
            //ResultSetHandler<List<HardwareQueue>> h = new BeanListHandler<HardwareQueue>(HardwareQueue.class);
            
            //Custom ResultSetHandler
            ResultSetHandler<List<HardwareQueue>> h = new ResultSetHandler<List<HardwareQueue>>() {
                @Override
                public List<HardwareQueue> handle(ResultSet rs) throws SQLException {
                    List<HardwareQueue> list = new ArrayList<HardwareQueue>();
                    while (rs.next()) {
                        HardwareQueue hardwareQueue = new HardwareQueue();
                        hardwareQueue.setId(rs.getString("id"));
                        hardwareQueue.setCode(rs.getString("code"));
                        hardwareQueue.setName(rs.getString("name"));
                        hardwareQueue.setStartDate(rs.getString("new_start_date"));
                        hardwareQueue.setCreatedBy(rs.getString("created_by"));
                        hardwareQueue.setCreatedTime(rs.getString("created_time"));
                        hardwareQueue.setModifiedBy(rs.getString("modified_by"));
                        hardwareQueue.setModifiedTime(rs.getString("modified_time"));
                        hardwareQueue.setSelected(rs.getString("selected"));
                        list.add(hardwareQueue);
                    }
                    return list;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query(sql, h, hardwareQueueId);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return list;
    }
    
    /* DBUtils - END */
}
