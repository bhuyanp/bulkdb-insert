# Benchmarking of Bulk DB Insert Performance
This project gives performace benchmarking of insert performance between MySQL and MongoDB. Sample size is 3k user records
with first name, last name and email. No custom connection pool or threadpool used for this test. CompletableFuture uses ForkJoinThreadPool internally which was 
used in this case for parallism. 

### Getting Started

#### Running Mongo and MySQL
<code>
docker compose up -d
</code>


#### Running The Application
<code>
mvn clean install
</code>

#### Sample Feed File
[MOCK_DATA.csv](src%2Fmain%2Fresources%2FMOCK_DATA.csv)

#### Upload Users
<code>
curl -F files=@src/main/resources/MOCK_DATA.csv  http://localhost:8080/user
</code>

You may upload the file multiple times to put more load on the application.

<code>
curl -F files=@src/main/resources/MOCK_DATA.csv -F files=@src/main/resources/MOCK_DATA.csv -F files=@src/main/resources/MOCK_DATA.csv http://localhost:8080/user
</code>

#### Changing Upload Mode
[application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml)<br/><br/>
<code>
##chose from bulk,single, singleparallel
bulkupload:
  insert:
    mode: bulk
</code>

### MySQL

#### userRepo.saveAll(users)
Time taken(secs): 2.266074735<br/>
Time taken(secs): 1.61492539<br/>
Time taken(secs): 1.351571491<br/>
Time taken(secs): 1.313490154<br/>
Time taken(secs): 1.265827431<br/>

#### users.forEach(userRepo::save)
Time taken(secs): 12.249369701<br/>
Time taken(secs): 10.262223861<br/>
Time taken(secs): 10.303472583<br/>
Time taken(secs): 10.560121837<br/>
Time taken(secs): 10.057869371<br/>

#### With Completable Future:
Time taken(secs): 2.788977373<br/>
Time taken(secs): 2.185201583<br/>
Time taken(secs): 2.077511226<br/>
Time taken(secs): 2.176857445<br/>
Time taken(secs): 2.183359848<br/>

### MongoDB
#### userRepo.saveAll(users)
Time taken(secs): 0.337762115<br/>
Time taken(secs): 0.120887507<br/>
Time taken(secs): 0.096094173<br/>
Time taken(secs): 0.07291179<br/>
Time taken(secs): 0.062028932<br/>

#### users.forEach(userRepo::save)
Time taken(secs): 2.467483886<br/>
Time taken(secs): 1.581889197<br/>
Time taken(secs): 1.397484578<br/>
Time taken(secs): 1.217457071<br/>
Time taken(secs): 1.245396296<br/>

#### With Completable Future:
Time taken(secs): 0.774913251<br/>
Time taken(secs): 0.483999768<br/>
Time taken(secs): 0.359407201<br/>
Time taken(secs): 0.331147394<br/>
Time taken(secs): 0.372126806<br/>

### Conclusion
For MySQL, the best performance comes from bulk insert with saveAll. However there may be cases
where we need to insert one by one along with insert into related tables and transaction management. In those cases MySQL performance drop significantlly
if we insert using single thread. Using CompletableFuture improves the performance of single records entry drastically but still behind bulk insert.

MongoDB has far better insert performance over MySQL. All benchmark tests produced far better result than MySQL. Interestingly enough, single record entry in a loop
using single thread also performed relatively well as compared to MySQL where we saw huge spike. 