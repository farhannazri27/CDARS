package com.onsemi.cdars.dao;

import com.onsemi.cdars.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.cdars.model.CardPairing;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardPairingDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardPairingDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public CardPairingDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertCardPairing(CardPairing cardPairing) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_card_pairing (pair_id, type, load_card, program_card, created_by, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, cardPairing.getPairId());
            ps.setString(2, cardPairing.getType());
            ps.setString(3, cardPairing.getLoadCard());
            ps.setString(4, cardPairing.getProgramCard());
            ps.setString(5, cardPairing.getCreatedBy());
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

    public QueryResult insertCardPairingFromCsv(CardPairing cardPairing) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_card_pairing (pair_id, type, load_card, program_card, created_by, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, cardPairing.getPairId());
            ps.setString(2, cardPairing.getType());
            ps.setString(3, cardPairing.getLoadCard());
            ps.setString(4, cardPairing.getProgramCard());
            ps.setString(5, cardPairing.getCreatedBy());
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

    public QueryResult updateCardPairing(CardPairing cardPairing) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_card_pairing SET pair_id = ?, type = ?, load_card = ?, program_card = ? WHERE id = ?"
            );
            ps.setString(1, cardPairing.getPairId());
            ps.setString(2, cardPairing.getType());
            ps.setString(3, cardPairing.getLoadCard());
            ps.setString(4, cardPairing.getProgramCard());
            ps.setString(5, cardPairing.getId());
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

    public QueryResult deleteCardPairing(String cardPairingId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_card_pairing WHERE id = '" + cardPairingId + "'"
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

    public CardPairing getCardPairing(String cardPairingId) {
        String sql = "SELECT * FROM cdars_card_pairing WHERE id = '" + cardPairingId + "'";
        CardPairing cardPairing = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardPairing = new CardPairing();
                cardPairing.setId(rs.getString("id"));
                cardPairing.setPairId(rs.getString("pair_id"));
                cardPairing.setType(rs.getString("type"));
                cardPairing.setLoadCard(rs.getString("load_card"));
                cardPairing.setProgramCard(rs.getString("program_card"));
                cardPairing.setCreatedBy(rs.getString("created_by"));
                cardPairing.setCreatedDate(rs.getString("created_date"));
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
        return cardPairing;
    }

    public CardPairing getCardPairingWithLoadCardSingle(String loadCard) {
        String sql = "SELECT * FROM cdars_card_pairing WHERE load_card = '" + loadCard + "' AND type = 'SINGLE'";
        CardPairing cardPairing = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardPairing = new CardPairing();
                cardPairing.setId(rs.getString("id"));
                cardPairing.setPairId(rs.getString("pair_id"));
                cardPairing.setType(rs.getString("type"));
                cardPairing.setLoadCard(rs.getString("load_card"));
                cardPairing.setProgramCard(rs.getString("program_card"));
                cardPairing.setCreatedBy(rs.getString("created_by"));
                cardPairing.setCreatedDate(rs.getString("created_date"));
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
        return cardPairing;
    }

    public CardPairing getCardPairingWithProgramCardSingle(String programCard) {
        String sql = "SELECT * FROM cdars_card_pairing WHERE program_card = '" + programCard + "' AND type = 'SINGLE'";
        CardPairing cardPairing = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardPairing = new CardPairing();
                cardPairing.setId(rs.getString("id"));
                cardPairing.setPairId(rs.getString("pair_id"));
                cardPairing.setType(rs.getString("type"));
                cardPairing.setLoadCard(rs.getString("load_card"));
                cardPairing.setProgramCard(rs.getString("program_card"));
                cardPairing.setCreatedBy(rs.getString("created_by"));
                cardPairing.setCreatedDate(rs.getString("created_date"));
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
        return cardPairing;
    }

    public CardPairing getCardPairingWithLoadCardProgramCardPair(String loadCard, String programCard) {
        String sql = "SELECT * FROM cdars_card_pairing WHERE load_card = '" + loadCard + "' AND program_card = '" + programCard + "' AND type = 'PAIR'";
        CardPairing cardPairing = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardPairing = new CardPairing();
                cardPairing.setId(rs.getString("id"));
                cardPairing.setPairId(rs.getString("pair_id"));
                cardPairing.setType(rs.getString("type"));
                cardPairing.setLoadCard(rs.getString("load_card"));
                cardPairing.setProgramCard(rs.getString("program_card"));
                cardPairing.setCreatedBy(rs.getString("created_by"));
                cardPairing.setCreatedDate(rs.getString("created_date"));
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
        return cardPairing;
    }

    public List<CardPairing> getCardPairingList() {
        String sql = "SELECT * FROM cdars_card_pairing ORDER BY id ASC";
        List<CardPairing> cardPairingList = new ArrayList<CardPairing>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            CardPairing cardPairing;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardPairing = new CardPairing();
                cardPairing.setId(rs.getString("id"));
                cardPairing.setPairId(rs.getString("pair_id"));
                cardPairing.setType(rs.getString("type"));
                cardPairing.setLoadCard(rs.getString("load_card"));
                cardPairing.setProgramCard(rs.getString("program_card"));
                cardPairing.setCreatedBy(rs.getString("created_by"));
                cardPairing.setCreatedDate(rs.getString("created_date"));
                cardPairingList.add(cardPairing);
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
        return cardPairingList;
    }

    public String getNextPairId() {
        String sql = "SELECT LPAD(IFNULL(MAX(m.pair_id)+1, CONCAT('00001')),5,'0') AS code "
                + "FROM cdars_card_pairing m ";
        String code = "";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                code = rs.getString("code");
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
        return code;
    }

    public Integer getCountLoadCard(String loadCard) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE load_card = '" + loadCard + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountLoadCardNEId(String loadCard, String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE load_card = '" + loadCard + "' AND id != '" + id + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountProgramCard(String programCard) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE program_card = '" + programCard + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountProgramCardNEId(String programCard, String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE program_card = '" + programCard + "' AND id !='" + id + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountLoadCardSingle(String loadCard) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE load_card = '" + loadCard + "' AND type = 'SINGLE'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountProgramCardSingle(String loadCard) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE program_card = '" + loadCard + "' AND type = 'SINGLE'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public Integer getCountLoadCardProgramCardPair(String loadCard, String programCard) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_card_pairing WHERE load_card = '" + loadCard + "' AND program_card = '" + programCard + "' AND type = 'PAIR'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }
}
