# ip-lab

The ip lab helps to store and analyse login ip and summarize it

e.g. brute force attack analyse

## usage:

- add data in lastb format:

```bash
curl --location 'http://{host}/collect/unformatted' \
--header 'Content-Type: text/plain' \
--data 'root    ssh:notty    192.168.0.1   Tue Jul  2 14:32 - 14:32  (00:00)    
root    ssh:notty    192.168.0.1   Tue Jul  2 14:32 - 14:32  (00:00)    
root     ssh:notty    192.168.0.1   Tue Jul  2 14:10 - 14:10  (00:00)    
'
```

- get top 10 ip in hosts.deny format:

```bash
curl --location 'http://{host}/info/blacklist'
```