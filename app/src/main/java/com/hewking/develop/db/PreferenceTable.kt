package com.hewking.develop.db

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/3/18 17:00
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
object PreferenceTable {

    var schemaVersion by Preference(GreenDaoUpgradeHelper.SCHEMA_VERSION,0)

}