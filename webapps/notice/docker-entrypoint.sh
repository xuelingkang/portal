#!/bin/bash
set -e

if [ "$(id -u)" -eq 0 ]; then
  if ! grep -E "^portal" /etc/group > /dev/null 2>&1; then
    groupadd portal
  fi

  if ! grep -E "^portal" /etc/passwd > /dev/null 2>&1; then
    useradd -d /portal -g portal portal
  fi

  uid=$(id -u portal)
  gid=$(id -g portal)

  chown -R "${uid}:${gid}" /app
  chown -R "${uid}:${gid}" /var/log/portal/notice
  chmod 777 /tmp

  exec gosu "${uid}:${gid}" "$@"
fi

exec "$@"

