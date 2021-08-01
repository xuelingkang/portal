local key = KEYS[1];
-- 时间周期，秒
local period = tonumber(ARGV[1]);
-- 时间周期内通过的数据包个数
local rate = tonumber(ARGV[2]);
-- 漏桶容量
local capacity = tonumber(ARGV[3]);
-- 消耗数据包个数
local count = tonumber(ARGV[4]);
-- 等待超时时间，毫秒
local timeout = tonumber(ARGV[5]);
-- 当前毫秒时间戳
local now = tonumber(ARGV[6]);
-- 每隔多少毫秒通过一个数据包
local passInterval = period * 1000 / rate;
-- 漏桶过期时间，秒
-- 排空漏桶的时间，保证漏桶过期的情况可以初始化为一个空桶
local expire = math.ceil(period * capacity / rate);
local leakyValues = redis.call('hmget', key, 'size', 'lastTime');
-- 漏桶中等待流出的数据包个数
local size = tonumber(leakyValues[1]);
-- 上次刷新漏桶的毫秒时间戳
local lastTime = tonumber(leakyValues[2]);
if (size == nil or lastTime == nil) then
    size = 0;
    lastTime = now;
end ;
-- 刷新漏桶
size = math.max(0, size + count - math.modf((now - lastTime) / passInterval));
-- 超过容量，溢出
if (size > capacity) then
    return -1;
end ;
-- 计算等待时间，毫秒
local waitTime = size * passInterval;
if (waitTime > timeout) then
    return waitTime;
end ;
-- 更新漏桶
redis.call('hmset', key, 'size', size, 'lastTime', now);
redis.call('expire', key, expire);
return waitTime;