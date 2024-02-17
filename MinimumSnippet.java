import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
public class MinimumSnippet {

	ArrayList<String> termList = new ArrayList<>();
	ArrayList<String> documentList = new ArrayList<>();
	ArrayList<Integer> termsPosition = new ArrayList<Integer>();
	ArrayList<Integer> matchingTermPos = new ArrayList<Integer>(); 
	int snippetSize = Integer.MAX_VALUE;// constant holding the maximum value an int 
	int snippetStartPos;    
	int snippetEndPos;     
	int count;
	
	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		ArrayList<String> tempDocument = new ArrayList<String>();
		// This makes a copy terms
		Iterator<String> firstIterator = terms.iterator();
		while(firstIterator.hasNext()) { 
			String term =  firstIterator.next(); 
			termList.add(term);
		}
		// This makes a copy of documents
		Iterator<String> secondIterator = document.iterator();
		while(secondIterator.hasNext()) {
			String doc =  secondIterator.next();
			documentList.add(doc);
			tempDocument.add(doc);
		}
		if (terms.size() == 1) {// For a list of one item   
			snippetSize = 1;
			for (int i = 0; i < tempDocument.size(); i++) {
				if (terms.get(0).equals(tempDocument.get(i))) {
					snippetStartPos = i; 
					snippetEndPos = snippetStartPos;					
					termsPosition.add(i);
				}
			}
		} else if (terms.size() > 1) { // For a list of more than one item 
			int tempSnipStartPos;    
			int tempSnipEndPos;   
			int tempSnipSize = 0; 

			while (tempDocument.containsAll(terms)) { 					
				matchingTermPos = new ArrayList<Integer>();			
				for (int i = 0; i < terms.size(); i++) {
					for (int j = 0; j < tempDocument.size(); j++) {
						if (terms.get(i).equals(tempDocument.get(j))) {
							matchingTermPos.add(j);						
						}
					}
				}			 
				termsPosition = new ArrayList<Integer>();
				Collections.sort(matchingTermPos);

				tempSnipStartPos = matchingTermPos.get(0);
				tempSnipEndPos = matchingTermPos.get(matchingTermPos.size() - 1);

				if (tempSnipStartPos > 0) {
					tempSnipSize = tempSnipEndPos - tempSnipStartPos + 1;				 
				} else {
					tempSnipSize = tempSnipEndPos;
				} 
				if (snippetSize > tempSnipSize) {							
					for (String t: terms) {
						termsPosition.add(count + tempDocument.indexOf(t));
					}
					tempDocument.remove(tempSnipStartPos);
					snippetStartPos = count + tempSnipStartPos;
					snippetEndPos = count + tempSnipEndPos;
					snippetSize = tempSnipSize;
				}
				count++;
			}
		} else { // If the document contains no terms
			throw new IllegalArgumentException();
		}
	}
	
  //Returns whether or not all terms were found in the document. 
	public boolean foundAllTerms() {
		if(documentList.containsAll(termList)) {
			return true;
		} 
		return false;
	}
  //Return the starting position of the snippet
	public int getStartingPos() {
		return snippetStartPos; 
	}
  //Return the ending position of the snippet
	public int getEndingPos() {		 
		return snippetEndPos; 
	}
  //Return total number of elements contained in the snippet.
	public int getLength() {	
		return snippetSize; 
	}
	
  //Returns the position of one of the search terms
	public int getPos(int index) {
		return termsPosition.get(index);
	}

}

