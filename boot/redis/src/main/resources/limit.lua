-- 漏桶算法限流
local c;
c = redis.call('get', KEYS[1]);
if (c and tonumber(c) > tonumber(ARGV[2])) then
    return c;
end ;
c = redis.call('incr', KEYS[1]);
if (tonumber(c) == 1) then
    redis.call('expire', KEYS[1], ARGV[1]);
end ;
return c;