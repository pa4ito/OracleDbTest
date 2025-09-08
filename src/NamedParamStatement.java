import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NamedParamStatement implements AutoCloseable {

    private final PreparedStatement prepStmt;
    private final List<String> fields = new ArrayList<>();

    public NamedParamStatement(final Connection conn, String sql) throws SQLException {
        int pos;
        while((pos = sql.indexOf(":")) != -1) {
            int end = sql.indexOf(" ", pos);
            if (end == -1) {
                end = sql.length();
            }
            fields.add(sql.substring(pos + 1,end));
            sql = sql.substring(0, pos) + "?" + sql.substring(end);
        }
        prepStmt = conn.prepareStatement(sql);
    }

    public void close() throws SQLException {
        prepStmt.close();
    }
    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }
    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }

    public void setInt(final String name, final int value) throws SQLException {
        prepStmt.setInt(getIndex(name), value);
    }

    public void setString(final String name, final String value) throws SQLException {
        prepStmt.setString(getIndex(name), value);
    }

    private int getIndex(final String name) {
        return fields.indexOf(name) + 1;
    }
}