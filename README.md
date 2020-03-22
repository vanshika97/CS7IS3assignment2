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

# Running Project
Grant Permission to bash script to automatically unzip trec_eval.zip, build java lucene project, and trecEval 
```
git clone https://github.com/QUzair/LuceneSE.git

chmod u+x trecEval.sh

./trecEval.sh
```


# Files In Project

Main Classes:
- News Model
	> Basic model for field in cranfield doc (id,title,author,biblio,content)
- Main
	> Main class which indexes and searches with different analyzers and similarity classes
	
Within Collections folder:
- fr94 - federal registry
	> Contains 1400 documents from the Cranfield Collection.
	
 Output/Other files:
- trecEval.sh
	> Bash Script to unzip and make trec_eval.zip, build java lucene project, and run trecEval on the outputted similarityFiles (contains 'DocRanks') and QRelsCorrectedforTRECeval
- stopWords.txt
	> List of stopwords taken from [https://www.ranks.nl/stopwords](https://www.ranks.nl/stopwords) 


## Custom Analyzer

Basic Custom analyzer with  stopwords taken from [https://www.ranks.nl/stopwords](https://www.ranks.nl/stopwords) 
```java
//Creating New Token Stream  
TokenStream tokenStream = new LowerCaseFilter(source);  
  
//Adding Filters  
tokenStream = new EnglishPossessiveFilter(tokenStream);  
tokenStream = new PorterStemFilter(tokenStream);  
tokenStream = new EnglishMinimalStemFilter(tokenStream);  
tokenStream = new KStemFilter(tokenStream);  
  
  
CharArraySet newStopSet = null;  
try {  
    newStopSet = StopFilter.makeStopSet(getStopWords()); //Set of Words from ranks.nl/stopwords
} catch (IOException e) {  
    e.printStackTrace();  
}  
tokenStream = new StopFilter(tokenStream, newStopSet);  
return new TokenStreamComponents(source, tokenStream);
```
# Results

|                |StandardAnalyzer                          |CustomAnalyzer                         |
|----------------|-------------------------------|-----------------------------|
|tfidf|0.1557            | 0.2796           |
|boolean          | 0.1782            | 0.2781            |
|bm25          |0.2864|0.3375|


As can be seen bm25 provides the best results along with the CustomAnalyzer.
