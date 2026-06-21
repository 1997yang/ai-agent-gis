
## 相关表
```roomsql
CREATE TABLE chat_sessions (
    id SERIAL PRIMARY KEY,
    created_at timestamp now(),
    session_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    title VARCHAR NOT NULL,
    CONSTRAINT chat_sessions_pkey PRIMARY KEY (id)
);



-- 1. 如果数据库还没有安装 PostGIS，执行此命令（需要超级用户权限）
-- CREATE EXTENSION postgis;

-- 2. 创建 POI（兴趣点）空间地理实体表
CREATE TABLE poi_locations (
    id VARCHAR(255) PRIMARY KEY DEFAULT gen_random_uuid(),
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    -- 使用 PostGIS 的 geometry 类型存储点坐标，SRID 4326 代表常见的 WGS84 经纬度坐标系
    geom geometry(Point, 4326),
    CONSTRAINT poi_locations_pkey PRIMARY KEY (id)
);

-- 3. 为空间字段创建 GIST 空间索引，大幅提升“查询周边（ST_DWithin）”等空间计算的检索性能
CREATE INDEX idx_poi_locations_geom ON poi_locations USING GIST (geom);

-- 插入测试数据：北京市中心附近的几个设施点
-- 假设经度约为 116.4，纬度约为 39.9
INSERT INTO poi_locations (name, category, geom) 
VALUES 
('天安门加油站', '加油站', ST_SetSRID(ST_MakePoint(116.3979, 39.9087), 4326)),
('故宫停车场', '停车场', ST_SetSRID(ST_MakePoint(116.3913, 39.9147), 4326)),
('王府井便利店', '便利店', ST_SetSRID(ST_MakePoint(116.4124, 39.9121), 4326));


select * from poi_locations

-- 1. 清理可能存在的旧表
DROP TABLE IF EXISTS SPRING_AI_CHAT_MEMORY;

CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- 2. 重建表结构：将 id 设置为主键，并使用 uuid_generate_v4() 或随机 UUID 默认值
CREATE TABLE SPRING_AI_CHAT_MEMORY (
    id VARCHAR(255) PRIMARY KEY DEFAULT gen_random_uuid(), -- 核心修改点：若未传 id，自动补全随机 UUID
    conversation_id VARCHAR(255) NOT NULL,
    "timestamp" TIMESTAMP NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    metadata TEXT
);

-- 3. 创建会话检索索引
CREATE INDEX idx_chat_memory_conv_id ON SPRING_AI_CHAT_MEMORY(conversation_id);


SELECT * FROM SPRING_AI_CHAT_MEMORY

SELECT
        id, name, category,
        ST_X(geom) AS longitude,
        ST_Y(geom) AS latitude
        FROM poi_locations
        WHERE ST_DWithin(
        geom,
        ST_SetSRID(ST_MakePoint(116.3979, 39.9087), 4326),
        10000 / 111320 -- 粗略将米转换为度，精确计算可使用 geography 类型
        )
        LIMIT 10;
```