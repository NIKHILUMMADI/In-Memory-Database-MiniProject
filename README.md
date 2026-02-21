Roll no.12
# In-Memory Database with TTL and Thread Safety

This project implements a command-driven in-memory database using Java.
It supports TTL-based expiration, thread safety using synchronization,
and lifecycle control using START and STOP commands.

Commands:
PUT <key> <value> [ttl]
GET <key>
DELETE <key>
STOP
START
EXIT
