package org.mohit.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mohit.beans.Account;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFileUsingSaxparser extends DefaultHandler {

    private Account acct;
    private String temp;
    private ArrayList<Account> accList = new ArrayList<Account>();

    /** The main method sets things up for parsing */
    public static void main(String[] args) throws IOException, SAXException,
                  ParserConfigurationException {
          
    	//Test comment
    	
           //Create a "parser factory" for creating SAX parsers
           SAXParserFactory spfac = SAXParserFactory.newInstance();

           //Now use the parser factory to create a SAXParser object
           SAXParser sp = spfac.newSAXParser();

           //Create an instance of this class; it defines all the handler methods
           ReadXMLFileUsingSaxparser handler = new ReadXMLFileUsingSaxparser();

           //Finally, tell the parser to parse the input and notify the handler
           sp.parse("bank.xml", handler);
          
           handler.readList();

    }


    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
           temp = new String(buffer, start, length);
    }
   

    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
    public void startElement(String uri, String localName,
                  String qName, Attributes attributes) throws SAXException {
           temp = "";
           if (qName.equalsIgnoreCase("Account")) {
                  acct = new Account();
                  acct.setType(attributes.getValue("type"));

           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {

           if (qName.equalsIgnoreCase("Account")) {
                  // add it to the list
                  accList.add(acct);

           } else if (qName.equalsIgnoreCase("Name")) {
                  acct.setName(temp);
           } else if (qName.equalsIgnoreCase("Id")) {
                  acct.setId(Integer.parseInt(temp));
           } else if (qName.equalsIgnoreCase("Amt")) {
                  acct.setAmt(Integer.parseInt(temp));
           }

    }

    private void readList() {
           System.out.println("No of  the accounts in bank '" + accList.size()  + "'.");
           Iterator<Account> it = accList.iterator();
           while (it.hasNext()) {
                  System.out.println(it.next().toString());
           }
    }
   
}


