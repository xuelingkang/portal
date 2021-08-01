-- 固定窗口计数器
local key = KEYS[1];
local period = tonumber(ARGV[1]);
local rate = tonumber(ARGV[2]);
local count = tonumber(ARGV[3]);
local current = tonumber(redis.call('get', KEYS[1]));
local init = current == nil;
if (init) then
    current = 0;
end ;
current = current + count;
if (current > rate) then
    return current;
end ;
current = redis.call('incrby', key, count);
if (init) then
    redis.call('expire', key, period);
end ;
return current;