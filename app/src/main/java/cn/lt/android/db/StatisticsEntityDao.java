package cn.lt.android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STATISTICS_ENTITY".
*/
public class StatisticsEntityDao extends AbstractDao<StatisticsEntity, Long> {

    public static final String TABLENAME = "STATISTICS_ENTITY";

    /**
     * Properties of entity StatisticsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MGameID = new Property(1, String.class, "mGameID", false, "M_GAME_ID");
        public final static Property MPkgName = new Property(2, String.class, "mPkgName", false, "M_PKG_NAME");
        public final static Property MActionType = new Property(3, String.class, "mActionType", false, "M_ACTION_TYPE");
        public final static Property MDownloadType = new Property(4, String.class, "mDownloadType", false, "M_DOWNLOAD_TYPE");
        public final static Property MRemark = new Property(5, String.class, "mRemark", false, "M_REMARK");
        public final static Property MPage = new Property(6, String.class, "mPage", false, "M_PAGE");
        public final static Property MPageID = new Property(7, String.class, "mPageID", false, "M_PAGE_ID");
        public final static Property MDownloadMode = new Property(8, String.class, "mDownloadMode", false, "M_DOWNLOAD_MODE");
        public final static Property MInstallMode = new Property(9, String.class, "mInstallMode", false, "M_INSTALL_MODE");
        public final static Property MInstallWay = new Property(10, String.class, "mInstallWay", false, "M_INSTALL_WAY");
        public final static Property MAdType = new Property(11, String.class, "mAdType", false, "M_AD_TYPE");
        public final static Property MCount = new Property(12, Integer.class, "mCount", false, "M_COUNT");
        public final static Property MTimeMillis = new Property(13, Double.class, "mTimeMillis", false, "M_TIME_MILLIS");
        public final static Property From_page = new Property(14, String.class, "from_page", false, "FROM_PAGE");
        public final static Property Resource_type = new Property(15, String.class, "resource_type", false, "RESOURCE_TYPE");
        public final static Property Word = new Property(16, String.class, "word", false, "WORD");
        public final static Property From_id = new Property(17, String.class, "from_id", false, "FROM_ID");
        public final static Property P1 = new Property(18, Integer.class, "P1", false, "P1");
        public final static Property P2 = new Property(19, Integer.class, "P2", false, "P2");
    };


    public StatisticsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public StatisticsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STATISTICS_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"M_GAME_ID\" TEXT," + // 1: mGameID
                "\"M_PKG_NAME\" TEXT," + // 2: mPkgName
                "\"M_ACTION_TYPE\" TEXT," + // 3: mActionType
                "\"M_DOWNLOAD_TYPE\" TEXT," + // 4: mDownloadType
                "\"M_REMARK\" TEXT," + // 5: mRemark
                "\"M_PAGE\" TEXT," + // 6: mPage
                "\"M_PAGE_ID\" TEXT," + // 7: mPageID
                "\"M_DOWNLOAD_MODE\" TEXT," + // 8: mDownloadMode
                "\"M_INSTALL_MODE\" TEXT," + // 9: mInstallMode
                "\"M_INSTALL_WAY\" TEXT," + // 10: mInstallWay
                "\"M_AD_TYPE\" TEXT," + // 11: mAdType
                "\"M_COUNT\" INTEGER," + // 12: mCount
                "\"M_TIME_MILLIS\" REAL," + // 13: mTimeMillis
                "\"FROM_PAGE\" TEXT," + // 14: from_page
                "\"RESOURCE_TYPE\" TEXT," + // 15: resource_type
                "\"WORD\" TEXT," + // 16: word
                "\"FROM_ID\" TEXT," + // 17: from_id
                "\"P1\" INTEGER," + // 18: P1
                "\"P2\" INTEGER);"); // 19: P2
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STATISTICS_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, StatisticsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mGameID = entity.getMGameID();
        if (mGameID != null) {
            stmt.bindString(2, mGameID);
        }
 
        String mPkgName = entity.getMPkgName();
        if (mPkgName != null) {
            stmt.bindString(3, mPkgName);
        }
 
        String mActionType = entity.getMActionType();
        if (mActionType != null) {
            stmt.bindString(4, mActionType);
        }
 
        String mDownloadType = entity.getMDownloadType();
        if (mDownloadType != null) {
            stmt.bindString(5, mDownloadType);
        }
 
        String mRemark = entity.getMRemark();
        if (mRemark != null) {
            stmt.bindString(6, mRemark);
        }
 
        String mPage = entity.getMPage();
        if (mPage != null) {
            stmt.bindString(7, mPage);
        }
 
        String mPageID = entity.getMPageID();
        if (mPageID != null) {
            stmt.bindString(8, mPageID);
        }
 
        String mDownloadMode = entity.getMDownloadMode();
        if (mDownloadMode != null) {
            stmt.bindString(9, mDownloadMode);
        }
 
        String mInstallMode = entity.getMInstallMode();
        if (mInstallMode != null) {
            stmt.bindString(10, mInstallMode);
        }
 
        String mInstallWay = entity.getMInstallWay();
        if (mInstallWay != null) {
            stmt.bindString(11, mInstallWay);
        }
 
        String mAdType = entity.getMAdType();
        if (mAdType != null) {
            stmt.bindString(12, mAdType);
        }
 
        Integer mCount = entity.getMCount();
        if (mCount != null) {
            stmt.bindLong(13, mCount);
        }
 
        Double mTimeMillis = entity.getMTimeMillis();
        if (mTimeMillis != null) {
            stmt.bindDouble(14, mTimeMillis);
        }
 
        String from_page = entity.getFrom_page();
        if (from_page != null) {
            stmt.bindString(15, from_page);
        }
 
        String resource_type = entity.getResource_type();
        if (resource_type != null) {
            stmt.bindString(16, resource_type);
        }
 
        String word = entity.getWord();
        if (word != null) {
            stmt.bindString(17, word);
        }
 
        String from_id = entity.getFrom_id();
        if (from_id != null) {
            stmt.bindString(18, from_id);
        }
 
        Integer P1 = entity.getP1();
        if (P1 != null) {
            stmt.bindLong(19, P1);
        }
 
        Integer P2 = entity.getP2();
        if (P2 != null) {
            stmt.bindLong(20, P2);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public StatisticsEntity readEntity(Cursor cursor, int offset) {
        StatisticsEntity entity = new StatisticsEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mGameID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mPkgName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mActionType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mDownloadType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mRemark
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mPage
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // mPageID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mDownloadMode
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // mInstallMode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // mInstallWay
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // mAdType
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // mCount
            cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13), // mTimeMillis
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // from_page
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // resource_type
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // word
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // from_id
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // P1
            cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19) // P2
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, StatisticsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMGameID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMPkgName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMActionType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMDownloadType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMRemark(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMPage(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMPageID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMDownloadMode(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMInstallMode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMInstallWay(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setMAdType(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMCount(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setMTimeMillis(cursor.isNull(offset + 13) ? null : cursor.getDouble(offset + 13));
        entity.setFrom_page(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setResource_type(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setWord(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFrom_id(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setP1(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setP2(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(StatisticsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(StatisticsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
