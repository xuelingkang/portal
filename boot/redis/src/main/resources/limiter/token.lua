local key = KEYS[1];
-- 时间周期，秒
local period = tonumber(ARGV[1]);
-- 时间周期内产生的令牌个数
local rate = tonumber(ARGV[2]);
-- 令牌桶容量
local capacity = tonumber(ARGV[3]);
-- 需要令牌个数
local count = tonumber(ARGV[4]);
-- 当前毫秒时间戳
local now = tonumber(ARGV[5]);
-- 每隔多少毫秒产生1个令牌
local tokenInterval = period * 1000 / rate;
-- 令牌桶过期时间，秒
-- 填满令牌桶的时间，保证令牌桶过期的情况可以初始化为最大容量
local expire = math.ceil(period * capacity / rate);
local tokenValues = redis.call('hmget', key, 'storedTokens', 'lastTime');
-- 令牌桶中令牌个数
local storedTokens = tonumber(tokenValues[1]);
-- 上次刷新令牌时间
local lastTime = tonumber(tokenValues[2]);
if (storedTokens == nil or lastTime == nil) then
    storedTokens = capacity;
    lastTime = now;
end ;
-- 刷新令牌
storedTokens = math.min(capacity, storedTokens + math.modf((now - lastTime) / tokenInterval));
local allow = storedTokens >= count;
-- 如果令牌足够，刷新令牌桶
if (allow) then
    storedTokens = storedTokens - count;
    lastTime = now;
    redis.call('hmset', key, 'storedTokens', storedTokens, 'lastTime', lastTime);
    redis.call('expire', key, expire);
end ;
return allow;