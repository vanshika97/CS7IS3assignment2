# News Article Collection Lucene SE

A search engine built upon the News Article Collection for  **"CS7IS3 INFORMATION RETRIEVAL AND WEB SEARCH CA 2**. 
Read Report - [here] (still to do)

1. Financial Times Limited (1991, 1992, 1993, 1994): ft ;
2. Federal Register (1994): fr94 ;
3. Foreign Broadcast Information Service (1996): fbis ;
4. Los Angeles Times (1989, 1990): latimes.

Ran similarities
- tfidf
- boolean
- bm25 

And a CustomAnalyzer

## Getting Started

- Run the following commands on ubuntu terminal or an windows via cmd as adminstrator

**To enter the system**

| Instance 1: MultiSim  |  Instance 2: BM25  |
| ------------- |:-------------:|
| $ ssh cs7is3@ec2-54-165-243-242.compute-1.amazonaws.com  | $ ssh vanshika@ec2-3-94-92-198.compute-1.amazonaws.com |
| Enter password: cs7is3  | Enter password:  vanshika  |

### Building the code

```sh
$ cd CS7IS3assignment2/
$ mvn clean
$ mvn package
```
### Running the code

```sh
$ java -jar target/NewsSearchEngine-1.0-SNAPSHOT.jar
```
### Evaluationg the results

```sh
$ cd src/trec_eval-9.0.7/
$ make
$ ./trec_eval ../qrels ../search_results.txt 
```

# Results

|                |EnglishAnalyzer                          |CustomAnalyzer                         |
|----------------|-------------------------------|-----------------------------|
|boolean          | 0.0573            | 0.090          |
|bm25          |0.3140|**0.3220**|  
|Multi          |0.3040|0.3190|


As can be seen bm25 provides the best results along with the CustomAnalyzer.
