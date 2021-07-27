-- 固定窗口限流
local c;
c = redis.call('get', KEYS[1]);
if (c and tonumber(c) > tonumber(ARGV[2])) then
    return c;
end ;
c = redis.call('incrby', KEYS[1], ARGV[3]);
if (tonumber(c) == ARGV[3]) then
    redis.call('expire', KEYS[1], ARGV[1]);
end ;
return c;