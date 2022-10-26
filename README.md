# made3_bd

# block1 
![Screenshot from 2022-10-25 16-59-18](https://user-images.githubusercontent.com/101146022/198083343-a3a80a0e-d80c-40b7-a3f9-48d656cdb03f.png)

#docker compose is in block1/part1 directory

![Screenshot from 2022-10-25 17-09-16](https://user-images.githubusercontent.com/101146022/198083584-906e6b5c-fdd7-4f00-8183-6e1eb72a5ef7.png)
![Screenshot from 2022-10-25 17-23-48](https://user-images.githubusercontent.com/101146022/198083601-b092aed6-7350-4682-8c49-3a9673a5361f.png)

#block2


1
root@776ee02d86b2:/# hdfs dfs -mkdir /das_ist_verzeichnis

2
root@776ee02d86b2:/# hdfs dfs -mkdir /das_ist_verzeichnis/das_ist2

3
after deletion file goes to trash directory.
if we say "-skipTrash" file will be deleted without going to trash.
also each file in trash directory will be deleted after some interval (fs.trash.interval)
by default files will not be deleted within 6 hours.

4
root@776ee02d86b2:/# hdfs dfs -touchz /das_ist_verzeichnis/das_ist2/leer

5
root@776ee02d86b2:/# hdfs dfs -rm -R /das_ist_verzeichnis/das_ist2/leer

6
root@776ee02d86b2:/# hdfs dfs -rm -r /das_ist_verzeichnis/ 



1. copy
(base) yuku@yuku-G771JW:~/made3/3_dl/hadoop/docker-hadoop$ docker cp einige_akte.txt namenode:/
(base) yuku@yuku-G771JW:~/made3/3_dl/hadoop/docker-hadoop$ sudo docker exec -it namenode /bin/bash
root@776ee02d86b2:/# hdfs dfs -put einige_akte.txt /

2. 3. 4. browse
das ist ein textroot@776ee02d86b2:/# hdfs dfs -cat /einige_akte.txt
das ist ein textroot@776ee02d86b2:/# hdfs dfs -tail /einige_akte.txt
root@776ee02d86b2:/# hdfs dfs -head /einige_akte.txt  

5
root@776ee02d86b2:/# hdfs dfs -mkdir /neue_direction
root@776ee02d86b2:/# hdfs dfs -cp /einige_akte.txt /neue_direction

2 replication factor
root@776ee02d86b2:/# hdfs dfs -setrep -w 1  /neue_direction/einige_akte.txt                
Replication 1 set: /neue_direction/einige_akte.txt
Waiting for /neue_direction/einige_akte.txt ...
WARNING: the waiting time may be long for DECREASING the number of replications.
.. done

root@776ee02d86b2:/# hdfs dfs -setrep -w 2  /neue_direction/einige_akte.txt
Replication 2 set: /neue_direction/einige_akte.txt
Waiting for /neue_direction/einige_akte.txt .... done

3 find info 
root@776ee02d86b2:/# hdfs fsck  /neue_direction/einige_akte.txt -files -blocks -locations
Connecting to namenode via http://namenode:9870/fsck?ugi=root&files=1&blocks=1&locations=1&path=%2Fneue_direction%2Feinige_akte.txt
FSCK started by root (auth:SIMPLE) from /172.20.0.5 for path /neue_direction/einige_akte.txt at Wed Oct 26 10:38:27 UTC 2022
/neue_direction/einige_akte.txt 16 bytes, replicated: replication=2, 1 block(s):  OK
0. BP-727990293-172.20.0.2-1666650464286:blk_1073741840_1016 len=16 Live_repl=2  
[DatanodeInfoWithStorage[172.20.0.7:9866,DS-6f33fd6c-2a4c-4491-ab52-2466656d8825,DISK], DatanodeInfoWithStorage[172.20.0.2:9866,DS-4eb2981e-02f5-4a63-b7e9-c7c170f62e14,DISK]]


Status: HEALTHY
 Number of data-nodes:	3
 Number of racks:		1
 Total dirs:			0
 Total symlinks:		0

Replicated Blocks:
 Total size:	16 B
 Total files:	1
 Total blocks (validated):	1 (avg. block size 16 B)
 Minimally replicated blocks:	1 (100.0 %)
 Over-replicated blocks:	0 (0.0 %)
 Under-replicated blocks:	0 (0.0 %)
 Mis-replicated blocks:		0 (0.0 %)
 Default replication factor:	3
 Average block replication:	2.0
 Missing blocks:		0
 Corrupt blocks:		0
 Missing replicas:		0 (0.0 %)

Erasure Coded Block Groups:
 Total size:	0 B
 Total files:	0
 Total block groups (validated):	0
 Minimally erasure-coded block groups:	0
 Over-erasure-coded block groups:	0
 Under-erasure-coded block groups:	0
 Unsatisfactory placement block groups:	0
 Average block group size:	0.0
 Missing block groups:		0
 Corrupt block groups:		0
 Missing internal blocks:	0
FSCK ended at Wed Oct 26 10:38:27 UTC 2022 in 1 milliseconds


The filesystem under path '/neue_direction/einige_akte.txt' is HEALTHY

4 

root@776ee02d86b2:/# hdfs fsck -blockId blk_1073741840     
Connecting to namenode via http://namenode:9870/fsck?ugi=root&blockId=blk_1073741840+&path=%2F
FSCK started by root (auth:SIMPLE) from /172.20.0.5 at Wed Oct 26 11:12:25 UTC 2022

Block Id: blk_1073741840
Block belongs to: /neue_direction/einige_akte.txt
No. of Expected Replica: 2
No. of live Replica: 2
No. of excess Replica: 0
No. of stale Replica: 0
No. of decommissioned Replica: 0
No. of decommissioning Replica: 0
No. of corrupted Replica: 0
Block replica on datanode/rack: 603dcd9a64c3/default-rack is HEALTHY
Block replica on datanode/rack: 3fdb3030e13a/default-rack is HEALTHY
