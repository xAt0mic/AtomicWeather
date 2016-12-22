package com.fredericborrel.atomicrss.data.dao.ormlite;

import com.fredericborrel.atomicrss.data.dao.RSSItemDao;
import com.fredericborrel.atomicrss.data.model.RSSItem;

/**
 * Created by Frederic on 22/12/2016.
 */

public class RSSItemOrmLiteDao extends BaseOrmLiteDao<RSSItem> implements RSSItemDao{
    @Override
    public Class getObjectType() {
        return RSSItem.class;
    }
}
