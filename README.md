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

### Login Credentials (if required in any of the following steps):

```sh
$ Username           : cs7is3 
$ Password           : cs7is3
$ Instance IP Address: ec2-54-165-243-242.compute-1.amazonaws.com

``` 

**To enter the system**

```sh
$ ssh cs7is3@ec2-54-165-243-242.compute-1.amazonaws.com
Enter password: cs7is3
```
### Building the code

```sh
$ cd CS7IS3assignment2/
$ mvn package
```
### Running the code

```sh
$ java -jar 
```
### Evaluationg the results

```sh
$ cd src/trec_eval-9.0.7/
$ make
$ ./trec_eval 
```

# Results

|                |StandardAnalyzer                          |CustomAnalyzer                         |
|----------------|-------------------------------|-----------------------------|
|tfidf|0.1557            | 0.2796           |
|boolean          | 0.1782            | 0.2781            |
|bm25          |0.2864|0.3375|


As can be seen bm25 provides the best results along with the CustomAnalyzer.
