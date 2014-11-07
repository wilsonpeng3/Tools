package misc;

public class JDBCManager {
    private String driverName = "";
    private String connStr = "";
    private String userName = "";
    private String passwd = "";

    public JDBCManager(String driverName, String connStr, String userName, String passwd) {
        this.driverName = driverName;
        this.connStr = connStr;
        this.userName = userName;
        this.passwd = passwd;
    }

}
