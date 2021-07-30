-- 默认执行time等随机命令后，禁止再执行写命令
-- 使用redis.replicate_commands()，持久化和主从复制只复制写命令而不是整个lua脚本，避免了随机写入的问题
redis.replicate_commands();
local key = KEYS[1];
-- 时间周期，秒
local period = tonumber(ARGV[1]);
-- 时间周期内产生的令牌个数
local rate = tonumber(ARGV[2]);
-- 令牌桶容量
local capacity = tonumber(ARGV[3]);
-- 需要令牌个数
local count = tonumber(ARGV[4]);
-- 等待超时时间，毫秒
local timeout = tonumber(ARGV[5]);
-- 每隔多少毫秒产生1个令牌
local tokenInterval = math.modf(period * 1000 / rate);
-- 令牌桶过期时间，秒
-- 二倍的填满令牌桶的时间
-- 如果使用一倍的填满时间极端情况下令牌桶过期时是空的
-- 使用二倍的填满时间可以保证令牌桶过期的情况可以初始化为最大容量
local expire = 2 * math.modf(period * capacity / rate);
local times = redis.call('time');
local now = tonumber(times[1]) * 1000 + math.modf(tonumber(times[2]) / 1000);
local tokenValuesLen = redis.call('hlen', key);
-- 如果令牌桶不存在，就初始化成最大容量
if (tokenValuesLen == 0) then
    redis.call('hmset', key, 'storedTokens', capacity, 'lastTime', now);
    redis.call('expire', key, expire);
end ;
local tokenValues = redis.call('hmget', key, 'storedTokens', 'lastTime');
-- 令牌桶中令牌个数
local storedTokens = tonumber(tokenValues[1]);
-- 上次刷新令牌时间
local lastTime = tonumber(tokenValues[2]);
if (now > lastTime) then
    storedTokens = math.min(capacity, storedTokens + math.modf((now - lastTime) / tokenInterval));
    lastTime = now;
end ;
-- 消耗令牌个数
local tokensToSpend = math.min(count, storedTokens);
-- 缺少令牌个数
local waitTokens = count - tokensToSpend;
-- 补充缺少令牌需要的时间，毫秒
local waitMillis = waitTokens * tokenInterval;
-- 实际等待时间
local actualWaitMillis = lastTime + waitMillis - now;
if (actualWaitMillis <= timeout) then
    lastTime = lastTime + waitMillis;
    storedTokens = storedTokens - tokensToSpend;
    redis.call('hmset', key, 'storedTokens', storedTokens, 'lastTime', lastTime);
    redis.call('expire', key, expire);
end ;
return actualWaitMillis;