# Benchmaring of DB Bulk Insert Performance



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