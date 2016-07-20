package ru.surf.nikita_makarov.githubtrends.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.surf.nikita_makarov.githubtrends.R;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "github_trends.db";
    private static final String unableToCreateString = "Unable to create databases";
    private static final String unableToUpgradeString = "Unable to upgrade database from version ";
    private static final String toNewString = " to new ";
    private static final int DATABASE_VERSION = 1;

    private Dao<RepositoryDetails, Integer> repositoryDao;
    private Dao<UserDetails, Integer> userDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RepositoryDetails.class);
            TableUtils.createTable(connectionSource, UserDetails.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), unableToCreateString, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            TableUtils.dropTable(connectionSource, RepositoryDetails.class, true);
            TableUtils.dropTable(connectionSource, UserDetails.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), unableToUpgradeString + oldVer + toNewString
                    + newVer, e);
        }
    }

    public Dao<RepositoryDetails, Integer> getRepositoryDao() throws SQLException {
        if (repositoryDao == null) {
            repositoryDao = getDao(RepositoryDetails.class);
        }
        return repositoryDao;
    }

    public Dao<UserDetails, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(UserDetails.class);
        }
        return userDao;
    }


}
